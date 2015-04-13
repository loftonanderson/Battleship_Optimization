import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		boolean generate = false; // If false test values, if true generate "numOfBoards" boards
		int numOfBoards = 200000; // Change this to increase number of boards generated.
		int size = 10; // 10x10 board
		int numOfPoints = 100;
		GameBoard [] masterList = new GameBoard [numOfBoards];
		GameBoard [] inputList = new GameBoard [numOfBoards];
		int [] linearSearchList = new int [numOfBoards];
		int [] randomSearchList = new int [numOfBoards];
		int [] modifiedLinearSearchList = new int [numOfBoards];
		int [] modifiedRandomSearchList = new int [numOfBoards];
		int [] modifiedEveryOtherSearchList = new int [numOfBoards];
		int [] modifiedEveryThirdSearchList = new int [numOfBoards];
		int [] spiralSearchList = new int [numOfBoards];
		int [] quadCheckerSearchList = new int [numOfBoards];
		Scanner battleship_board_scanner = null;
		Scanner spiral_scanner = null;
		Scanner quad_checkers_scanner = null;
		double linearAvg = 0;
		double randomAvg = 0;
		double modifiedLinearAvg = 0;
		double modifiedRandomAvg = 0;
		double modifiedEveryOtherAvg = 0;
		double modifiedEveryThirdAvg = 0;
		double spiralAvg = 0;
		double quadCheckerAvg = 0;
		Point [] spiralPoints = new Point [numOfPoints];
		Point [] quadCheckerPoints = new Point [numOfPoints];
		int [] linearSearchFrequency = new int [numOfPoints];
		int [] randomSearchFrequency = new int [numOfPoints];
		int [] modifiedLinearSearchFrequency = new int [numOfPoints];
		int [] modifiedRandomSearchFrequency = new int [numOfPoints];
		int [] modifiedEveryOtherSearchFrequency = new int [numOfPoints];
		int [] modifiedEveryThirdSearchFrequency = new int [numOfPoints];
		int [] spiralSearchFrequency = new int [numOfPoints];
		int [] quadCheckerSearchFrequency = new int [numOfPoints];
		

		try{
			spiral_scanner = new Scanner(new File ("Spiral_Points"));
			quad_checkers_scanner = new Scanner(new File ("Quadratic_Checkers_Points"));
		}
		catch(Exception e){
			System.out.println("could not find file");
		}
		
		for (int i=0; i<numOfPoints; i++){
			int a = spiral_scanner.nextInt();
			int b = spiral_scanner.nextInt();
			Point guess = new Point(a,b);
			spiralPoints[i] = guess;
			
			int c = quad_checkers_scanner.nextInt();
			int d = quad_checkers_scanner.nextInt();
			Point guess2 = new Point(c,d);
			quadCheckerPoints[i] = guess2;
		}
		
		spiral_scanner.close();
		quad_checkers_scanner.close();
		
		if (!generate){
			// Used to read Boards from file
			try{
				battleship_board_scanner = new Scanner(new File ("GameBoards"));
			}
			catch(Exception e){
				System.out.println("could not find file");
			}
			
			for (int i=0; i<numOfBoards; i++){
				int [][] boardArray = new int [size][size];
				int row = 0;
				int col = 0;
			
				while(row<11){
	            String line = battleship_board_scanner.nextLine();
		            for (int j = 0; j < line.length(); j++) {
		            	int peg = (int)(line.charAt(j)) - 48;
		            	boardArray[row][col] = peg;
		            	col++;
		            }
	            row++;
	            col = 0;
				}
				GameBoard GB = new GameBoard(boardArray, spiralPoints, quadCheckerPoints);
				inputList[i] = GB;
			}
			
			battleship_board_scanner.close();
			
			for (int i=0; i<numOfBoards; i++){
				// Erase/comment out nested for loop bellow to erase boards being printed out
				/*
				for (int row=0; row<size; row++){
					for (int col=0; col<size; col++){
						System.out.print(inputList[i].getArray()[row][col]);
					}
					System.out.println();
				}
				*/
				// Run every search on same board, and store return value in appropriate list
				linearSearchList[i] = inputList[i].linearSearch();
				randomSearchList[i] = inputList[i].randomSearch();
				modifiedLinearSearchList[i] = inputList[i].modifiedLinearSearch();
				modifiedRandomSearchList[i] = inputList[i].modifiedRandomSearch();
				modifiedEveryOtherSearchList[i] = inputList[i].modifiedEveryOtherSearch();
				modifiedEveryThirdSearchList[i] = inputList[i].modifiedEveryThirdSearch();
				spiralSearchList[i] = inputList[i].spiralSearch();
				quadCheckerSearchList[i] = inputList[i].quadCheckerSearch();
				
				// Update frequency of number occurring in specified search
				linearSearchFrequency[linearSearchList[i]-1] +=1;
				randomSearchFrequency[randomSearchList[i]-1] +=1;
				modifiedLinearSearchFrequency[modifiedLinearSearchList[i]-1] +=1;
				modifiedRandomSearchFrequency[modifiedRandomSearchList[i]-1] +=1;
				modifiedEveryOtherSearchFrequency[modifiedEveryOtherSearchList[i]-1] +=1;
				modifiedEveryThirdSearchFrequency[modifiedEveryThirdSearchList[i]-1] +=1;
				spiralSearchFrequency[spiralSearchList[i]-1] +=1;
				quadCheckerSearchFrequency[quadCheckerSearchList[i]-1] +=1;
				
				/*
				// Erase/comment out every print statement to only calculate data 
				// and not have every board's search output printed out
				System.out.println();
				System.out.println("Linear Search: " +linearSearchList[i]);
				System.out.println("Random Search: " +randomSearchList[i]);
				System.out.println("Modified Linear Search: " +modifiedLinearSearchList[i]);
				System.out.println("Modified Random Search: " + modifiedLinearSearchList[i]);
				System.out.println("Modified Every Other Search: " + modifiedEveryOtherSearchList[i]);
				System.out.println("Modified Every Third Search: " + modifiedEveryThirdSearchList[i]);
				System.out.println("Spiral Search: " +spiralSearchList[i]);
				System.out.println("Quadratic Checkers Search: " +quadCheckerSearchList[i]);
				System.out.println();
				*/
			}
			
			// Print out frequencies of data
			System.out.println("##########");
			System.out.println("## Data ##");
			System.out.println("##########");
			System.out.println();
			System.out.println("-Linear Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(linearSearchFrequency[i]+"  ");
				/*
				if(linearSearchFrequency[i] != 0){
					System.out.print((i+1)+":"+linearSearchFrequency[i]+"  ");
				}
				*/
			}
			System.out.println();
			System.out.println();
			System.out.println("-Random Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(randomSearchFrequency[i]+"  ");
				/*
				if(randomSearchFrequency[i] != 0){
					System.out.println((i+1)+":"+randomSearchFrequency[i]+"  ");
				}
				*/
			}
			System.out.println();
			System.out.println();
			System.out.println("-Modified Linear Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(modifiedLinearSearchFrequency[i]+"  ");
				/*
				if(modifiedLinearSearchFrequency[i] != 0){
					System.out.println((i+1)+":"+modifiedLinearSearchFrequency[i]+"  ");
				}
				*/
			}
			System.out.println();
			System.out.println();
			System.out.println("-Modified Random Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(modifiedRandomSearchFrequency[i]+"  ");
				//if(modifiedRandomSearchFrequency[i] != 0){
				//	System.out.println((i+1)+":"+modifiedRandomSearchFrequency[i]+"  ");
				//}
			}
			System.out.println();
			System.out.println();
			System.out.println("-Modified Every Other Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(modifiedEveryOtherSearchFrequency[i]+"  ");
				//if(modifiedEveryOtherSearchFrequency[i] != 0){
				//	System.out.println((i+1)+":"+modifiedEveryOtherSearchFrequency[i]+"  ");
				//}
			}
			System.out.println();
			System.out.println();
			System.out.println("-Modified Every Third Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(modifiedEveryThirdSearchFrequency[i]+"  ");
				//if(modifiedEveryThirdSearchFrequency[i] != 0){
				//	System.out.println((i+1)+":"+modifiedEveryThirdSearchFrequency[i]+"  ");
				//}
			}
			System.out.println();
			System.out.println();
			System.out.println("-Spiral Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(spiralSearchFrequency[i]+"  ");
				//if(spiralSearchFrequency[i] != 0){
				//	System.out.println((i+1)+":"+spiralSearchFrequency[i]+"  ");
				//}
			}
			System.out.println();
			System.out.println();
			System.out.println("-Quadradic Checker Search Frequency-");
			for (int i=0; i<numOfPoints; i++){
				System.out.println(quadCheckerSearchFrequency[i]+"  ");
				//if(quadCheckerSearchFrequency[i] != 0){
				//	System.out.println((i+1)+":"+quadCheckerSearchFrequency[i]+"  ");
				//}
			}
			System.out.println();
			
			
			// Generate Averages
			for (int i=0; i<numOfBoards; i++){
				linearAvg += linearSearchList[i];
				randomAvg += randomSearchList[i];
				modifiedLinearAvg += modifiedLinearSearchList[i];
				modifiedRandomAvg += modifiedRandomSearchList[i];
				modifiedEveryOtherAvg += modifiedEveryOtherSearchList[i];
				modifiedEveryThirdAvg += modifiedEveryThirdSearchList[i];
				spiralAvg += spiralSearchList[i];
				quadCheckerAvg += quadCheckerSearchList[i];
			}
			
			linearAvg = linearAvg/numOfBoards;
			randomAvg = randomAvg/numOfBoards;
			modifiedLinearAvg = modifiedLinearAvg/numOfBoards;
			modifiedRandomAvg = modifiedRandomAvg/numOfBoards;
			modifiedEveryOtherAvg = modifiedEveryOtherAvg/numOfBoards;
			modifiedEveryThirdAvg = modifiedEveryThirdAvg/numOfBoards;
			spiralAvg = spiralAvg/numOfBoards;
			quadCheckerAvg = quadCheckerAvg/numOfBoards;
			
			// Print out Averages
			System.out.println();
			System.out.println();
			System.out.println("##############");
			System.out.println("## Averages ##");
			System.out.println("##############");
			System.out.println();
			System.out.println("Linear Average: " + getGrade(linearAvg) + " ("+linearAvg+")");
			System.out.println("Random Average: " + getGrade(randomAvg) + " ("+randomAvg+")");
			System.out.println("Modified Linear Average: " + getGrade(modifiedLinearAvg) + " ("+modifiedLinearAvg+")");
			System.out.println("Modified Random Average: " + getGrade(modifiedRandomAvg) + " ("+modifiedRandomAvg+")");
			System.out.println("Modified Every Other Average: " + getGrade(modifiedEveryOtherAvg) + " ("+modifiedEveryOtherAvg+")");
			System.out.println("Modified Every Third Average: " + getGrade(modifiedEveryThirdAvg) + " ("+modifiedEveryThirdAvg+")");
			System.out.println("Spiral Average: " + getGrade(spiralAvg) + " ("+spiralAvg+")");
			System.out.println("Quadratic Checkers Average: " + getGrade(quadCheckerAvg) + " ("+quadCheckerAvg+")");
		}
		
