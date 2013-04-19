package mazeEscapeApp;

import java.util.Scanner;

public class GCellCoordinate {
	private int row;
	private int col;
	
	public GCellCoordinate(int r, int c)
	{
		row=r;
		col=c;
	}
	
	public GCellCoordinate(String s)
	{
		Scanner scan = new Scanner(s);
		row=scan.nextInt();
		col=scan.nextInt();
		scan.close();
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
	
	public String toString()
	{
		return row + " " + col;
	}
	
	

}
