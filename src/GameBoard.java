import java.util.Random;
		
public class GameBoard {
	
	private int size = 10;
	private int [][] boardArray = new int [size][size];
	private int [] shipSizeArray = { 5,4,3,3,2 };
	private String grade; 
	// TODO add board grading (rank) system
	
	public GameBoard(){
		grade = null;
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				boardArray[row][col] = 0;
				//System.out.print(boardArray[row][col]);
			}
			//System.out.println();
		}
		//System.out.println();
	}
	
	public GameBoard(int [][] array){
		grade = null;
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				boardArray[row][col] = array[row][col];
			}
		}
	}
	
	public int [][] getArray(){
		return boardArray;
	}
	
	public void clearBoard(){
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				boardArray[row][col] = 0;
			}
		}
		System.out.println("------------------");
		System.out.println("");
	}
	
	public void randomlyPlace(){
		Random generator = new Random();
		int numOfShips = 0;
	
		while(numOfShips < 5){
			int shipX = generator.nextInt(size);
			int shipY = generator.nextInt(size);
			
			if (boardArray[shipY][shipX] !=0){
				System.out.println("Random position already filled.");
				continue;
			}
			else{
				int direction = generator.nextInt(4) + 1;
				if (direction == 1){ // North
					boolean flag = true;
					
					for (int i=1; i<shipSizeArray[numOfShips]; i++){
						if (shipY-i >= 10 || shipY-i < 0){
							flag = false;
						}
						else if (boardArray[shipY-i][shipX] != 0){
							flag = false;
						}
					}
					if (flag){
						for (int i=0; i<shipSizeArray[numOfShips]; i++){
							boardArray[shipY-i][shipX] = shipSizeArray[numOfShips];
						}
						numOfShips+=1;
					}
				}
				else if (direction == 2){ // East
					boolean flag = true;
					
					for (int i=1; i<shipSizeArray[numOfShips]; i++){
						if (shipX+i >= 10 || shipX+i < 0){
							flag = false;
						}
						else if (boardArray[shipY][shipX+i] != 0){
							flag = false;
						}
					}
					if (flag){
						for (int i=0; i<shipSizeArray[numOfShips]; i++){
							boardArray[shipY][shipX+i] = shipSizeArray[numOfShips];
						}
						numOfShips+=1;
					}
				}
				else if (direction == 3){ // South
					boolean flag = true;
					
					for (int i=1; i<shipSizeArray[numOfShips]; i++){
						if (shipY+i >= 10 || shipY+i < 0){
							flag = false;
						}
						else if (boardArray[shipY+i][shipX] != 0){
							flag = false;
						}
					}
					if (flag){
						for (int i=0; i<shipSizeArray[numOfShips]; i++){
							boardArray[shipY+i][shipX] = shipSizeArray[numOfShips];
						}
						numOfShips+=1;
					}
				}
				else if (direction == 4){ // West
					boolean flag = true;
					
					for (int i=1; i<shipSizeArray[numOfShips]; i++){
						if (shipX-i >= 10 || shipX-i < 0){
							flag = false;
						}
						else if (boardArray[shipY][shipX-i] != 0){
							flag = false;
						}
					}
					if (flag){
						for (int i=0; i<shipSizeArray[numOfShips]; i++){
							boardArray[shipY][shipX-i] = shipSizeArray[numOfShips];
						}
						numOfShips+=1;
					}
				}
			}
		}	
		
		// Print out grid with ships inserted
		/*
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				System.out.print(boardArray[row][col]+" ");
			}
			System.out.println();
			System.out.println();
		}
		System.out.println();
		*/
	}
	
	public int linearSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				if (boardArray[row][col] != 0){
					numOfHits+=1;
				}
				numOfMoves+=1;
				if (numOfHits == 17){
					return numOfMoves;
				}
			}
		}
		return numOfMoves;
	}
	
	public int randomSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		Random generator = new Random();
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		while (numOfHits < 17){
			int randCol = generator.nextInt(size);
			int randRow = generator.nextInt(size);
			
			if (checkArray[randRow][randCol] != 0){
				continue;
			}
			else {
				checkArray[randRow][randCol] = 1;
				numOfMoves+=1;
				if (boardArray[randRow][randCol] != 0){
					numOfHits+=1;
				}
			}
		}
		
		return numOfMoves;
	}

	// TODO Optimize with queue and list of ships?
	public int modifiedLinearSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				
				// If the space has already been checked, continue to next space
				if (checkArray[row][col] != 0){
					continue;
				}
				
				// If space hasn't been checked
				else {
					checkArray[row][col] = 1;
					numOfMoves+=1;
					
					// If battleship is hit
					if (boardArray[row][col] != 0){
						numOfHits+=1;
						if (numOfHits == 17){
							return numOfMoves;
						}
						
						// Check to the left 
						boolean leftHit = false;
						if (col-1 >= 0 && checkArray[row][col-1] == 0){
							checkArray[row][col-1] = 1;
							numOfMoves+=1;
							if (boardArray[row][col-1] != 0){
								numOfHits+=1;
								leftHit = true;
								if (numOfHits == 17){
									return numOfMoves;
								}
							}
						}
						if (leftHit){
							boolean keepSearching = true;
							int num = 2;
							while (keepSearching){
								if (col-num >= 0 && checkArray[row][col-num] == 0){
									checkArray[row][col-num] = 1;
									numOfMoves+=1;
									if (boardArray[row][col-num] != 0){
										numOfHits+=1;
										num+=1;
										if (numOfHits == 17){
											return numOfMoves;
										}
									}
									else{
										keepSearching = false;
									}
								}
								else{
									keepSearching = false;
								}
							}
						}
						if (numOfHits == 17){
							return numOfMoves;
						}
						
						// Check below
						boolean belowHit = false;
						if (row+1 < 10 && checkArray[row+1][col] == 0){
							checkArray[row+1][col] = 1;
							numOfMoves+=1;
							if (boardArray[row+1][col] != 0){
								numOfHits+=1;
								belowHit = true;
								if (numOfHits == 17){
									return numOfMoves;
								}
							}
						}
						if (belowHit){
							boolean keepSearching = true;
							int num = 2;
							while (keepSearching){
								if (row+num < 10 && checkArray[row+num][col] == 0){
									checkArray[row+num][col] = 1;
									numOfMoves+=1;
									if (boardArray[row+num][col] != 0){
										numOfHits+=1;
										num+=1;
										if (numOfHits == 17){
											return numOfMoves;
										}
									}
									else{
										keepSearching = false;
									}
								}
								else{
									keepSearching = false;
								}
							}
						}
						if (numOfHits == 17){
							return numOfMoves;
						}
						
						// Check to the right
						boolean rightHit = false;
						if (col+1 < 10 && checkArray[row][col+1] == 0){
							checkArray[row][col+1] = 1;
							numOfMoves+=1;
							if (boardArray[row][col+1] != 0){
								numOfHits+=1;
								rightHit = true;
								if (numOfHits == 17){
									return numOfMoves;
								}
							}
						}
						if (rightHit){
							boolean keepSearching = true;
							int num = 2;
							while (keepSearching){
								if (col+num < 10 && checkArray[row][col+num] == 0){
									checkArray[row][col+num] = 1;
									numOfMoves+=1;
									if (boardArray[row][col+num] != 0){
										numOfHits+=1;
										num+=1;
										if (numOfHits == 17){
											return numOfMoves;
										}
									}
									else{
										keepSearching = false;
									}
								}
								else{
									keepSearching = false;
								}
							}
						}
						if (numOfHits == 17){
							return numOfMoves;
						}
						
						// Check above
						boolean aboveHit = false;
						if (row-1 >= 0 && checkArray[row-1][col] == 0){
							checkArray[row-1][col] = 1;
							numOfMoves+=1;
							if (boardArray[row-1][col] != 0){
								numOfHits+=1;
								aboveHit = true;
								if (numOfHits == 17){
									return numOfMoves;
								}
							}
						}
						if (aboveHit){
							boolean keepSearching = true;
							int num = 2;
							while (keepSearching){				
								if (row-num >= 0 && checkArray[row-num][col] == 0){
									checkArray[row-num][col] = 1;
									numOfMoves+=1;
									if (boardArray[row-num][col] != 0){
										numOfHits+=1;
										num+=1;
										if (numOfHits == 17){
											return numOfMoves;
										}
									}
									else{
										keepSearching = false;
									}
								}
								else{
									keepSearching = false;
								}
							}
						}
						if (numOfHits == 17){
							return numOfMoves;
						}
					}
					
					// If all battleships are sunk, return number of moves
					if (numOfHits == 17){
						return numOfMoves;
					}
				}
			}
		}	
		return numOfMoves;
	}
	
	public int modifiedRandomSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		Random generator = new Random();
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
	
		while (numOfHits < 17){
			int col = generator.nextInt(size);
			int row = generator.nextInt(size);
			
			// If the space has already been checked, continue to next space
			if (checkArray[row][col] != 0){
				continue;
			}
			
			// If space hasn't been checked
			else {
				checkArray[row][col] = 1;
				numOfMoves+=1;
				
				// If battleship is hit
				if (boardArray[row][col] != 0){
					numOfHits+=1;
					if (numOfHits == 17){
						return numOfMoves;
					}
					
					// Check to the left 
					boolean leftHit = false;
					if (col-1 >= 0 && checkArray[row][col-1] == 0){
						checkArray[row][col-1] = 1;
						numOfMoves+=1;
						if (boardArray[row][col-1] != 0){
							numOfHits+=1;
							leftHit = true;
							if (numOfHits == 17){
								return numOfMoves;
							}
						}
					}
					if (leftHit){
						boolean keepSearching = true;
						int num = 2;
						while (keepSearching){
							if (col-num >= 0 && checkArray[row][col-num] == 0){
								checkArray[row][col-num] = 1;
								numOfMoves+=1;
								if (boardArray[row][col-num] != 0){
									numOfHits+=1;
									num+=1;
									if (numOfHits == 17){
										return numOfMoves;
									}
								}
								else{
									keepSearching = false;
								}
							}
							else{
								keepSearching = false;
							}
						}
					}
					if (numOfHits == 17){
						return numOfMoves;
					}
					
					// Check below
					boolean belowHit = false;
					if (row+1 < 10 && checkArray[row+1][col] == 0){
						checkArray[row+1][col] = 1;
						numOfMoves+=1;
						if (boardArray[row+1][col] != 0){
							numOfHits+=1;
							belowHit = true;
							if (numOfHits == 17){
								return numOfMoves;
							}
						}
					}
					if (belowHit){
						boolean keepSearching = true;
						int num = 2;
						while (keepSearching){
							if (row+num < 10 && checkArray[row+num][col] == 0){
								checkArray[row+num][col] = 1;
								numOfMoves+=1;
								if (boardArray[row+num][col] != 0){
									numOfHits+=1;
									num+=1;
									if (numOfHits == 17){
										return numOfMoves;
									}
								}
								else{
									keepSearching = false;
								}
							}
							else{
								keepSearching = false;
							}
						}
					}
					if (numOfHits == 17){
						return numOfMoves;
					}
					
					// Check to the right
					boolean rightHit = false;
					if (col+1 < 10 && checkArray[row][col+1] == 0){
						checkArray[row][col+1] = 1;
						numOfMoves+=1;
						if (boardArray[row][col+1] != 0){
							numOfHits+=1;
							rightHit = true;
							if (numOfHits == 17){
								return numOfMoves;
							}
						}
					}
					if (rightHit){
						boolean keepSearching = true;
						int num = 2;
						while (keepSearching){
							if (col+num < 10 && checkArray[row][col+num] == 0){
								checkArray[row][col+num] = 1;
								numOfMoves+=1;
								if (boardArray[row][col+num] != 0){
									numOfHits+=1;
									num+=1;
									if (numOfHits == 17){
										return numOfMoves;
									}
								}
								else{
									keepSearching = false;
								}
							}
							else{
								keepSearching = false;
							}
						}
					}
					if (numOfHits == 17){
						return numOfMoves;
					}
					
					// Check above
					boolean aboveHit = false;
					if (row-1 >= 0 && checkArray[row-1][col] == 0){
						checkArray[row-1][col] = 1;
						numOfMoves+=1;
						if (boardArray[row-1][col] != 0){
							numOfHits+=1;
							aboveHit = true;
							if (numOfHits == 17){
								return numOfMoves;
							}
						}
					}
					if (aboveHit){
						boolean keepSearching = true;
						int num = 2;
						while (keepSearching){				
							if (row-num >= 0 && checkArray[row-num][col] == 0){
								checkArray[row-num][col] = 1;
								numOfMoves+=1;
								if (boardArray[row-num][col] != 0){
									numOfHits+=1;
									num+=1;
									if (numOfHits == 17){
										return numOfMoves;
									}
								}
								else{
									keepSearching = false;
								}
							}
							else{
								keepSearching = false;
							}
						}
					}
					if (numOfHits == 17){
						return numOfMoves;
					}
				}
				
				// If all battleships are sunk, return number of moves
				if (numOfHits == 17){
					return numOfMoves;
				}
			}
		}
		return numOfMoves;
	}
	
	public int modifiedEveryOtherSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		Random generator = new Random();
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		return numOfMoves;
	}
}
