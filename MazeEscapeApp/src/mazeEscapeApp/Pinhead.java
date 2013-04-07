package mazeEscapeApp;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;

import org.jhotdraw.figures.RectangleFigure;
import org.jhotdraw.framework.HandleEnumeration;
import org.jhotdraw.standard.HandleEnumerator;
import org.jhotdraw.util.CollectionsFactory;
import org.jhotdraw.util.StorableInput;
import org.jhotdraw.util.StorableOutput;


public class Pinhead extends RectangleFigure {

	private Rectangle   fDisplayBox;

	/*
	 * Serialization support.
	 */
	private static final long serialVersionUID = 184722075881789163L;
	private int rectangleFigureSerializedDataVersion = 1;

	public Pinhead() {
		this(new Point(0,0), new Point(0,0));
	}

	public Pinhead(Point origin, Point corner) {
		basicDisplayBox(origin,corner);
	}

	public void basicDisplayBox(Point origin, Point corner) {
		fDisplayBox = new Rectangle(origin);
		fDisplayBox.add(corner);
	}

	public HandleEnumeration handles() {
		List handles = CollectionsFactory.current().createList();
		//BoxHandleKit.addHandles(this, handles);
		return new HandleEnumerator(handles);
	}

	public Rectangle displayBox() {
		return new Rectangle(
			fDisplayBox.x,
			fDisplayBox.y,
			fDisplayBox.width,
			fDisplayBox.height);
	}

	protected void basicMoveBy(int x, int y) {
		//fDisplayBox.translate(x,y);
	}

	public void drawBackground(Graphics g) {
		Rectangle r = displayBox();
		int[] xpoints = new int[]{r.width/2+r.x, r.x, r.x+r.width};
		int[] ypoints = new int[]{r.y, r.y+r.height, r.y+r.height};
		g.fillPolygon(xpoints, ypoints, 3);
	}

	public void drawFrame(Graphics g) {
		Rectangle r = displayBox();
		int[] xpoints = new int[]{r.width/2+r.x, r.x, r.x+r.width};
		int[] ypoints = new int[]{r.y, r.y+r.height, r.y+r.height};
		g.drawPolygon(xpoints, ypoints, 3);
	}

	//-- store / load ----------------------------------------------

	public void write(StorableOutput dw) {
		super.write(dw);
		dw.writeInt(fDisplayBox.x);
		dw.writeInt(fDisplayBox.y);
		dw.writeInt(fDisplayBox.width);
		dw.writeInt(fDisplayBox.height);
	}

	public void read(StorableInput dr) throws IOException {
		super.read(dr);
		fDisplayBox = new Rectangle(
			dr.readInt(),
			dr.readInt(),
			dr.readInt(),
			dr.readInt());
	}
}
