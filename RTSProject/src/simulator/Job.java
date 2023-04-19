package simulator;

public class Job{
	int c;	//completion time == duration
	int a;	//arrival time
	int d;	//deadline
	int s;	//start time
	int f;	//finish time
	
	public Job(int computationTime, int arrivalTime, int deadline) 
	{
		c = computationTime;
		a = arrivalTime;
		d = deadline;//test
	}
} 
