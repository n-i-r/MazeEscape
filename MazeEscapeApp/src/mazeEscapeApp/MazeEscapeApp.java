package mazeEscapeApp;

import org.jhotdraw.framework.DrawingView;

/**
 * Main class for the MazeEscape game.
 * 
 * @author ammadshaikh
 * 
 */

public class MazeEscapeApp {
	private static MazeEscape maze;
	
	public static void main(String[] args){
		maze = new MazeEscape("Easy");

		createMaze (maze, "Easy");
	}
	
	/*
	 * Creates a new maze of the given difficulty level
	 * 
	 * @return maze - the MazeEscape instance create
	 */
	public static void createMaze(MazeEscape maze, String difficulty){
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
		
		// Calculates the final maze score
		try {
			maze.calculateScore();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new game. Used for every instance after the first one.
	 */
	public static void newGame(MazeEscape m){
		m.setVisible(false);
		MazeEscape newMaze = new MazeEscape("Easy");
		createMaze (newMaze, "Easy");
		maze = newMaze;
		m.exit();
	}
}