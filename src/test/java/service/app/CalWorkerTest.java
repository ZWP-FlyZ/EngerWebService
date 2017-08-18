package service.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import service.app.callables.BaseWork;
import service.app.callables.DataAuthBaseWork;
import service.app.callables.IndexDataWork;
import service.app.model.AllSimData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TwoDecMap;
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
		
//		List<BaseWork<String,String>> works = new ArrayList<>();
//		works.add(new TestWork1("test1"));
//		works.add(new TestWork2("test2"));
//		works.add(new TestWork3("test3"));
//		
//		List<String> re =cw.submitWorkList(works);
//		for(String s:re)
//			System.err.println(s);

		RequestData d1 = new RequestData();
		RequestData d2 = new RequestData();
		RequestData d3 = new RequestData();
		RequestData d4 = new RequestData();
		
		d1.setTargeDay("2017-01-02");
		d1.setRoleType(RoleType.ROLE_TRAFFIC);
		d1.setRoleName(null);
		d1.setPlace1(null);
		d1.setPlace2(null);
		
		d2.setTargeDay("2017-04-07");
		d2.setRoleType(RoleType.ROLE_LAND);
		d2.setRoleName(null);
		d2.setPlace1(null);
		d2.setPlace2(null);
		
		d3.setTargeDay("2017-08-07");
		d3.setRoleType(RoleType.ROLE_LAND);
		d3.setRoleName(null);
		d3.setPlace1(null);
		d3.setPlace2(null);
		
		d4.setTargeDay("2017-01-07");
		d4.setRoleType(RoleType.ROLE_LAND);
		d4.setRoleName(null);
		d4.setPlace1("杭州");
		d4.setPlace2(null);
		
		
		
		
		List<BaseWork<RequestData,List<TwoDecMap<String, ? extends TypeData>>>> 
													works = new ArrayList<>();
		
		works.add(new IndexDataWork(d1));
//		works.add(new IndexDataWork(d2));
//		works.add(new IndexDataWork(d3));
//		works.add(new IndexDataWork(d4));
		
		cw.submitWorkList(works);
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
