package baseMazeCode;
import AdjacencyListGraph.*;

public class MazeDisplay {
	private MazeInfo mazeInfo;
	private ALGraph mst;
	private EdgeList edges;
	private int row;
	private boolean isSolved;
	@SuppressWarnings("unused")
	private VertexList vSoln;
	private EdgeList eSoln;
	private EdgeList nonPath;
	
	public MazeDisplay(MazeInfo mI, ALGraph m)
	{
		mazeInfo=mI;
		mst=m;
		edges=mst.edges();
		row=0;
		isSolved=false;
		nonPath=new EdgeList();
	}
	
	public MazeDisplay(MazeInfo mI, ALGraph m, VertexList vS, EdgeList eS)
	{
		mazeInfo=mI;
		mst=m;
		edges=mst.edges();
		row=0;
		isSolved=true;
		vSoln=vS;
		eSoln=eS;
		nonPath=new EdgeList();
		
	}
	
	public void drawMaze()
	{
		drawTop();
		for(int x=1;x<=mazeInfo.getN();x++)
			drawBody();
		System.out.println();
		for(Edge e: nonPath)
			System.out.println(e.getIdentity());
	}
	
	private void drawTop()
	{
		System.out.print(" ");
		for(int x=0;x<mazeInfo.getN();x++)
		{
			boolean removed=false;
			
			if(mazeInfo.getRStart()==0 && mazeInfo.getCStart()==x)
				removed=true;
			else if(mazeInfo.getRFinish()==0 && mazeInfo.getCFinish()==x)
				removed=true;
			
			if((x>0 && x<(mazeInfo.getN()-1)) && removed)
				System.out.print("  ");
			else
				System.out.print("_ ");
		}
		System.out.print("\n");
	}
	
	private void drawBody()
	{
		drawStart();
		
		for(int col=0;col<mazeInfo.getN();col++)
		{
			boolean removed=false;
			boolean path=false;
			//Check for removed floor
			for(Edge e:edges)
			{
				
				if(e.getIdentity().getRow()==row && e.getIdentity().getCol()==col)
				{
					if(e.getIdentity().getRow2()==row+1 && e.getIdentity().getCol2()==col)
					{
						//System.out.print(" ");
						removed=true;
					}
				}
				
				if(col>0 && col<(mazeInfo.getN()-1))
				{
					if(mazeInfo.getRStart()==row && mazeInfo.getCStart()==col)
						removed=true;
					else if(mazeInfo.getRFinish()==row && mazeInfo.getCFinish()==col)
						removed=true;
				}

			}
			
			if(isSolved)
			{
				for(Edge e:eSoln)
				{
					if(e.getIdentity().getRow()==row && e.getIdentity().getCol()==col)
					{
						if(e.getIdentity().getRow2()==row+1 && e.getIdentity().getCol2()==col)
						{
							//System.out.print(" ");
							path=true;
						}
						else if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==col+1)
						{
							//System.out.print(" ");
							path=true;
						}
						else if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==col-1)
						{
							//System.out.print(" ");
							path=true;
						}
						else if(e.getIdentity().getRow2()==row-1 && e.getIdentity().getCol2()==col)
						{
							//System.out.print(" ");
							path=true;
						}
						else
						{
							nonPath.add(e);
						}
						
					}
					else if(e.getIdentity().getRow()==row+1 && e.getIdentity().getCol()==col)
					{
						if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==col)
							path=true;
					}
					else if(e.getIdentity().getRow()==row && e.getIdentity().getCol()==col+1)
					{
						if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==col)
							path=true;
					}
					else if(e.getIdentity().getRow()==row-1 && e.getIdentity().getCol()==col)
					{
						if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==col)
							path=true;
					}
					else if(e.getIdentity().getRow()==row && e.getIdentity().getCol()==col-1)
					{
						if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==col)
							path=true;
					}
					
				}
				
				
				if(mazeInfo.getRStart()==row && mazeInfo.getCStart()==col)
					path=true;
				else if(mazeInfo.getRFinish()==row && mazeInfo.getCFinish()==col)
					path=true;
			}
			
			if (path)
			{
				System.out.print("*");
			}
			else if(removed)
				System.out.print(" ");
			else
				System.out.print("_");
			
			//Check for removed column
			
			if(!(col==mazeInfo.getN()-1))
			{
				boolean removed2=false;
	
				for(Edge e:edges)
				{
					if(e.getIdentity().getRow()==row && e.getIdentity().getCol()==col)
					{
						if(e.getIdentity().getRow2()==row && e.getIdentity().getCol2()==(col+1))
						{
							//System.out.print(" ");
							removed2=true;
						}
					}
					
				}
				if(removed2)
				{
					System.out.print(" ");
				}
				else
					System.out.print("|");
			}
		}
		
		drawFinish();
		//System.out.print("\n");
		row++;
	}
		
	private void drawFinish()
	{
		if(mazeInfo.getRStart()==row && mazeInfo.getCStart()==(mazeInfo.getN()-1))
		{
			if(row==(mazeInfo.getN()-1) && isSolved)
				System.out.print("|");
			else
				System.out.print(" ");
		}
		else if(mazeInfo.getRFinish()==row && mazeInfo.getCFinish()==(mazeInfo.getN()-1))
		{
			if(row==(mazeInfo.getN()-1) && isSolved)
				System.out.print("|");
			else
				System.out.print(" ");
		}
		else
			System.out.print("|");
		
		System.out.print("\n");
	}
	
	private void drawStart()
	{
		if(mazeInfo.getRStart()==row && mazeInfo.getCStart()==0)
		{
			if(row==(mazeInfo.getN()-1) && isSolved)
				System.out.print("|");
			else
				System.out.print(" ");
		}
		else if(mazeInfo.getRFinish()==row && mazeInfo.getCFinish()==0)
		{
			if(row==(mazeInfo.getN()-1) && isSolved)
			{
				System.out.print("|");
			}
			else
				System.out.print(" ");
		}
		else
			System.out.print("|");
		
		//System.out.print("\n");
	}
}
