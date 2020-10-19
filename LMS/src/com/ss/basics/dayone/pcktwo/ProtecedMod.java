/**
 * 
 */
package com.ss.basics.dayone.pcktwo;

import com.ss.basics.dayone.PolyExample;

/**
 * @author ppradhan
 *
 */
public class ProtecedMod extends PolyExample{

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
