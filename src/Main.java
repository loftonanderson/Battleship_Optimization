import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		boolean generate = false; // If false test values, if true generate "numOfBoards" boards
		int numOfBoards = 500; // Change this to increase number of boards generated.
		int size = 10; // 10x10 board
		GameBoard [] masterList = new GameBoard [numOfBoards];
		GameBoard [] inputList = new GameBoard [numOfBoards];
		int [] linearSearchList = new int [numOfBoards];
		int [] randomSearchList = new int [numOfBoards];
		int [] modifiedLinearSearchList = new int [numOfBoards];
		int [] modifiedRandomSearchList = new int [numOfBoards];
		Scanner x = null;
		double linearAvg = 0;
		double randomAvg = 0;
		double modifiedLinearAvg = 0;
		double modifiedRandomAvg = 0;
		
		
		
		if (!generate){
			// Used to read Boards from file
			try{
				x = new Scanner(new File ("GameBoards"));
			}
			catch(Exception e){
				System.out.println("could not find file");
			}
			
			for (int i=0; i<numOfBoards; i++){
				int [][] boardArray = new int [size][size];
				int row = 0;
				int col = 0;
			
				while(row<11){
	            String line = x.nextLine();
		            for (int j = 0; j < line.length(); j++) {
		            	int peg = (int)(line.charAt(j)) - 48;
		            	boardArray[row][col] = peg;
		            	col++;
		            }
	            row++;
	            col = 0;
				}
				GameBoard GB = new GameBoard(boardArray);
				inputList[i] = GB;
			}
			
			x.close();
			
			for (int i=0; i<numOfBoards; i++){
				// Erase/comment out nested for loop bellow to erase boards being printed out
				for (int row=0; row<size; row++){
					for (int col=0; col<size; col++){
						System.out.print(inputList[i].getArray()[row][col]);
					}
					System.out.println();
				}
				// Erase/comment out every print statement to only calculate data 
				// and not have every board's search output printed out
				System.out.println();
				linearSearchList[i] = inputList[i].linearSearch();
				System.out.println("Linear Search: " +inputList[i].linearSearch());
				randomSearchList[i] = inputList[i].randomSearch();
				System.out.println("Random Search: " +inputList[i].randomSearch());
				modifiedLinearSearchList[i] = inputList[i].modifiedLinearSearch();
				System.out.println("Modified Linear Search: " +inputList[i].modifiedLinearSearch());
				modifiedRandomSearchList[i] = inputList[i].modifiedRandomSearch();
				System.out.println("Modified Random Search: " + inputList[i].modifiedRandomSearch());
				System.out.println();
			}
			
			System.out.println("##########");
			System.out.println("## Data ##");
			System.out.println("##########");
			System.out.println();

			for (int i=0; i<numOfBoards; i++){
				System.out.print(linearSearchList[i] +" ");
				linearAvg += linearSearchList[i];
			}
			System.out.println();
			for (int i=0; i<numOfBoards; i++){
				System.out.print(randomSearchList[i] +" ");
				randomAvg += randomSearchList[i];
			}
			System.out.println();
			for (int i=0; i<numOfBoards; i++){
				System.out.print(modifiedLinearSearchList[i] +" ");
				modifiedLinearAvg += modifiedLinearSearchList[i];
			}
			System.out.println();
			for (int i=0; i<numOfBoards; i++){
				System.out.print(modifiedRandomSearchList[i] +" ");
				modifiedRandomAvg += modifiedRandomSearchList[i];
			}
			
			linearAvg = linearAvg/numOfBoards;
			randomAvg = randomAvg/numOfBoards;
			modifiedLinearAvg = modifiedLinearAvg/numOfBoards;
			modifiedRandomAvg = modifiedRandomAvg/numOfBoards;
			System.out.println();
			System.out.println();
			System.out.println("Linear Average: " + getGrade(linearAvg) + " ("+linearAvg+")");
			System.out.println("Random Average: " + getGrade(randomAvg) + " ("+randomAvg+")");
			System.out.println("Modified Linear Average: " + getGrade(modifiedLinearAvg) + " ("+modifiedLinearAvg+")");
			System.out.println("Modified Random Average: " + getGrade(modifiedRandomAvg) + " ("+modifiedRandomAvg+")");
		}
		
// ---------------------------------------------------------------------------------		
		
		else {
			
			// Generate Boards
			
			for (int i=0; i< numOfBoards; i++){
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
