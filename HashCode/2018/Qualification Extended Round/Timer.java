package hashcode2018;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.HashMap;

public class Timer{
	private PrintStream ps;
	private HashMap<String,Task> timings;
	private boolean printTime;
	public Timer(boolean printTime)
	{
		this.ps = System.out;
		timings = new HashMap<String,Task>();
		this.printTime = printTime;
	}
	
	public Timer(PrintStream ps, boolean printTime)
	{
		this.ps = ps;
		timings = new HashMap<String,Task>();
		this.printTime = printTime;
	}
	public Timer(File f, boolean printTime) throws FileNotFoundException
	{
		this.ps = new PrintStream(f);
		timings = new HashMap<String,Task>();
		this.printTime = printTime;
	}
	public Timer(String s, boolean printTime) throws FileNotFoundException
	{
		this.ps = new PrintStream(s);
		timings = new HashMap<String,Task>();
		this.printTime = printTime;
	}
	
	public void start(String task , int unitsOfWork, long printInterval)
	{
		timings.put(task, new Task(task,unitsOfWork,printInterval));
		String s = String.format("[Timer] Task %s Started " , task);
		if(printTime)
		{
			s += "Current Time: " + getTimeStamp();
		}
		ps.println(s);
	}
	public void start(String task)
	{
		start(task,1,1);
	}
	public void end(String taskName, int workUnits)
	{
		Task task = timings.get(taskName);
		if(task != null && task.end(workUnits))
		{
			String s = String .format("[Timer] Task %s Completed: (100%%) Time elapsed: (%.3fs) " 
					,taskName ,task.getElapsedTime() / 1000d);
			if(printTime)
			{
				s += "Current Time: " + getTimeStamp();
			}
			ps.println(s);
			if(task.workUnits() > 1)
			{
				ps.printf("[Timer] Task %s Average time per work unit: %.3fs%n",taskName,(double)task.getElapsedTime()/task.workUnits()/1000);
			}
			timings.remove(taskName);
		} else if(task.shouldPrint())
		{
			String s = String .format("[Timer] Task %s Completed: (%.2f%%) Time elapsed: (%.3fs) " 
					,taskName,task.getCompletedPercentage() ,task.getElapsedTime() / 1000d);
			if(printTime)
			{
				s += "Current Time: " + getTimeStamp();
			}
			ps.println(s);
			task.resetPrintTime();
		}
	}
	public void end(String taskName)
	{
		end(taskName,1);
	}
	public String getTimeStamp()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
	}
}
