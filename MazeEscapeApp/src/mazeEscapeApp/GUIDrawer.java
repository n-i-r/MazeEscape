package mazeEscapeApp;

import java.awt.Color;
import java.awt.Point;

import org.jhotdraw.framework.DrawingView;
import org.jhotdraw.framework.FigureAttributeConstant;

import baseMazeCode.MazeFactory;
import baseMazeCode.MazeInfo;

import AdjacencyListGraph.ALGraph;
import AdjacencyListGraph.Coordinate;
import AdjacencyListGraph.Edge;
import AdjacencyListGraph.EdgeList;
import AdjacencyListGraph.Vertex;
import AdjacencyListGraph.VertexList;
import HeapPriorityQueue.KeyValPair;

/*
 * Used for doing GUI-related work for the maze
 */
public class GUIDrawer {
	private MazeEscape mazeEscape;
	private DrawingView view;
	private static final int SHIFT = 50;
	private MazeFactory mazeFactory;

	public GUIDrawer(MazeEscape m) {
		mazeEscape = m;
		view = m.view();
		mazeFactory = new MazeFactory(mazeEscape.getLengthMaze());
	}

	/**
	 * Draws the maze onto the given view
	 */
	public void drawMaze() {
		int n = mazeEscape.getLengthMaze();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				mazeEscape.getgCellClickableArea()[r][c] = new GCellArea(
						new Point(SHIFT + c * mazeEscape.getgCellPixelLength(),
								SHIFT + r * mazeEscape.getgCellPixelLength()),
						new Point(SHIFT + c * mazeEscape.getgCellPixelLength()
								+ mazeEscape.getgCellPixelLength(), SHIFT + r
								* mazeEscape.getgCellPixelLength()
								+ mazeEscape.getgCellPixelLength()), mazeEscape);
				mazeEscape.getgCellClickableArea()[r][c].setRow(r);
				mazeEscape.getgCellClickableArea()[r][c].setColumn(c);

				view.add(mazeEscape.getgCellClickableArea()[r][c]);
				mazeEscape.getGridCells()[r][c] = new GridCell(SHIFT + c
						* mazeEscape.getgCellPixelLength(), SHIFT + r
						* mazeEscape.getgCellPixelLength(), mazeEscape);
			}
		}
	}

	/**
	 * Create underlying maze representation, and update graphical maze
	 * representation by removing unnecessary edges, or walls in the maze
	 */
	public void generateAndDrawMaze() {
		// Create maze of size length * length (or n * n)
		KeyValPair<MazeInfo, ALGraph> mazeParts = mazeFactory.generateMaze();
		// Retrieve relevant info and minimum spanning tree
		MazeInfo mazeInfo = mazeParts.getKey();
		ALGraph mst = mazeParts.getValue();
		int steps = mazeFactory.getMinimumSteps();
		mazeEscape.setSteps(steps);
		mazeEscape.setGUIDrawer(this);

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

		mazeEscape.setStartCell(mazeEscape.getgCellClickableArea()[mazeInfo
				.getRStart()][mazeInfo.getCStart()]);
		mazeEscape.setEndCell(mazeEscape.getgCellClickableArea()[mazeInfo
				.getRFinish()][mazeInfo.getCFinish()]);
		mazeEscape.getEndCell().setAttribute(
				FigureAttributeConstant.FILL_COLOR, Color.WHITE);

	}

	/**
	 * Used to remove the start/end line at a given location r, c
	 * 
	 * @param r
	 * @param c
	 */
	private void removeStartOrEndLine(int r, int c) {
		if (c == 0)
			mazeEscape.getGridCells()[r][c].removeLine(GridCell.LEFT);
		else if (r == 0)
			mazeEscape.getGridCells()[r][c].removeLine(GridCell.UP);
		else if (c == mazeEscape.getLengthMaze() - 1)
			mazeEscape.getGridCells()[r][c].removeLine(GridCell.RIGHT);
		else if (r == mazeEscape.getLengthMaze() - 1)
			mazeEscape.getGridCells()[r][c].removeLine(GridCell.DOWN);

	}

	/**
	 * Removes the line shared by the two cells given by the two coordinates
	 */
	public void removeLine(int r1, int c1, int r2, int c2) {
		if (!(r1 > mazeEscape.getLengthMaze()
				|| r2 > mazeEscape.getLengthMaze()
				|| c1 > mazeEscape.getLengthMaze() || c2 > mazeEscape
				.getLengthMaze()))
			if (r1 == r2 && c1 < c2) {
				mazeEscape.getGridCells()[r1][c1].removeLine(GridCell.RIGHT);
				mazeEscape.getGridCells()[r2][c2].removeLine(GridCell.LEFT);
			} else if (r1 == r2 && c1 > c2) {
				mazeEscape.getGridCells()[r1][c1].removeLine(GridCell.LEFT);
				mazeEscape.getGridCells()[r2][c2].removeLine(GridCell.RIGHT);
			} else if (c1 == c2 && r1 < r2) {
				mazeEscape.getGridCells()[r1][c1].removeLine(GridCell.DOWN);
				mazeEscape.getGridCells()[r2][c2].removeLine(GridCell.UP);
			} else if (c1 == c2 && r1 > r2) {
				mazeEscape.getGridCells()[r1][c1].removeLine(GridCell.UP);
				mazeEscape.getGridCells()[r2][c2].removeLine(GridCell.DOWN);
			}
	}
	
	public void drawSolution()
	{
		//Get the list of "solution" vertices from the mazeFactory
		//TODO: change this to come from the Solver (unless we don't mind ignoring our UML lol)
		VertexList vSoln = mazeFactory.getVertexSoln();
		
		//Iterate through the VertexList and change the color of the relevant cells
		for(Vertex v : vSoln)
		{
			Coordinate c = v.getElement();
		    mazeEscape.getgCellClickableArea()[c.getRow()][c.getCol()].setAttribute(FigureAttributeConstant.FILL_COLOR, Color.YELLOW);
		}
		
		//Refresh the view so we can see the solution
		mazeEscape.view().repairDamage();
		
	}
	
}