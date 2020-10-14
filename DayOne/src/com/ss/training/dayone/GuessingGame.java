/**
 * 
 */
package com.ss.training.dayone;

import java.util.Scanner;

/**
 * @author caq
 *
 */
public class GuessingGame {

	boolean hasWon;
	int highestPossibleNumber;
	int marginOfError;
	int numberOfGuessesBeforeGameOver;
	int numberOfGuessesMade;
	int winningNumber;
	
	Scanner scan;
	
	public GuessingGame(Scanner scan) {
		this.hasWon = false;
		this.highestPossibleNumber = 100;
		this.marginOfError = 10;
		this.numberOfGuessesBeforeGameOver = 5;
		this.numberOfGuessesMade = 0;
		this.winningNumber = (int)(Math.random() * highestPossibleNumber + 1);
		this.scan = scan;
	}
	
	public GuessingGame(Scanner scan, int highestPossible, int marginOfError, int numberOfGuessesBeforeGameOver) {
		this.hasWon = false;
		this.highestPossibleNumber = highestPossible;
		this.marginOfError = marginOfError;
		this.numberOfGuessesBeforeGameOver = numberOfGuessesBeforeGameOver;
		this.numberOfGuessesMade = 0;
		this.winningNumber = (int)(Math.random() * highestPossibleNumber + 1);
		this.scan = scan;
	}
	
	public boolean checkIfNumberIsWithinMarginOfError(int numToCheck) {
		int diffBetweenWinningAndChosen = this.winningNumber - numToCheck;
		return Math.abs(diffBetweenWinningAndChosen) <= this.marginOfError;
	}
	
	public int handleNumberChoosing() {
		int numberChosen = readIntInput();
		if(numberChosen < 1 || numberChosen > this.highestPossibleNumber) {
			System.out.println("Please choose a number withing the range.");
			return handleNumberChoosing();
		}
		return numberChosen;
	}
	
	public int readIntInput() {
		String txtInput = this.scan.nextLine();
		try {
			int inputAsInt = Integer.parseInt(txtInput);
			return inputAsInt;
		} catch(Exception e) {
			System.out.println("Please enter a number:");
			return readIntInput();
		}
	}
	
	public void runGame() {
		System.out.println("Please guess a number between 1-" + this.highestPossibleNumber + ".");
		
		do {
			int numOfGuessesLeft = this.numberOfGuessesBeforeGameOver - this.numberOfGuessesMade;
			System.out.println("Try to guess within " + this.marginOfError + " of the winning number. You have " + numOfGuessesLeft + " guesses left.");
			int numberChosen = handleNumberChoosing();
			this.hasWon = checkIfNumberIsWithinMarginOfError(numberChosen);
			this.numberOfGuessesMade++;
			if(!this.hasWon && this.numberOfGuessesMade < this.numberOfGuessesBeforeGameOver) {
				System.out.println("This guess wasn't close enough to the winning number, please try again!");
			}
			System.out.println();
			System.out.println("*****************************************************************************");
			System.out.println();
		} while (!this.hasWon && this.numberOfGuessesMade < this.numberOfGuessesBeforeGameOver );
		
		if(this.hasWon) {
			System.out.println("Congratulations you were close enough! The winning number was " + this.winningNumber);
		} else {
			System.out.println("Sorry you weren't able to get close enough.");
		}
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		//GuessingGame gg = new GuessingGame(sc);
		GuessingGame gg = new GuessingGame(sc, 100, 10, 5);
		gg.runGame();
		System.out.println("Thanks for playing!");
	}
	

}
