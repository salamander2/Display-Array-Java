package displayArray;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*****************************************************************************
 * This version of the program takes control of the sequence of events from 
 * the user program.
 * 
 * Order of Events:
 *    1. Initialize
 *    	a. get Size
 *      b. get Title
 *      c. get Resizable
 *      d. get Grid lines
 *      e. get AutoTimerInterval
 *      f. get Board data
 *   2. draw graphics
 *   3. if AutoTimerInterval is non-zero, 
 *   	a. start the Timer
 *   	b. indicate Timer is running in Title
 *      c. update data
 *      d. get updated data
 *      e. repaint screen
 *      f. sleep
 *      g. if updateData returns false, then stop timer.
 *   4. on mouse click
 *      a. calculate the coordinates of the mouse click
 *      b. send the data to the user program (mouseXY)
 *      c. get updated data
 *      d. repaint screen
 * 
 *****************************************************************************/
public class DisplayArray implements ComponentListener {

	//Global variables and their default values
	//static int SCRSIZE = 720;	//screen size (not used. We use number of squares in SIZE *30 pixels each for starting size)  
	static int SIZE = 32; //board size

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
	private JFrame frame;
	private DrawingPanel panel;
	private int[][] board;
	private String title = "Display of Array Data";
	private boolean resizeMe = true;
	private int gridLines = 2;
	private int interval = 0;

	private ArrayGame ago;

	//constructor
	DisplayArray(ArrayGame agoObject) {	
		this.ago = agoObject;

		//Get all of the setup information from the ArrayGame class
		int tempSize = ago.setSize();
		if (tempSize > 0 && tempSize < 80) SIZE = tempSize;
		board = new int[SIZE][SIZE];

		String s = ago.setTitle();
		if (s.length() > 0) title = s;

		resizeMe = ago.setResizable();
		gridLines = ago.setGridLines();
		board = ago.getArray();

		int interval = ago.autoTimerInterval();
		if (interval > 10) this.interval = interval;

		createAndShowGUI();

		if (interval > 0) startTimer();
	}


	private void createAndShowGUI() {
		panel = new DrawingPanel();

		//frame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame(title);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = frame.getContentPane();	
		content.add(panel, BorderLayout.CENTER);		
		//frame.setSize(SCRSIZE, SCRSIZE); //may not be needed since my JPanel has a preferred size
		frame.setResizable(resizeMe);		
		frame.addComponentListener(this);
		frame.pack();
		frame.setLocationRelativeTo( null ); //Interesting. This only works after setSize() or pack()
		frame.setVisible(true);
		
		//once the panel is visible, initialize the graphics. (Is this before paintComponent is run?)
		panel.initGraphics();

	}

	private void startTimer() {
		myAL myAL = new myAL();
		javax.swing.Timer timer = new javax.swing.Timer(interval, myAL);
		myAL.getTimerObject(timer);
		timer.start();
		frame.setTitle(title + " ... timer running ...");
	}

	//Actionlistener for timer inner class
	private class myAL implements ActionListener {
		javax.swing.Timer timer;

		void getTimerObject(javax.swing.Timer timer){
			this.timer = timer;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ago.updateData() == false) {
				timer.stop();
				frame.setTitle(title + " ... timer stopped.");
			}
			board = ago.getArray();												
			panel.repaint();
		}
	}
	
	//DrawingPanel inner class
	private class DrawingPanel extends JPanel	
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

		//** Called by createGUI() AND anytime the JFrame is resized.
		private void initGraphics() {
			jpanelW = this.getSize().width;		
			jpanelH = this.getSize().height;	
			blockX = (int)((jpanelW/SIZE)+0.5);
			blockY = (int)((jpanelH/SIZE)+0.5);
			this.setFont(new Font("Arial",0,(blockX+blockY)/2));
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


		class MyMouseListener extends MouseAdapter	{	//inner class inside DrawingPanel
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				//calculate which square you clicked on
				int i = (int) x/blockX;
				int j = (int) y/blockY;
				boolean leftClick = true;
				if (e.getButton() != MouseEvent.BUTTON1) leftClick = false;
				
				ago.mouseXY(i, j, leftClick);
				board = ago.getArray();												
				panel.repaint();
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
