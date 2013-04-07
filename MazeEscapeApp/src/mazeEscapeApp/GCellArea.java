package mazeEscapeApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import org.jhotdraw.figures.RectangleFigure;
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
	public static int score;
	private MazeEscape maze;

	public GCellArea() {
		super();
	}

	public GCellArea(Point origin, Point corner, MazeEscape m) {
		super(origin, corner);

		maze = m;
	}
	/*
	 * Used by handleClick to check if two cells are adjacent.
	 */
	private boolean areCellsAdjacent(GCellArea cell1, GCellArea cell2) {
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
	 * Handles the clicks on any given grid cell.
	 */
	private void handleClick() {
		// Don't allow the user to move away from end cell after reached.

		if (maze.isFirstClick() == true) {
			// The first click should be the startCell.
			maze.setCurrentlySelected(maze.getStartCell());
			maze.getStartCell().setAttribute(
					FigureAttributeConstant.FILL_COLOR, Color.CYAN);
			maze.setFirstClick(false);
			System.out.println("First Click");
			score++;
		} else if (maze.isReachedEndCell() == true) {

		} else if (this == maze.getEndCell()
				&& maze.isReachedEndCell() == false
				&& areCellsAdjacent(maze.getCurrentlySelected(), this)) {
			maze.setReachedEndCell(true);
			System.out.println("You win.");
			score++;
		} else {
			// Essentially, check to see if the currently selected gridcell is
			// adjacent to the newly selected cell. If so, then move; otherwise,
			// let nothing happen.

			if (areCellsAdjacent(maze.getCurrentlySelected(), this)) {
				maze.setCurrentlySelected(this);
				this.setAttribute(FigureAttributeConstant.FILL_COLOR,
						Color.CYAN);
				score++;

			}
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

}