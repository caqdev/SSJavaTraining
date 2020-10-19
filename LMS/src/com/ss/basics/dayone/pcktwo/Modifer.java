/**
 * 
 */
package com.ss.basics.dayone.pcktwo;

import com.ss.basics.dayone.PolyExample;

/**
 * @author ppradhan
 *
 */
public class Modifer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PolyExample pe = new PolyExample();
//		pe.add("Demo"); // inheritence
		pe.add(1, "Demo"); // trying to access private method
	}

//	public static void add(String b) {
//		System.out.println("Adding one String - inside child");
//	}

}
