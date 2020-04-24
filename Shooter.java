
public class Shooter extends DefaultCritter {
	
	/// Instance Variables ///
	private int lives;
	private int score;
	
	/// Constructor ///
	public Shooter(double x0, double y0, String fileName, Draw w, int nLives)
	{
		super (x0, y0, fileName, w);
		lives = nLives;
		score = 0;
	}
	
	
	/// Instance methods ///
	
	// Rotate the Shooter(turret) with the amount of degrees and stopping at horizontal.
	public void rotateWith(double degrees) { 
		
		double currentAngle = super.angle();
		double newAngle = currentAngle + degrees;

		if (newAngle < 90 && newAngle > -90) {
			Shooter.super.angleWith(degrees);
		}
		else if (newAngle >= 90) {
			Shooter.super.setAngleTo(90);
		}
		else if (newAngle <= 90) {
			Shooter.super.setAngleTo(-90);
		}
	}
	
	// Move Shooter right
	public void moveRight(double units, double max) {
		
		double x = Shooter.super.getX();
		double radius = Shooter.super.getWidth() / 2.0;
		max -= radius;
		
		if (x + units >= max) {
			Shooter.super.moveTo(max, Shooter.super.getY());
		}
		else {
			Shooter.super.moveWith(units, 0);
		}
	}
	
	// Move Shooter left
	public void moveLeft(double units, double min) {
		
		double x = Shooter.super.getX();
		double radius = Shooter.super.getWidth() / 2.0;
		min += radius;
		
		if (x - units <= min) {
			Shooter.super.moveTo(min, Shooter.super.getY());
		}
		else {
			Shooter.super.moveWith(-1.0 * units, 0);
		}
	}
	
	// Rotate SHooter clockwise
	public void rotateCW(double degrees) {
		Shooter.this.rotateWith(-1.0 * degrees);
	}
	
	// Rotate Shooter counterclockwise
	public void rotateCCW(double degrees) {
		Shooter.this.rotateWith(1.0 * degrees);
	}
	
	public int lives() {
		return lives;
	}
	
	public void loseLife()
	{
		--lives;
	}
	
	public void resetLives(int n)
	{
		lives = n;
	}
	
	public void changeScore(int n)
	{
		score += n;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void resetScore(int n)
	{
		score = n;
	}
	
	
	
	/// Test Client ///
	public static void main(String[] args)
	{
		Draw window = new Draw();
		window.enableDoubleBuffering();
		
		Shooter player = new Shooter(0.5, 0.5, "rocket_1.png", window, 1);
		
		player.display();
		window.clear();
		window.show();

		
/*
		for (int i = 0; i < 120; ++i) { // Test CCW Rotation
			window.clear();
			player.rotateWith(1);
			StdOut.println(player.angle());
			player.display();
			window.show();
			window.pause(20);
		}
		
		for (int i = 0; i < 240; ++i) { // Test CW Rotation
			window.clear();
			player.rotateWith(-1);
			StdOut.println(player.angle());
			player.display();
			window.show();
			window.pause(20);
		}
		
*/
		for (int i = 0; i < 400; i++) { // Test moving right
			window.clear();
			player.moveRight(0.001, 1);
			player.display();
			window.show();
		}
		
		for (int i = 0; i < 200; i++) { // Test moving left
			window.clear();
			player.moveLeft(0.002, 0);
			player.display();
			window.show();
		}
		
		
	}

}
