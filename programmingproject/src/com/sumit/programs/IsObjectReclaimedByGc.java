package com.sumit.programs;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class IsObjectReclaimedByGc {

	private static Object object;

	public static void main(String[] args) {
		
		object = new Object();
		
		// Reference queue, to which registered reference objects are appended by the
		// garbage collector after the appropriate object has been deleted.
		//A phantom reference is always associated with a references queue during construction time. 
		//This is because phantom references are enqueued in the queue only when the the object is about to be garbage collected, after the finalize method(if any) has been executed on it. 
		//Calling a get() on the Phantom reference always returns null. And that is quite appropriate because the finalize function has already run on the referent object.
		ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
		
		// Create a new phantom reference that refers to the given object and is registered with this queue.
		PhantomReference<Object> wr = new PhantomReference<Object>(object, rq);

		// start a new thread that will remove all references of object
		new Thread(runnable).start();
		
		// wait for all the references to the object to be removed
		try {
		    while (true) {
		
  Reference<?> r = rq.remove();
		
  if (r == wr) {
		

System.out.println("Object is about to be reclaimed." +
		

		"We clear the referent so that it can be reclaimed.");
		

r.clear();
		
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
