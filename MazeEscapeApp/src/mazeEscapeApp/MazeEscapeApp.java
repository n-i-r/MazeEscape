package mazeEscapeApp;

import javax.swing.JFrame;
import javax.swing.JTextArea;
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
	
	//Load Code
	private static MazeSaveLoad msl=new MazeSaveLoad();
	private static boolean loadMazeOnCreation = false;

	public static void main(String[] args) {
		createInstructionsScreen();
		if(loadMazeOnCreation)
			maze = new MazeEscape(true);
		else
			maze = new MazeEscape();
		createMaze(maze);
	}

	/**
	 * Sets up instructions screen
	 */
	private static void createInstructionsScreen() {
		String instructionString = "Instructions: \n\n"
				+ "The red cell is the start cell, and the\n"
				+ " white cell is the \"light\" at the end of \n"
				+ " the tunnel. Click on the red cell to enter into the maze \n"
				+ "i.e. start the game. Then navigate through the maze by \n"
				+ "clicks. \n\n"
				+ "The game is grid based, although you may not see \n"
				+ "the grid itself. Click adjacent/valid cells to progress \n"
				+ "through the maze. If wishing to travel in a valid \n"
				+ "straight line instead of constantly needing to hit each \n"
				+ "grid cell, hit the end cell you'd like to move to. \n\n"
				+ "Hover over the buttons in the toolbar to see what each \n"
				+ "respective button does. Reset button (first button) \n"
				+ "is disabled until user starts playing.";

		JFrame aFrame = new JFrame("Instructions");

		JTextArea txt = new JTextArea(instructionString);
		aFrame.add(txt);
		txt.setEditable(false);
		txt.setCursor(null);
		txt.setOpaque(false);
		txt.setFocusable(false);
		aFrame.setSize(350, 350);
		aFrame.setVisible(true);
	}

	/*
	 * Creates a new maze of the given difficulty level
	 * 
	 * @return maze - the MazeEscape instance create
	 */
	public static void createMaze(MazeEscape maze) {
		maze.open();
		maze.setSize(900, 900);
		maze.setLocationRelativeTo(null);
		DrawingView view = maze.view();
		
		// Generates internal maze representation and updates graphical
		// representation
		if(loadMazeOnCreation)
		{
			System.out.println("lm");
			msl.loadGame(maze);
			GUIDrawer guiDrawer = new GUIDrawer(maze,msl);
			msl.updateMaze(maze,guiDrawer);
			unmarkForLoad();
		}
		else
		{
			GUIDrawer guiDrawer = new GUIDrawer(maze, msl);
			// Draws plain n x n grid
			guiDrawer.drawMaze();
			guiDrawer.generateAndDrawMaze();
		}

		// Refresh view
		view.repairDamage();

		// Start cell blue
		// maze.getStartCell().setAttribute(FigureAttributeConstant.FILL_COLOR,
		// Color.BLUE);

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
	public static void newGame(MazeEscape m) {
		oldMaze = m;
		oldMaze.setVisible(false);
		// If track is already playing, it won't play new track
		if (music.loops_done == 1) {
			loop = true;
			music.loops_done = 0;
		} else {
			loop = false;
		}
		// System.out.println(music.loop_times);
		// System.out.println(loop);
		// oldMaze = m;
		old = true;
		MazeEscape newMaze = null;
		if(loadMazeOnCreation)
		{	
			newMaze = new MazeEscape(true);
		}
		else
		{
			newMaze = new MazeEscape();
		}
		
		maze = newMaze;
		createMaze(maze);

	}
	
	public static void markForLoad()
	{
		loadMazeOnCreation = true;
	}
	
	public static void unmarkForLoad()
	{
		loadMazeOnCreation = false;
	}
}
