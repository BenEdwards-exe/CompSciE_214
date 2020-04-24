// Handles details outside the game loop and uses InvadersGameState to handle details in the game loop.

import java.awt.event.KeyEvent;

public class MainGame {
	
	
	public static void main(String[] args)
	{
		Draw screen = new Draw(); // Window for main output
		
		// Standard settings for screen.
		screen.setCanvasSize(700, 600);
		screen.setXscale(0, 700);
		screen.setYscale(0, 600);
		screen.enableDoubleBuffering();

		Status state = Status.INTRO; // Starting state to for the intro screen.
		
		// Create two shooter objects to also accommodate multiplayer 
		Shooter player1 = new Shooter(0, 0, "rocket_1.png", screen, 3);
		Shooter player2 = new Shooter(0, 0, "rocket_2.png", screen, 3);
		
		// All the .png files that are used for the objects Shooter, Enemy,
		// Missile and Bunker were drawn with Inkscape 2D vector software that
		// can be download at "https://inkscape.org/release/inkscape-0.92.4/"
		
		// Main Game Loop //
		while (state != Status.QUIT) {
			
			if (screen.isKeyPressed(KeyEvent.VK_Q)) { // If the quit key is pressed
				state = Status.QUIT;
			}
			
			
			if (state == Status.INTRO) { // Display the Intro SCreen.
				state = GameState.introScreen(screen, state);
			}
			
			if (state == Status.INSTRUCTIONS) { // Display Instructions
				state = GameState.instructions(screen, state);
			}
			
			if (state == Status.HOME) { // Display the Home Screen
				state = GameState.homeScreen(screen, state);
			}
			
			if (state == Status.SINGLECONTROLS) { // Display Single Player Controls
				state = GameState.singleControls(screen, state);
			}
			
			while (state == Status.SINGLELEVEL) { // Single Player Levels
				state = GameState.singleLevels(screen, player1, state);
				
				if (state == Status.LEVELCOMPLETE) { // If a level is completed
					state = GameState.levelComplete(screen, state, Status.SINGLELEVEL);
					GameState.level += 1;
				}
				
				if (state == Status.GAMEOVER) {
					StdAudio.play("believe_me.wav"); // Audio file from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/batman/believe_me.wav"
					state = GameState.gameOver(screen, player1, state);
					
		
					player1.resetLives(3);
					player1.resetScore(0);
				}
			}
			
			if (state == Status.MULTICONTROLS) { // Display Multiplayer Controls
				state = GameState.multiControls(screen, state);
			}
			
			while (state == Status.MULTILEVEL) { // Multilayer Levels
				state = GameState.multiLevels(screen, player1, player2, state);
				
				if (state == Status.LEVELCOMPLETE) {
					state = GameState.levelComplete(screen, state, Status.MULTILEVEL);
					GameState.level += 1;
				}
				
				if (state == Status.GAMEOVER) {
					StdAudio.play("believe_me.wav"); // Audio file from "http://www.wavsource.com/snds_2020-03-30_7102365145747638/tv/batman/believe_me.wav"
					state = GameState.gameOver(screen, player1, player2, state);

					player1.resetLives(3);
					player2.resetLives(3);
					player1.resetScore(0);
					player2.resetScore(0);
				}
			}
			
		} // Main Loop
		
		if (state == Status.QUIT) {
			GameState.quitGame(screen);
		}
		
		System.exit(0);
	}

} // class
