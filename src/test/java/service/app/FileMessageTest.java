package service.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import service.app.util.FileStorageUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileMessageTest {

	@Autowired
	FileStorageUtil fs;
	
	
	@Test
	public void test() {

		
		if(fs.setMessageTo("message", "1:2:3:4:5:6")){
			String ms = fs.getMessageFrom("message", "init");
			System.err.println(ms);
		}else
			System.err.println("set err");
		
	}

}
