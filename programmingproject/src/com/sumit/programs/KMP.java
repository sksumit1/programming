package com.sumit.programs;
/**
 * When a mismatch occurs, we may not need to restart the comparison all the way back (from the next input position)
 * Create a array H, that determines how many characters to shift the pattern to the right in case of a mismatch during the pattern matching process.
 * When there is a character mismatch during matching, in the pattern, look for longest suffix that matches the prefix.
 * The "Array H"
 * -------------
 * For each position i in the pattern P, define h(i) to be the length of the longest proper suffix of P[1.....i] that matches a prefix of P
 * and P(h[i]+1) != P(i+1)
 * Proper suffix : Matches a prefix of p, but not everything
 * 
 * Compute Prefix function (P)
 * m = P.length
 * H[] = new H[1...m]
 * H[1] = 0;
 * k = 0;
 * for q = 2 to m
 *      while k>0 and P[k+1] != P[q]
 *             k = H[k]
 *      if P[k+1] == P[q]
 *             k = k+1
 *      H[q] = k
 * return H
 * 
 * 
 * KMP Matcher  (T,P)
 * n = T.length
 * m = P.length
 * H = Compute-Prefix-function(P)
 * q = 0;                                   //Number of characters already matched
 * for i = 1 to n
 *     while q > 0 and P[q+1] != T[i]       //Last match at q, but q+1 doesn't match
 *     		q = H[q]
 *     if P[q+1] == T[i]                    // Next character matches
 *     		q = q + 1                        
 *     if q == m
 *     		print "Pattern match occurs at index " i - m
 *     	q = H[q]
 *
 *
 * If a mismatch happens at i+1, (where i is index of pattern)
 * No of elements (H[i]) that will continue to match after shifting by i - H[i]   
 * We are not comparing the positions in pattern that have already matched.
 * hence total number of comparisons = n + "no. of unsuccessful comparisons"
 * 									 = n + "max. no. of total shifts"
 *                                   ~ 2n = O(n)
 * H[i] = longest proper prefix of pattern which is also suffix of 1...i & next character is different
 * Computation of H[] = O(m)
 * hence KMP = O(m+n)
 */
public class KMP {
	
	
	int [] computePrefixFunction(char[] pattern) {
		int h[] = new int[pattern.length];
		h[0] = 0;
		int k = -1;
		for (int q = 1; q < pattern.length; q++) {
			while(k>= 0 && pattern[k+1] != pattern[q]) {      
				k = h[k]-1;
			}
			if(pattern[k+1] == pattern[q]) {
				k  = k + 1;
			}
			h[q] = k+1;
		}		
		return h;
	}
	
	boolean kmpmatcher(char[]text,char[] pattern) {
		boolean found = false;
		int n = text.length;
		int m = pattern.length;
		int h[] = this.computePrefixFunction(pattern);
		int q = 0;
		for (int i = 0; i < n; i++) {
			while(q > 0 && pattern[q] != text[i]) {
				q = h[q-1];
			}
			if(pattern[q] == text[i]) {
				q = q + 1;
			}
			if(q == m) {
				System.out.println("Pattern found at "+(i+1-m));
				found = true;
				q = h[q-1];
			}
		}
		return found;
	}
	
	public static void main(String[] args) {
		KMP kmp = new KMP();
		char[] text = "cozacocacolacococacola".toCharArray();
		char[] pattern = "cocacola".toCharArray();
		boolean match = kmp.kmpmatcher(text, pattern);
		if(match) {
			System.out.println("Pattern found !!");
		} else {
			System.out.println("Pattern not found !!");
		}
	}

}
