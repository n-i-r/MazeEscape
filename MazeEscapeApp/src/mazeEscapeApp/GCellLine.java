package mazeEscapeApp;

import java.util.List;

import org.jhotdraw.figures.LineFigure;
import org.jhotdraw.framework.HandleEnumeration;
import org.jhotdraw.standard.HandleEnumerator;
import org.jhotdraw.util.CollectionsFactory;

/*
 * Essentially a non-modifiable line figure
 */
public class GCellLine extends LineFigure {
	private static final long serialVersionUID = 8940548985446361162L;

	public GCellLine() {
		super();
	}

	@Override
	public void basicMoveBy(int dx, int dy) {

	}

	@Override
	public HandleEnumeration handles() {
		@SuppressWarnings("rawtypes")
		List handles = CollectionsFactory.current().createList(fPoints.size());
		// for (int i = 0; i < fPoints.size(); i++) {
		// handles.add(new PolyLineHandle(this, locator(i), i));
		// }
		return new HandleEnumerator(handles);
	}
}
