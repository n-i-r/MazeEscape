package mazeEscapeUtils;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import mazeEscapeApp.MazeEscape;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.standard.AbstractTool;

public class ResetButton extends AbstractTool{
	private MazeEscape maze;

	public ResetButton(DrawingEditor newDrawingEditor, MazeEscape m) {
		super(newDrawingEditor);
		maze = m;
	}
	
	public void activate()
	{
		JOptionPane.showMessageDialog(null, "Maze is now reset, click OK to continue!");
		maze.setStepsTaken(0);
		maze.setTimePassed(0);
		maze.setReset(true);
		maze.setCurrentlySelected(maze.getStartCell());
		maze.setFirstClick(true);
		maze.setTool(new MazeNavigateTool(maze), "maze tool");
		deactivate();
	}
	
	public void mouseDown(MouseEvent e, int x, int y)
	{
	}
}