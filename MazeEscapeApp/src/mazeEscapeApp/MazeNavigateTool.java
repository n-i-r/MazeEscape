package mazeEscapeApp;

import java.awt.event.MouseEvent;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.framework.DrawingView;
import org.jhotdraw.framework.Figure;
import org.jhotdraw.framework.Handle;
import org.jhotdraw.standard.AWTCursor;
import org.jhotdraw.standard.SelectionTool;

public class MazeNavigateTool extends SelectionTool {
	
	private MazeEscape mazeEscape;
	
	public MazeNavigateTool(MazeEscape me) {
		super((DrawingEditor)me);
		mazeEscape = me;
	}
	public void mouseMove(MouseEvent evt, int x, int y)
	{
		DrawingView view=getActiveView();
		
		Handle handle = view.findHandle(x, y);
		Figure figure = view.drawing().findFigure(x, y);

		if (handle != null) {
			view.setCursor(handle.getCursor());
		}
		else if (figure != null && isValidMove(figure)) {
			view.setCursor(new AWTCursor(java.awt.Cursor.HAND_CURSOR));
		}
		else if (figure != null && !isValidMove(figure))
		{
			view.setCursor(new AWTCursor(java.awt.Cursor.WAIT_CURSOR));
		}
		else {
			view.setCursor(new AWTCursor(java.awt.Cursor.DEFAULT_CURSOR));
		}
	}
	
	private boolean isValidMove(Figure f)
	{
		if(mazeEscape.isFirstClick())
		{
			if((Figure)mazeEscape.getStartCell()==f)
				return true;
			else
				return false;
		}
		else
		{
			GCellArea curr = mazeEscape.getCurrentlySelected();
			GCellArea[] adjCells = curr.getAdjacentGCells();
	
			for (GCellArea gc : adjCells) {
				if ((Figure)gc == f)
					return true;
			}
		}
		return false;
	}
}
