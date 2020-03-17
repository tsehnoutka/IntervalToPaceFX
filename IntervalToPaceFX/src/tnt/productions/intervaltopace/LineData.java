package tnt.productions.intervaltopace;

public class LineData {
	private float totalDist = 0; // Total Distance for this Interval
	private float excessDist = 0;
	private int excessTime=0;
	private  int totalTime = 0; // Total Time for this Interval
	private int x=1;
	

	public int getX() {
		return x;
	}

	public void incrementX() {
		this.x++;
	}

	public LineData()
	{
		totalDist = 0; // Total Distance for this Interval
		excessDist = 0;
		excessTime=0;
		totalTime = 0; // Total Time for this Interval
	}

	public float getTotalDist() {
		return totalDist;
	}

	public void setTotalDist(float totalDist) {
		this.totalDist = totalDist;
	}
	public float addTotalDist(float totalDist) {
		this.totalDist += totalDist;
		return this.totalDist;
	}

	public float getExcessDist() {
		return excessDist;
	}

	public void setExcessDist(float excessDist) {
		this.excessDist = excessDist;
	}

	public int getExcessTime() {
		return excessTime;
	}

	public void setExcessTime(int excessTime) {
		this.excessTime = excessTime;
	}

	public  int getTotalTime() {
		return totalTime;
	}

	public  void setTotalTime(int inTotalTime) {
		this.totalTime = inTotalTime;
	}
	public  int addTotalTime(int inTotalTime) {
		this.totalTime += inTotalTime;
		return this.totalTime;
	}

	
	
}