// ---------------------------------------------------------------------------------		
		
		else {
			
			// Generate Boards
			int counter = 1;
			for (int i=0; i< numOfBoards; i++){
				System.out.println(counter);
				counter+=1;
				GameBoard GB = new GameBoard();
				GB.randomlyPlace();
				masterList[i] = GB;
			}
		
			// print Boards to file
			
			try {
				FileWriter fw = new FileWriter("Gameboards");
				PrintWriter pw = new PrintWriter(fw);
				
				// Probably not the most efficient way
				for (int i=0; i<numOfBoards; i++){
					for (int row=0; row<size; row++){
						for (int col=0; col<size; col++){
							pw.print(masterList[i].getArray()[row][col]);
						}
					pw.println();
					}
				pw.println();
				}
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getGrade(double score){
		String grade = "error";
		if (score> 17 && score <= 30){
			grade = "A+";
		}
		else if (score> 30 && score <= 40){
			grade = "A";
		}
		else if (score> 40 && score <= 45){
			grade = "A-";
		}
		else if (score> 45 && score <= 50){
			grade = "B+";
		}	
		else if (score> 50 && score <= 55){
			grade = "B";
		}
		else if (score> 55 && score <= 60){
			grade = "B-";
		}
		else if (score> 60 && score <= 65){
			grade = "C+";
		}
		else if (score> 65 && score <= 70){
			grade = "C";
		}
		else if (score> 70 && score <= 75){
			grade = "C-";
		}
		else if (score> 75 && score <= 80){
			grade = "D+";
		}
		else if (score> 80 && score <= 85){
			grade = "D";
		}
		else if (score> 85 && score <= 90){
			grade = "D-";
		}
		else if (score> 90 && score <= 95){
			grade = "F+";
		}
		else if (score> 95 && score <= 100){
			grade = "F";
		}
		return grade;
	}
}
