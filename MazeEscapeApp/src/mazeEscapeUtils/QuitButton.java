package mazeEscapeUtils;
import javax.swing.JOptionPane;

import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.standard.*;

public class QuitButton extends AbstractTool{

	public QuitButton(DrawingEditor newDrawingEditor) {
		super(newDrawingEditor);
	}
	
	public void activate()
	{
		JOptionPane.showMessageDialog(null, "The maze will now implode.");
		System.exit(0);
	}
}
