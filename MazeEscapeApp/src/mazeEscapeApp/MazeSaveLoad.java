package mazeEscapeApp;

import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import mazeEscapeUtils.MazeFileFilter;

public class MazeSaveLoad {
	private ArrayList<String> mazeFile;                 //The ArrayList that holds the initial maze numberings
	private ArrayList<GCellCoordinate> saveCells;       //The modified cells since the game began
	private final JFileChooser fc = new JFileChooser(); //The file chooser for save/load purposes
	
	public MazeSaveLoad(ArrayList<String> maze)
	{
		//Instantiations
		mazeFile = maze;
		saveCells = new ArrayList<GCellCoordinate>();
		
		//Configure the file chooser
		MazeFileFilter filter = new MazeFileFilter();
		fc.addChoosableFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(filter);
	}
	
	public MazeSaveLoad()
	{
		//Instantiations
		mazeFile = null;
		saveCells = new ArrayList<GCellCoordinate>();
		
		//Configure the file chooser
		MazeFileFilter filter = new MazeFileFilter();
		fc.addChoosableFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(filter);
	}
	
	public ArrayList<GCellCoordinate> getCellArray()
	{
		return saveCells;
	}
	
	public void setMazeFile(ArrayList<String> m)
	{
		mazeFile = m;
	}
	
	public void saveGame(MazeEscape m)
	{
		
	}
	private void saveGame(GCellArea cS, boolean iFC, boolean rEC, boolean reset,
			boolean on, int lvlPts, int tP, int tS, int mS, int sT, String t, String c)
	{
		//Have the user select the save location
		fc.setSelectedFile(new File("MazeSave"));
		int returnVal = fc.showSaveDialog(null);
		
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			//Add the .maze extension to the file
			File file = fc.getSelectedFile();
			file = new File(file.getPath() + ".maze");
			try {
				//Write mazeFile and saveCells to the file
				PrintWriter out = new PrintWriter(file);
				
				//Header
				out.println("MazeEscape1 Save");
				
				//MazeFile
				for(String s:mazeFile)
				{
					out.println(s);
				}
				
				//Separator token
				out.println("***===***");
				
				//SaveCells
				for(GCellCoordinate gc:saveCells)
				{
					out.println(gc);
				}
				
				//Separator
				out.println("***===***");
				
				//Currently Selected
				out.println(new GCellCoordinate(cS));
				
				//isFirstClick
				out.println(iFC);
				
				//reachedEndCell
				out.println(rEC);
				
				//reset
				out.println(reset);
				
				//on
				out.println(on);
				
				//levelPoints
				out.println(lvlPts);
				
				//timePassed
				out.println(tP);
				
				//timeScore
				out.println(tS);
				
				//minSteps
				out.println(mS);
				
				//stepsTaken
				out.println(sT);
				
				//time
				out.println(t);
				
				//count
				out.println(s);
				
				out.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Could not write to that location. Please try again.");
			}
		}
	}

	public void loadGame()
	{
		mazeFile=new ArrayList<String>();
		saveCells=new ArrayList<GCellCoordinate>();
		
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			
			try
			{
				Scanner scan = new Scanner(file);
				
				if(scan.nextLine().equals("MazeEscape1 Save"))
				{
					boolean readingMazeFile = true;
					boolean readingCoordinateFile=false;
					
					while(readingMazeFile)
					{
						String s = scan.nextLine();
						if(s.equals("***===***"))
						{
							readingCoordinateFile=true;
							readingMazeFile=false;
						}
						else
						{
							mazeFile.add(s);
						}
					}
					
					while(readingCoordinateFile && scan.hasNext())
					{
						String s = scan.next();
						saveCells.add(new GCellCoordinate(s));
					}
						
				}
			}catch(FileNotFoundException e)
			{
				//Implement me!
			}
		}
	}
}
