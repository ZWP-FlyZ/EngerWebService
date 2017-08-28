package service.app;

import org.junit.Test;

public class StringSpiltTest {

	@Test
	public void test() {
		
			String test = "1:2:3:4:5:6";
			String[] ss = test.split(":");
			System.err.println(ss.length);
			
			for(String s:ss)
				System.err.println(s);
		
		
	}

}
