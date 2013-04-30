package mazeEscapeApp;

public class CampaignStore {
	private double accuracy;
	private double score;
	private int rounds;
	
	private final int LENGTH_FACTOR = 4;
	private final int SCORE_FACTOR = 4;
	private final int POINTS_FACTOR = 4;
	
	public CampaignStore()
	{
		rounds = 0;
		score = 0;
		accuracy = 0;
	}
	
	/**
	 * Increments the rounds
	 */
	public void increment()
	{
		rounds++;
	}
	
	public int getLengthMaze()
	{
		return rounds * LENGTH_FACTOR;
	}
	
	public int getTimeScore()
	{
		return rounds * SCORE_FACTOR;
	}
	
	public int getLevelPoints()
	{
		return rounds * POINTS_FACTOR;
	}
	
	public double getScore()
	{
		return score;
	}
	
	public double getAccuracy()
	{
		return accuracy;
	}
	
	public void updateScore(double newScore)
	{
		if(score == 0)
			score = newScore;
		else
		{
			score += newScore;
			System.out.println(score);
		}
	}
	
	public void updateAccuracy(double newAcc)
	{
		if(accuracy == 0)
		{
			accuracy = newAcc;
		}
		else
		{
			accuracy += newAcc;
			accuracy /= 2;
		}
	}

}
