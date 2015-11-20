package displayArray;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This version of DisplayArray is a passive version.
 * The main class created by the user retains control over everything.
 * This allows more things to be done.
 * BUT it also means that the user can totally screw things up by calling functions in the wrong order or at the wrong time.
 * 
 * It uses a MouseObserver pattern to send the mouse data to the main program when the mouse is clicked.
 *
 ******************************************************************************
 * 
 * Recommended Order of Events (call these methods in sequence):
 * 
 * 	  0. Make DisplayArray object, passing it the mouse observer and the grid size.
 * 
 *    1. Initialize
 *    	a. setSize()
 *      b. setTitle()
 *      c. setResizable()
 *      d. setGridLines()
 *      e. reset any colours you want to
 *      
 *   2. draw graphics
 *   	a. sendArray()  This calls repaint() automatically
 *   	b. repaint() can be called on demand if needed.
 *   
 *   3. on mouse click
 *      a. calculate the coordinates of the mouse click and stores in a point p.
 *      b. send the complete MouseEvent to the user program (mouseXY), as well as the point p.
 *
 * 
 *****************************************************************************/
 
public class DisplayArray implements ComponentListener{


	//"constants" - no, they're not final.
	private static int SCRSIZE = 720;	//screen size
	private static int SIZE = 30; //board size

	/**
	 * Array of Colours.
	 * COLOURLINES is the colour of the background and then the gridlines
	 * Color[0] is the neutral colour used for empty squares.
	 * A value of -1 in the board[][] array will be mapped to be the same as the highest colour.
	 * No other negative numbers are displayed. 
	 */
	static Color COLOURLINES = new Color(0,0,0);
	//Here are 15 colours: 0-14
	static Color colrArray[] = new Color[] {
		new Color(222,222,222), //Color 0 is typically used for empty squares. USe (100,100,100) or (222,222,222)
		new Color(10,10,160), new Color(0,180,0),	//dark blue & green
		new Color(200,130, 40),	new Color(150,150,255),	//sand and light blue	
		Color.GREEN, Color.ORANGE,
		Color.CYAN, Color.YELLOW,
		new Color(150,0,150), new Color(255,40,255), //purple and fuschia
		Color.PINK, Color.RED,	
		Color.GRAY.brighter(), Color.WHITE		
	};


	//instance variables
	//for graphics
	private JFrame frame;
	private DrawingPanel panel;
	private MouseObserver executionTarget;
	//for data
	private int[][] board; 
	private String title = "Display of Array Data";
	private boolean resizeMe;
	private int gridLines = 1;


	/**
	 * Called at the beginning of the DisplayArray program
	 * @param tempSize The size of the square grid
	 */
	public void setSize(int tempSize) {		
		if (tempSize > 0 && tempSize < 80) SIZE = tempSize;
		else SIZE = 30;
		panel.repaint();
	}

	/**
	 * Called at the beginning of the DisplayArray program
	 * @param s title for the JFRame
	 */
	public void setTitle(String s) {
		if (s.length() > 0) title = s;
		frame.setTitle(title);
	}
	
	/**
	 * Called at the beginning of the DisplayArray program
	 * @param n Thickness of grid lines in pixels. Allowable values: 0,1,2.
	 */
	public void setGridLines(int n){
		if (n<0 || n > 2) n = 1;		
	}

	/**
	 * Called at the beginning of the DisplayArray program
	 * @param allowResize True if you want to be able to resize the grid display
	 */
	public void setResizable(boolean allowResize) {
		resizeMe = allowResize;
		frame.setResizable(resizeMe);
	}

	/**
	 * Call this method repeatedly to send updated board data to the DisplayArray program
	 * It will automatically call repaint()
	 * @param 2D data array
	 */
	public void sendArray(int[][] data) {
		board = data;
		panel.repaint();
	}
	
