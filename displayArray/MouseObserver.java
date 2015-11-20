package displayArray;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * This interface is require for passing mouseEvents back to the user program.
 * It allows the DisplayArray class to trigger an event in the user program 
 * (by running the user method "mouseProcessing". 
 * 
 * @param e is the mouse event
 * @param p is a point made up of the (x,y) coordinates of the mouse click, but in grid coordinates not pixels
 */
public interface MouseObserver {
    public void mouseProcessing(MouseEvent e, Point p); 

}