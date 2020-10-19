/**
This is a class to demonstrate Polymorphism in OOP - Overloading
 * 
 */
package com.ss.basics.dayone;

/**
 * @author ppradhan
 *
 */
public class PolyExample {
	
	public static void add(int a, int b) {
		System.out.println("Adding two integers");
	}
	
	public static void add(int a, String b) {
		System.out.println("Adding one integer & one String");
	}
	
	protected static void add(String b) {
		System.out.println("Adding one String");
	}
	
	static void add(String b, String a) {
		System.out.println("Adding two Strings");
	}
}
