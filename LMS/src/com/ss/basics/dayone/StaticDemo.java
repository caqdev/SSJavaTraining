/**
 * 
 */
package com.ss.basics.dayone;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ppradhan
 *
 */
public class StaticDemo {
	
	public static void main(String[] args) {
		Static stClassOne = new Static();
		stClassOne.printInstanceCount();
		
		String name = "Test Name"; //
		
		Static stClassTwo = new Static();
		stClassTwo.printInstanceCount();
		
		Static stClassThree = new Static();
		stClassThree.printInstanceCount();
		
		Map<Integer, String> myMap = new HashMap<>();
		myMap.put(1, "John"); //hashcode 1
		
		myMap.get(1);
	}
}
