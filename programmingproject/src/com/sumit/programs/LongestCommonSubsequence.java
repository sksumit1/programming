package com.sumit.programs;
//https://www.youtube.com/watch?v=Qf5R-uYQRPk
/**
 * Case 1
 * P0 = "    x"    Q0 = "    x"
 * LCS(P0,Q0) = 1+ LCS(P1,Q1)
 * 
 * Case 2
 * P0 = "    x"    Q0 = "    y"
 * LCS(P0,Q0) = max{ LCS(P1,Q0), LCS(P0,Q1) }
 */
import java.io.*;
import java.util.*;

public class LongestCommonSubsequence {

    public static void main(String[] args) throws IOException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    	BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            String row1 = reader.readLine();
            String[] cols1 = row1.split(" ");
            int size1 = Integer.parseInt(cols1[0]);
            int size2 = Integer.parseInt(cols1[1]);
            String row2 = reader.readLine();
            String[] cols2 = row2.split(" ");
            int[] ar1 = new int[size1];
            for(int i =0 ; i< size1; i++) {
                ar1[i] = Integer.parseInt(cols2[i]);
            }            
            String row3 = reader.readLine();
            String[] cols3 = row3.split(" ");
            int[] ar2 = new int[size2];
            for(int i =0 ; i< size2; i++) {
                ar2[i] = Integer.parseInt(cols3[i]);
            }
            List<Integer> sequence =  new ArrayList<Integer>();
            int length = longestCommonSubsequence(ar1,ar2,size1-1,size2-1, sequence);
            java.util.Collections.reverse(sequence);
            Iterator<Integer> it = sequence.iterator();
            while(it.hasNext()) {
                System.out.print(it.next()+" ");
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
    }
    
    public static int longestCommonSubsequence(int [] ar1, int[] ar2, int ptr1, int ptr2 , List<Integer> sequence) {
        if(ptr1 < 0 || ptr2 < 0) {
            return 0;
        }
        if(ar1[ptr1] == ar2[ptr2]) {
            List<Integer> childSequence = new ArrayList<Integer>();
            int value = longestCommonSubsequence(ar1,ar2,ptr1-1,ptr2-1, childSequence);
            sequence.add(ar1[ptr1]);
            sequence.addAll(childSequence);
            return value+1;
        } else {
            List<Integer> leftChildSequence = new ArrayList<Integer>();
            List<Integer> rightChildSequence = new ArrayList<Integer>();
            int left = longestCommonSubsequence(ar1,ar2,ptr1-1,ptr2,leftChildSequence);
            int right = longestCommonSubsequence(ar1,ar2,ptr1,ptr2-1,rightChildSequence);
            if(left > right) {
                sequence.addAll(leftChildSequence);
                return left;
            } else {
                sequence.addAll(rightChildSequence);
                return right;
            }
        }
    }
}
