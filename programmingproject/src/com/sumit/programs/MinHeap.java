package com.sumit.programs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Childs of a node i are 2i & 2i+1   -> for array indexes, childs are 2i+1, 2i+2
 * Parent of a node = mod|(i-1)/2|    -> for array indexes, parent is mod|(i-1)/2|
 */
public class MinHeap {
	
	int HEAP_SIZE = 10;
	int heap[] = new int[HEAP_SIZE];
	int currentSize = 0;
	
	void add(int value) {
		if(this.isFull()) {
			System.out.println("Heap is already full !!");
			return;
		}
		int pointer = currentSize;
		currentSize++;
		heap[pointer] = value;
		while(pointer > 0) {
			int parent = heap[(pointer-1)/2];
			int currentValue = heap[pointer];
			if(parent > currentValue) {
				heap[pointer] = parent;
				heap[(pointer-1)/2] = currentValue;
				pointer = (pointer-1)/2;
			} else {
				break;
			}
		}
	}
	
	void pop() {
		if(this.isEmpty()) {
			System.out.println("Heap is empty !!");
			return;
		}
		System.out.println(heap[0]);
		heap[0] = heap[currentSize-1];
		currentSize--;
		int currentPointer = 0;
		while(currentPointer < currentSize) {
			if(((2*currentPointer+1) < currentSize && heap[currentPointer] > heap[2*currentPointer+1]) && ((2*currentPointer+2) < currentSize && heap[currentPointer] > heap[2*currentPointer+2])) {
				//Find the minimum of the two
				if(heap[2*currentPointer+1] < heap[2*currentPointer+2]) {
					int temp = heap[currentPointer];
					heap[currentPointer] = heap[2*currentPointer+1];
					heap[2*currentPointer+1] = temp;
					currentPointer = 2*currentPointer+1;
				} else {
					int temp = heap[currentPointer];
					heap[currentPointer] = heap[2*currentPointer+2];
					heap[2*currentPointer+2] = temp;
					currentPointer = 2*currentPointer+2;
				}
			} else if((2*currentPointer+2) < currentSize && (heap[currentPointer] > heap[2*currentPointer+2])) {
				int temp = heap[currentPointer];
				heap[currentPointer] = heap[2*currentPointer+2];
				heap[2*currentPointer+2] = temp;
				currentPointer = 2*currentPointer+2;
			} else if((2*currentPointer+1) < currentSize && heap[currentPointer] > heap[2*currentPointer+1]) {
				int temp = heap[currentPointer];
				heap[currentPointer] = heap[2*currentPointer+1];
				heap[2*currentPointer+1] = temp;
				currentPointer = 2*currentPointer+1;
			} else {
				break;
			}
		}
	}
	
	boolean isFull() {
		if(currentSize == HEAP_SIZE) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean isEmpty() {
		if(currentSize == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	void display() {
		for (int i = 0; i < currentSize; i++) {
			System.out.print(heap[i]+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		MinHeap heap = new MinHeap();
		System.out.println("Heap size = "+heap.HEAP_SIZE);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean condition = true;
		try {
			do {
				System.out.println("1. Add nodes");
				System.out.println("2. Pop");
				System.out.println("3. Display");
				System.out.println("4. Exit");
				String input = br.readLine();
				switch(Integer.parseInt(input)) {
				case 1:
					System.out.println("Enter value :");
					input = br.readLine();
					heap.add(Integer.parseInt(input));
					break;
				case 2:
					heap.pop();
					break;
				case 3:
					heap.display();
					break;
				case 4:
					condition = false;
					break;
				default:
					System.out.println("Invalid entry");
				}
			} while(condition);
		} finally {
			if(br != null) {
				br.close();
			}
		}
	}

}
