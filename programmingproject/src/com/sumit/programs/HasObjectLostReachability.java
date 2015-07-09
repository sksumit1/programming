package com.sumit.programs;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class HasObjectLostReachability {

private static Object object;
	
	public static void main(String[] args) {
		
		object = new Object();
		
		// Reference queue, to which registered reference objects are appended by the
		// garbage collector after the appropriate reachability changes are detected.
		//The garbage collector will make it eligible for garbage collection because object only has a week reference pointing to it. 
		//The object has been finalized but yet not GCed
		ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
		
		// Create a new weak reference that refers to the given object and is registered with this queue.
		WeakReference<Object> wr = new WeakReference<Object>(object, rq);
		//NOTE: SoftReference is almost same like weakreference but its only GCed when garbage collector needs to GC to avoid getting Out of Memory

		// start a new thread that will remove all references of object
		new Thread(runnable).start();
		
		// wait for all the references to the object to be removed
		try {
		    while (true) {
		
  Reference<?> r = rq.remove();
		
  if (r == wr) {
		

System.out.println("Object is no longer referenced.");
		
  }
		
  break;
		    }
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				System.out.println("Setting object to null");
				object = null;
				System.out.println("Running Garbage Collection...");
				Runtime.getRuntime().gc(); // run GC to collect the object
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
