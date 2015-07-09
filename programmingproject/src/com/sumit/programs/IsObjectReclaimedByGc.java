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
		//PhantomReferences avoid a fundamental problem with finalization: finalize() methods can "resurrect" objects by creating new strong references to them. 
		//So what, you say? Well, the problem is that an object which overrides finalize() must now be determined to be garbage in at least two separate garbage collection cycles in order to be collected. 
		//When the first cycle determines that it is garbage, it becomes eligible for finalization. 
		//Because of the (slim, but unfortunately real) possibility that the object was "resurrected" during finalization, the garbage collector has to run again before the object can actually be removed. 
		//And because finalization might not have happened in a timely fashion, an arbitrary number of garbage collection cycles might have happened while the object was waiting for finalization. 
		//This can mean serious delays in actually cleaning up garbage objects, and is why you can get OutOfMemoryErrors even when most of the heap is garbage.
		//With PhantomReference, this situation is impossible -- when a PhantomReference is enqueued, there is absolutely no way to get a pointer to the now-dead object (which is good, because it isn't in memory any longer). 
		//Because PhantomReference cannot be used to resurrect an object, the object can be instantly cleaned up during the first garbage collection cycle in which it is found to be phantomly reachable.
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
