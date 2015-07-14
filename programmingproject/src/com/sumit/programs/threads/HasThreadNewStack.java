package com.sumit.programs.threads;

public class HasThreadNewStack extends Thread {
	
	public void run() {
		if(Thread.currentThread() != this) {
			System.out.println("Thread Id "+Thread.currentThread().getId()+" Thread is called by invoking run()");
		} else {
			System.out.println("Thread Id "+Thread.currentThread().getId()+" Thread is called by invoking start()");
		}
	}
	
	public static void main(String[] args) {
		HasThreadNewStack thread1 = new HasThreadNewStack();
		System.out.println("Thread Id "+Thread.currentThread().getId()+" Calling with run()");
		thread1.run();
		System.out.println("Thread Id "+Thread.currentThread().getId()+" Calling with start()");
		thread1.start();
	}

}
