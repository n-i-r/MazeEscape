package baseMazeCode;

public class MazeInfo {
	private int n;
	private int rStart;
	private int cStart;
	private int rFinish;
	private int cFinish;
	
	public MazeInfo(int nVal, int rS, int cS, int rF, int cF)
	{
		n=nVal;
		rStart=rS;
		cStart=cS;
		rFinish=rF;
		cFinish=cF;
	}
	
	public int getN()
	{
		return n;
	}
	
	public int getRStart()
	{
		return rStart;
	}
	
	public int getCStart()
	{
		return cStart;
	}
	
	public int getRFinish()
	{
		return rFinish;
	}
	
	public int getCFinish()
	{
		return cFinish;
	}
}
