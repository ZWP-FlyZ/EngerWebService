package service.app;

import static org.junit.Assert.*;

import org.junit.Test;

import service.app.util.TimeTools;

public class TimeToolsTest {

	@Test
	public void test() {
		
		
		for(String s:TimeTools.getTenMuList("2017-01-01 23:43:00&2017-01-02 01:50:00"))
			System.err.println(s);
	}

}
