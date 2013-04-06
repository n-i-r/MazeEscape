import java.awt.Color;
import java.awt.Point;

import javax.swing.JMenuBar;

import org.jhotdraw.application.DrawApplication;
import org.jhotdraw.framework.DrawingView;
import org.jhotdraw.framework.FigureAttributeConstant;

import AdjacencyListGraph.ALGraph;
import AdjacencyListGraph.Coordinate;
import AdjacencyListGraph.Edge;
import AdjacencyListGraph.EdgeList;
import HeapPriorityQueue.MyEntry;

/**
 * Main class for the MazeEscape game.
 * 
 * @author ammadshaikh
 * 
 */

public class MazeEscapeApp extends DrawApplication {
	// The row, column arrays storing the clickable areas and respective
	// GridCell frames
	private static GCellArea[][] gCellClickableArea;
	private static GridCell[][] gridCells;

	// For an n * n maze, length = width = n
	private static int lengthMaze;
	private static int gCellPixelLength;

	// Currently selected grid cell (used for game; user grid selection)
	private static GCellArea currentlySelected;
	private static GCellArea startCell, endCell;
	// Used in handleClick. Checks to see if first selected cell
	private static boolean isFirstClick = true;
	private static boolean reachedEndCell = false;

	private static final long serialVersionUID = 8276520671195082139L;

	public MazeEscapeApp() {
		super("MazeEscapeApp");
		//This better work!!!!!!!!!!!!!!!!!!!!!!!!!!! 
		//FOR THE KINGDOM!!!!!!!!!!!!!!!!!!!!
		//AHHHHHHHHHHHHH!!!!!!!!
		
		// FOR SPARTA! 
		//LOL
	}

	public static void main(String[] args) {
		// Instantiate and open window
		DrawApplication window = new MazeEscapeApp();
		window.open();
		window.setSize(900, 900);
		DrawingView view = window.view();

		// Length = Width for n * n maze
		setDifficulty("Easy");
		setGcellPixelLength(30);
		gridCells = new GridCell[lengthMaze][lengthMaze];
		gCellClickableArea = new GCellArea[lengthMaze][lengthMaze];

		// Draw the graphical maze grid
		MazeEscapeApp.drawMaze(gCellPixelLength, view);

		// Generates internal maze representation and updates graphical
		// representation
		generateAndDrawMaze();

		// Update the DrawingView to show updated information
		view.repairDamage();
	}

