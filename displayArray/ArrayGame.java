package displayArray;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * This is an example of a custom array based game written by a user. It can be called anything.
 * @author griffin
 *
 */
public class ArrayGame implements MouseObserver{
	//constants
	final static int SIZE = 22; //board size
	final static int EMPTY = 0;
	
	//instance variables
	private String title = "Game #2 Title";
	private int[][] board = new int[SIZE][SIZE];
	private DisplayArray disp;
	private Point mouseLocation = new Point(0,0);
	
	public static void main(String[] args) {
		new ArrayGame();
	}
	
	//constructor
	ArrayGame() {
		initBoard();
		setUpGraphics();
		runGame();
	}
	
	void initBoard() {
		for (int i=0;i<SIZE;i++) {
			for (int j=0;j<SIZE;j++) {
				board[i][j]=EMPTY;
			}
		}
		//DEBUG: set some values for testing
		board[7][2] = 1;
		board[2][2] = 2;
		board[5][5] = 73;
	}
	
	void setUpGraphics() {
		disp = new DisplayArray(this, SIZE); 
//		disp.setSize(SIZE);
		disp.setResizable(true);
		disp.setTitle(title);
		disp.sendArray(board);
		//disp.repaint();			
	}
	
	//TODO: FIX ME this method has a much messier way of working with mouse clicks.
	void runGame() {
		
		while(true) {
			try {
				Thread.sleep(50);
			} catch(InterruptedException e) {}
		}
	}
	
	void processClick(){
		board[mouseLocation.x][mouseLocation.y]++;
		disp.sendArray(board);
	}

	@Override
	public void mouseProcessing(MouseEvent e, Point p) {
		mouseLocation = p;
		processClick(); //you could send the mouse event over -- if you wanted to see if the left or right button was clicked.
	}
	
	
}
