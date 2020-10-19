/**
 * 
 */
package com.ss.basics.dayone;

/**
 * @author ppradhan
 *
 */
public class Static {
	
	private static Integer instanceCount = 0;
	
	public Static() {
		instanceCount++;
	}
	
	public void printInstanceCount() {
		System.out.println("# of instances created: "+instanceCount);
	}

}
