package com.sumit.programs;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRU {
	
	private ConcurrentLinkedQueue<DataObject> cache =  new ConcurrentLinkedQueue<DataObject>();
	private Map<Integer,DataObject> cacheCounter = new HashMap<Integer,DataObject>(2); //Size of cache = 2
	private static LRU instance = new LRU();
	private LRU() {
		super();
	}
	public static LRU getInstance() {
		return instance;
	}
	
	
	public void put(DataObject object) {
		Object cached = cacheCounter.get(object.hashCode());
		if(cached == null) {
			cacheCounter.put(object.hashCode(), object);
			cache.add(object);
		} else {
			cache.remove(cached);
			cache.add(object);
		}
	}
	
	public DataObject get(int id) {
		DataObject cached = cacheCounter.get(id);	
		return cached;
	}
	
	public static void main(String[] args) {
		DataObject object1 = new DataObject(1, "One");
		LRU.getInstance().put(object1);
		DataObject object2 = new DataObject(2, "Two");
		LRU.getInstance().put(object2);
		DataObject object11 = new DataObject(1, "One");
		LRU.getInstance().put(object11);
		DataObject object3 = new DataObject(3, "Three");
		LRU.getInstance().put(object3);
	}
	

}
class DataObject {
	private int id;
	private String content;
	private Timestamp creationTime;
	private Timestamp lastAccessTime;

	public DataObject(int id, String content) {
		super();
		this.id = id;
		this.content = content;
		creationTime = new Timestamp(System.currentTimeMillis());
		lastAccessTime = new Timestamp(System.currentTimeMillis());
	}
	
	public void markDataAsFetched() {
		lastAccessTime = new Timestamp(System.currentTimeMillis());
	}
	
	@Override
	public String toString() {
		return id+" : "+content+" : "+creationTime+" : "+lastAccessTime;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DataObject) {
			if(this.id == ((DataObject)obj).id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id;
	}
}
