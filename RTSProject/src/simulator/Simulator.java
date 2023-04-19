package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

	static Random randomNum = new Random();
	
	static List<Job> jobList = new ArrayList<Job>();
	
	
	public static void main(String[] args){

//		Job job1 = new Job(2, 3);
//		System.out.println(job1.c);
//		System.out.println(job1.d);
		int jobCount = randomNum.nextInt(1, 10);
		makeJobs(jobCount);
		for(int i = 0 ; i < jobCount ; i++)
		{
			System.out.println("Job " + (i+1) + ':');
			System.out.println("C= " + jobList.get(i).c);
			System.out.println("D= " + jobList.get(i).d + "\n");
		}
	
	}
	
	
	//yo
	
	public static void makeJobs(int jobNum)
	{
		
		for(int i = 0 ; i<jobNum ; i++)
		{
			int completion = randomNum.nextInt(1, 12);
			int deadline = randomNum.nextInt(completion+1, 15);
			
			jobList.add(new Job(completion, deadline));
		}
	}
		
}
