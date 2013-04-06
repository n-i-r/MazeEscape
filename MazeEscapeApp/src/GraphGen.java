//GraphGen takes in a file with the maze details. It parses the file and creates the
//graph equivalent. It returns a key/value pair with the graph and the maze info.

import java.util.*;

import HeapPriorityQueue.MyEntry;
import java.io.*;

import AdjacencyListGraph.*;

public class GraphGen {
	private File file;              //The text file to parse
	private ALGraph graph;          //The output graph
	private Scanner scan;           //The Scanner that reads the File
	private Scanner parse;          //The Scanner that reads the individual lines of the file
	private MazeInfo mazeInfo;      //The class that stores the details for the maze
	private int line;               //The current line # of the File being parsed
	private ArrayList<Vertex[]> vertexList;  //A list of Vertex arrays to help create the graph
	private int globalRow;          //The current row of the maze being constructed
	private boolean debug=false;    //An internal toggle to aid debugging
	
	public GraphGen(File f)
	{
		file=checkFile(f);
		graph=new ALGraph();
		vertexList=new ArrayList<Vertex[]>();
		globalRow=0;
		
		try {
			scan=new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, but we couldn't find that file.");
			System.out.println("We're dispatching a search squad, but in the meantime, \nplease try again.");
			System.out.println("\nTip: If you're using Windows Vista or higher, try holding Shift");
			System.out.println("and right-clicking the file, choose Copy as Path, and paste");
			System.out.println("that here.");
			
			System.out.println("\nGo ahead and try to enter the path again:");
			readInput();
		}
	}
	
	public GraphGen()
	{
		graph=new ALGraph();
		vertexList=new ArrayList<Vertex[]>();
		globalRow=0;
	}
	
	private void readInput()
	{
		//Accepts the input path from the user
		boolean loop=true;
		while(loop)
		{
			loop=false;
			scan=new Scanner(System.in);
			String path=scan.nextLine();
			if(path.toLowerCase().equals("quit"))
			{
				loop=false;
				System.exit(0);
			}
			else
			{
				file=new File(path);
				file=checkFile(file);
			}
			
			try
			{
				scan=new Scanner(file);
			}catch(FileNotFoundException e)
			{
				System.out.println("Sorry, but we still couldn't find your file.");
				System.out.println("Feel free to try again, or type QUIT to quit.");
				System.out.println("\nType below:");
				loop=true;
			}
		}
	}		
		
	public MyEntry<ALGraph, MazeInfo> generate()
	{
		//Set line to 0 since we're just starting the parse sequence
		line=0;
		
		while(scan.hasNextLine())
		{
			line++;
			if(line==1)
			{
				//This condition is for the very first line of the input
				//Since this line has values like n, rStart, etc, it needs to be handles differently
				boolean response=processLine1(scan.nextLine());
				if(response==false)
					throw new RestartProgramSignal();
			}
			else
			{
				//The other lines of the input is handled by this
				boolean response=processBodyLine(scan.nextLine());
				if(response==false)
					throw new RestartProgramSignal();
			}
		}
		
		if(debug)System.out.println("Parse sequence completed.");
		
		//Combine the newly generated graph with the MazeInfo and return it
		MyEntry<ALGraph, MazeInfo> returnVals=new MyEntry<ALGraph, MazeInfo>(graph, mazeInfo);
		return returnVals;
	}
	
	public MyEntry<ALGraph, MazeInfo> manualGenerate()
	{
		scan=new Scanner(System.in);
		line=1;
		
		System.out.println("\nEnter the first line that contains the maze information (separated\nby spaces):");
		String ln1=scan.nextLine();
		boolean rsp1=processLine1(ln1);
		if(!rsp1)
			throw new RestartProgramSignal();
		
		for(int x=1;x<=(2*mazeInfo.getN()-1);x++)
		{
			line++;
			System.out.println("Enter a line of maze weights ("+x+" of " + (2*mazeInfo.getN()-1)+"):");
			boolean response=processBodyLine(scan.nextLine());
			if(response==false)
				throw new RestartProgramSignal();
		}
		
		MyEntry<ALGraph, MazeInfo> returnVals=new MyEntry<ALGraph, MazeInfo>(graph, mazeInfo);
		return returnVals;
	}
		
	
	
