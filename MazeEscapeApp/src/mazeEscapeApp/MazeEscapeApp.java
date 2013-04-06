package mazeEscapeApp;

import org.jhotdraw.framework.DrawingView;

/**
 * Main class for the MazeEscape game.
 * 
 * @author ammadshaikh
 * 
 */

public class MazeEscapeApp {
	public static void main(String[] args) throws InterruptedException {
		// Instantiate and open window
		MazeEscape maze = new MazeEscape("Easy");
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
		maze.calculateScore();
	}
}