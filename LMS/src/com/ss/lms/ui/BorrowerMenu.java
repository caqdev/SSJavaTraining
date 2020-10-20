package com.ss.lms.ui;

import java.util.Scanner;

import com.ss.lms.entity.Borrower;
import com.ss.lms.service.BorrowerService;

public class BorrowerMenu {

	ConsoleHelper helper;
	Scanner scan;
	
	public BorrowerMenu(Scanner scan) {
		helper = new ConsoleHelper();
		this.scan = scan;
	}

	public void borrowerCardNoInput() {
		BorrowerService borrowerService = new BorrowerService();
		Integer cardNo = -1;
		boolean isInputInRange = false;
		Borrower borrower;
		while(!isInputInRange) {
			System.out.println("Enter your card number (Type 0 to go back):");
			cardNo = helper.readIntInput(scan);
			if(cardNo < 0) {
				System.out.println("Please enter 0 or a positive number");
				continue;
			}else {
				isInputInRange = true;
			}
		}
		if(cardNo == 0) {
			return;
		}
		borrower = borrowerService.getBorrowerById(cardNo);
		if(borrower == null) {
			System.out.println("Card No not found please enter another number");
		}
		mainBorrowerMenu(borrower);
		
	}

	private void mainBorrowerMenu(Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("Hello " + borrower.getBorrowerName() +"!");
		System.out.println("What would you like to do?\n1) Check-out Book \n2) Return a Book \n3) Quit to previous");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			return;
		default:
			System.out.println("Please enter a number from the list");
			mainBorrowerMenu(borrower);
		}
		mainBorrowerMenu(borrower);
	}
	
	
}
