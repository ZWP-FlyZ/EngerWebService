package service.app;

import org.junit.Test;

import service.app.tramodel.MyAdd;
import service.app.tramodel.TypeData;
import service.app.util.TwoDecMap;

public class TwoDecMapTest {

	TwoDecMap<String, T1> m1 = new TwoDecMap<>();
	TwoDecMap<String, T1> m2 = new TwoDecMap<>();
	TwoDecMap<String, T1> m3 = null;
	
	TwoDecMap<String, TypeData> mt1 = new TwoDecMap<>();
	TwoDecMap<String, TypeData> mt2 = new TwoDecMap<>();
	TwoDecMap<String, TypeData> mt3 = null;
	

	
	@Test
	public void test() {
		//Gson gson = new Gson();
//		m1.put("1", "1", new T1("1_1"));
//		m1.put("2", "1", new T1("2_1"));
//		m1.put("2", "2", new T1("2_2"));
//		m1.put("4", "5", new T1("4_5"));
//		m1.put("6", "6", new T1("6_6"));
//		
//		m2.put("1", "1", new T1("1_1"));
//		m2.put("1", "2", new T1("1_2"));
//		m2.put("3", "3", new T1("3_3"));
//		m2.put("4", "1", new T1("4_1"));
//		m2.put("6", "6", new T1("6_6"));
//		
//		m3 = m1.add(m3);
//		System.err.println(gson.toJson(m3));
//		
//		m3 = m2.add(m3);
//		System.err.println(gson.toJson(m3));
		
//		TypeData td = new TypeData();
//		td.setType("mt1");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt1.put("1", "1", td);
//		
//		td = new TypeData();
//		td.setType("mt2");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt1.put("2", "2", td);
//		
//		td = new TypeData();
//		td.setType("mt3");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt1.put("1", "3", td);
//		
//		td = new TypeData();
//		td.setType("mt3");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt1.put("7", "3", td);
//		
//		
//		
//		td = new TypeData();
//		td.setType("mt1");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt2.put("1", "1", td);
//		
//		td = new TypeData();
//		td.setType("mt2");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt2.put("2", "2", td);
//		
//		td = new TypeData();
//		td.setType("mt2");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt2.put("1", "4", td);
//		
//		td = new TypeData();
//		td.setType("mt3");
//		td.setTypDatOfAllEng(1.0);
//		td.setTypDatOfAllLen(1.0);
//		mt2.put("3", "3", td);
//		
//		mt3 = mt1.add(mt3);
//		System.err.println(gson.toJson(mt3));
//		System.err.println(gson.toJson(mt2));
//		mt3 = mt2.add(mt3);
//		System.err.println(gson.toJson(mt3));
		
		
		
	}
	
	class T1 implements MyAdd{
		String v;
		public T1(String v){
			this.v = v;
		}
		@Override
		public Object add(Object o) {
			if(o==null||!(o instanceof T1)) return this;
			T1 t = (T1) o;
			v = v+" | "+t.v;
			return this;
		}
		
	}
	
	

}
