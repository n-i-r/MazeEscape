package mazeEscapeApp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.jhotdraw.framework.DrawingView;

import baseMazeCode.RestartProgramSignal;

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
	private static boolean load = true;
	private static String difficulty = "";
	private static CampaignStore cs = new CampaignStore();

	// Load Code
	private static MazeSaveLoad msl = new MazeSaveLoad();
	private static boolean loadMazeOnCreation = false;

	public static void main(String[] args) throws InterruptedException {
		loadSplash();
		boolean loop = true;
		boolean multiple = false;
		while (loop) {
			try {
				if (multiple) {
					oldMaze = maze;
					oldMaze.setVisible(false);
				}

				loop = false;
				createLoadOptions();
				if (!multiple)
					createInstructionsScreen();
				if (loadMazeOnCreation)
					maze = new MazeEscape(true);
				else
				{
					if(difficulty.equals(""))
						maze = new MazeEscape();
					else
						maze=new MazeEscape(difficulty);
				}

				multiple = false;
				createMaze(maze);
			} catch (RestartProgramSignal e) {
				loop = true;
				multiple = true;
			}
		}
	}

	/**
	 * Loads up the splash screen on application launch
	 * 
	 * @throws InterruptedException
	 */
	private static void loadSplash() throws InterruptedException {
		SplashScreen screen = new SplashScreen("ESCAPE", "Yes");
		screen.show("Center");
		int i = 0;
		while (load == true) {
			Thread thread = new Thread();
			thread.start();
			screen.setProgress(i);
			screen.setStatus("Loading: " + i + "% loaded");
			i++;
			if (i > 100) {
				screen.close();
				load = false;
			}
			thread.sleep(35);
		}
	}

	/**
	 * Sets up instructions screen
	 */
	private static void createInstructionsScreen() {
		SplashScreen instructions = new SplashScreen("INSTRUCTIONS", "No");
		instructions.show("Corner");
//		String instructionString = "Instructions: \n\n"
//				+ "The blue cell is the start cell, and the white cell \n"
//				+ "is the \"light\" at the end ofthe tunnel. \n"
//				+ "Click on the blue cell to enter into the maze \n"
//				+ "i.e. start the game. Then navigate through the maze by \n"
//				+ "clicks. \n\n"
//				+ "The game is grid based, although you may not see \n"
//				+ "the grid itself. Click adjacent/valid cells to progress \n"
//				+ "through the maze. If wishing to travel in a valid \n"
//				+ "straight line instead of constantly needing to hit each \n"
//				+ "grid cell, hit the end cell you'd like to move to. \n\n"
//				+ "Hover over the buttons in the toolbar to see what each \n"
//				+ "respective button does. The reset button (first button) \n"
//				+ "and forfeit button (third button) are disabled until \n"
//				+ "the user starts playing.";
//
//		JFrame aFrame = new JFrame("Instructions");
//
//		JTextArea txt = new JTextArea(instructionString);
//		aFrame.add(txt);
//		txt.setEditable(false);
//		txt.setCursor(null);
//		txt.setOpaque(false);
//		txt.setFocusable(false);
//		aFrame.setSize(350, 350);
//		aFrame.setVisible(true);
	}

	private static void createLoadOptions() {
		Object[] options = { "Easy", "Medium", "Hard", "Campaign", "Load Game" };

		// Creates message and options for dialog box
		final JOptionPane optionPane = new JOptionPane(
				"Welcome to MazeEscape \nSelect a maze difficulty or choose Load Game to resume an existing game.",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null,
				options, options[0]);

		// Creates dialog box displaying the message
		final JDialog dialog = new JDialog(new JFrame(), "MazeEscape v1.4.7",
				true);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();

				// Will execute a specific method right before closing.
				if (dialog.isVisible() && (e.getSource() == optionPane)
						&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
					System.out.println("Closed!");
					dialog.setVisible(false);
				}
			}
		});
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		// Allows to set difficulty of maze, based on selection
		String value = optionPane.getValue().toString();
		System.out.println(value);
		if (value == "Load Game") {
			markForLoad();
		} else if (value == "Easy") {
			unmarkForLoad("Easy");
		}
		else if (value == "Medium")
			unmarkForLoad("Medium");
		else if (value == "Hard")
			unmarkForLoad("Hard");
		else if (value == "Campaign")
			unmarkForLoad("Campaign");
		else
			System.exit(0);
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
		if (loadMazeOnCreation) {
			msl.loadGame(maze);
			GUIDrawer guiDrawer = new GUIDrawer(maze, msl);
			msl.updateMaze(maze, guiDrawer);
			unmarkForLoad();
		} else {
			msl.resetMSL();
			GUIDrawer guiDrawer = new GUIDrawer(maze, msl);
			// Draws plain n x n grid
			guiDrawer.drawMaze();
			guiDrawer.generateAndDrawMaze();
		}

		// Refresh view
		view.repairDamage();;

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
		boolean campaign = m.isCampaign();
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
		if (loadMazeOnCreation) {
			newMaze = new MazeEscape(true);
		} else {
			if(campaign)
				newMaze = new MazeEscape("Campaign");
			else
				newMaze = new MazeEscape();
		}

		maze = newMaze;
		createMaze(maze);

	}

	public static void markForLoad() {
		loadMazeOnCreation = true;
	}

	public static void unmarkForLoad() {
		loadMazeOnCreation = false;
		difficulty = "";
	}
	
	public static void unmarkForLoad(String diff)
	{
		loadMazeOnCreation = false;
		difficulty = diff;
	}
	public static CampaignStore getCampaignStore()
	{
		return cs;
	}
}
