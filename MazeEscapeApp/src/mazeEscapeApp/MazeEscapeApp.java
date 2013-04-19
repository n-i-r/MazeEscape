package mazeEscapeApp;

import org.jhotdraw.framework.DrawingView;

/**
 * Main class for the MazeEscape game.
 * 
 * @author TrollFace
 * 
 */

public class MazeEscapeApp {
	private static MazeEscape maze;
	private static MazeEscape oldMaze;
	private static PlayMusic music;
	private static boolean loop = true;
	private static boolean old = false;
	
	public static void main(String[] args){
		maze = new MazeEscape();
		createMaze(maze);
	}
	
	/*
	 * Creates a new maze of the given difficulty level
	 * 
	 * @return maze - the MazeEscape instance create
	 */
	public static void createMaze(MazeEscape maze) {
		maze.open();
		maze.setSize(900, 900);
		DrawingView view = maze.view();

		GUIDrawer guiDrawer = new GUIDrawer(maze);

		// Draws plain n x n grid
		guiDrawer.drawMaze();

		// Generates internal maze representation and updates graphical
		// representation
		guiDrawer.generateAndDrawMaze();

		// Refresh view
		view.repairDamage();
		
		// Destroys old maze if completed
		if (old == true) {
			destroyOldMaze(oldMaze);
		}
		
		// Starts application soundtrack
		if (loop == true) {
			music = new PlayMusic();
			loop = false;
		}
		
		// Calculates the final maze score
		try {
			maze.calculateScore();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void playMusic() {
		music = new PlayMusic();
	}
	
	// Removes the old maze
	private static void destroyOldMaze(MazeEscape maze) {
		maze.exit();
	}

	
	/**
	 * Creates a new game. Used for every instance after the first one.
	 */
	public static void newGame(MazeEscape m){
		oldMaze = m;
		oldMaze.setVisible(false);
		// If track is already playing, it won't play new track
		if (music.loops_done == 1) {
			loop = true;
			music.loops_done = 0;
		}
		else {
			loop = false;
		}
//		System.out.println(music.loop_times);
//		System.out.println(loop);
		//oldMaze = m;
		old = true;
		MazeEscape newMaze = new MazeEscape();
		maze = newMaze;
		createMaze(maze);
	}
}