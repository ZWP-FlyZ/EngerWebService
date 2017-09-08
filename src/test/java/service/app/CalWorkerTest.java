package service.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import service.app.callables.BaseWork;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalWorkerTest {


	
	@Test
	public void test() {

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
