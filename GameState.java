// Represents the state of the game.
// Responsible for managing updates every step
// in the game loop (process input, update state of various objects, update screen).

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class GameState {
	

	public static int level = 1; // The level being played
	
	
	// Display the into screen
	public static Status introScreen(Draw screen, Status gameStatus) {
		
		for (int i = 0; i <= 400; i += 4) { // Display the intro title
			screen.clear();
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			// MainHeader and all similar font headers were made on 
			// "https://fontmeme.com/futuristic-fonts/"  with the 'Terminator' font.
			screen.picture(350, -100 + i, "MainHeader_50.png");
			screen.show();		
			
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User quits
				gameStatus = Status.QUIT;
				return gameStatus;
			}
		}
		
		screen.pause(500); // Pause for effect
		
		// Read the High Score from a file
		In highScore = new In("HighScore.txt");
		String all = highScore.readAll();
		String[] allArr;
		allArr = all.split(",");
		boolean isScore = !all.isEmpty();
		
		while (gameStatus == Status.INTRO) {
			
			/// Events and Logic ///
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) { // user wants to continue
				screen.pause(400);
				gameStatus = Status.INSTRUCTIONS;
			}
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // user quits
				gameStatus = Status.QUIT;
			}
			
			/// Animation ///
			screen.clear();
			
			// Backround
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			
			// Intro Title
			screen.picture(350, 300, "MainHeader_50.png");
			
			// Space to continue
			Font font1 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font1);
			screen.setPenColor(new Color(228, 255, 68));
			screen.text(350, 100, "<Press Enter To Continue>");
			
			// High Score
			if (isScore) {
				screen.text(350, 50, "High Score: " + allArr[0]);
			}
			else {
				screen.text(350, 50, "High Score: " + "none so far");
			}
			
			screen.show();
		}
		
		return gameStatus;
	}

	// Display the game instructions
	public static Status instructions(Draw screen, Status gameStatus) 
	{
		while (gameStatus == Status.INSTRUCTIONS) {
			/// Events and Logic ///
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User Quits
				gameStatus = Status.QUIT;
			}
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) { // Player wants to continue
				gameStatus = Status.HOME;
				screen.pause(400);
			}
			
			/// Animation ///
			screen.clear();
			
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			screen.setPenColor(new Color(228, 255, 68));
			
			Font font1 = new Font("Apple Casual", Font.BOLD, 24);
			screen.setFont(font1);
			screen.text(350, 550, "Instructions:");
			
			Font font2 = new Font("Apple Casual", Font.BOLD, 18);
			screen.setFont(font2);
			screen.text(350, 500, "You have been contracted by the Galactic Gunship Federation ");
			screen.text(350, 480, "to destroy an enemy force of alien ships. The aliens have ");
			screen.text(350, 460, "assured us that they will be sending multiple waves of ships.");
			screen.text(350, 440, "Destroy all the ships by shooting them with your missiles.");
			screen.text(350, 420, "If an alien ship touches you or gets passed you, you will be");
			screen.text(350, 400, "defeated. If you are hit 3 times by enemy missiles you will die.");
			screen.text(350, 380, "Different enemies deliver a different amount of points if destroyed.");
			screen.text(350, 360, "You lose points when you miss an enemy. Aim well.");
			screen.text(350, 340, "Good Luck!");
			
			Font font3 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font3);
			screen.text(350, 240, "<Press Enter To Continue>");
			screen.text(350, 210, "<Press Q To Quit Any Time>");
			
			
			screen.show();
		}
		return gameStatus;
	}
	
	// Display the home screen
	// Display the home screen with options for the
	// player to select.
	public static Status homeScreen(Draw screen, Status gameStatus)
	{		
		// Selector to indicate which option is picked
		DefaultCritter selector = new DefaultCritter(200, 250, "selector.png", screen);
		
		Color c1 = new Color(0);
		c1 = Color.GREEN;
		screen.setPenColor(c1);
		long time = System.currentTimeMillis(); // For flashing the box around the option.
		
		
		while (gameStatus == Status.HOME) {
			
			/// Events and Logic///
			
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User quits
				gameStatus = Status.QUIT;
			}
			
			// If the selector is moved
			if (screen.isKeyPressed(KeyEvent.VK_UP)) {
				selector.moveTo(selector.getX(), 250);
			}
			else if (screen.isKeyPressed(KeyEvent.VK_DOWN)) {
				selector.moveTo(selector.getX(), 210);
			}
			
			if (screen.isKeyPressed(KeyEvent.VK_ENTER) ) {
				if (selector.getY() == 250) {
					screen.pause(200);
					gameStatus = Status.SINGLECONTROLS;
				}
				else if (selector.getY() == 210) {
					screen.pause(200);
					gameStatus = Status.MULTICONTROLS;
				}
			}
			
			/// Animation ///
			screen.clear();
			
			// Backround found on "http://primusdatabase.com/index.php?title=File:Starry_sky.jpg"
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);

			// Display Title
			screen.picture(350, 500, "MainHeader_35.png");
			
			// Display Options.
			screen.picture(350, 320, "Selectmode.png");
			screen.picture(350, 250, "Single-player.png");
			screen.picture(350, 210, "Multiplayer.png");
			
			// Display the selector
			selector.display();
			
			// Display box around selected choice and flicker the box.
			if (System.currentTimeMillis() >= time + 200.0 && System.currentTimeMillis() < time + 400.0) {
				c1 = new Color(48, 181, 18);
			}
			else if (System.currentTimeMillis() >= time + 400.0){
				c1 = new Color(100, 255, 45);
				time = System.currentTimeMillis();
			}
			screen.setPenColor(c1);
			screen.rectangle(350, selector.getY(), 125, 20);
			
			// Display instructions at bottom of screen
			screen.setPenColor(new Color(228, 255, 68));
			Font font1 = new Font("Arial", Font.BOLD, 16);
			screen.setFont(font1);
			screen.textLeft(450, 75, "UP and DOWN to move cursor.");
			screen.textLeft(450, 50, "ENTER to select.");
			screen.textLeft(450, 25, "Q to QUIT.");
			
			screen.show();
		}
		
		return gameStatus;
	} // home screen
	
	// Display singleplayer controls
	// Display single player controls
	public static Status singleControls(Draw screen, Status gameStatus) {
	
		while (gameStatus == Status.SINGLECONTROLS) {
			
			/// Events and Logic ///
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User quits
				gameStatus = Status.QUIT;
			}
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) {
				gameStatus = Status.SINGLELEVEL;
				screen.pause(200);
			}
			if (screen.isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
				gameStatus = Status.HOME;
			}
			
			/// Animation ///
			screen.clear();
			
			// Backround
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			
			// Controls
			screen.picture(350, 550, "Single-player.png");
			screen.picture(350, 500, "Controls.png");
			Font font1 = new Font("Arial", Font.BOLD, 18);
			screen.setFont(font1);
			screen.setPenColor(new Color(228, 255, 68));
			screen.textLeft(240, 450, "RIGHT  -  move right");
			screen.textLeft(240, 425, "LEFT   -  move left");
			screen.textLeft(240, 400, "UP     -  rotate clockwise");
			screen.textLeft(240, 375, "DOWN   -  rotate counterclockwise");
			screen.textLeft(240, 350, "SPACE  -  fire turret");
			screen.textLeft(240, 325, "Q      -  quit game");
			
			// Space to continue
			Font font2 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font2);
			screen.text(350, 200, "<Press Enter To Continue>");
			screen.text(350, 170, "<Press Backspace To Go Back>");
			
			
			screen.show();
		}
		
		
		return gameStatus;
	}
	
	// Run a singleplayer level
	public static Status singleLevels(Draw screen, Shooter player, Status gameStatus) 
	{
		
		// Move the player to the starting position
		player.moveTo(350, 25);
		player.setAngleTo(0);
		
		// Array of missiles allowed for player
		Missile[] playerM = new Missile[4];
		
		// Array of missiles allowed by enemies
		Missile[] enemyM = new Missile[5];
		
		// Enemies
		Enemy[][] enemy = new Enemy[5][10];
		for (int i = 0; i < enemy.length; i++) {
			for (int j = 0; j < enemy[i].length; j++) {
				if (level - i > 1) { // Type 2 enemies increase with one row per level.
					enemy[i][j] = new Enemy(80 + j*60, 580 - i*40, "enemy_2.png", screen, 200);
				}
				else {
					enemy[i][j] = new Enemy(80 + j*60, 580 - i*40, "enemy_1.png", screen, 100);
				}
			}
		}
		
		// Bunkers
		Bunker[] bunker = new Bunker[3];
		for (int i = 0; i < bunker.length; i++) {
			bunker[i] = new Bunker(150 + 200 * i, 150, "bunker100.png", screen);
		}
		
		
		// Get the system time for missile timing.
		long playerM_Timer = System.currentTimeMillis();
		long enemyM_Timer = System.currentTimeMillis();
		
		// Game Variables //
		double playerMaxX = 700.0; // player maximum right 
		double playerMinX = 0.0; // player minimum left
		double playerVX = 6.0; // player x speed;
		double playerRotateV = 3.0; // player rotate speed;
		double playerMissileV = 12.0; // player missile speed;
		double playerMissileT = 200.0; // milliseconds between missiles.
		double enemyVX = 1.0 + level * 0.1;
		double enemyVY = 8.0;
		double enemyMissileV = 8.0 + level * 0.2;
		double enemyMissileT = 1000.0 - level * 30.0;
		
		// Checking time between frames
		long total1, total2;
		
		while (gameStatus == Status.SINGLELEVEL) {
			total1 = System.currentTimeMillis();
			
			/// Events and Logic ///
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User quits.
				gameStatus = Status.QUIT;
			}
			if (screen.isKeyPressed(KeyEvent.VK_BACK_SPACE)) { // Go to intro screen.
				gameStatus = Status.HOME;
			}
			
			// Moving and Rotating player
			if (screen.isKeyPressed(KeyEvent.VK_RIGHT)) { // Move right
				player.moveRight(playerVX, playerMaxX);
			}
			if (screen.isKeyPressed(KeyEvent.VK_LEFT)) { // Move left
				player.moveLeft(playerVX, playerMinX);
			}
			if (screen.isKeyPressed(KeyEvent.VK_UP)) { // Rotate CW
				player.rotateCW(playerRotateV);
			}
			if (screen.isKeyPressed(KeyEvent.VK_DOWN)) { // Rotate CCW
				player.rotateCCW(playerRotateV);
			}
			
			
			// Firing a player missile
			if (screen.isKeyPressed(KeyEvent.VK_SPACE)) {
				if (System.currentTimeMillis() >= playerM_Timer + playerMissileT ) {
					// If 'playerMissileT' millisec passed since previous missile fire
					for (int i = 0; i < playerM.length; i++) {
						if (playerM[i] == null) {
							playerM[i] = new Missile(player.getX(), player.getY(), "missile_1.png", screen, playerMissileV, player.angle());
							int n = StdRandom.uniform(2, 4);
							StdAudio.play("phasers" + n + ".wav");
							// Audio files from:
							// http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/phasesr2.wav
							// http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/phasers3.wav
							break;
						}
					}
					playerM_Timer = System.currentTimeMillis(); // Reset the timer.
				}
			}
			
			
			// Firing an enemy missile
			if (System.currentTimeMillis() >= enemyM_Timer + enemyMissileT) {
				// if 'enemyMissileT' millisec passed since prevoius missile fire
				
				int row = StdRandom.uniform(0, enemy.length);
				int col = StdRandom.uniform(0, enemy[row].length);
				
				if (Enemy.isAlive(enemy[row][col])) {
					
					double enemyX = enemy[row][col].getX();
					double enemyY = enemy[row][col].getY();
					
					for (int i = 0; i < enemyM.length; i++) {
						if (enemyM[i] == null) {
							enemyM[i] = new Missile(enemyX, enemyY, "missile_2.png", screen, enemyMissileV, 180);
							break;
						}
					}
				}
				enemyM_Timer = System.currentTimeMillis();
			}
			
			// Move player missiles
			Missile.moveMissiles(playerM, player);
			
			// Move enemy missiles
			Missile.moveMissiles(enemyM);
			
			// Move enemies and Check if they have reached bottom or hit player
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[i].length; j++) {
					
					boolean enemyAlive = Enemy.isAlive(enemy[i][j]);
					
					if (enemyAlive) {
						enemy[i][j].moveEnemy(enemyVX, enemyVY);
					}
					if (enemyAlive && enemy[i][j].isAtBottom(0.0)) {
						// if enemy is alive and reached bottom
						gameStatus = Status.GAMEOVER;
						break;
					}
					if (enemyAlive && enemy[i][j].isContactMade(player)) {
						// if enemy is alive and made contact with player
						StdOut.println("Contact");
						gameStatus = Status.GAMEOVER;
						break;
					}
				} // for
				if (gameStatus == Status.GAMEOVER) {
					break; // Break out of outer for loop
				}
			} // for
			
			// Test if enemy is hit with a player missile
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[0].length; j++) {
					if (Enemy.isAlive(enemy[i][j])) {
						if (Missile.isHit(enemy[i][j], playerM)) {
							StdAudio.play("peeeooop_x.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/peeeooop_x.wav"
							player.changeScore(enemy[i][j].getScore());
							Enemy.destroy(enemy, i, j);
						}
					}
				}
			}
			
			// Test if all the enemies are defeated
			if (Enemy.isAllDead(enemy)) {
				gameStatus = Status.LEVELCOMPLETE;
			}
			
			// Test if player is hit with an enemy missile
			if (Missile.isHit(player, enemyM)) {
				StdAudio.play("rain_man_uh_oh.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/movies/rain_man/rain_man_uh_oh.wav"
				player.loseLife();
			}
			
			// Test if player is dead
			if (player.lives() <= 0) {
				gameStatus = Status.GAMEOVER;
			}
			
			// Test if bunker is destroyed
			for (int i = 0; i < bunker.length; i++) {
				if (bunker[i] != null) {
					if (bunker[i].getHealth() <= 0) {
						StdAudio.play("explosion_x.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/explosion_x.wav"
						bunker[i] = null;
					}
				}
			}
			
			// Test if bunker is hit
			for (int i = 0; i < bunker.length; i++) {
				if (bunker[i] != null) {
					if (Missile.isHit(bunker[i], enemyM)) {
						bunker[i].changeImage("bunker" + bunker[i].getHealth() + ".png");
						bunker[i].loseHealth();
					}
					if (Missile.isHit(bunker[i], playerM)) {
						bunker[i].changeImage("bunker" + bunker[i].getHealth() + ".png");
						bunker[i].loseHealth();
						
					}
				}
			}		
			
			/// Animation ///
			screen.clear(Draw.BLACK);
			
			// Display Player
			player.display();
			
			
			// Display bunker
			for (int i = 0; i < bunker.length; i++) {
				if (bunker[i] != null) {
					bunker[i].display();
				}
			}

			// Display Player Missiles
			Missile.display(playerM);
			
			// Display Enemy Missiles
			Missile.display(enemyM);
			
			// Display Enemies
			Enemy.display(enemy);
			
			// Display Player Lives
			Font font1 = new Font("Apple Casual", Font.BOLD, 18);
			screen.setFont(font1);
			screen.textLeft(620, 585, "Lives: " + player.lives());
			
			// Display Player Score
			screen.setFont(font1);
			screen.textLeft(10, 585, "Score: " + player.getScore());
			
			// Display Level
			screen.setFont(font1);
			screen.text(350, 585, "Level: " + level);

			
			screen.show();
			
			// Keep the frames below 50fps to avoid game moving to fast
			total2 = System.currentTimeMillis();
			int totalTime = (int) (total2 - total1);
			if (totalTime < 20) {
				screen.pause(20 - totalTime);
			}
			
		} // while
		
		playerM = null;
		enemyM = null;
		bunker = null;
		
		return gameStatus;
	}
	
	// Display multiplayer controls
	public static Status multiControls(Draw screen, Status gameStatus) {
	
		
		while (gameStatus == Status.MULTICONTROLS) {
			
			/// Events and Logic ///
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User quits
				gameStatus = Status.QUIT;
			}
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) {
				gameStatus = Status.MULTILEVEL;
				screen.pause(200);
			}
			if (screen.isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
				gameStatus = Status.HOME;
			}
			
			
			
			
			/// Animation ///
			screen.clear();
			
			// Backround
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			
			// Controls
			screen.picture(350, 550, "Multiplayer.png");
			screen.picture(350, 500, "Controls.png");
			Font font1 = new Font("Arial", Font.BOLD, 18);
			screen.setFont(font1);
			screen.setPenColor(new Color(228, 255, 68));
			
			// Player 1
			screen.textLeft(190, 450, "Player 1:");
			screen.textLeft(190, 425, "D  -  move right");
			screen.textLeft(190, 400, "A  -  move left");
			screen.textLeft(190, 375, "W  -  rotate CW");
			screen.textLeft(190, 350, "S  -  rotate CCW");
			screen.textLeft(190, 325, "H  -  fire turret");
			screen.textLeft(190, 300, "Q  -  quit game");
			
			// Player 2
			screen.textLeft(390, 450, "Player 2:");
			screen.textLeft(390, 425, "RIGHT  -  move right");
			screen.textLeft(390, 400, "LEFT   -  move left");
			screen.textLeft(390, 375, "UP     -  rotate CW");
			screen.textLeft(390, 350, "DOWN   -  rotate CCW");
			screen.textLeft(390, 325, "NUM_0  -  fire turret");
			screen.textLeft(390, 300, "Q      -  quit game");
			
			// Space to continue
			Font font2 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font2);
			screen.text(350, 200, "<Press Enter To Continue>");
			
			
			screen.show();
		}
		
		
		return gameStatus;
	}
	
	// Run a multiplayer level
	public static Status multiLevels(Draw screen, Shooter player1, Shooter player2, Status gameStatus) 
	{
		
		// Move the players to the starting position
		player1.moveTo(200, 25);
		player1.setAngleTo(0);
		player2.moveTo(500, 25);
		player2.setAngleTo(0);
		
		// Array of n missiles allowed for players
		Missile[] player1M = new Missile[4];
		Missile[] player2M = new Missile[4];
		
		// Array of missiles allowed by enemies
		Missile[] enemyM = new Missile[5];
		
		// Enemies
		Enemy[][] enemy = new Enemy[5][10];
		for (int i = 0; i < enemy.length; i++) {
			for (int j = 0; j < enemy[i].length; j++) {
				if (level - i > 1) { // Type 2 enemies increase with one row per level.
					enemy[i][j] = new Enemy(80 + j*60, 580 - i*40, "enemy_2.png", screen, 200);
				}
				else {
					enemy[i][j] = new Enemy(80 + j*60, 580 - i*40, "enemy_1.png", screen, 100);
				}
			}
		}
		
		// Bunkers
		Bunker[] bunker = new Bunker[3];
		for (int i = 0; i < bunker.length; i++) {
			bunker[i] = new Bunker(150 + 200 * i, 150, "bunker100.png", screen);
		}
		
		
		// Get the system time for missile timing.
		long player1M_Timer = System.currentTimeMillis();
		long player2M_Timer = System.currentTimeMillis();
		long enemyM_Timer = System.currentTimeMillis();
		
		// Game Variables //		
		double playerMaxX = 700.0; // player maximum right 
		double playerMinX = 0.0; // player minimum left
		double playerVX = 6.0; // player x speed;
		double playerRotateV = 3.0; // player rotate speed;
		double playerMissileV = 12.0; // player missile speed;
		double playerMissileT = 200.0; // milliseconds between missiles.
		double enemyVX = 1.0 + level * 0.1;
		double enemyVY = 8.0;
		double enemyMissileV = 8.0 + level * 0.2;
		double enemyMissileT = 1000.0 - level * 30.0;
		
		// Checking time between frames
		long total1, total2;
		
		while (gameStatus == Status.MULTILEVEL) {
			total1 = System.currentTimeMillis();
			
			/// Events and Logic ///
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // User quits.
				gameStatus = Status.QUIT;
			}
			if (screen.isKeyPressed(KeyEvent.VK_BACK_SPACE)) { // Go to intro screen.
				gameStatus = Status.HOME;
			}
			
			// Moving and Rotating player1
			if (screen.isKeyPressed(KeyEvent.VK_D)) { // Move right
				player1.moveRight(playerVX, playerMaxX);
			}
			if (screen.isKeyPressed(KeyEvent.VK_A)) { // Move left
				player1.moveLeft(playerVX, playerMinX);
			}
			if (screen.isKeyPressed(KeyEvent.VK_W)) { // Rotate CW
				player1.rotateCW(playerRotateV);
			}
			if (screen.isKeyPressed(KeyEvent.VK_S)) { // Rotate CCW
				player1.rotateCCW(playerRotateV);
			}
			
			// Moving and Rotating player2
			if (screen.isKeyPressed(KeyEvent.VK_RIGHT)) { // Move right
				player2.moveRight(playerVX, playerMaxX);
			}
			if (screen.isKeyPressed(KeyEvent.VK_LEFT)) { // Move left
				player2.moveLeft(playerVX, playerMinX);
			}
			if (screen.isKeyPressed(KeyEvent.VK_UP)) { // Rotate CW
				player2.rotateCW(playerRotateV);
			}
			if (screen.isKeyPressed(KeyEvent.VK_DOWN)) { // Rotate CCW
				player2.rotateCCW(playerRotateV);
			}
			
			// Firing a player1 missile
			if (screen.isKeyPressed(KeyEvent.VK_H)) {
				if (System.currentTimeMillis() >= player1M_Timer + playerMissileT ) {
					// If 'playerMissileT' millisec passed since previous missile fire
					
					for (int i = 0; i < player1M.length; i++) {
						if (player1M[i] == null) {
							player1M[i] = new Missile(player1.getX(), player1.getY(), "missile_1.png", screen, playerMissileV, player1.angle());
							int n = StdRandom.uniform(2, 4);
							StdAudio.play("phasers" + n + ".wav");
							// Audio files from:
							// http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/phasesr2.wav
							// http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/phasers3.wav
							break;
						}
					}
					player1M_Timer = System.currentTimeMillis(); // Reset the timer.
				}
			}
			
			// Firing a player2 missile
			if (screen.isKeyPressed(KeyEvent.VK_NUMPAD0)) {
				if (System.currentTimeMillis() >= player2M_Timer + playerMissileT ) {
					// If 'playerMissileT' millisec passed since previous missile fire
					
					for (int i = 0; i < player2M.length; i++) {
						if (player2M[i] == null) {
							player2M[i] = new Missile(player2.getX(), player2.getY(), "missile_1.png", screen, playerMissileV, player2.angle());
							int n = StdRandom.uniform(2, 4);
							StdAudio.play("phasers" + n + ".wav");
							// Audio files from:
							// http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/phasesr2.wav
							// http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/phasers3.wav
							break;
						}
					}
					player2M_Timer = System.currentTimeMillis(); // Reset the timer.
				}
			}
			
			
			// Firing an enemy missile
			if (System.currentTimeMillis() >= enemyM_Timer + enemyMissileT) {
				// if 'enemyMissileT' millisec passed since prevoius missile fire
				
				int row = StdRandom.uniform(0, enemy.length);
				int col = StdRandom.uniform(0, enemy[row].length);
				
				//StdOut.println("enemy: " + enemyI + ", " + enemyJ);
				
				if (Enemy.isAlive(enemy[row][col])) {
					
					double enemyX = enemy[row][col].getX();
					double enemyY = enemy[row][col].getY();
					
					for (int i = 0; i < enemyM.length; i++) {
						if (enemyM[i] == null) {
							enemyM[i] = new Missile(enemyX, enemyY, "missile_2.png", screen, enemyMissileV, 180);
							break;
						}
					}
				}
				enemyM_Timer = System.currentTimeMillis();
			}
			
			// Moving player1 missiles and checking if in bounds
			Missile.moveMissiles(player1M, player1);
			
			// Moving player2 missiles and checking if in bounds
			Missile.moveMissiles(player2M, player2);

			// Moving enemy missiles and checking if in bounds
			Missile.moveMissiles(enemyM);
			
			// Move enemies and Check if they have reached bottom or hit a player
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[0].length; j++) {
					
					boolean enemyAlive = Enemy.isAlive(enemy[i][j]);
					
					if (enemyAlive) {
						enemy[i][j].moveEnemy(enemyVX, enemyVY);
					}
					if (enemyAlive && enemy[i][j].isAtBottom(0.0)) {
						// if enemy is alive and reached bottom
						gameStatus = Status.GAMEOVER;
						break;
					}
					if (enemyAlive && enemy[i][j].isContactMade(player1)) { // Enemies hit player 1
						// if enemy is alive and made contact with player
						gameStatus = Status.GAMEOVER;
						break;
					}
					if (enemyAlive && enemy[i][j].isContactMade(player2)) { // Enemies hit player 2
						// if enemy is alive and made contact with player
						gameStatus = Status.GAMEOVER;
						break;
					}
				} // for
				if (gameStatus == Status.GAMEOVER) {
					break;
				}
			} // for
			
			
			
			// Test if enemy is hit with a player missile
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[i].length; j++) {
					if (Enemy.isAlive(enemy[i][j])) {
						if (Missile.isHit(enemy[i][j], player1M)) { // Hit with player 1 missile
							StdAudio.play("peeeooop_x.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/peeeooop_x.wav"
							player1.changeScore(enemy[i][j].getScore());
							Enemy.destroy(enemy, i, j);
						}
						else if (Missile.isHit(enemy[i][j], player2M)) { // Hit with player 2 missile
							StdAudio.play("peeeooop_x.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/peeeooop_x.wav"
							player2.changeScore(enemy[i][j].getScore());
							Enemy.destroy(enemy, i, j);
						}
					}
				}
			}
			
			
			// Test if all the enemies are defeated
			if (Enemy.isAllDead(enemy)) {
				gameStatus = Status.LEVELCOMPLETE;
			}
			
			// Test if a player is hit with an enemy missile
			if (Missile.isHit(player1, enemyM)) { // is Player 1 hit
				StdAudio.play("rain_man_uh_oh.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/movies/rain_man/rain_man_uh_oh.wav"
				player1.loseLife();
			}
			if (Missile.isHit(player2, enemyM)) { // is Player 2 hit
				StdAudio.play("rain_man_uh_oh.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/movies/rain_man/rain_man_uh_oh.wav"
				player2.loseLife();
			}
			
			// Test if a player is dead
			if (player1.lives() <= 0 || player2.lives() <= 0) {
				gameStatus = Status.GAMEOVER;
			}
			
			// Test if bunker is destroyed
			for (int i = 0; i < bunker.length; i++) {
				if (bunker[i] != null) {
					if (bunker[i].getHealth() <= 0) {
						StdAudio.play("explosion_x.wav"); // File from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/sfx/explosion_x.wav"
						bunker[i] = null;
					}
				}
			}
			
			// Test if bunker is hit
			for (int i = 0; i < bunker.length; i++) {
				if (bunker[i] != null) {
					if (Missile.isHit(bunker[i], enemyM)) {
						bunker[i].changeImage("bunker" + bunker[i].getHealth() + ".png");
						bunker[i].loseHealth();
					}
					if (Missile.isHit(bunker[i], player1M) || Missile.isHit(bunker[i], player2M)) {
						bunker[i].changeImage("bunker" + bunker[i].getHealth() + ".png");
						bunker[i].loseHealth();
						
					}
				}
			}
			
			/// Animation ///
			screen.clear(Draw.BLACK);
			
			// Display Players
			player1.display();
			player2.display();			
			
			// Display bunker
			for (int i = 0; i < bunker.length; i++) {
				if (bunker[i] != null) {
					bunker[i].display();
				}
			}		
					
			// Display Player 1 Missiles
			Missile.display(player1M);
			
			// Display Player 2 Missiles
			Missile.display(player2M);
			
			// Display Players' Lives
			Font font1 = new Font("Apple Casual", Font.BOLD, 18);
			screen.setFont(font1);
			screen.textLeft(500, 585, "P1 Lives: " + player1.lives() + " P2 Lives: " + player2.lives());
			
			// Display Players' Score
			screen.setFont(font1);
			screen.textLeft(10, 585, "P1 Score: " + player1.getScore() + " P2 Score: " + player2.getScore());
			
			// Display Level
			screen.setFont(font1);
			screen.text(350, 585, "Level: " + level);
			
			// Display Enemy Missiles
			Missile.display(enemyM);
			
			// Display Enemies
			Enemy.display(enemy);
			
			screen.show();
			
			// Keep the frames below 50fps to avoid game moving to fast
			total2 = System.currentTimeMillis();
			int totalTime = (int) (total2 - total1);
			if (totalTime < 20) {
				screen.pause(20 - totalTime);
			}
			
		} // while
		
		player1M = null;
		player2M = null;
		enemyM = null;
		bunker = null;
		
		return gameStatus;
	}
	
	// Display quit game screen
	public static void quitGame(Draw screen)
	{
		screen.clear();
		
		screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
		screen.picture(350, 300, "GameQuit.png");
		
		screen.show();
		// Pause to let player read the message before program stops.
		screen.pause(1500);
	}

	// Game Over for singleplayer
	public static Status gameOver(Draw screen, Shooter player, Status gameStatus) {
			
		while(screen.hasNextKeyTyped()) { // Get rid of all the previously saved key typing.
			screen.nextKeyTyped();
		}
		
		String name = new String();
		int score = player.getScore();
		boolean scoreSaved = false;
		
		
		while (gameStatus == Status.GAMEOVER) {
				
			if (screen.isKeyPressed(KeyEvent.VK_ESCAPE)) { // User quits
				gameStatus = Status.QUIT;
				screen.pause(200);
			}
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) { // User continues
				if (!scoreSaved) { // Score has not been saved
					Score highScore = new Score(name, score);
					Score.update("HighScore.txt", highScore);
					scoreSaved = true;
					screen.pause(200);
				}
				else { // Score has been saved
					gameStatus = Status.HOME;
					level = 1;
					screen.pause(200);
				}
			}
			if (screen.hasNextKeyTyped()) {
				if (!screen.isKeyPressed(KeyEvent.VK_ENTER)) {
					if (screen.isKeyPressed(KeyEvent.VK_BACK_SPACE)) { // Take a character away
						name = name.substring(0, name.length() - 1);
						screen.nextKeyTyped();
					}
					else { // Any other key pressed
						name += screen.nextKeyTyped();
					}
				}

			}
			
			screen.clear();
				
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			screen.picture(350, 300, "GameOver.png");
			
			Font font2 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font2);
			screen.setPenColor(new Color(228, 255, 68));
			screen.text(350, 200, "Score: " + score);
			screen.text(350, 150, "<Press ESCAPE To Quit>");
			if (!scoreSaved) {
				screen.text(350, 130, "<Type Your Name And Press Enter To Save Your Score>");
				screen.text(350, 80, name);
			}
			else {
				screen.text(350, 130, "<Press Enter To Retry");
			}
			
			screen.show();
		}
		
		return gameStatus;
	}
	
	// Game Over for multiplayer
	public static Status gameOver(Draw screen, Shooter player1, Shooter player2, Status gameStatus) {
		
		while(screen.hasNextKeyTyped()) { // Get rid of all the previously saved key typing.
			screen.nextKeyTyped();
		}
		
		while (gameStatus == Status.GAMEOVER) {
				
			if (screen.isKeyPressed(KeyEvent.VK_ESCAPE)) {
				gameStatus = Status.QUIT;
				screen.pause(200);
			}
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) {
				gameStatus = Status.HOME;
				level = 1;
				screen.pause(200);
			}
			
			screen.clear();
				
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			screen.picture(350, 300, "GameOver.png");
			
			Font font2 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font2);
			screen.text(350, 200, "Player 1 Score: " + player1.getScore());
			screen.text(350, 180, "Player 2 Score: " + player2.getScore());
			screen.text(350, 100, "<Press Escape To Quit>");
			screen.text(350, 80, "<Press Enter To Retry>");
			
			screen.show();
		}
		
		return gameStatus;
	}
	
	// Display level completed screen
	public static Status levelComplete(Draw screen, Status gameStatus, Status previous) {
		
		while (gameStatus == Status.LEVELCOMPLETE) {
			// Events and Logic
			if (screen.isKeyPressed(KeyEvent.VK_ENTER)) {
				gameStatus = previous;
				int n = StdRandom.uniform(1, 5); // To play a random file
				StdAudio.play("completely_different" + n + ".wav");
				// Audio files are from:
				// http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/mpfc/completely_different1_x.wav
				// http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/mpfc/completely_different2.wav
				// http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/mpfc/completely_different3.wav
				// http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/mpfc/completely_different4.wav
			}
			if (screen.isKeyPressed(KeyEvent.VK_Q)) {
				gameStatus = Status.QUIT;
			}
			
			// Animation
			screen.clear();
			
			// Backround
			screen.picture(350, 300, "Starry_sky.jpg", 700, 600);
			screen.picture(350, 300, "LevelComplete.png");
			
			// Space to continue 
			Font font2 = new Font("Apple Casual", Font.BOLD, 20);
			screen.setFont(font2);
			screen.text(350, 200, "<Press Enter To Continue To Next Level>");
			
			// Q to Quit
			screen.text(350, 150, "<Press Q To Quit>");
			
			screen.show();
		}
		
		return gameStatus;
	}
	
	
	public static void main(String[] args)
	{
		Draw screen = new Draw(); // Window for main output
		
		// Standard settings for screen.
		screen.setCanvasSize(700, 600);
		screen.setXscale(0, 700);
		screen.setYscale(0, 600);
		screen.enableDoubleBuffering();

		Status state = Status.INTRO; // Starting state to for the intro screen.
		
		// Create a player to test high score system 
		Shooter player = new Shooter(0, 0, "rocket_1.png", screen, 3);
		
		// Give player a random score and zero life\
		player.resetScore(StdRandom.uniform(200, 1800));
		player.loseLife();
		player.loseLife();
		player.loseLife();
		
		state = Status.GAMEOVER;
		
		if (state == Status.GAMEOVER) {
			StdAudio.play("believe_me.wav"); // Audio file from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/batman/believe_me.wav"
			state = GameState.gameOver(screen, player, state);
		}
		
		
		System.exit(0);
		
	}


} // InvaderGameState
