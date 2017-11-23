package service.app;

import java.util.List;

import org.junit.Test;

import service.app.util.TimeTools;

public class TimeToolsTest {

	@Test
	public void test() {
		List<String> tt = TimeTools.getYMlist("2017-07-01:2017-09-310");
		for(String t:tt)
			System.err.println(t);
		System.err.println(TimeTools.compareYMD("2017-09-31", "2017-10-1"));
	}

}
