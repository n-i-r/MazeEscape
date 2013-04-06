package AdjacencyListGraph;

public class Coordinate {
	private int row;
	private int col;
	private int row2;
	private int col2;
	boolean vertex;
	
	public Coordinate(int r, int c)
	{
		row=r;
		col=c;
		vertex=true;
	}
	
	public Coordinate(int r, int c, int r2, int c2)
	{
		row=r;
		col=c;
		row2=r2;
		col2=c2;
		vertex=false;
	}
	
	public Coordinate(Coordinate coord1, Coordinate coord2)
	{
		row=coord1.getRow();
		col=coord1.getCol();
		row2=coord2.getRow();
		col2=coord2.getCol();
		vertex=false;
	}
	
	public boolean isVertex()
	{
		return vertex;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getRow2()
	{
		if(!isVertex())
			return row2;
		else
			throw new GraphDataTypeMismatch();
	}
	
	public int getCol2()
	{
		if(!isVertex())
			return col2;
		else
			throw new GraphDataTypeMismatch();
	}
	
	public String toString()
	{
		if(isVertex())
			return row + " " + col;
		else
			return row + " " + col + " " + row2 + " " + col2;
	}
	
	public boolean equals(Coordinate c)
	{
		if(c.isVertex())
		{
			return (c.getCol()==col && c.getRow()==row);
		}
		else
		{
			return (c.getCol()==col && c.getRow()==row && c.getCol2()==col2 && c.getRow2()==row2);
		}
	}
}
