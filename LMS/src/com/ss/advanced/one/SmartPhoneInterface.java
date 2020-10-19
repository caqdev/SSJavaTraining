/**
 * 
 */
package com.ss.advanced.one;

/**
 * @author ppradhan
 *
 */
public interface SmartPhoneInterface {
	
	public void defineOS();
	
	public Integer definePrice();
	
	default void processor() {
		System.out.println("Processer in abs");
	}
	
	static Integer processorSpeed() {
		System.out.println("Processer in abs");
		return null;
	}
	
}
