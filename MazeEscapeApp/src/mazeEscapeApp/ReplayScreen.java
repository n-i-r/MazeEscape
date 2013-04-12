package mazeEscapeApp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class to display dialog box for selecting maze difficulty
 * 
 * @author Mohamed Bhura
 * 
 */
public class ReplayScreen {
	private MazeEscape maze;

	public ReplayScreen(MazeEscape m) {
		super();
		maze = m;
	}

	public void selectDifficulty() {
		Object[] options = { "Easy", "Medium", "Hard" };

		// Creates message and options for dialog box
		final JOptionPane optionPane = new JOptionPane(
				"Welcome to Mazeland! \n" + "Please select maze difficulty:",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null,
				options, options[0]);

		// Creates dialog box displaying the message
		final JDialog dialog = new JDialog(new JFrame(), "Difficulty Selection", true);
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

		// Allows to set difficulty of maze, based on selection
		String value = optionPane.getValue().toString();
		System.out.println(value);
		if (value == "Easy") {
			maze.setDifficulty("Easy");
		} else if (value == "Medium") {
			maze.setDifficulty("Medium");
		} else if (value == "Hard") {
			maze.setDifficulty("Hard");
		}
	}

}
