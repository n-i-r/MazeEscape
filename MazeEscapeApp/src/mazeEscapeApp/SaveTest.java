package mazeEscapeApp;

import java.util.*;

public class SaveTest {
	public static void main (String[] args)
	{
		ArrayList<String> test = new ArrayList<String>();
		
		test.add("hi");
		test.add("hello");
		
		MazeSaveLoad msl = new MazeSaveLoad(test);
		
		ArrayList<GCellCoordinate> test2 = msl.getCellArray();
		
		test2.add(new GCellCoordinate(1,2));
		test2.add(new GCellCoordinate(3,4));
		
		msl.saveGame();
		msl.loadGame();
	}
}