	private boolean processLine1(String s)
	{
		//Parses the first line of the input
		
		int n, rStart, rFinish, cStart, cFinish;
		boolean status=true;
		
		parse=new Scanner(s);
		try
		{
			//Attempt to parse the info
			n=parse.nextInt();
			rStart=parse.nextInt();
			cStart=parse.nextInt();
			rFinish=parse.nextInt();
			cFinish=parse.nextInt();
			
			//Check to make sure the input is valid
			if(n<2)
			{
				System.out.println("Your N value is too small. Please try again.");
				status=false;
			}
			if(rStart<0 || rStart>=n)
			{
				System.out.println("Check your r_start value. It's a bit wierd...");
				status=false;
			}
			if(cStart<0 || cStart>=n)
			{
				System.out.println("Check your c_start value. It doesn't seem right.");
				status=false;
			}
			if(rFinish<0 || rFinish>=n)
			{
				System.out.println("Check your r_finish value. It's a bit too big/small.");
				status=false;
			}
			if(cFinish<0 || cFinish>=n)
			{
				System.out.println("Check your c_finish value. It's doesn't fit properly.");
				status=false;
			}
			if(rStart==rFinish && cStart==cFinish)
			{
				System.out.println("Your maze starts and ends at the same spot. Last time we checked,\nthat's not really a maze...");
				status=false;
			}
			
			//Check for non-critical conditions
			if(status==true)
			{
				if(rStart!=0 && cStart!=0)
					System.out.println("\nSo you know, your starting point isn't on the border.\nBut we're cool with that if you did that intentionally.\n");
				if(rFinish!=(n-1) && cFinish!=(n-1))
					System.out.println("\nSo you know, your ending point isn't on the border.\nBut we're cool with that if you did that intentionally.\n");
			}
		}catch(Exception e)
		{
			System.out.println("Something is wrong with the first line of your file...");
			return false;
		}
		
		mazeInfo=new MazeInfo(n, rStart, cStart, rFinish, cFinish);
		return status;
	}
	
	private boolean processBodyLine(String s)
	{
		//This method parses all the other lines of the input besides the first one
		
		if(debug)System.out.println("globalRow: "+globalRow);
		
		//Parse the individual line of the input and store in an ArrayLIst
		parse=new Scanner(s);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		while(parse.hasNext())
			arr.add(parse.nextInt());
		
		if (arr.size()==mazeInfo.getN())
		{
			
			//If the input line corresponds to the information about the horizontal maze lines
			if(line%2!=0)
				processHorizontal(arr);
			else
			{
				System.out.println("Whoah there! Your input has some problems.");
				System.out.println("Make sure you're alternating between lines of length N-1 and N.");
				System.out.println("In this case, line " + line+" should have been "+(mazeInfo.getN()-1)+" numbers long, but it wasn't...");
			}
		}
		else if(arr.size()==(mazeInfo.getN())-1)
		{
			//Vertical maze lines
			if(line%2==0)
				processVertical(arr);
			else
			{
				System.out.println("Whoah there! Your input has some problems.");
				System.out.println("Make sure you're alternating between lines of length N-1 and N.");
				System.out.println("In this case, line " + line+" should have been "+(mazeInfo.getN())+" numbers long, but it wasn't...");
				return false;
			}
			
		}
		else
		{
			//Error
			System.out.println("Whoah there! Your input has some problems.");
			System.out.println("Make sure you're alternating between lines of length N-1 and N.");
			System.out.println("Take another look at line "+line);
			return false;
		}
		return true;
	}
	
