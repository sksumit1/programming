package com.sumit.programs;
//https://www.youtube.com/watch?v=uufaK2uLnSI
public class SearchInRotatedSortedArray {


    public int search(int[] nums, int target) {
        return search(nums,0,nums.length-1,target);
    }
    
    public int search(int[] nums, int startIndex, int endIndex, int target) {
        if(endIndex < startIndex) {
            return -1;
        } else if (startIndex == endIndex) {
            if(nums[startIndex] == target) {
                return startIndex;
            } else {
                return -1;
            }
        }
        int midNode = nums[(startIndex+endIndex)/2];
        if(midNode == target) {
            return (startIndex+endIndex)/2;
        }
        if(nums[startIndex] > midNode) {
            if(midNode < target && target <= nums[endIndex])
                return searchnum(nums,(startIndex+endIndex)/2+1,endIndex,target);
            else 
                return search(nums,startIndex, (startIndex+endIndex)/2-1,target);
        } else  {
            if(nums[startIndex] <= target && target < midNode)
                return searchnum(nums,startIndex,(startIndex+endIndex)/2-1,target);
            else 
                return search(nums,(startIndex+endIndex)/2+1,endIndex,target);
        }
    }
    
    public int searchnum(int[] nums, int startIndex, int endIndex, int target) {
        if(endIndex < startIndex) {
            return -1;
        } else if (startIndex == endIndex) {
            if(nums[startIndex] == target) {
                return startIndex;
            }
        }
        int midIndex = (startIndex+endIndex)/2;
        if(nums[midIndex] == target) {
            return midIndex;
        } else if (nums[midIndex] > target) {
            return searchnum(nums, startIndex, midIndex-1, target);
        } else {
            return searchnum(nums, midIndex+1, endIndex, target);
        }
    }
    
}
