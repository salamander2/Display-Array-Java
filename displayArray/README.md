## Master Branch
###  This version of the program takes control of the sequence of events from the user program.
 
**Order of Events (controlled by DisplayArray.java):**

1. Initialize
  1. get Size
  2. get Title
  3. get Resizable
  4. get Grid lines
  5. get AutoTimerInterval
  6. get Board data
2. draw graphics
3. if AutoTimerInterval is non-zero, 
  1. start the Timer
  2. indicate Timer is running in Title
  3. update data
  4. get updated data
  5. repaint screen
  6. sleep
  7. if updateData returns false, then stop timer.
4. on mouse click
  1. calculate the coordinates of the mouse click
  2. send the data to the user program (mouseXY)
  3. get updated data
  4. repaint screen
