import java.awt.event.KeyEvent;

public class Missile extends DefaultCritter {
	
	/// Instance Variables ///
	private double vx;
	private double vy;
	private double v;
	
	/// Constructor ///
	public Missile(double x0, double y0, String fileName, Draw w, Double speed, double degrees) {
		
		super(x0, y0, fileName, w);
		
		Missile.super.setAngleTo(degrees);
		
		v = speed;
		vx = Missile.calc_vx(v, Missile.super.angle());
		vy = Missile.calc_vy(v, Missile.super.angle());
	}
	
	/// Instance Methods ///
	
	// Check if Missile is in the screen/bounds
	public boolean isInBounds(double minX, double maxX, double minY, double maxY) {
		
		boolean inside;
		
		double missileX = Missile.super.getX();
		double missileY = Missile.super.getY();
		
		if ((missileX >= minX && missileX <= maxX) && (missileY >= minY && missileY <= maxY)) {
			inside = true;
		}
		else {
			inside = false;
		}
		
		return inside;
	}
	
	// Move a single Missile
	public void moveMissile()
	{
		Missile.super.moveWith(vx, vy);
	}
	
	// Check if Missile hit in a target radius
	public boolean isHit(double radius, double px, double py) {
		
		boolean hit = false;

		double missileDX = Math.abs(px - Missile.super.getX());
		double missileDY = Math.abs(py - Missile.super.getY());
		double distance = Math.sqrt(missileDX*missileDX + missileDY*missileDY);
		
		if (distance <= radius) {
			hit = true;
		}
		
		return hit;
	}
	
	// Check if Missile hit a DefaultCritter
	public static boolean isHit(DefaultCritter critter, Missile[] missile) {
		
		boolean hit = false;
		
		double radius = critter.getWidth() / 2.0;
		radius *= 1.1;
		
		for (int i = 0; i < missile.length; i++) {
			if (critter != null && missile[i] != null) {
				double missileDX = Math.abs(critter.getX() - missile[i].getX());
				double missileDY = Math.abs(critter.getY() - missile[i].getY());
				double distance = Math.sqrt(missileDX*missileDX + missileDY*missileDY);
				
				if (distance <= radius) {
					hit = true;
					missile[i] = null;
					break;
				}
			}
		}
		
		return hit;
	}
	
	
	/// Static Methods ///
	
	// Calculate the horizontal velocity
	public static double calc_vx(double speed, double angle) {
		
		double velocity;
		double direction;
		
		if (angle > 0.0 && angle < 180.0) { // Left
			direction = -1.0;
		}
		else if (angle < 0.0 && angle > -180.0) { // Right
			direction = 1.0;
		}
		else { // Up or Down
			direction = 0;
		}
		
		double angleX = (90.0 - Math.abs(angle)) * Math.PI / 180.0 ; // Angle with respect to x-axis in radians.
		
		velocity = speed * Math.cos(Math.abs(angleX)) * direction;
		
		return velocity;
	}
	
	// Calculate vertical velocity
	public static double calc_vy(double speed, double angle) {
		
		double velocity;
		double direction;
		
		if (angle < 90.0 && angle > -90.0) { // Up
			direction = 1.0;
		}
		else if (angle > 90.0 && angle < 270.0) { // Down
			direction = -1.0;
		}
		else { // Left and Right
			direction = 0.0;
		}
		
		double angleX = (90.0 - Math.abs(angle)) * Math.PI / 180.0; // Angle with respect to a-axis in radians.
		
		velocity = speed * Math.sin(Math.abs(angleX)) * direction;
		
		return velocity;
	}

	// Move an array of Missile and penalize Shooter for missing
	public static void moveMissiles(Missile[] missiles, Shooter player)
	{
		for (int i = 0; i < missiles.length; i++) {
			if (missiles[i] != null) {
				if (missiles[i].isInBounds(0, 700, 0, 600)) {
					missiles[i].moveMissile();
				}
				else {
					missiles[i] = null;
					player.changeScore(-10); // Penalty for missing target
				}
			}
		}
	}
	
	// Move an array of Missile
	public static void moveMissiles(Missile[] missiles)
	{
		for (int i = 0; i < missiles.length; i++) {
			if (missiles[i] != null) {
				if (missiles[i].isInBounds(0, 700, 0, 600)) {
					missiles[i].moveMissile();
				}
				else {
					missiles[i] = null;
				}
			}
		}
	}
	
	
	/// Test Client ///
	public static void main(String[] args)
	{
		Draw testScreen = new Draw();
		testScreen.setCanvasSize(700, 600);
		testScreen.setXscale(0, 700);
		testScreen.setYscale(0, 600);
		testScreen.enableDoubleBuffering();
		
		double x = Missile.calc_vx(20, 0);
		double y = Missile.calc_vy(20, 0);
		StdOut.println(x + "\n" + y);
		
		Missile missile1 = new Missile(350, 200, "missile_1.png", testScreen, 1.0, -20);
		Missile missile2 = new Missile(130, 100, "missile_1.png", testScreen, 1.0, -45);
		Missile missile3 = new Missile(200, 300, "missile_1.png", testScreen, 1.0, 180.0);
		Enemy enemy = new Enemy(500, 500, "enemy_1.png", testScreen, 100);
		
		while (!testScreen.isKeyPressed(KeyEvent.VK_Q)) {
			testScreen.clear();
			
			missile1.moveMissile();
			missile1.display();
			
			missile3.moveMissile();
			missile3.display();
			
			if (Enemy.isAlive(enemy)) {
				enemy.display();
				if (missile2.isHit(enemy.getWidth() / 2.0, enemy.getX(), enemy.getY())) {
					enemy = null;
					missile2 = null;
					StdOut.println("Destroyed");
				}
			}
			
			if (missile2 != null) {
				missile2.moveMissile();
				missile2.display();
			}
			

			
			
			testScreen.show();
		}
		
		StdOut.println("Quit");
		
	}
	
}
