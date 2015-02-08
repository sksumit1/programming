package com.sumit.programs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReturnPairFromBSTArray {
	
	BufferedReader reader = null;
	int array[] = null;
	int sum;
	
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
			System.out.println("Enter sum");
			sum = Integer.parseInt(reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void listPairs() {
		for (int i = 0; i < array.length; i++) {
			this.findPairs(i);
		}
	}
	
	private void findPairs(int index) {
		if(index >= array.length || array[index] == -1) {
			return;
		}
		int target = sum - array[index];
		if(target == 0) {
			System.out.println(array[index]);
		} else if(this.find(target, 0)) {
			System.out.println(array[index]+" "+target);
		}
	}
	
	private boolean find(int value,int indexptr) {
		if(indexptr >= array.length || array[indexptr] == -1) {
			return false;
		} else if(value == array[indexptr]) {
			return true;
		} else  if(value < array[indexptr]) {
			return this.find(value, 2*indexptr+1);
		} else {
			return this.find(value, 2*indexptr+2);
		}
	}
	
	public static void main(String[] args) throws IOException {
		ReturnPairFromBSTArray obj = new ReturnPairFromBSTArray();
		obj.init();
		obj.takeInput();
		obj.listPairs();
		obj.close();
	}

}
