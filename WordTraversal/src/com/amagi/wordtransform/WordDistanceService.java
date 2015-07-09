package com.amagi.wordtransform;

import java.util.ArrayList;

public class WordDistanceService {
	private static WordDistanceService service;
	private static Object lock = new Object();
	
	ProcessedDictionary dictionary;
	
	private WordDistanceService() {
		dictionary = new ProcessedDictionary();
	}
	
	public static WordDistanceService getService() {
		if(service == null) {
			synchronized (lock) {
				if(service == null) {
					service = new WordDistanceService();
				}
			}
		}
		return service;
	}
	
	public int findValidDistance(String source, String dest) {
		return dictionary.findMinDistance(source, dest);
	}
	
	public String[] findSortestPath(String source, String dest) {
		return null;
	}
	
	public ArrayList<String[]> findAllShortestPath(String source, String dest) {
		return null;
	}
}
