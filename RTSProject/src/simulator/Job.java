package simulator;

public class Job{
	int jobNum;
	int c;	//completion time == duration
	int a;	//arrival time
	int d;	//deadline
	int s;	//start time
	int f;	//finish time
	int late; //lateness
	int maxLate;
	
	public Job(int jobNumber, int computationTime, int arrivalTime, int deadline) 
	{
		setJobNum(jobNumber);
		setCompletion(computationTime);
		setArrival(arrivalTime);
		setDeadline(deadline);
	}
	
	
	
	public Job(int jobNumber, int computationTime, int deadline) 
	{
		setJobNum(jobNumber+1);
		setCompletion(computationTime);
		setDeadline(deadline);
	}
	
	public int getjobNum() {
		return jobNum;
	}

	public void setJobNum(int j) {
		this.jobNum = j+1;
	}
	
	public int getDeadline()
	{
		return d;
	}

	public int getCompletion() {
		return c;
	}

	public int getArrival() {
		return a;
	}

	public int getStart() {
		return s;
	}

	public int getFinish() {
		return f;
	}

	public void setCompletion(int c) {
		this.c = c;
	}

	public void setArrival(int a) {
		this.a = a;
	}

	public void setDeadline(int d) {
		this.d = d;
	}

	public void setStart(int s) {
		this.s = s;
	}

	public void setFinish(int f) {
		this.f = f;
	}
	
	public void setLateness(int L)
	{
		this.late = L;
	}
	
	public int getLateness()
	{
		return late;
	}
	public int getMaxLate() {
		return maxLate;
	}

	public void setMaxLate(int M) {
		this.maxLate = M;
	}
	
	
} 
