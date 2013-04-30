package mazeEscapeApp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

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
	private int accuracy;
	private int points;
	private MazeEscape maze;
	private JOptionPane optionPane = null;

	public WinnerScreen(MazeEscape m) {
		super();

		maze = m;
	}

	/**
	 * @param accuracy
	 * @param points
	 */
	public void writeOutput(double accuracy, int points) {
		this.points = points;

		// Creates message and options for dialog box
		if(!maze.isCampaign())
		{
			this.accuracy=(int)accuracy;
			optionPane = new JOptionPane(
					"Your completion accuracy is " + this.accuracy + "%!\n"
							+ "Your maze score is " + this.points + " points!\n"
							+ "Play Again?", JOptionPane.QUESTION_MESSAGE,
					JOptionPane.YES_NO_OPTION);
		}
		else
		{
			CampaignStore cs = MazeEscapeApp.getCampaignStore();
			double totalScore = cs.getScore();
			double totalAcc = cs.getAccuracy();
			
			System.out.println("Total Score: "+totalScore);
			System.out.println("Total Acc: "+totalAcc);
			
			DecimalFormat fmt = new DecimalFormat("#.##");
			
			optionPane = new JOptionPane(
					"Your completion accuracy is " + fmt.format(accuracy) + "%!\n"
							+ "Your maze score is " + this.points + " points!\n\n"
							+ "Overall Campaign Stats:\nAccuracy: "+fmt.format(totalAcc)
							+ "\nPoints: " + fmt.format(totalScore)
							+ "\n\nContinue Campaign?", JOptionPane.QUESTION_MESSAGE,
					JOptionPane.YES_NO_OPTION);
		}

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
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		// If yes option selected, new maze will generate
		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			MazeEscapeApp.newGame(maze);
			// If no option selected, program will close.
		} else if (value == JOptionPane.NO_OPTION) {
			maze.exit();
		}
	}

	public void forfeit() {

		// Creates message and options for dialog box
		final JOptionPane optionPane = new JOptionPane(
				"You forfeited the game!\n" + "Play Again?",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

		// Creates dialog box displaying the message
		final JDialog dialog = new JDialog(new JFrame(), "You lose!", true);
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
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		// If yes option selected, new maze will generate
		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			MazeEscapeApp.newGame(maze);
			// If no option selected, program will close.
		} else if (value == JOptionPane.NO_OPTION) {
			maze.exit();
		}
	}

}
