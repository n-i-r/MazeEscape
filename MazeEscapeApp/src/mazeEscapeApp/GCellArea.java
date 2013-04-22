package mazeEscapeApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.jhotdraw.figures.RectangleFigure;
import org.jhotdraw.framework.DrawingView;
import org.jhotdraw.framework.FigureAttributeConstant;
import org.jhotdraw.framework.HandleEnumeration;
import org.jhotdraw.standard.HandleEnumerator;
import org.jhotdraw.util.CollectionsFactory;

/**
 * A class representing the Clickable area in a grid cell
 * 
 */
public class GCellArea extends RectangleFigure {
	private static final long serialVersionUID = 4658070300128221307L;
	private int row, column;
	private MazeEscape maze;
	private DrawingView view;
	private ArrayList<GCellCoordinate> saveCells;

	// Variables for creation of player
	protected static Pinhead larry;
	private static final int SHIFT = 50;
	private static boolean isActualFirstClick = true;

	public GCellArea() {
		super();
		saveCells = new ArrayList<GCellCoordinate>();
	}

	public GCellArea(Point origin, Point corner, MazeEscape m,
			ArrayList<GCellCoordinate> a) {
		super(origin, corner);
		maze = m;
		view = maze.view();
		saveCells = a;
	}

	/*
	 * Used by handleClick to check if the destination cell is a possible move
	 */
	public boolean isValidMove(GCellArea cell1, GCellArea cell2) {
		GCellArea[] adjCells = cell1.getAdjacentGCells();

		for (GCellArea gc : adjCells) {
			if (gc != null && gc.getRow() == cell2.getRow()
					&& gc.getColumn() == cell2.getColumn()) {
				return true;
			}
		}
		return areCellsLinear(cell1, cell2);
	}

