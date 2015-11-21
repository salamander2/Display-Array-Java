package displayArray;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

/**
 * This is an example of a custom array based game written by a user. 
 * It can be called anything.
 * 
 * In this version, observer-pattern, the user program retains control of the
 * program. Thus, it needs to do things in the correct order.
 * Calling DisplayArray.setSize() after the display has painted may cause unpredictable results.
 * 
 * This program must implement the MouseObserver interface in order for MouseClick information to be
 * transmitted back to this program.
 * 
 * ##### THERE IS NO TIMER IN THE DISPLAYARRAY BECAUSE IT MUST BE IMPLEMENTED HERE
 * 
 */
public class UserArrayGame implements MouseObserver{
	
	//constants
	final static int SIZE = 22; //board size
	final static int EMPTY = 0;
	
	//instance variables
	private String title = "Game #2 Title";
	private int[][] board = new int[SIZE][SIZE];
	private DisplayArray disp;
	private Point mouseLocation = new Point(0,0);
	private boolean gameOver = false;
	
	public static void main(String[] args) {
		new UserArrayGame();
	}
	
	//constructor
	private UserArrayGame() {
		initBoard();
		setUpGraphics();
		//autoRunGame();
	}
	
	private void initBoard() {
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
	
	/**
	 * This sends all of the information needed to initialize the graphics display. 
	 */
	private void setUpGraphics() {
		disp = new DisplayArray(this, SIZE); 
		disp.setResizable(true);
		disp.setTitle(title);
		disp.setColor(0, new Color(77,77,77));
		disp.sendArray(board); //this calls repaint(), so it must be the last thing done here
	}
	
	/**
	 * This method is used for running a game without needing mouse clicks.
	 */
	private void autoRunGame() {
		int sleeptime = 50;
		
		//game loop timer
		while(!gameOver) {
			board[6][7]++;
			if (board[6][7] == 88) gameOver = true;
			
			try {
				Thread.sleep(sleeptime);
			} catch(InterruptedException e) {}
		}
	}
	
	/** This method could be called anything you wish.
	 *  For click based games (e.g. TicTacToe) the whole game must be controlled here.
	 */
	private void processClick(){
		board[mouseLocation.x][mouseLocation.y]++;
		disp.sendArray(board);
		if (board[0][0] == 5) gameOver = true;  //now what?
		
		if (gameOver) endGame();
	}
	
	private void endGame() {
		String msgTitle = "Game Over";
		String msgText = "Thanks for playing.";
		JOptionPane.showMessageDialog(null, msgText, msgTitle, JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	}

	@Override
	public void mouseProcessing(MouseEvent e, Point p) {
		mouseLocation = p;
		processClick();
	}
	
	
}
