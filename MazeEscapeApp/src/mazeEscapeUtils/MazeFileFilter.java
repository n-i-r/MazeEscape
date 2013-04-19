package mazeEscapeUtils;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

public class MazeFileFilter extends FileFilter{
	
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		
		String extension = parseExtension(f);
		
		if(extension != null && extension.equals("maze"))
			return true;
		
		return false;
	}
	
	public String getDescription()
	{
		return "MazeEscape Game Save (*.maze)";
	}
	
	private String parseExtension(File f)
	{
		String s = f.getName();
		String extension=null;
		int i=s.lastIndexOf('.');
		
		if(i>0 && i<s.length()-1)
			extension = s.substring(i+1).toLowerCase();
		
		return extension;
	}

}
