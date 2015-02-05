package com.sumit.programs;
/**
 * You are given an array of positive integers. Convert it to a sorted array with minimum cost (minimum number of operations). Only valid operation are
1) Decrement -> cost = 1
2) Delete an element completely from the array -> cost = value of element
For example:
4,3,5,6, -> cost 1 //cost is 1 to make 4->3
10,3,11,12 -> cost 3 // cost 3 to remove 3
 *
 */
public class LowestCost {

	static int overallcost = 0;
	static int array[] = {10, 2 ,3};
	public static void main(String[] args) {
		int start = 0;
		int end = array.length -1;
		decideonmin(array,start,end);
		System.out.println("Cost = "+overallcost);
		printarray();
	}
	
	static void printarray() {
		System.out.println();
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}
	
	static void decideonmin(int array[],int start,int end) {
		if(start >= end || alldeleted(array, start, end)) {
			return;
		}
		int[] min = findminwithindex(array, start, end);
		int costtomin = 0;
		for (int i = start; i < min[1]; i++) {
			if(array[i] == -1) {
				continue; //Skipping deleted node
			}
			costtomin+=(array[i]-min[0]);
		}
		if(costtomin <= min[0]) {
			for (int i = start; i < min[1]; i++) {
				if(array[i] == -1) {
					continue; //Skipping deleted node
				}
				array[i] = min[0];
			}
			overallcost+=costtomin;
			decideonmin(array,min[1]+1,end);
		} else {
			overallcost+=min[0];
			System.out.println("Deleting "+min[0]);
			array[min[1]] = -1; //-1 means deleted node
			decideonmin(array,start,end);
		}
	}
	
	static boolean alldeleted(int array[],int start,int end) {
		for (int i = start; i <= end; i++) {
			if(array[i] != -1) {
				return false;
			}
		}
		return true;
	}
	
	static int[] findminwithindex(int array[],int start,int end) {
		int[] ar ={Integer.MAX_VALUE,-1};
		for (int i = start; i <= end; i++) {
			if(array[i] != -1 && array[i] < ar[0]) {
				ar[0] = array[i];
				ar[1] = i;
			}
		}
		return ar;
	}
}
