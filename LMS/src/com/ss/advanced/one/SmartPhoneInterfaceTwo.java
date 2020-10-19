/**
 * 
 */
package com.ss.advanced.one;

/**
 * @author ppradhan
 *
 */
public interface SmartPhoneInterfaceTwo {
	
	public void defineOS();
	
	public Integer definePrice();
	
	default void processor() {
		System.out.println("Processer in interface 2");
	}
	
	static Integer processorSpeed() {
		System.out.println("Processer in abs");
		return null;
	}
}
