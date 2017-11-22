package service.app;

import java.util.List;

import org.junit.Test;

import service.app.util.TimeTools;

public class PreLoadTest {

	
	
	@Test
	public void test() {
		List<String> tt = TimeTools.getPreLoadYMList();
		for(String t:tt){
			System.err.println(t);
		}
	}

}
