package service.app;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;



public class ThreadPoolTest {

	ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Test
	public void test()  {
//		Future<String> f1 =  threadPool.submit(c1);
//		Future<String> f2 =  threadPool.submit(c2);
//		Future<String> f3 =  threadPool.submit(c3);
//		
//		try {
//			System.err.println(f3.get());
//			System.err.println(f2.get());
//			System.err.println(f1.get());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
	}
	
	
	Callable<String> c1 = new Callable<String>() {

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			Thread.sleep(3000);
			return "c1:sleep3s";
		}
		
	};
	
	Callable<String> c2 = new Callable<String>() {

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			Thread.sleep(2000);
			return "c2:sleep2s";
		}
		
	};
	
	Callable<String> c3 = new Callable<String>() {

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			Thread.sleep(1000);
			return "c3:sleep1s";
		}
		
	};

}
