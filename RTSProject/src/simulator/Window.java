package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;



public class Window {

	
	
	
	
	//frame
	private JFrame frame;
	
	//panels
	private JPanel algoPanel;
	private JPanel jobPanel;
	private JPanel statPanel;
	
	//dropDown
	private JComboBox<String> algos;
	
	
	
	//Buttons
	private JButton ph = new JButton("PH");
	
	//labels
	private JLabel currentAlgo;
	
	//table
	private JTable jobTable;
	
	public Window(List<Job> jobsInOrder, int jobCount, int maxLate) //EDF constructor
	{
		
		List<JTextPane> visualJobs = new ArrayList<JTextPane>();
		
		jobPanel = new JPanel();
		jobPanel.setBorder(BorderFactory.createTitledBorder("Schedule"));
		
		int fullWidth =  jobsInOrder.get(jobCount-1).getFinish();
		
		for(int i = 0 ; i<jobCount ; i++)	//Creates and adds job text panes to midPanel
		{
			int jobWidth = jobsInOrder.get(i).getCompletion();
			JTextPane temp = new JTextPane();
			temp.setText("J"+jobsInOrder.get(i).getjobNum());
			temp.setToolTipText("Job "+jobsInOrder.get(i).getjobNum() + "- Completion time"  + ":" + jobWidth);
			temp.setBackground(Color.LIGHT_GRAY);
			temp.setPreferredSize(new Dimension(((1000/fullWidth)*jobWidth), 50));	//Calculates width of this job (1000/fullWidth)=base unit. jobWidth = 'completion' multiplier
			temp.setEditable(false);
			if(jobsInOrder.get(i).getLateness()>0)//Job is late
			{
				temp.setBackground(new Color(255,114,118));
				temp.setForeground(Color.white);
			}
			
			visualJobs.add(temp);	//if I don't do this roundabout way, the next loops affect Every job's width.
			jobPanel.add(visualJobs.get(i));
		}
		
		algoPanel = new JPanel();
//		algoPanel.setLayout(new GridLayout(3,2));
		
		currentAlgo = new JLabel("Earliest Deadline First");
		currentAlgo.setFont(new Font("Verdana",Font.BOLD, 18));
		currentAlgo.setHorizontalTextPosition(SwingConstants.CENTER);
		
		algoPanel.add(currentAlgo);
//		algoPanel.add(new JLabel("PH"));
//		algoPanel.add(new JLabel("PH"));
//		algoPanel.add(new JLabel("PH"));
//		algoPanel.add(new JLabel("PH"));
//		algoPanel.add(new JLabel("PH"));
		
		Object[] headings = new Object[jobCount+1];
		
		headings[0] = "";
		for(int i = 1 ; i<=jobCount ; i++)
		{
			headings[i] = ("J" + jobsInOrder.get(i-1).getjobNum());
			
		}
		
		Object[][] tableData = new Object[6][jobCount+1];
			tableData[0][0] = "Ci"; 
			tableData[1][0] = "Di";
			tableData[2][0] = "Ai";
			tableData[3][0] = "Si";
			tableData[4][0] = "Fi";
			tableData[5][0] = "Li";
				
		for(int i = 1 ; i <=  jobCount ; i++)
		{
			System.out.println("Job " + jobsInOrder.get(i-1).getjobNum());
			tableData[0][i] = jobsInOrder.get(i-1).getCompletion();	//completion
			tableData[1][i] = jobsInOrder.get(i-1).getDeadline();	//deadline
			tableData[2][i] = jobsInOrder.get(i-1).getArrival();	//arrival
			tableData[3][i] = jobsInOrder.get(i-1).getStart();	//start time
			tableData[4][i] = jobsInOrder.get(i-1).getFinish();	//finish time
			tableData[5][i] = jobsInOrder.get(i-1).getLateness();	//lateness
		}
		
		
		System.out.println("heading length" + headings.length);
		System.out.println("tableData length" + tableData.length);
		
		
		jobTable = new JTable(tableData, headings);
		jobTable.setEnabled(false);
		jobTable.setFont(new Font("Verdana",Font.PLAIN, 14));
		
		statPanel = new JPanel();
		statPanel.add(jobTable);
		
		
		algos = new JComboBox<String>();
		
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1100, 800);
		

//		frame.add(algos, BorderLayout.NORTH);	//FIXME uncomment when dropdown works/is useable
		frame.add(algoPanel, BorderLayout.NORTH);
	    frame.add(jobPanel, BorderLayout.CENTER);
	    frame.add(statPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
