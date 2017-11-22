package service.app;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import service.app.dataservice.LruDataService;


@SpringBootApplication
public class EngerWebServiceApplication implements CommandLineRunner {

	@Autowired
	LruDataService lds;
	
	public static void main(String[] args) {
		
		SpringApplication.run(EngerWebServiceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		lds.preloadCache();
	}
}
