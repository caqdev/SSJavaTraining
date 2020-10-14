/**
 * 
 */
package com.ss.training.dayone;

import java.util.Scanner;

/**
 * @author caq
 *
 */
public class EvenWinsGame {

	boolean isPlayerOnesTurn;
	int numberOfChipsTotal;
	int numberOfChipsLeft;
	Player playerOne;
	Player playerTwo;
	Scanner scan;
	public EvenWinsGame(Scanner scan) {
		this.scan = scan;
		isPlayerOnesTurn = true;
	}
	
	public int determineMaxNumberOfChipsForLegalMove() {
		return (this.numberOfChipsLeft == 1) ? 1 : this.numberOfChipsLeft / 2;
	}
	public void displayScore() {
		if(isPlayerOnesTurn) {
			System.out.println(this.playerOne.getPlayerName() + " has " + this.playerOne.getNumberOfChips() + " chips.");
			System.out.println(this.playerTwo.getPlayerName() + " has " + this.playerTwo.getNumberOfChips() + " chips.");
		} else {
			System.out.println(this.playerTwo.getPlayerName() + " has " + this.playerTwo.getNumberOfChips() + " chips.");
			System.out.println(this.playerOne.getPlayerName() + " has " + this.playerOne.getNumberOfChips() + " chips.");
		}
	}
	
	public int handleChipTaking(int maxNumberAllowed, Player currentPlayer) {
		System.out.println("How many will you take, " + currentPlayer.getPlayerName() + "?");
		int numOfChipsToTake = readIntInput();
		if(numOfChipsToTake < 1) { 
			System.out.println("Illegal Move: You must take at least 1 chip."); 
			return handleChipTaking(maxNumberAllowed, currentPlayer);
			}
		else if(numOfChipsToTake > maxNumberAllowed) {
			System.out.println("Illegal Move: You may not take more than " + maxNumberAllowed + " chips.");
			return handleChipTaking(maxNumberAllowed, currentPlayer);
		}
		return numOfChipsToTake;
		
	}
	public boolean handlePostGame() {
		displayScore();
		if((this.playerOne.getNumberOfChips() % 2) == 0) {
			System.out.println(this.playerOne.getPlayerName() + " wins!");
		} else {
			System.out.println(this.playerTwo.getPlayerName() + " wins!");
		}
		
		String newGameInput;
		boolean startNewGame = false;
		boolean leaveLoop = false;
		do {
			System.out.println("Would you like to play another game? (y/n)");
			newGameInput = this.scan.nextLine();
			if(newGameInput.equalsIgnoreCase("y")) {
				startNewGame = true;
				leaveLoop = true;
			} else if(newGameInput.equalsIgnoreCase("n")) {
				startNewGame = false;
				leaveLoop = true;
			} else {
				System.out.println("Please enter either 'y' or 'n'.");
			}
		} while (!leaveLoop);
		return startNewGame;
	}
	
	public void playGame() {
		Player currentPlayer;
		do {
			currentPlayer = this.isPlayerOnesTurn ? this.playerOne : this.playerTwo;
			displayScore();
			System.out.println("It is your turn, " + currentPlayer.getPlayerName() + ".");
			System.out.println("There are " + this.numberOfChipsLeft + " chips remaining.");
			int maxNumberOfChipsToTake = determineMaxNumberOfChipsForLegalMove();
			System.out.print("You may take any number of chips from 1 to " + maxNumberOfChipsToTake + ". ");
			int numOfChipsTaken = handleChipTaking(maxNumberOfChipsToTake, currentPlayer);
			currentPlayer.addChips(numOfChipsTaken);
			this.numberOfChipsLeft -= numOfChipsTaken;
			System.out.println();
			System.out.println("***********************************************************");
			System.out.println();
			this.isPlayerOnesTurn = !this.isPlayerOnesTurn;
		} while (this.numberOfChipsLeft > 0);
		
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
	
	public void resetGame() {
		this.isPlayerOnesTurn = true;
		this.numberOfChipsLeft = -1;
		this.numberOfChipsTotal = -1;
		this.playerOne = null;
		this.playerTwo = null;
	}
	public void  runGame() {
		setUpGame();
		playGame();
		boolean startNewGame = handlePostGame();
		if(startNewGame) {
			resetGame();
			runGame();
		} else {
			System.out.println("Thank you for playing!");
		}

	}
	
	public void setUpPile() {
		
		System.out.println("Please select how many chips will be in the pile. Please choose an odd number of at least 3:");
		int pileAmount = -1;
		do {
			pileAmount = readIntInput();
			if(pileAmount < 3) {
				System.out.println("Please choose at least 3 chips for the pile:");
			} else if ((pileAmount % 2) != 1) {
				System.out.println("Please choose and odd number for the pile:");
			}
		} while(pileAmount < 3 || (pileAmount % 2) != 1);
		this.numberOfChipsLeft = pileAmount;
		this.numberOfChipsTotal = pileAmount;
		
	}
	
	public void setUpPlayers() {
		System.out.println("Welcome to the Even Wins Game!");
		String playerOneName = "";
		do {
			System.out.println("Please enter the name of the first player:");
			playerOneName = this.scan.nextLine();
		} while (playerOneName.isEmpty());
		this.playerOne = new Player(playerOneName);
		String playerTwoName = "";
		do {
			if(playerOneName.equalsIgnoreCase(playerTwoName)) { System.out.println("Please make sure the names of both players do not match.");}//implement
			System.out.println("Please enter a name for player 2:");
			playerTwoName = this.scan.nextLine();
		} while (playerTwoName.isEmpty() || playerOneName.equalsIgnoreCase(playerTwoName));
		this.playerTwo = new Player(playerTwoName);
	}
	
	public void setUpGame() {
		setUpPlayers();
		setUpPile();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		EvenWinsGame ewg = new EvenWinsGame(scan);
		ewg.runGame();
	}

}

class Player {
	
	String playerName;
	int numberOfChips;
	
	public Player(String pname) {
		playerName = pname;
		numberOfChips = 0;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getNumberOfChips() {
		return numberOfChips;
	}
	
	public void addChips(int numOfChipsAdded) {
		numberOfChips += numOfChipsAdded;
	}
}
