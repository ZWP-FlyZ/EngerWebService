package service.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import service.app.callables.BaseWork;
import service.app.worker.CalWorker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalWorkerTest {

	@Autowired
	CalWorker cw;
	
	@Test
	public void test() {
//		System.err.println(cw.submitWork());
//		System.err.println(cw.submitWork();
//		System.err.println(cw.submitWork(new TestWork3("test3")));
		
		List<BaseWork<String,String>> works = new ArrayList<>();
		works.add(new TestWork1("test1"));
		works.add(new TestWork2("test2"));
		works.add(new TestWork3("test3"));
		
		List<String> re =cw.submitWorkList(works);
		for(String s:re)
			System.err.println(s);	
	}
	
	
	class TestWork1 extends BaseWork<String,String>{

		public TestWork1(String data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(1000);
			return "sleep1000  " + data;
		}
		
	}
	
	class TestWork2 extends BaseWork<String,String>{

		public TestWork2(String data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(2000);
			return "sleep2000  "+ data;
		}
		
	}
	
	class TestWork3 extends BaseWork<String,String>{

		public TestWork3(String data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(3000);
			return "sleep3000  "+ data;
		}
		
	}
	
	

}
