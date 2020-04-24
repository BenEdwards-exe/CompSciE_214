
public class Score {

	private String name;
	private int score;
	
	public Score(String s, int n)
	{
		name = s;
		score = n;
	}
	
	// Reads a text file of comma separated names with scores (e.g. "Ben 150,") and puts the given score in the right place in the file.
	public static void update(String file, Score playerScore)
	{
		
		In scores = new In(file);
		
		String all = scores.readAll();
		Out scoresUpdate = new Out(file); 
		
		if (all.isEmpty()) { // If the file is empty
			scoresUpdate.print(playerScore.name + " " + playerScore.score + ",");
			return;
		}
		
		String[] list = all.split(",");
		
		int start = 0; // where the second for loop should start (Where the first one stopped)
		boolean printed = false;
		
		// Prints the list of scores until playerScore is higher than the next score. 
		for (int i = 0; i < list.length; i++) {
			start = i;
			String[] p = list[i].split(" "); // Splitting the name and score
			int currentScore = Integer.parseInt(p[1]); 
			if (playerScore.score < currentScore) {
				scoresUpdate.print(list[i] + ",");
			}
			else {
				scoresUpdate.print(playerScore.name + " " + playerScore.score + ",");
				printed = true;
				break;
			}
		}
		
		if (printed == false) { // If the playerScore has not been printed then it must be the lowest.
			scoresUpdate.print(playerScore.name + " " + playerScore.score + ",");
		}
		else { // Print everything after playerScore has been filled in. 
			for (int i = start; i < list.length; i++) {
				scoresUpdate.print(list[i] + ",");
			}
		}
		
		scores.close();
		scoresUpdate.close();
		
	}

	public static void main(String[] args) {
		
		// Print the text file
		In in0 = new In("HighScoreTest.txt");
		StdOut.println(in0.readAll());
		in0.close();
		
		// Update the text file
		Score test = new Score("test4", 500);
		Score.update("HighScoreTest.txt", test);
		
		// Print the updated text file
		In in1 = new In("HighScoreTest.txt");
		StdOut.println(in1.readAll());
		in1.close();
		

	}

}
