package com.amagi.wordtransform;

public class TestMain {
	
	public static void main(String[] args) {
		WordDistanceService service = WordDistanceService.getService();
		
		System.out.println(service.findValidDistance("CAP", "CAPE"));
		System.out.println(service.findValidDistance("CAP", "APE"));
		System.out.println(service.findValidDistance("CAP", "ATE"));
		
		System.out.println("============");
		System.out.println(service.findValidDistance("C", "DOG"));
	}
}
