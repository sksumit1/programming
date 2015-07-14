package com.sumit.programs.threads;

public class IsRunnableInterrupted implements Runnable {
	
	public void run() {
		System.out.println("Thread execution started");
		//while(!Thread.interrupted()) { //Both are same
		while(!Thread.currentThread().isInterrupted()) {
		}
		System.out.println("Thread Interrupted");
	}
	
	public static void main(String[] args) {
		Thread th = new Thread(new IsRunnableInterrupted());
		th.start();
		th.interrupt();
	}

}
