package mazeEscapeUtils;

import javax.swing.JOptionPane;

import mazeEscapeApp.MazeEscape;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.standard.*;

public class SaveButton extends AbstractTool {
	private MazeEscape maze;

	public SaveButton(DrawingEditor newDrawingEditor, MazeEscape m) {
		super(newDrawingEditor);
		maze = m;
	}

	public void activate() {
		maze.setTool(new MazeNavigateTool(maze), "maze tool");
		maze.getMSL().saveGame();
		deactivate();
	}
}
