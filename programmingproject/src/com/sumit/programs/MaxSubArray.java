package com.sumit.programs;

public class MaxSubArray {
    /**Kadane's algorithm**/
    /**States that a max sub array for n is max(sub-array[n-1]+n,n)**/
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int previousSubArraySum = nums[0];
        for(int i=1;i<nums.length;i++) {
            previousSubArraySum = max(previousSubArraySum+nums[i],nums[i]);
            if(max < previousSubArraySum) {
                max = previousSubArraySum;
            }
        }
        return max;
    }
    
    public int max(int a, int b) {
        if(a<b) {
            return b;
        } else {
            return a;
        }
    }
}