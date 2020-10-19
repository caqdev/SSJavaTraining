package com.ss.advanced.one;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

	public static void main(String[] args) {
		Trainee t1 = new Trainee("John", 1);
		Trainee t2 = new Trainee("John", 1);
		
		if(t1.equals(t2)) {
			System.out.println("Trainees are same");
		}else {
			System.out.println("Trainees are not same");
		}

		Map<Trainee, String> myMap = new HashMap<>();
		myMap.put(t1, "John Lives in VA");
		myMap.put(t2, "John now Lives in MD");
		
		System.out.println(myMap.get(t1));
		System.out.println(myMap.get(t2));
		System.out.println("Hascode of t1: "+t1.hashCode());
		System.out.println("Hascode of t2: "+t2.hashCode());
		
		System.out.println("Size of my Map: "+myMap.size());
	}

}

