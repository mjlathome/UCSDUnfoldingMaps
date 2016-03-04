package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet{
	
	public void setup()
	{
		size(400, 400);
		background(200, 200, 200);	// grey
	}
	
	public void draw()
	{
		fill(255, 255, 0);	// yellow
		ellipse(200, 200, 390, 390);	// head
		
		fill(0, 0, 0);	// black
		ellipse(120, 130, 50, 70);	// left eye
		ellipse(280, 130, 50, 70);	// right eye
		
		// mouth
		noFill();
		// strokeWeight(4);
		arc(200, 280, 75, 75, 0, PI); 
	}
}
