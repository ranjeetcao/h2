package com.amagi.wordtransform;

public class WordDistanceCalculator {

	public int distance(String sourceWord, String destWord) {
		int leftCell, topCell, cornerCell;
		int sourceWordLength = sourceWord.length();
		int destWordLenght = destWord.length();

		int[][] table = new int[sourceWordLength+1][destWordLenght+1];

		for (int i = 0; i < sourceWordLength; i++) {
			for (int j = 0; j < destWordLenght; j++) {
				table[i][j] = -1;
			}
		}

		for (int i = 0; i <= sourceWordLength; i++) {
			table[i][0] = i;
		}

		for (int j = 0; j <= destWordLenght; j++) {
			table[0][j] = j;
		}

		for (int i = 1; i <= sourceWordLength; i++) {
			for (int j = 1; j <= destWordLenght; j++) {
				leftCell = table[i][j - 1];
				leftCell += 1; // deletion

				topCell = table[i - 1][j];
				topCell += 1; // insertion

				cornerCell = table[i - 1][j - 1];

				if (sourceWord.charAt(i - 1) != destWord.charAt(j - 1)) {
					cornerCell += 1; // replace
				}
				table[i][j] = min(leftCell, topCell, cornerCell);
			}
		}
		return table[sourceWordLength][destWordLenght];
	}

	private int min(int... arr) {
		int min = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (min > arr[i]) {
				min = arr[i];
			}
		}
		return min;
	}
}
