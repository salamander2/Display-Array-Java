package displayArray;

/*****************************************************
 * This version of the interface is predicated on
 * 1. the DisplayArray program creating an instance 
 *    of the array data class
 * 2. the data handling class implementing the interface
 * 3. DisplayArray calling the various methods to get and send data
 * 
 * Contents of the integer array: 
 * 		numbers from 0 to 15 are allowed and are coloured differently. 
 *    (There are 16 colours plus a grid line colour)
 * 		numbers from 48-90 are considered to be ASCII characters and will be changed to a character
 * 		that character will be displayed. This range allows #0-9 as well as all uppercase letters to be displayed.
 * 		Values between 16 and 47 are not displayed on the screen.
 * 
 * Limitations:
 *    There is no way to change the colours in the colour array without having to edit the DisplayArray.java code.
 * @author Michael Harwood
 *
 */
public interface ArrayDataInterface {
	
	/**
	 * Called at the beginning of the DisplayArray program
	 * @return the size of the square grid
	 */
	public int setSize();
	/**
	 * Called at the beginning of the DisplayArray program
	 * @return title for the JFRame
	 */
	public String setTitle();
	/**
	 * Called at the beginning of the DisplayArray program
	 * @return True if you want to be able to resize the display
	 */
	public boolean setResizable();
	/**
	 * Called at the beginning of the DisplayArray program
	 * @return 0 = no grid lines, 1=1pixel wide, 2=2pixels wide. Anything else=1pixel
	 */
	public int setGridLines();
	/**
	 * Display Array calls this method repeatedly - everytime there is a mouse click. 
	 * This method will return the updated array to DisplayArray for it to display.
	 * @return the array representing the board.
	 */
	public int[][] getArray();
	
	/**
	 * DisplayArray calls this whenever the mouse is clicked. to send the data back.
	 * @param x the x coordinate of the mouse
	 * @param y the y coordinate of the mouse
	 * @param leftButtonClicked  True if the left mouse button clicked. False if any other button clicked.
	 */
	public void mouseXY(int x, int y, boolean leftButtonClicked);  
	
	/**
	 * Called at the beginning of the DisplayArray program
	 * This enables a timer that will automatically call udpateData() and then update the screen.
	 * 
	 * ERROR: If the value is too small, eg less than 250ms, two or three repaints are combined into one,
	 *  so the display will miss updates.
	 * A zero or negative value means no timer. 
	 * The timer interval must be more than 10ms.
	 * The timer will call updateData() every timerInterval and then it calls getArray() to display the updated data on the screen.
	 * @return the timer interval in milliseconds.
	 */
	public int autoTimerInterval();	
	
	/**
	 * This method is called at the interval specified by autoTimerInterval(). 
	 * After this, getArray() is called and then the updated data is displayed.
	 * @return False will stop the timer from continuing - e.g. at the end of the game. There is no way to restart it. MouseClicks still work though.
	 */
	public boolean updateData();
	
	/**
	 * TODO: It would be really nice to be able to replace one colour. To change the pallet of colours.
	 */
}