	private void processVertical(ArrayList<Integer> arr)
	{
		int row=globalRow;
		
		if(debug)System.out.println("Line: " + line);
		if(debug)System.out.println("vertexList.size()-1: " + (vertexList.size()-1) + " row: "+row);

		if(vertexList.size()==row)
		{
			//Need to created new vertices for this row...
			int col=0;
			
			Vertex[] vertices = new Vertex[mazeInfo.getN()];
			vertices[0]=graph.insertVertex(new Coordinate(row,col));
			col++;
			
			if(debug)System.out.println("vertices.length: "+vertices.length + " arr.size: " + arr.size());
			
			for(int x=1;x<=arr.size();x++)
			{
				if(debug)System.out.println(x + " " + col);
				vertices[x]=graph.insertVertex(new Coordinate(row, col));
				graph.insertEdge(vertices[x-1], vertices[x], arr.get(x-1), new Coordinate(vertices[x-1].getElement(), vertices[x].getElement()));
				col++;
				
			}
			if(debug)System.out.println("Row: "+row+" pV");
			vertexList.add(row, vertices);
		}
		else
		{
			//Vertices already exist for this row
			//Just connect the dots
			Vertex[] vertices = vertexList.get(row);
			
			for(int x=1;x<vertices.length;x++)
			{
				graph.insertEdge(vertices[x-1], vertices[x], arr.get(x-1), new Coordinate(vertices[x-1].getElement(), vertices[x].getElement()));
			}
			
			if(debug)System.out.println("pV else\n");
		}
	}
	
	private void processHorizontal(ArrayList<Integer> arr)
	{
		int row=globalRow;
		
		if(debug)System.out.println("vertexList.size()-1: " + (vertexList.size()-1) + " row: "+row);

		if(true)
		{
			//There exists vertices for this row already
			//Start building the next row
			int col=0;
			row++;
			globalRow++;
			Vertex[] vertices = new Vertex[mazeInfo.getN()];
			
			if(debug)System.out.println("arrSize: " + arr.size() + " verticesLength: "+vertices.length);
			
			for(int x=0;x<arr.size();x++)
			{
				if(debug)System.out.println(x);
				vertices[x]=graph.insertVertex(new Coordinate(row, col));
				graph.insertEdge(vertexList.get(row-1)[x], vertices[x], arr.get(x), new Coordinate(vertexList.get(row-1)[x].getElement(),vertices[x].getElement()));
				col++;
			}
			vertexList.add(row, vertices);
		}
		if(debug)System.out.println("Row: "+row+" pH");
	}
	
	private File checkFile(File f)
	{
		//Checks for erroneous quotes
		String path=f.toString();
		if(path.charAt(0)=='"')
		{
			path=path.replace('"',' ');
			path=path.trim();
		}
		File file=new File(path);
		return file;
	}
	
	public MyEntry<ALGraph, MazeInfo> autoGenerate(int n)
	{
		int num=n;
		int rStart=(int)(num*Math.random());
		int cStart=0;
		int rFinish=(int)(num*Math.random());
		int cFinish=(num-1);
		
		String s=(num + " " + rStart + " " + cStart + " " + rFinish + " " + cFinish);
		processLine1(s);
		System.out.println(s);
		line++;
		
		for(int x=1;x<=(2*num-1);x++)
		{
			line++;
			StringBuffer lne=new StringBuffer();
			if(x%2==0)
			{
				for(int y=1;y<=num;y++)
				{
					lne.append((int)(30*Math.random()) + " ");
				}
					System.out.println(lne);
					processBodyLine(lne.toString());
				
			}
			else
			{
				for(int y=1;y<num;y++)
				{
					lne.append((int)(30*Math.random()) + " ");
				}
					System.out.println(lne);
					processBodyLine(lne.toString());
			}
		}
		System.out.println();
		
		MyEntry<ALGraph, MazeInfo> returnVals=new MyEntry<ALGraph, MazeInfo>(graph, mazeInfo);
		return returnVals;
	}
			
		
		
	
}
