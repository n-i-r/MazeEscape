package mazeEscapeUtils;

import javax.swing.JOptionPane;

import mazeEscapeApp.MazeEscape;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.standard.*;

public class LoadButton extends AbstractTool {
	private MazeEscape maze;

	public LoadButton(DrawingEditor newDrawingEditor, MazeEscape m) {
		super(newDrawingEditor);
		maze = m;
	}

	public void activate() {
		maze.setTool(new MazeNavigateTool(maze), "maze tool");
		maze.loadGame();
		deactivate();
	}
}
