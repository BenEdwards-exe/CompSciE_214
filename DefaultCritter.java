// Implements the default methods of the Critter API.

public class DefaultCritter implements Critter {
	
	/// Instance Variables ///
	private double rx;
	private double ry;
	private double angle = 0;
	private Picture image;
	private String imageName;
	public final Draw window;
	
	/// Constructor ///
	public DefaultCritter(double x0, double y0, String fileName, Draw w)
	{
		rx = x0;
		ry = y0;
		window = w;
		imageName = fileName;
		image = new Picture(imageName);
	}
	
	
	/// Instance Methods ///
	
	// Move Critter to location.
	public void moveTo(double x, double y)
	{
		rx = x;
		ry = y;
	}
	
	// Move Critter with increment.
	public void moveWith(double x, double y)
	{
		rx += x;
		ry += y;
	}
	
	// Change angle with the increment.
	public void angleWith(double degrees)
	{
		angle += degrees;
	}
	
	// Change angle to the value
	public void setAngleTo(double degrees)
	{
		angle = degrees;
	}
	
	// Display the Critter.
	public void display()
	{	
		window.picture(rx, ry, imageName, angle);
	}
	
	// Display array of Critter.
	public static void display(Critter[] critter)
	{	
		for (int i = 0; i < critter.length; i++) {
			if(critter[i] != null) {
				critter[i].display();
			}
		}
	}
	
	// Return the angle of Critter.
	public double angle()
	{
		return angle;
	}
	
	// Return the x-coordinate of Critter.
	public double getX()
	{
		return rx;
	}
	
	// Return the y-coordinate of Critter.
	public double getY()
	{
		return ry;
	}
	
	// Returns the width of Critter.
	public double getWidth()
	{
		return image.width();
	}
	
	// Returns the height of Critter.
	public double getHeight()
	{
		return image.height();
	}
	
	public void changeImage(String fileName)
	{
		imageName = fileName;
		image = new Picture(imageName);
	}
	
	// Returns the name of the image used for display.
	public String getImageFileName()
	{
		return imageName;
	}
	
	
	// Test Client.
	public static void main(String[] args)
	{
		// Window that will be used.
		Draw window = new Draw();
		window.setCanvasSize(600, 600);
		window.setXscale();
		window.setYscale();
		
		DefaultCritter rocket = new DefaultCritter(0.04, 0.04, "rocket_1.png", window);
		

		
		rocket.display(); // test display
		window.pause(2000);
		
		rocket.moveTo(0.8, 0.6); // test moveTo
		rocket.setAngleTo(60);; // test angleTo
		rocket.display();
		window.pause(2000);
		
		rocket.moveWith(-0.2, 0.2); // test moveWith
 		rocket.angleWith(-30); // test angleWith
		rocket.display();
		
	}

}
