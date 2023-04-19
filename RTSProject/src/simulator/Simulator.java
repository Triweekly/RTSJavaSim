package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Simulator {

	static Random randomNum = new Random();
	
	static List<Job> jobList = new ArrayList<Job>();
	
	static int maxJobs = 15;	//maximum amount of jobs
	
	public static void main(String[] args){

//		Job job1 = new Job(2, 3);
//		System.out.println(job1.c);
//		System.out.println(job1.d);
		int jobCount = randomNum.nextInt(5, maxJobs);
		makeJobs(jobCount);
		List<Job> jobOrder = sortList(jobList, jobCount);
		
		

	
		int currentTime = 0;
		int currentJob = 0;
		while(true) 
		{
			if(currentJob==jobCount)break;
			jobOrder.get(currentJob).setStart(currentTime);
			currentTime = currentTime + jobOrder.get(currentJob).getCompletion();
			jobOrder.get(currentJob).setFinish(currentTime);
			currentJob++;
			
		}
		
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
		
		for(int i = 0 ; i < jobCount ; i++)
		{
			System.out.println("Job " + (jobOrder.get(i).jobNum+1) + ':');
			System.out.println("C= " + jobOrder.get(i).c);
			System.out.println("A= " + jobOrder.get(i).a);
			System.out.println("D= " + jobOrder.get(i).d);
			System.out.println("Starts at " + jobOrder.get(i).s);
			System.out.println("Finishes by " + jobOrder.get(i).f);
			System.out.println("Lateness of " + jobOrder.get(i).late + "\n");
			
		}
		System.out.println("Max lateness is " + maxLate);
		
		setUpWindow(jobOrder, jobCount, maxLate);	//1 implies EDF
		
	}
	
	
	
	private static void setUpWindow(List<Job> orderedJobs, int jobAmount, int maxLateness)
	{
		new Window(orderedJobs, jobAmount, maxLateness);
		
	}



	public static void makeJobs(int jobNum)
	{
		
		for(int i = 0 ; i<jobNum ; i++)
		{
			
			int completion = randomNum.nextInt(1, 8);//count=1
			int deadline = randomNum.nextInt(completion, (jobNum*8)+2);	//FIXME get a more realistic deadline?
			System.out.println("C="+completion+" D="+deadline+"\n");
			int arrival = 1;
			
			try
			{
				arrival = randomNum.nextInt(1, (deadline-completion)+1);	//guarantees that there is 
			}
			catch(IllegalArgumentException iae)
			{
				System.out.println(iae.getMessage());
			}
			jobList.add(new Job(i, completion, arrival, deadline));
		}
	}
		
		
	public static List<Job> sortList(List<Job> unsortedList, int length)
	{
		Collections.sort(unsortedList, Comparator.comparing(Job::getDeadline));
		
		return unsortedList;//FIXME
	}
	
	
	
	
	
	
	
}
