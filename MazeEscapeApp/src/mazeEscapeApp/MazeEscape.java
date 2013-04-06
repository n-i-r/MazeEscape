package mazeEscapeApp;

import javax.swing.JMenuBar;

import org.jhotdraw.application.DrawApplication;

/*
 * Class represents an instance of a mazeEscape game. No GUI involved here.
 */
public class MazeEscape extends DrawApplication {
	private static final long serialVersionUID = 5843503278232195529L;
	// The row, column arrays storing the clickable areas and respective
	// GridCell frames
	private GCellArea[][] gCellClickableArea;
	private GridCell[][] gridCells;

	// For an n * n maze, length = width = n
	private int lengthMaze;
	private int gCellPixelLength = 35;

	// Currently selected grid cell (used for game; user grid selection)
	private GCellArea currentlySelected;
	private GCellArea startCell, endCell;

	// Used in handleClick. Checks to see if first selected cell
	private boolean isFirstClick = true;
	private boolean reachedEndCell = false;

	// Used for calculation of score
	private boolean on = true;
	private int timePassed = 0;
	private int timeScore = 0;

	public MazeEscape(String difficulty) {
		super("MazeEscape");

		setDifficulty(difficulty);

		gridCells = new GridCell[lengthMaze][lengthMaze];
		gCellClickableArea = new GCellArea[lengthMaze][lengthMaze];
	}

	/**
	 * Calculates the score based on amount of steps taken combined with time
	 * taken to reach the end
	 */
	@SuppressWarnings("static-access")
	public void calculateScore() throws InterruptedException {
		while (on == true) {
			Thread thread = new Thread();
			thread.start();
			timePassed++;
			// Checks if end of the maze has been reached
			if (isReachedEndCell() == true) {
				// Calculates bonus points if reached within allotted maze time
				if (timePassed < timeScore) {
					int score = endCell.getScore();
					System.out.println(score);
					System.out.println(timePassed);
					score = score * (timeScore - timePassed);
					System.out.println("Your score is: " + score + "!");
					on = false;
				} else {
					int score = endCell.getScore();
					System.out.println("Your score is: " + score + "!");
					on = false;
				}
			}
			thread.sleep(1000);
		}
	}

	public void setDifficulty(String difficulty) {
		if (difficulty.equals("Easy")) {
			lengthMaze = 10;
			timeScore = 15;
		} else if (difficulty.equals("Medium")) {
			lengthMaze = 15;
			timeScore = 30;
		} else if (difficulty.equals("Hard")) {
			lengthMaze = 30;
			timeScore = 60;
		}
	}

	/*
	 * Getters and setters below here
	 */

	// No menus created
	@Override
	protected void createMenus(JMenuBar mb) {
		super.createMenus(mb);
	}

	public GCellArea[][] getgCellClickableArea() {
		return gCellClickableArea;
	}

	public void setgCellClickableArea(GCellArea[][] gCellClickableArea) {
		this.gCellClickableArea = gCellClickableArea;
	}

	public GridCell[][] getGridCells() {
		return gridCells;
	}

	public void setGridCells(GridCell[][] gridCells) {
		this.gridCells = gridCells;
	}

	public int getLengthMaze() {
		return lengthMaze;
	}

	public void setLengthMaze(int lengthMaze) {
		this.lengthMaze = lengthMaze;
	}

	public int getgCellPixelLength() {
		return gCellPixelLength;
	}

	public void setgCellPixelLength(int gCellPixelLength) {
		this.gCellPixelLength = gCellPixelLength;
	}

	public GCellArea getCurrentlySelected() {
		return currentlySelected;
	}

	public void setCurrentlySelected(GCellArea currentlySelected) {
		this.currentlySelected = currentlySelected;
	}

	public GCellArea getStartCell() {
		return startCell;
	}

	public void setStartCell(GCellArea startCell) {
		this.startCell = startCell;
	}

	public GCellArea getEndCell() {
		return endCell;
	}

	public void setEndCell(GCellArea endCell) {
		this.endCell = endCell;
	}

	public boolean isFirstClick() {
		return isFirstClick;
	}

	public void setFirstClick(boolean isFirstClick) {
		this.isFirstClick = isFirstClick;
	}

	public boolean isReachedEndCell() {
		return reachedEndCell;
	}

	public void setReachedEndCell(boolean reachedEndCell) {
		this.reachedEndCell = reachedEndCell;
	}

}
