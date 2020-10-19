/**
 * 
 */
package com.ss.basics.dayone;

/**
 * @author ppradhan
 *
 */
public class Overriding extends PolyExample{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			add("Demo"); // inheritence
			add(1, "Demo"); // trying to access private method
	}
	
//	public static void add(String b) {
//		System.out.println("Adding one String - inside child");
//	}

}
