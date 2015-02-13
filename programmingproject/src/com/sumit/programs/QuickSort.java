package com.sumit.programs;
//Always swap (left index -1) with the pivot in case of crossover or equality of the left & right pointers.
//https://www.youtube.com/watch?v=8hHWpuAPBHo
//Average & best case : nlogn. Worst case : n2
public class QuickSort {
		
	static void quicksort(int ar[], int startIndex, int endIndex) {
		if(startIndex >= endIndex && startIndex >=0 && endIndex < ar.length) {
			return;
		}
		int pivot = ar[startIndex];
		int leftPtr = startIndex+1;
		int rightPtr = endIndex;
		while(leftPtr <= rightPtr && leftPtr <= endIndex && rightPtr >= startIndex+1) {
			while(leftPtr <= endIndex && ar[leftPtr] <= pivot) {
				leftPtr++;
			}
			while(rightPtr >= (startIndex+1) && ar[rightPtr] > pivot) {
				rightPtr--;
			}
			if((leftPtr < rightPtr) && (ar[leftPtr] > pivot && ar[rightPtr] < pivot)){
				int temp = ar[leftPtr];
				ar[leftPtr] = ar[rightPtr];
				ar[rightPtr] = temp;
			}
		}
		int temp = ar[leftPtr-1];
		ar[leftPtr-1] = pivot;
		ar[startIndex] = temp;
		quicksort(ar, startIndex,leftPtr-2);
		quicksort(ar,leftPtr,endIndex);
	}
	
	public static void main(String[] args) {
		int ar[] = {1,4,2,6,4,2,6,2};
		quicksort(ar,0,ar.length-1);
		for (int i = 0; i < ar.length; i++) {
			System.out.print(ar[i]+" ");
		}
	}

}
