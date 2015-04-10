import java.awt.Point;
import java.util.Random;

		
public class GameBoard {
	
	private int size = 10;
	private int [][] boardArray = new int [size][size];
	private int [] shipSizeArray = { 5,4,3,3,2 };
	private int numOfPoints = 100;
	private Point [] spiralPoints = new Point [numOfPoints];
	private Point [] quadCheckerPoints = new Point [numOfPoints];
	
	//TODO evolving heuristics for Battleship Power Point
	
	public GameBoard(){
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				boardArray[row][col] = 0;
				//System.out.print(boardArray[row][col]);
			}
			//System.out.println();
		}
		//System.out.println();
	}
	
	public GameBoard(int [][] array, Point [] sArray, Point [] qcArray){
		spiralPoints = sArray;
		quadCheckerPoints = qcArray;
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
				//System.out.println("Random position already filled.");
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
		int start = 0;
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		while(numOfHits < 17){
			for (int row=0; row<size; row++){
				for (int col=start; col<size; col+=2){
					
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
			
			if(numOfHits >=17){
				return numOfMoves;
			}
			else{
				start += 1;
			}
		} // end of while
		return 10000; // This will throw off average and make it easy to see where the search messes up.
	} // end of every other search

	public int modifiedEveryThirdSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		int start = 0;
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		while(numOfHits < 17){
			for (int row=0; row<size; row++){
				for (int col=start; col<size; col+=3){
					
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
			if(numOfHits >=17){
				return numOfMoves;
			}
			else{
				start += 1;
			}
		} // end of while
		return 10000; // This will throw off average and make it easy to see where the search messes up.
	} // end of every third search

	public int spiralSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		for(int i=0; i<numOfPoints; i++){
			int row = (int) spiralPoints[i].getY();
			int col = (int) spiralPoints[i].getX();
			
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

	public int quadCheckerSearch(){
		int numOfHits = 0;
		int numOfMoves = 0;
		int [][] checkArray = new int [size][size];
		
		for (int row=0; row<size; row++){
			for (int col=0; col<size; col++){
				checkArray[row][col] = 0;
			}
		}
		
		for(int i=0; i<numOfPoints; i++){
			int row = (int) quadCheckerPoints[i].getY();
			int col = (int) quadCheckerPoints[i].getX();
			
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
}