	/**
	 * 
	 * @param n The index of the colour that you want to replace 
	 * @param c The new colour
	 */
	public void setColor(int n, Color c) {
		if (n >=0 && n < colrArray.length)
			colrArray[n] = c;	
	}
	
	/**
	 * Call this method if you want to trigger a repaint() manually 
	 */
	public void repaint() {
		panel.repaint();
	}

	/**
	 * Constructor.
	 * @param tempSize The size of the square grid. If the size is not between 1 and 80, then size is set to 30.
	 */
	public DisplayArray(MouseObserver dataClass, int tempSize) {
		executionTarget = dataClass;
		if (tempSize > 0 && tempSize < 80) SIZE = tempSize;
		else SIZE = 30;
		board = new int[SIZE][SIZE];		
		createAndShowGUI();
	}


	private void createAndShowGUI() {
		panel = new DrawingPanel();

		//JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame(title);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = frame.getContentPane();
		// content.setLayout(new BorderLayout(2,2));	
		content.add(panel, BorderLayout.CENTER);		
		frame.setSize(SCRSIZE, SCRSIZE); //may not be needed since my JPanel has a preferred size
		frame.setResizable(resizeMe);
		frame.setLocationRelativeTo( null );
		frame.addComponentListener(this);
		frame.pack();
		frame.setVisible(true);

		//once the panel is visible, initialize the graphics. (Is this before paintComponent is run?)
		panel.initGraphics();

	}

	private class DrawingPanel extends JPanel	//inner class
	{		
		int jpanelW, jpanelH;
		int blockX, blockY;		

		public DrawingPanel() {
			setBackground(COLOURLINES);
			//Because the panel size variables don't get initialized until the panel is displayed,
			//we can't do a lot of graphics initialization here in the constructor.
			this.setPreferredSize(new Dimension(SIZE*30,SIZE*30));
			this.setFont(new Font("Arial",0,36));
			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);			
		}

		//** Called by createGUI()
		private void initGraphics() {
			jpanelW = this.getSize().width;		
			jpanelH = this.getSize().height;	
			blockX = (int)((jpanelW/SIZE)+0.5);
			blockY = (int)((jpanelH/SIZE)+0.5);
			this.setFont(new Font("Arial",0,blockX));
			// System.out.println("init");
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);			
			Graphics2D g2 = (Graphics2D) g; 

			//colour in each square
			for (int i=0;i<SIZE;i++) {
				for (int j=0;j<SIZE;j++) {
					int n = board[i][j];
					if (n < colrArray.length ) {
						g.setColor(colrArray[n]);
					} else if (n == -1) {
						g.setColor(colrArray[colrArray.length-1]);					
					} else {
						g.setColor(colrArray[0]);						
					}
					g.fillRect(blockX*i, blockY*j, blockX, blockY);
					if (n >= 48 && n <= 90) {
						g.setColor(Color.BLACK);
						char c = (char)n;
						g.drawString(c+"", blockX*i+(int)(blockX*0.2), blockY*j+(int)(blockY*0.9)); //position letters properly.
					}					
				}
			}
			//Draw  grid
			g.setColor(COLOURLINES);
			if (gridLines == 0) return;
			if (gridLines == 2) g2.setStroke(new BasicStroke (2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
			for (int i=0;i<SIZE;i++) {
				g.drawLine(blockX*i,0,blockX*i,jpanelH);
				g.drawLine(0,blockY*i,jpanelW,blockY*i);
			}
			//reset lines
			//g2.setStroke(new BasicStroke (1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		}


		private class MyMouseListener extends MouseAdapter	{	//inner class inside DrawingPanel
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				//calculate which square you clicked on
				int i = (int)  x/blockX;
				int j = (int) y/blockY;	// blockY/y
				
				executionTarget.mouseProcessing(e, new Point(i,j));

			}		
		} //end of MyMouseListener class

	} //end of DrawingPanel class

	@Override
	public void componentResized(ComponentEvent e) {
		panel.initGraphics();
		panel.repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}

}
