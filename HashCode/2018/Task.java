package hashcode2018;


import java.util.ArrayList;

public class Task {
	private String name;
	private long starttime;
	private int workUnits;
	private int completedWorkUnits;
	private long printtime;
	private long printInterval;
	private ArrayList<Integer> pastValues;
	public Task(String name)
	{
		this(name,1);
	}
	public Task(String name, int workUnits)
	{
		this(name,workUnits,1000);
	}
	public Task(String name, int workUnits, long printInterval)
	{
		this.name = name;
		this.starttime = System.currentTimeMillis();
		this.workUnits = workUnits;
		this.completedWorkUnits = 0;
		this.printtime = this.starttime;
		this.printInterval = printInterval;
	}
	
	public double getCompletedPercentage() {
		return ((double)completedWorkUnits / (double) workUnits) * 100;
	}
	public long getElapsedTime()
	{
		return System.currentTimeMillis() - starttime;
	}
	public boolean end()
	{
		return end(1);
	}
	public boolean end(int workUnits)
	{
		this.completedWorkUnits += workUnits;
		return this.completedWorkUnits >= this.workUnits;
	}
	public boolean shouldPrint()
	{
		return System.currentTimeMillis() - this.printtime > this.printInterval;
	}
	public void resetPrintTime()
	{
		this.printtime = System.currentTimeMillis();
	}
	public int workUnits()
	{
		return this.workUnits;
	}
}
