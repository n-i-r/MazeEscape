package mazeEscapeUtils;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import mazeEscapeApp.GUIDrawer;
import mazeEscapeApp.MazeEscape;
import mazeEscapeApp.MazeEscapeApp;
import mazeEscapeApp.WinnerScreen;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.standard.AbstractTool;

public class ForfeitButton extends AbstractTool {

	private GUIDrawer guiDrawer;
	private MazeEscape maze;

	public ForfeitButton(DrawingEditor newDrawingEditor, GUIDrawer gd, MazeEscape me) {
		super(newDrawingEditor);
		guiDrawer = gd;
		maze = me;
	}

	public void setGUIDrawer(GUIDrawer gd) {
		guiDrawer = gd;
	}

	public void activate() {
		JOptionPane.showMessageDialog(null,
				"You have now lost the game. Click OK to see the solution. Click anywhere to start new game.");
			maze.setOn(false);
			guiDrawer.drawSolution();
			WinnerScreen screen = new WinnerScreen(maze);
			screen.forfeit();

			//TODO: Insert other "game over" code here to lock the maze
	}
	
	public void deactivate()
	{
		JOptionPane.showMessageDialog(null, "The maze will now implode.");
		maze.exit();
	}
	
	public void mouseDown(MouseEvent e, int x, int y)
	{
		//WinnerScreen screen = new WinnerScreen(maze);
		//screen.forfeit();
	}
}
