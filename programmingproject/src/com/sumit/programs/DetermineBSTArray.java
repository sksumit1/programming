package com.sumit.programs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * Given a array, determine if its a BST
 * For any node i, its child are 2i and 2i+1
 * For any node i, its parent is mod|(i-1)/2|
 */
public class DetermineBSTArray {
	
	BufferedReader reader = null;
	int array[] = null;
	boolean isBST = true;
	
	void init() throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	void close() {
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Eg. : 6 3 9 1 5 7 10 -1 2 4
	void takeInput() {
		try {
			System.out.println("Give input data");
			String[] data = reader.readLine().split(" ");
			array = new int[data.length];
			for (int i = 0; i < data.length; i++) {
				array[i] = Integer.parseInt(data[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void determineBST() {
		this.findmax(0);
		if(this.isBST) {
			System.out.println("This is a BST");
		}
	}
	
	private int findmax(int index) {
		if(index >= array.length) {
			return -1;
		}
		int left = -1 ;
		if((index*2+1) < array.length) {
			left = findmax(index*2+1);
		}
		int right = -1;
		if((index*2+2) < array.length) {
			right = findmax(index*2+2);
		}
		if(left != -1 && left > array[index]) {
			System.out.println("This is not a BST");
			this.isBST = false;
		}
		if(right != -1 && right < array[index]) {
			System.out.println("This is not a BST");
			this.isBST = false;
		}
		if(right != -1) {
			return right;
		} else {
			return array[index];
		}
	}
	
	public static void main(String[] args) throws IOException {
		DetermineBSTArray obj = new DetermineBSTArray();
		obj.init();
		obj.takeInput();
		obj.determineBST();
		obj.close();
	}

}
