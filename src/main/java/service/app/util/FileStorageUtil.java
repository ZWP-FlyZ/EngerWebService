package service.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Component
public class FileStorageUtil implements InitializingBean{

	private final Path rootLocation;
	
	private final String helpDocName = "helpDocument.pdf";
	
	
	public FileStorageUtil(){
		rootLocation = Paths.get("helpdoc");
	}
	
    public void store(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(helpDocName);
        
        if (file.isEmpty()) {
            throw new NullPointerException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new NullPointerException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING);
    }
    
    
    public Resource loadAsResource() throws MalformedURLException {
    	String filename = helpDocName;
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            throw new NullPointerException(
                    "Could not read file: " + filename);
        }
    }
    
    
    public Resource loadAsResource(String filename) throws MalformedURLException {
    	
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            throw new NullPointerException(
                    "Could not read file: " + filename);
        }
    }
    
   
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
    
    
   
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    
    
    public String getMessageFrom(String fileName,String defString){
    	if(fileName==null) return defString;
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		try {
			File file = load(fileName).toFile();
			if(!file.exists()||file.isDirectory()) return defString;
			br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file),"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
			      sb.append(line);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return defString;
    }
    

    @Transactional
	public boolean setMessageTo(String fileName,String message){
    	if(fileName==null||message==null) return false;
    	BufferedWriter bw ;
    	try {
    		File file = load(fileName).toFile();
    		file.delete();
    		file.createNewFile();
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file),"UTF-8"));
			bw.write(message);
			bw.flush();
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
			return false;
		}    	
    }
    
    
    

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
        	System.err.println(e.toString());
        	
        }
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		 init();
	}
    
	
	
}
