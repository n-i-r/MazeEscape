import java.awt.Point;
import org.jhotdraw.framework.DrawingView;

/**
 * Class that represents a grid cell to be used in the Maze. This only draws the
 * lines for the grid cell, without the actual clickable area. Useful for
 * decoupling clickable area
 * 
 * @author ammadshaikh
 * 
 */
public class GridCell {
	private GCellLine _up, _down, _left, _right;
	// True if line is on screen
	private boolean isUp, isDown, isLeft, isRight;
	private final int SCALEFACTOR = MazeEscapeApp.getGcellPixelLength();
	private DrawingView _view;
	public static final String UP = "up", DOWN = "down", LEFT = "left",
			RIGHT = "right";

	public GridCell(int x, int y, DrawingView view) {
		// Instantiate the lines and view members
		_up = new GCellLine();
		_down = new GCellLine();
		_left = new GCellLine();
		_right = new GCellLine();
		_view = view;

		// Set the line's points
		_up.setPoints(new Point(x, y), new Point(x + SCALEFACTOR, y));
		_down.setPoints(new Point(x, y + SCALEFACTOR), new Point(x
				+ SCALEFACTOR, y + SCALEFACTOR));
		_left.setPoints(new Point(x, y), new Point(x, y + SCALEFACTOR));
		_right.setPoints(new Point(x + SCALEFACTOR, y), new Point(x
				+ SCALEFACTOR, y + SCALEFACTOR));

		// Add the lines to the view
		_view.add(_up);
		_view.add(_down);
		_view.add(_left);
		_view.add(_right);
		isUp = isDown = isLeft = isRight = true;
		
	}

	public void addLine(String side) {
		if (side.equals(UP)){
			_view.add(_up);
			isUp = true;
		}
		else if (side.equals(DOWN)){
			_view.add(_down);
			isDown = true;
		}
		else if (side.equals(LEFT)){
			_view.add(_left);
			isLeft = true;
		}
		else if (side.equals(RIGHT)){
			_view.add(_right);
			isRight = true;
		}
	}

	public void removeLine(String side) {
		if (side.equals(UP)){
			_view.remove(_up);
			isUp = false;
		}
		else if (side.equals(DOWN)){
			_view.remove(_down);
			isDown = false;
		}
		else if (side.equals(LEFT)){
			_view.remove(_left);
			isLeft = false;
		}
		else if (side.equals(RIGHT)){
			_view.remove(_right);
			isRight = false;
		}
	}
	
	public boolean isUp() {
		return isUp;
	}

	public boolean isDown() {
		return isDown;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public boolean isRight() {
		return isRight;
	}
}
