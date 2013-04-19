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
	
	public void saveGame()
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
				
				for(String s:mazeFile)
				{
					out.println(s);
				}
				
				//Separator token
				out.println("***===***");
				
				for(GCellCoordinate c:saveCells)
				{
					out.println(c);
				}
				
				out.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Could not write to that location. Please try again.");
			}
		}
	}

	public void loadGame()
	{
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			//Scanner scan = new Scanner(file);
			
			//if(scan.nextLine().equals("MazeEscape1 Save"))
			//{
			//	boolean readingMazeFile = true;
			//}
		}
	}

}
