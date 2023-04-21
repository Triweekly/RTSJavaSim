package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Simulator {

	static Random randomNum = new Random();
	
	static List<Job> jobList = new ArrayList<Job>();
	static List<Job> jobOrder;
	
	private int maxlate;
	
	static int maxJobs = 16;	//maximum amount of jobs
	
	public static void main(String[] args){

//		Job job1 = new Job(2, 3);
//		System.out.println(job1.c);
//		System.out.println(job1.d);
		int jobCount = randomNum.nextInt(5, maxJobs);
		makeJobs(jobCount);
		jobOrder = sortListEDD(jobList, jobCount, "EDD");
		
		

		jobOrder = calcJobStats(jobCount, jobOrder, "EDD");
//		int currentTime = 0;
//		int currentJob = 0;
//		while(true) 
//		{
//			if(currentJob==jobCount)break;
//			jobOrder.get(currentJob).setStart(currentTime);
//			currentTime = currentTime + jobOrder.get(currentJob).getCompletion();
//			jobOrder.get(currentJob).setFinish(currentTime);
//			currentJob++;
//			
//		}
//		
		int first = 1;
		int maxLate  = 0;
		for(int i = 0 ; i<jobCount ; i++)
		{
			int thisLate = jobOrder.get(i).getFinish()-jobOrder.get(i).getDeadline();
			jobOrder.get(i).setLateness(thisLate);
			
			if(first == 1) 
				{
				maxLate = thisLate;
				first = 0;
				}
			else if (thisLate>maxLate)
				{
				maxLate = thisLate;
				}
			
		}
		
		
//		for(int i = 0 ; i < jobCount ; i++)
//		{
//			System.out.println("\n\nJob " + (jobOrder.get(i).jobNum+1) + ':');
//			System.out.println("C= " + jobOrder.get(i).c);
//			System.out.println("A= " + jobOrder.get(i).a);
//			System.out.println("D= " + jobOrder.get(i).d);
//			System.out.println("Starts at " + jobOrder.get(i).s);
//			System.out.println("Finishes by " + jobOrder.get(i).f);
//			System.out.println("Lateness of " + jobOrder.get(i).late + "\n");
//			
//		}
//		System.out.println("Max lateness is " + maxLate);
//		
		
		
		
		
		
		setUpWindow(jobOrder, jobCount, maxLate, true);	//1 implies EDF
		
	}
	
	
	
	private static void setUpWindow(List<Job> orderedJobs, int jobAmount, int maxLateness, boolean sync)
	{
		new Window(orderedJobs, jobAmount, maxLateness, sync, "EDD");
	}



	public static void makeJobs(int jobNum)
	{
		
		for(int i = 0 ; i<jobNum ; i++)
		{
			
			int completion = randomNum.nextInt(1, 8);//count=1
			int deadline = randomNum.nextInt(completion, (jobNum*8)+2);	//FIXME get a more realistic deadline?
//			System.out.print("\nC="+completion+" D="+deadline+" #" + (i+1));
			int arrival = 0;
			
			try
			{
				arrival = randomNum.nextInt(1, (deadline-completion)+1);	//guarantees that there is 
			}
			catch(IllegalArgumentException iae)
			{
			}
			jobList.add(new Job(i, completion, arrival, deadline));
		}
	}
		
		
	public static List<Job> sortListEDD(List<Job> unsortedList, int length, String algo)
	{
		
			if(algo=="EDD")Collections.sort(unsortedList, Comparator.comparing(Job::getDeadline));
			
			else
			{
				int currentTime = 0;
				int smallLength = length;
				int bigLength = length;
				List<Job> forEDFOrder = new ArrayList<Job>();
				List<Job> forEDFReturn = new ArrayList<Job>();
				for(int i = 0 ; i<length ; i++) 
					{
						forEDFOrder.add(unsortedList.get(i));
						
					}
				
				for(int j = 0 ; j<bigLength ; j++)
				{
					Collections.sort(forEDFOrder, Comparator.comparing(Job::getArrived).thenComparing(Job::getDeadline).thenComparing(Job::getArrival));
					if(forEDFOrder.get(0).getArrival()>currentTime)
					{
						currentTime = forEDFOrder.get(0).getArrival();
//						System.out.println("Apparently " +forEDFOrder.get(0).getArrival()+">" + currentTime + "(J" + forEDFOrder.get(0).getjobNum()+")");
						Collections.sort(forEDFOrder, Comparator.comparing(Job::getArrival).thenComparing(Job::getDeadline));
					}
					System.out.println("currentTime = " + currentTime+"+" +forEDFOrder.get(0).getCompletion() + "+1");
					currentTime = currentTime + forEDFOrder.get(0).getCompletion()+1;
					System.out.println("Current time is " + currentTime);
					for(int i = 0 ; i<smallLength ; i++) 
					{
						forEDFOrder.get(i).setArrived(currentTime);
						
					}
					forEDFReturn.add(forEDFOrder.get(0));
					
					forEDFOrder.remove(0);
					if(forEDFOrder.size()==0)break;
					smallLength--;
					
				}	
				return forEDFReturn;
			}
		return unsortedList;//FIXME
	}
	
	
public static List<Job> calcJobStats(int jobCount, List<Job> listForStats, String algo)
{
	int currentTime = 0;
	int currentJob = 0;
	while(true) 
	{
		if(currentJob==jobCount)break;
		if(currentTime<listForStats.get(currentJob).getArrival()&&algo=="EDF")
		{
			currentTime += listForStats.get(currentJob).getArrival()-currentTime;
		}
		listForStats.get(currentJob).setStart(currentTime);
		
		currentTime = currentTime + listForStats.get(currentJob).getCompletion();
		listForStats.get(currentJob).setFinish(currentTime);
		currentJob++;
		
	}
	
	int first = 1;
	int maxLate  = 0;
	for(int i = 0 ; i<jobCount ; i++)
	{
		int thisLate = listForStats.get(i).getFinish()-listForStats.get(i).getDeadline();
		listForStats.get(i).setLateness(thisLate);
		listForStats.get(i).setMaxLate(thisLate);
		
		if(first == 1) 
			{
			maxLate = thisLate;
			
			first = 0;
			}
		else if (thisLate>maxLate)
			{
			maxLate = thisLate;
			}
		
	}
	listForStats.get(jobCount-1).setMaxLate(maxLate);
	
	return listForStats;
}
	
	
	
	
	
	
	
}
