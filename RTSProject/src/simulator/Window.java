package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;



public class Window {

	
	
	
	
	//frame
	private JFrame frame;
	
	//Jobs
	private JTextPane test1;
	private JTextPane test2;
	private JTextPane test3;
	
	private JPanel midPanel;
	
	public Window(List<Job> jobsInOrder, int jobCount, int maxLate) //EDF constructor
	{
		
		List<JTextPane> visualJobs = new ArrayList<JTextPane>();
		
		midPanel = new JPanel();
		midPanel.setBorder(BorderFactory.createTitledBorder("Schedule"));
		
		int fullWidth =  jobsInOrder.get(jobCount-1).getFinish();
		
		for(int i = 0 ; i<jobCount ; i++)	//Creates and adds job text panes
		{
			int jobWidth = jobsInOrder.get(i).getCompletion();
			JTextPane temp = new JTextPane();
			temp.setText("J"+jobsInOrder.get(i).getjobNum());
			temp.setToolTipText("Job "+jobsInOrder.get(i).getjobNum() + "- Completion time" +jobsInOrder.get(i).getjobNum() + ":" + jobWidth);
			temp.setBackground(Color.LIGHT_GRAY);
			temp.setPreferredSize(new Dimension(((1000/fullWidth)*jobWidth), 20));
			temp.setEditable(false);
			
			visualJobs.add(temp);
			midPanel.add(visualJobs.get(i));
			
		}
		
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1100, 800);
		

		frame.add(midPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
