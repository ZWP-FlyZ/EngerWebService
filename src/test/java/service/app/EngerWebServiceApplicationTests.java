package service.app;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import service.app.dao.SetDao;
import service.app.util.TypeGetter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EngerWebServiceApplicationTests {

	@Autowired
	TypeGetter tg;
	
	@Autowired
	SetDao sd;

	
	@Test
	public void contextLoads() {
		//String s = tg.getBusTranCarLenType(new Double(7.0));
		//System.err.println(s);
		
		List<String> i = tg.getBusTranCarLenTypeAll();
		for(String s:i) System.err.println(s);
		//sd.reguser(null);
		
		for(String s:tg.getTaxiTranDpTypeAll())
		System.err.println(s);
		
	}

}
