package com.ss.advanced.one;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LambdaStreams {

	public static void main(String[] args) {
		List<Integer> myList = new ArrayList<>();
		
		for(int i=0; i<100; i++) myList.add(i);
//		System.out.println("Size of my list: "+myList.size());
		
//		for(Integer i: myList) {
//			System.out.println(i);
//		}
//		
//		myList.forEach(i -> {
//			System.out.println(i);
//		});
		
		Stream<Integer> seqStream = myList.stream();
		
		Stream<Integer> bigNumsSeq = seqStream.filter(p -> (p>90));
		
		bigNumsSeq.forEach(i -> {
			System.out.println(i);
		});
		System.out.println("----------------------PAR STREAM----------------");
		Stream<Integer> parStream = myList.parallelStream();
		
		Stream<Integer> bigNumsPar = parStream.filter(p -> (p>90));
		
		bigNumsPar.forEach(i -> {
			System.out.println(i);
		});

	}

}
