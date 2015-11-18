# Display-Array-Java
Code to graphically display the contents of a square array of ints. 

----------

*Preamble:*
This program originated from a desire to make more use of 2D arrays in teaching Java programming. I thought that it would be really handy if there was a prebuit program that would handle the graphics of displaying a 2D array. Seeing a graphical representation of an array makes it more understandable, and should motivate students to experiment more.  It's not really that hard to do the graphics, but it takes a couple of weeks to teach, and often there is only time for that later on in the course (if at all).


------------
*Features:*
* Integer values of 0-15 are coloured in different colours. It would be nice also to be able to colour in a value of x=-1, but I haven't gotten around to that yet.
* Integer values of 48-90 are displayed as the ASCII character. This allows the user to display capital letters, digits 0-9, and some punctuation. 
* The array size can be set to anything from 1x1 to 80x80
* The array can be resized by dragging it once it's displayed
* The title can be set to whatever you want
* Grid lines can be enabled or disabled
* Mouse clicks send the coordinates of the mouse click back to the main program
* A timer can be set so that a program can be run without requiring mouse clicking (e.g. the game of Life).

*Requirements:*
* For simplicity's sake, the array must be a square array of integers (int). 
* The main user class can be called anything - however, we must have a way to send data back and forth between the user program and this program (called `DisplayArray.java`).

---------
**Versions**

* There are three versions with very similar functionality, but with one significant difference. 
* Each version is in a different branch of this repository.
* The master branch has the version where all of the control is surrendered to DisplayArray.java
* * The limitation with this is that there is no way to change a colour (e.g. if you want 1=RED). You have to edit DisplayArray.java
* 

There is a sample user program in each branch that can be used as a starting template for making array programs to work with DisplayArray.java.

:warning: Do not modify any other files (except perhaps to change the colours in DisplayArray.java).


