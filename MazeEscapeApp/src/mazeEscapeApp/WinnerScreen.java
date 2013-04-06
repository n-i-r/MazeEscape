package mazeEscapeApp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class to display dialog box when end of maze is reached.
 * 
 * @author Mohamed Bhura
 * 
 */
public class WinnerScreen {
	private double accuracy;
	private int points;

	public WinnerScreen() {
		super();
	}

	/**
	 * @param accuracy
	 * @param points
	 */
	public void writeOutput(double accuracy, int points) {
		this.accuracy = accuracy;
		this.points = points;

		// Creates message and options for dialog box
		final JOptionPane optionPane = new JOptionPane(
				"Your completion accuracy is " + accuracy + "%!\n"
						+ "Your maze score is " + points + " points!\n"
						+ "Play Again?", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION);

		// Creates dialog box displaying the message
		final JDialog dialog = new JDialog(new JFrame(), "You won!", true);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				
				// Will execute a specific method right before closing.
				if (dialog.isVisible() && (e.getSource() == optionPane)
						&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
					System.out.println("Closed!");
					dialog.setVisible(false);
				}
			}
		});
		dialog.pack();
		dialog.setVisible(true);
		
		// If yes option selected, new maze will generate
		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			// todo
		// If no option selected, program will close.
		} else if (value == JOptionPane.NO_OPTION) {
			//todo
		}

	}

}
