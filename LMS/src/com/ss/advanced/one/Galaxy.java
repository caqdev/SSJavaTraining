package com.ss.advanced.one;

public class Galaxy implements SmartPhoneInterface,
	SmartPhoneInterfaceTwo{

	@Override
	public void defineOS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer definePrice() {
		processor();
		SmartPhoneInterfaceTwo.processorSpeed();
		return null;
	}

	@Override
	public void processor() {
		// TODO Auto-generated method stub
		SmartPhoneInterface.super.processor();
	}

}
