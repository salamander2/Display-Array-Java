package displayArray;

/**
 * Template for user array game.
 */


public class MyArrayGame extends ArrayGame {

	//constants
	static int SIZE = 15; //board size
	static int EMPTY = 0;
	
	//instance variables
	String title = "Game #1 title";
	int[][] board = new int[SIZE][SIZE];
	
	public static void main(String[] args) {
		new MyArrayGame();
	}
	
	//constructor
	MyArrayGame() {
		//do all initialization
		initBoard();
		//hand over control to DisplayArray
		new DisplayArray(this);		
	}
	
	void initBoard() {
		for (int i=0;i<SIZE;i++) {
			for (int j=0;j<SIZE;j++) {
				board[i][j]=EMPTY;
			}
		}
		
		//DEBUG: set some values for testing
		board[1][2] = 1;
		board[2][2] = 2;
		board[3][2] = 3;
		board[4][2] = 4;
		board[5][2] = 5;
		board[6][2] = 6;
		board[7][2] = 7;
		board[8][2] = 8;
		board[9][2] = 9;
		
		board[0][3] = 10;
		board[1][3] = 11;
		board[2][3] = 12;
		board[3][3] = 13;
		board[4][3] = 14;
		board[5][3] = 15;
		board[6][3] = 16;
		board[7][3] = 17;
		board[8][3] = 18;
		board[9][3] = 19;
		
		board [4][4] = 69;
		
	}
	
	//methods for the interface in the parent class
	@Override
	public int setSize() {
		return SIZE;
	}

	@Override
	public String setTitle() {
		return title;
	}

	@Override
	public boolean setResizable() {
		return true;
	}
	
	@Override
	public int setGridLines() {
		return 1;
	}

	@Override
	public int [][] getArray() {
		return board;
	}

	/* This is where all of the processing will happen */
	@Override
	public void mouseXY(int x, int y, boolean leftClick) {
		board[x][y]++;
		
	}

	@Override
	public int autoTimerInterval() {
		return 0;
	}
	
	/* This is where all of the processing happens if a Timer has started */
	@Override
	public boolean updateData() {
		board [6][6] ++;
		if (board[6][6] > 90) board[6][6] = 0;
		
		//click on square [0][0] three times to stop timer
		if (board[0][0] == 3) return false;
		
		return true;
		
	}

}
