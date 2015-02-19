package com.sumit.programs;

public class RabinKarp {
	
	private static int BASE = 26; //Since we are dealing with only lower case ascii alpahabets
	private static int MODULO = 13; // A big prime
	
	public static Long hornerValue(char[] ar, int startIndex, int endIndex, Long previousHornerValue) {
		Long currentValue = 0l;
		if(previousHornerValue == null) {
			for (int i = startIndex; i <= endIndex; i++) {
				currentValue = BASE*currentValue + ar[i] - 96;
			}
		} else {
			Long divisor = 1l;
			for (int i = startIndex; i <= endIndex; i++) {
				divisor*=BASE;
			}
			currentValue = BASE*(previousHornerValue%divisor) + ar[endIndex] - 96;
		}
		return currentValue;
	}
	
	public static Long getHash(Long hornerValue) {
		if(hornerValue != null) {
			return hornerValue % MODULO;
		} else {
			return null;
		}
	}
	
	public static boolean verifyMatch(char[] pattern, char[] text, int startIndex) {
		boolean match = true;
		for (int i = 0; i < pattern.length; i++) {
			if(pattern[i] != text[startIndex+i]) {
				match = false;
				break;
			}
		}
		return match;
	}
	
	public static void main(String[] args) {
		char[] text = "cozacocacolacococacola".toCharArray();
		char[] pattern = "cocacola".toCharArray();
		Long patternHornerValue = hornerValue(pattern, 0, pattern.length-1, null);
		Long patternHash = getHash(patternHornerValue);
		//System.out.println("patternHornerValue :: "+patternHornerValue+" patternHash :: "+patternHash);
		int endIndex = pattern.length-1;
		int startIndex = 0;
		Long windowHornerValue = null;
		boolean matchExists = false;
		while(endIndex < text.length) {
			//System.out.println("------"+String.valueOf(text, startIndex, endIndex+1-startIndex)+"------------");
			windowHornerValue = hornerValue(text, startIndex, endIndex, windowHornerValue);
			Long windowHash = getHash(windowHornerValue);
			//System.out.println("windowHornerValue :: "+windowHornerValue+" windowHash :: "+windowHash);
			if(windowHash == patternHash) {
				//Potential Match
				if(verifyMatch(pattern, text, startIndex)) {
					System.out.println("Pattern found at index "+startIndex);
					matchExists = true;
				}
			}
			startIndex++;
			endIndex++;
		}
		if(matchExists) {
			System.out.println("Match exists");
		} else {
			System.out.println("Match doesnot exists");
		}
	}

}
