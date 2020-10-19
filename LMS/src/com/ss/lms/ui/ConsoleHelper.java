package com.ss.lms.ui;

import java.util.Scanner;

public class ConsoleHelper {

	public String acceptProperLengthInput(Scanner scan, int maxLength) {
		String input = scan.nextLine();
		if (input.length() == 0) {
			System.out.println("Please enter something.");
			return acceptProperLengthInput(scan, maxLength);
		} else if (input.length() > maxLength) {
			System.out.println("Please enter something that is less than " + maxLength + " characters");
			return acceptProperLengthInput(scan, maxLength);
		}
		return input;
	}
	
	public boolean handleYesNoPrompt(Scanner scan) {
		boolean shouldExitLoop = false;
		boolean isInputYes = false;
		while(!shouldExitLoop) {
			String userInput = scan.nextLine();
			if(userInput.equalsIgnoreCase("n")) {
				shouldExitLoop = true;
				isInputYes = false;
			} else if(userInput.equalsIgnoreCase("y")){
				shouldExitLoop = true;
				isInputYes = true;
			} else {
				System.out.println("Please choose 'y' or 'n'");
			}
		}
		return isInputYes;
	}
	public int readIntInput(Scanner scan) {
		String txtInput = scan.nextLine();
		try {
			int inputAsInt = Integer.parseInt(txtInput);
			return inputAsInt;
		} catch (Exception e) {
			System.out.println("Please enter a number:");
			return readIntInput(scan);
		}
	}
	
}
