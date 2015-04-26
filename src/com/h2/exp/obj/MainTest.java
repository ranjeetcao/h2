package com.h2.exp.obj;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MainTest {
	
	public static void main(String[] args) throws SQLException {
		PropertiesTable table = new PropertiesTable();
		table.createTable();
		
		table.add("name", "Mr. Bob");
		String name = (String) table.get("name");
		System.out.println(name);
		
		table.add("isLoggedIn", true);
		Boolean isLoggedIn = (Boolean) table.get("isLoggedIn");
		System.out.println(isLoggedIn);
		
		
		Set<Address> data = new HashSet<Address>();
		data.add(new Address("1/2 abc ave", 123456));
		data.add(new Address("2/3 xyz ave", 234567));
		
		table.add("settings", data);
		
		Set<String> settings = (Set<String>) table.get("settings");
		System.out.println(settings);
		
		table.remove("settings");
		settings = (Set<String>) table.get("settings");
		System.out.println(settings);

		DBManager.get().dispose();
	}

}

class Address implements Serializable{
	String street;
	int zipCode;
	
	public Address(String line1, int zipCode) {
		this.street = line1;
		this.zipCode = zipCode;
	}
	
	
	@Override
	public String toString() {
		return "Address [street=" + street + ", zipCode=" + zipCode + "]";
	}
}