import java.awt.Font;
import java.awt.event.KeyEvent;

public class Bunker extends DefaultCritter {

	/// Instance Variables ///
	private int health;
	private int minHealth;
	
	// Constructor
	public Bunker(double x0, double y0, String fileName, Draw w) {
		super(x0, y0, fileName, w);
		health = 100;
		minHealth = 0;
	}
	
	/// Instance methods ///
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMinHealth()
	{
		return minHealth;
	}
	
	public void loseHealth()
	{
		if (health >= 10) { // Health can reach a minimum of zero.
			health -= 10;
		}
	}
	
	@Override
	public void changeImage(String file)
	{
		// Since the images differ with height, when an image is changed
		// the bunker needs to be moved to remain "at the same position"
		
		double oldH = super.getHeight();
		double oldW = super.getWidth();
		
		if (health >= 10 && health <= 100) {;
			super.changeImage(file);
		}
		
		double newH = super.getHeight();
		double newW = super.getWidth();
		
		double dH = (newH - oldH) / 2.0;
		double dW = (newW - oldW) / 2.0;
		
		super.moveWith(dW, dH);
		
	}
	
	
	public void display()
	{
		int h = this.getHealth();
		String health = Integer.toString(h);
		
		if ( h >= 10 && h <= 100) {
			super.display();
			
			// Display Bunker health inside Bunker
			Font font1 = new Font("Apple Casual", Font.BOLD, 18);
			super.window.setFont(font1);
			super.window.text(Bunker.super.getX(), Bunker.super.getY(), health);
		}
	}
	
	
	// Test Client
	public static void main(String[] args)
	{
		for (int i = 0; i < 100; i++) {
			StdOut.println(StdRandom.uniform(1, 5));
		}
		Draw testScreen = new Draw();
		testScreen.setCanvasSize(700, 600);
		testScreen.setXscale(0, 700);
		testScreen.setYscale(0, 600);
		testScreen.enableDoubleBuffering();
		
		Bunker bunker = new Bunker(350, 200, "bunker100.png", testScreen);
		StdOut.println(bunker.getHealth());
		
		Missile[] missile = new Missile[1];
		
		while (!testScreen.isKeyPressed(KeyEvent.VK_Q)) {
			
			if (testScreen.isKeyPressed(KeyEvent.VK_SPACE)) {
				// Testing if losing life happens when
				// spacebar is pressed.
				
				missile[0] = new Missile(350, 400, "missile_1.png", testScreen, 2.5, 180.0);
				

			}
			
			if (bunker != null) {
				if (bunker.getHealth() <= bunker.getMinHealth()) {
					bunker = null;
				}
			}
			
			if (bunker != null) {
				if (Missile.isHit(bunker, missile))
				bunker.loseHealth();
			}
			
			testScreen.clear();
			
			if (bunker != null) {
				bunker.display();
				StdOut.println(bunker.getHealth());
			}
			
			if (missile[0] != null) {
				missile[0].moveMissile();
				missile[0].display();
			}
			
			
			testScreen.show();
			
		}
		
		StdOut.println("Quit");
		System.exit(0);
	} // Main
	

}