	// Checks to see if two cells are next to each other w/o a wall between them
	public boolean adjCheck(GCellArea cell1, GCellArea cell2) {
		GCellArea[] adjCells = cell1.getAdjacentGCells();

		for (GCellArea gc : adjCells) {
			if (gc != null && gc.getRow() == cell2.getRow()
					&& gc.getColumn() == cell2.getColumn()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Recursively checks a line of cells to see if they're all adjacent
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 */
	private boolean areCellsLinear(GCellArea origin, GCellArea destination) {
		if (origin.getRow() == destination.getRow()) {
			if (origin.getColumn() == destination.getColumn())
				return true;
			else if (origin.getColumn() < destination.getColumn()) {
				GCellArea nextCell = maze.getgCellClickableArea()[origin
						.getRow()][origin.getColumn() + 1];
				if (adjCheck(origin, nextCell))
					return areCellsLinear(nextCell, destination);
				else
					return false;
			} else if (origin.getColumn() > destination.getColumn()) {
				GCellArea nextCell = maze.getgCellClickableArea()[origin
						.getRow()][origin.getColumn() - 1];
				if (adjCheck(origin, nextCell))
					return areCellsLinear(nextCell, destination);
				else
					return false;
			} else
				return false;
		} else if (origin.getColumn() == destination.getColumn()) {
			if (origin.getRow() == destination.getRow())
				return true;
			else if (origin.getRow() < destination.getRow()) {
				GCellArea nextCell = maze.getgCellClickableArea()[origin
						.getRow() + 1][origin.getColumn()];
				if (adjCheck(origin, nextCell))
					return areCellsLinear(nextCell, destination);
				else
					return false;
			} else if (origin.getRow() > destination.getRow()) {
				GCellArea nextCell = maze.getgCellClickableArea()[origin
						.getRow() - 1][origin.getColumn()];
				if (adjCheck(origin, nextCell))
					return areCellsLinear(nextCell, destination);
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * Handles the clicks on any given grid cell.
	 */

	private void handleClick() {
		// Don't allow the user to move away from end cell after reached.

		if (maze.isFirstClick() == true) {
			// The first click should be the startCell.
			int r = maze.getStartCell().row;
			int c = maze.getStartCell().column;
			if (maze.getReset() == true) {
				maze.setReset(false);
				view.remove(larry);
			}

			larry = new Pinhead(new Point(SHIFT + c
					* maze.getgCellPixelLength(), SHIFT + r
					* maze.getgCellPixelLength()), new Point(SHIFT + c
					* maze.getgCellPixelLength() + maze.getgCellPixelLength(),
					SHIFT + r * maze.getgCellPixelLength()
							+ maze.getgCellPixelLength() - 1));
			larry.setAttribute(FigureAttributeConstant.FILL_COLOR, Color.PINK);
			larry.setAttribute(FigureAttributeConstant.FRAME_COLOR, Color.RED);
			view.add(larry);
			maze.setCurrentlySelected(maze.getStartCell());
			maze.getStartCell().setAttribute(
					FigureAttributeConstant.FILL_COLOR, Color.CYAN);
			maze.setFirstClick(false);
			System.out.println("First Click");
			maze.setStepsTaken(maze.getStepsTaken() + 1);
			saveCells.add(new GCellCoordinate(maze.getStartCell()));
		} else {
			if (isValidMove(maze.getCurrentlySelected(), this)) {
				move(maze.getCurrentlySelected(), this);
			}
		}

	}
	
	public void drawLarry()
	{
		int r = maze.getCurrentlySelected().row;
		int c = maze.getCurrentlySelected().column;
		larry = new Pinhead(new Point(SHIFT + c
				* maze.getgCellPixelLength(), SHIFT + r
				* maze.getgCellPixelLength()), new Point(SHIFT + c
				* maze.getgCellPixelLength()
				+ maze.getgCellPixelLength(), SHIFT + r
				* maze.getgCellPixelLength()
				+ maze.getgCellPixelLength() - 1));
		larry.setAttribute(FigureAttributeConstant.FILL_COLOR,
				Color.PINK);
		larry.setAttribute(FigureAttributeConstant.FRAME_COLOR,
				Color.RED);
		view.add(larry);
	}

	/**
	 * Recursively moves the player from its current location to the destination
	 * 
	 * @param origin
	 * @param destination
	 */
	private void move(GCellArea origin, GCellArea destination) {
		// Removes player + increments step counter
		view.remove(larry);
		maze.setStepsTaken(maze.getStepsTaken() + 1);

		// Check to see if the destination is right next to the beginning
		if (adjCheck(origin, destination)) {
			maze.setCurrentlySelected(destination);
			int r = maze.getCurrentlySelected().row;
			int c = maze.getCurrentlySelected().column;

			if (destination == maze.getEndCell()) {
				maze.setReachedEndCell(true);
				System.out.println("You win.");
			} else {
				// Creates player in newly selected cell
				larry = new Pinhead(new Point(SHIFT + c
						* maze.getgCellPixelLength(), SHIFT + r
						* maze.getgCellPixelLength()), new Point(SHIFT + c
						* maze.getgCellPixelLength()
						+ maze.getgCellPixelLength(), SHIFT + r
						* maze.getgCellPixelLength()
						+ maze.getgCellPixelLength() - 1));
				larry.setAttribute(FigureAttributeConstant.FILL_COLOR,
						Color.PINK);
				larry.setAttribute(FigureAttributeConstant.FRAME_COLOR,
						Color.RED);
				view.add(larry);
				destination.setAttribute(FigureAttributeConstant.FILL_COLOR,
						Color.CYAN);
				saveCells.add(new GCellCoordinate(destination));
			}
		} else {
			GCellArea adjacent;

			if (origin.getRow() == destination.getRow()
					&& origin.getColumn() < destination.getColumn())
				adjacent = maze.getgCellClickableArea()[origin.getRow()][origin
						.getColumn() + 1];
			else if (origin.getRow() == destination.getRow()
					&& origin.getColumn() > destination.getColumn())
				adjacent = maze.getgCellClickableArea()[origin.getRow()][origin
						.getColumn() - 1];
			else if (origin.getColumn() == destination.getColumn()
					&& origin.getRow() < destination.getRow())
				adjacent = maze.getgCellClickableArea()[origin.getRow() + 1][origin
						.getColumn()];
			else
				adjacent = maze.getgCellClickableArea()[origin.getRow() - 1][origin
						.getColumn()];

			maze.setCurrentlySelected(adjacent);
			adjacent.setAttribute(FigureAttributeConstant.FILL_COLOR,
					Color.CYAN);
			saveCells.add(new GCellCoordinate(adjacent));
			move(adjacent, destination);
		}

	}

	// Empty body so no translation happens
	protected void basicMoveBy(int x, int y) {
	}

	@Override
	public HandleEnumeration handles() {
		@SuppressWarnings("rawtypes")
		List handles = CollectionsFactory.current().createList();
		// BoxHandleKit.addHandles(this, handles);

		// Handle clicks on maze boxes. Probably not the best place, but it
		// works as intended.
		handleClick();

		return new HandleEnumerator(handles);
	}

	@Override
	public void drawFrame(Graphics g) {

	}

	public GCellArea[] getAdjacentGCells() {
		GCellArea[] adjacentCells = new GCellArea[4];

		GridCell[][] gCells = maze.getGridCells();

		// North, South, East, West
		if (row > 0 && !gCells[row][column].isUp()) {
			adjacentCells[0] = maze.getgCellClickableArea()[row - 1][column];
		}
		if (row < maze.getgCellClickableArea().length - 1
				&& !gCells[row][column].isDown()) {
			adjacentCells[1] = maze.getgCellClickableArea()[row + 1][column];
		}
		if (column < maze.getgCellClickableArea().length - 1
				&& !gCells[row][column].isRight()) {
			adjacentCells[2] = maze.getgCellClickableArea()[row][column + 1];
		}
		if (column > 0 && !gCells[row][column].isLeft()) {
			adjacentCells[3] = maze.getgCellClickableArea()[row][column - 1];
		}

		return adjacentCells;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void callHandleClick() {
		handleClick();
	}

}
