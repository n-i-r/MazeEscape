package mazeEscapeApp;

public class GCellCoordinate {
	private int row;
	private int col;
	
	public GCellCoordinate(int r, int c)
	{
		row=r;
		col=c;
	}
	
	public GCellCoordinate(GCellArea cell)
	{
		row=cell.getRow();
		col=cell.getColumn();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	

}
