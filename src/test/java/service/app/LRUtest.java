package service.app;

import org.junit.Test;

import service.app.util.MyLRU;

public class LRUtest {

	@Test
	public void test() {
		
		MyLRU<String,String> lru = new MyLRU<String,String>(2);
		
		lru.printAll();
		
		lru.add("k1", "v1");
		lru.add("k2", "v2");
		lru.printAll();
		lru.add("k3", "v3");
		lru.printAll();
		lru.add("k4", "v4");
		lru.printAll();
		lru.clear();
		lru.printAll();	
	}

}
