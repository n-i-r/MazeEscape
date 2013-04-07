package mazeEscapeApp;

import javax.swing.JOptionPane;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.standard.*;

public class ForfeitButton extends AbstractTool {

	private GUIDrawer guiDrawer;
	private MazeEscape mazeEscape;

	public ForfeitButton(DrawingEditor newDrawingEditor, GUIDrawer gd, MazeEscape me) {
		super(newDrawingEditor);
		guiDrawer = gd;
	}

	public void setGUIDrawer(GUIDrawer gd) {
		guiDrawer = gd;
	}

	public void activate() {
		JOptionPane.showMessageDialog(null,
				"You have now lost the game. Click OK to see the solution.");

			guiDrawer.drawSolution();
			mazeEscape.gameOverTools();
			//TODO: Insert other "game over" code here to lock the maze
	}
	
	public void deactivate()
	{
		System.exit(0);
	}
}
