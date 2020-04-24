// Specifying the minimum API for in-game objects.

public interface Critter {
	
	// Move Critter to location.
	public void moveTo(double x, double y);
	
	// Move Critter with increment.
	public void moveWith(double x, double y);
	
	// Change angle with the increment.
	public void angleWith(double degrees);
	
	// Change angle to the value
	public void setAngleTo(double degrees);
	
	// Return the angle of Critter.
	public double angle();
	
	// Return the x-coordinate of Critter.
	public double getX();
	
	// Return the y-coordinate of Critter.
	public double getY();
	
	// Returns the width of Critter.
	public double getWidth();
	
	// Returns the height of Critter.
	public double getHeight();
	
	// Change the image used for display
	public void changeImage(String file);
	
	// Returns the name of the image used for display.
	public String getImageFileName();
	
	// Display the Critter.
	public void display();
	
	
}
