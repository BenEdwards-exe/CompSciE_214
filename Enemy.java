import java.awt.event.KeyEvent;

public class Enemy extends DefaultCritter {
	
	/// Instance variables ///
	private double maxRight;
	private double maxLeft;
	private boolean movingLeft;
	private int score;
	
	/// Constructor ///
	public Enemy(double x0, double y0, String fileName, Draw w, int nScore) {
		super(x0, y0, fileName, w);
		
		maxRight = x0 + 40.0;
		maxLeft = x0 - 40.0;
		movingLeft = true;
		score = nScore;
	}
	
	
	/// Instance Methods ///	
	
	// Move enemy in their sequence: Left, Down, Right, Down
	public void moveEnemy(double horizontal, double vertical)
	{
		double enemyX = Enemy.super.getX();
		
		if (movingLeft) { // Move left
			super.moveWith(-1.0 * horizontal, 0);
			if (enemyX <= maxLeft) {
				movingLeft = false;
				super.moveWith(0, -1.0 * vertical); // Move down
			}
		}
		
		if (!movingLeft) { // Move right
			super.moveWith(horizontal, 0);
			if (enemyX >= maxRight) {
				movingLeft = true;
				super.moveWith(0, -1.0 * vertical); // Move down
			}
		}
	} // moveEnemy
	
	// Check if enemies reached bottom of screen
	public boolean isAtBottom(double bottom) {
		
		boolean atBottom;
		
		double r = super.getHeight() / 2.0;
		double y = super.getY();
		
		if ((y - r) <= bottom) { // at bottom of screen.
			atBottom = true;
		}
		else {
			atBottom = false;
		}
		
		return atBottom;
	} // atBottom
	
	// Check if enemy made contact with player
	public boolean isContactMade(Shooter player)
	{
		boolean contact;
		
		double enemyRadius = super.getWidth() / 2.0;
		double enemyX = super.getX();
		double enemyY = super.getY();
		
		double playerRadius = player.getHeight() / 2.0;
		double playerX = player.getX();
		double playerY = player.getY();
		
		double dx = Math.abs(enemyX - playerX);
		double dy = Math.abs(enemyY - playerY);
		double distance = Math.sqrt(dx*dx + dy*dy);
		
		if (distance <= (enemyRadius + playerRadius)) {
			contact = true;
		}
		else {
			contact = false;
		}
		
		return contact;
	}
	
	
	public int getScore()
	{
		return score;
	}
	
	
	/// Static Methods ///
	
	// Check if enemy is alive
	public static boolean isAlive(Enemy enemy)
	{
		if (enemy != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Check if an array of Enemy is alive
	public static boolean isAllDead(Enemy[][] enemy)
	{
		boolean allDead = true; // assume that all is dead.
		
		for (int i = 0; i < enemy.length; i++) {
			for (int j = 0; j < enemy[i].length; j++) {
				if (enemy[i][j] == null && allDead == true) { // if current is dead and everyone before him
					allDead = true;
				}
				else { // someone is alive so not everyone is dead.
					allDead = false;
					break;
				}
			}
		}
		return allDead;
	}
	
	
	public static void destroy(Enemy enemy)
	{
		enemy = null;
	}
	
	
	public static void destroy(Enemy[][] enemy, int i, int j)
	{
		enemy[i][j] = null;
	}
	
	// Display array of Enemy
	public static void display(Enemy[][] enemies)
	{
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					enemies[i][j].display();
				}
			}
		}
	}
	
	
	
	
	
	// Test Client
	public static void main(String[] args)
	{
		Draw testScreen = new Draw();
		testScreen.setCanvasSize(700, 600);
		testScreen.setXscale(0, 700);
		testScreen.setYscale(0, 600);
		testScreen.enableDoubleBuffering();
		
		// Test single enemy
		Enemy single = new Enemy(350, 200, "enemy_1.png", testScreen, 100);
		
		while (!testScreen.isKeyPressed(KeyEvent.VK_Q)) {
			testScreen.clear();
			single.moveEnemy(2, 10);
			single.display();
			
			if (single.isAtBottom(0)) {
				StdOut.println("At bottom");
				break;
			}
			
			testScreen.show();
		}
		
		
		
		// Test Multiple Enemies
		Enemy[][] enemy = new Enemy[4][10];
		
		testScreen.clear();
		testScreen.picture(350, 300, "Starry_sky.jpg");
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				
				enemy[i][j] = new Enemy(60 + j*60, 580 - i*40, "enemy_1.png", testScreen, 100);
				enemy[i][j].display();
			}
		}
		
		testScreen.show();
		
		while (!testScreen.isKeyPressed(KeyEvent.VK_Q)) {
			testScreen.clear();
			testScreen.picture(350, 300, "Starry_sky.jpg");
			if (testScreen.isKeyPressed(KeyEvent.VK_SPACE)) { // destroy enemy
				Enemy.destroy(enemy, 1, 1);
			}
			
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[0].length; j++) {
					if (Enemy.isAlive(enemy[i][j])) {
						enemy[i][j].moveEnemy(10, 10);
						enemy[i][j].display();
					}
				}
			}
			
			
			testScreen.show();
			
		}
		
		StdOut.println("Quit");
		
		
		
	} // main

}
