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
		if(this.checkBST(0,Integer.MIN_VALUE,Integer.MAX_VALUE)) {
			System.out.println("This is a BST");
		} else {
			System.out.println("This is not a BST");
		}
	}
	
	private boolean checkBST(int index,Integer min, Integer max) {
		if(index >= array.length) {
			return true;
		}
		if((min != -1 && array[index] <= min) || (max != -1 && array[index] >= max)) {
			return false;
		}
		if(!(this.checkBST(2*index+1, min, array[index])) || !(this.checkBST(2*index+2, array[index], max))) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		DetermineBSTArray obj = new DetermineBSTArray();
		obj.init();
		obj.takeInput();
		obj.determineBST();
		obj.close();
	}

}