	/**
	 * Draws the maze onto the given view
	 * 
	 * @param sizeBoxes
	 *            - pixel length of a side of a cell
	 * @param view
	 *            - the DrawingView to draw onto
	 */
	public static void drawMaze(int sizeBoxes, DrawingView view) {
		int n = lengthMaze;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				gCellClickableArea[r][c] = new GCellArea(new Point(c
						* sizeBoxes, r * sizeBoxes), new Point(c * sizeBoxes
						+ sizeBoxes, r * sizeBoxes + sizeBoxes));
				gCellClickableArea[r][c].setRow(r);
				gCellClickableArea[r][c].setColumn(c);

				view.add(gCellClickableArea[r][c]);
				gridCells[r][c] = new GridCell(c * sizeBoxes, r * sizeBoxes,
						view);
			}
		}
	}

	/**
	 * Removes the line shared by the two cells given by the two coordinates
	 */
	public static void removeLine(int r1, int c1, int r2, int c2) {
		if (!(r1 > lengthMaze || r2 > lengthMaze || c1 > lengthMaze || c2 > lengthMaze))
			if (r1 == r2 && c1 < c2) {
				gridCells[r1][c1].removeLine(GridCell.RIGHT);
				gridCells[r2][c2].removeLine(GridCell.LEFT);
			} else if (r1 == r2 && c1 > c2) {
				gridCells[r1][c1].removeLine(GridCell.LEFT);
				gridCells[r2][c2].removeLine(GridCell.RIGHT);
			} else if (c1 == c2 && r1 < r2) {
				gridCells[r1][c1].removeLine(GridCell.DOWN);
				gridCells[r2][c2].removeLine(GridCell.UP);
			} else if (c1 == c2 && r1 > r2) {
				gridCells[r1][c1].removeLine(GridCell.UP);
				gridCells[r2][c2].removeLine(GridCell.DOWN);
			}
	}

	/**
	 * Create underlying maze representation, and update graphical maze
	 * representation by removing unnecessary edges, or walls in the maze
	 */
	private static void generateAndDrawMaze() {
		// Create factory and maze of size length * length (or n * n)
		MazeFactory mazeFactory = new MazeFactory();
		MyEntry<MazeInfo, ALGraph> mazeParts = mazeFactory
				.generateMaze(lengthMaze);
		// Retrieve relevant info and minimum spanning tree
		MazeInfo mazeInfo = mazeParts.getKey();
		ALGraph mst = mazeParts.getValue();

		// Retrieve edges that need to be removed from base grid to generate
		// a maze
		EdgeList el = mst.edges();
		for (Edge e : el) {
			Coordinate c = e.getIdentity();
			removeLine(c.getRow(), c.getCol(), c.getRow2(), c.getCol2());
		}
		// Remove start and end edges
		removeStartOrEndLine(mazeInfo.getRStart(), mazeInfo.getCStart());
		removeStartOrEndLine(mazeInfo.getRFinish(), mazeInfo.getCFinish());

		startCell = gCellClickableArea[mazeInfo.getRStart()][mazeInfo
				.getCStart()];
		endCell = gCellClickableArea[mazeInfo.getRFinish()][mazeInfo
				.getCFinish()];
		endCell.setAttribute(FigureAttributeConstant.FILL_COLOR, Color.WHITE);

	}

	/**
	 * Used to remove the start/end line at a given location r, c
	 * 
	 * @param r
	 * @param c
	 */
	private static void removeStartOrEndLine(int r, int c) {
		if (c == 0)
			gridCells[r][c].removeLine(GridCell.LEFT);
		else if (r == 0)
			gridCells[r][c].removeLine(GridCell.UP);
		else if (c == lengthMaze - 1)
			gridCells[r][c].removeLine(GridCell.RIGHT);
		else if (r == lengthMaze - 1)
			gridCells[r][c].removeLine(GridCell.DOWN);

	}

	public static void setDifficulty(String difficulty) {
		if (difficulty.equals("Easy")) {
			lengthMaze = 10;
		} else if (difficulty.equals("Medium")) {
			lengthMaze = 15;
		} else if (difficulty.equals("Hard")) {
			lengthMaze = 30;
		}
	}
	
	// No menus created
	@Override
	protected void createMenus(JMenuBar mb){
		super.createMenus(mb);
	}

	public static int getGcellPixelLength() {
		return gCellPixelLength;
	}

	public static void setGcellPixelLength(int newLength) {
		gCellPixelLength = newLength;
	}

	public static GCellArea[][] getgCellClickableArea() {
		return gCellClickableArea;
	}

	public static void setgCellClickableArea(GCellArea[][] gCellClickableArea) {
		MazeEscapeApp.gCellClickableArea = gCellClickableArea;
	}

	public static GridCell[][] getGridCells() {
		return gridCells;
	}

	public static void setGridCells(GridCell[][] gridCells) {
		MazeEscapeApp.gridCells = gridCells;
	}

	public static int getLengthMaze() {
		return lengthMaze;
	}

	public static void setLengthMaze(int lengthMaze) {
		MazeEscapeApp.lengthMaze = lengthMaze;
	}

	public static int getgCellPixelLength() {
		return gCellPixelLength;
	}

	public static void setgCellPixelLength(int gCellPixelLength) {
		MazeEscapeApp.gCellPixelLength = gCellPixelLength;
	}

	public static GCellArea getCurrentlySelected() {
		return currentlySelected;
	}

	public static void setCurrentlySelected(GCellArea currentlySelected) {
		MazeEscapeApp.currentlySelected = currentlySelected;
	}

	public static GCellArea getStartCell() {
		return startCell;
	}

	public static void setStartCell(GCellArea startCell) {
		MazeEscapeApp.startCell = startCell;
	}

	public static GCellArea getEndCell() {
		return endCell;
	}

	public static void setEndCell(GCellArea endCell) {
		MazeEscapeApp.endCell = endCell;
	}

	public static boolean isFirstClick() {
		return isFirstClick;
	}

	public static void setFirstClick(boolean isFirstClick) {
		MazeEscapeApp.isFirstClick = isFirstClick;
	}

	public static boolean isReachedEndCell() {
		return reachedEndCell;
	}

	public static void setReachedEndCell(boolean reachedEndCell) {
		MazeEscapeApp.reachedEndCell = reachedEndCell;
	}
}
