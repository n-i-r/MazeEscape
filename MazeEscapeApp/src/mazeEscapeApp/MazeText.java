package mazeEscapeApp;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import org.jhotdraw.figures.TextFigure;
import org.jhotdraw.framework.FigureAttributeConstant;
import org.jhotdraw.framework.HandleEnumeration;
import org.jhotdraw.standard.HandleEnumerator;
import org.jhotdraw.util.CollectionsFactory;
import org.jhotdraw.util.ColorMap;

/**
 * 
 * @author Mohamed Bhura
 * Figure to display text on maze screen
 */
public class MazeText extends TextFigure {

	private int fOriginX;
	private int fOriginY;

	// cache of the TextFigure's size
	transient private boolean fSizeIsDirty = true;

	private String fText;
	private Font fFont;

	public MazeText() {
		fOriginX = 0;
		fOriginY = 0;
		fFont = createCurrentFont();
		setAttribute(FigureAttributeConstant.FILL_COLOR, ColorMap.color("None"));
		fText = "";
		fSizeIsDirty = true;
	}
	
	public MazeText(int x, int y) {
		fOriginX = x;
		fOriginY = y;
		fFont = createCurrentFont();
		setAttribute(FigureAttributeConstant.FILL_COLOR, ColorMap.color("None"));
		fText = "";
		fSizeIsDirty = true;
	}
	
	
	public void basicDisplayBox(Point newOrigin, Point newCorner) {
		fOriginX = newOrigin.x;
		fOriginY = newOrigin.y;
	}

	/**
	 * @see org.jhotdraw.framework.Figure#displayBox()
	 */
	public Rectangle displayBox() {
		Dimension extent = textExtent();
		return new Rectangle(fOriginX, fOriginY, extent.width, extent.height);
	}
	
	@Override
	public HandleEnumeration handles() {
		List handles = CollectionsFactory.current().createList();
		//handles.add(new NullHandle(this, RelativeLocator.northWest()));
		//handles.add(new NullHandle(this, RelativeLocator.northEast()));
		//handles.add(new NullHandle(this, RelativeLocator.southEast()));
		//handles.add(new FontSizeHandle(this, RelativeLocator.southWest()));
		return new HandleEnumerator(handles);
	}



}
