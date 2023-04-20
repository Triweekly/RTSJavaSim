package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	
	//JTextPane
	private JTextPane latest;
	
	//Buttons
	private JButton ph = new JButton("PH");
	
	//labels
	private JLabel currentAlgo;
	
	//table
	private JTable jobTable;
	
	//scroll pane
	JScrollPane tableHolder;
	
	public Window(List<Job> jobsInOrder, int jobCount, int maxLate, boolean sync) //EDF constructor
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
		
		int rows = 6;
		int cols = jobCount+1;
		
		Object[] headings = new Object[cols];
		
		headings[0] = "";
		for(int i = 1 ; i<=jobCount ; i++)
		{
			headings[i] = ("J" + jobsInOrder.get(i-1).getjobNum());
			
		}
		
		for(int i = 0 ; i<cols ; i++)
		{
			System.out.print("|"+headings[i] + "|");
		}
			System.out.print("\n");
		
		Object[][] tableData = new Object[6][cols];
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
		System.out.println(headings);
		
		System.out.println("tableData length" + (cols));
		System.out.println(tableData.toString());
		
		
		
		jobTable = new JTable(tableData, headings);
		jobTable.setEnabled(false);
		jobTable.setFont(new Font("Verdana",Font.PLAIN, 14));
		
		
		
		tableHolder = new JScrollPane();
		statPanel = new JPanel();
		statPanel.add(tableHolder, BorderLayout.CENTER);
		tableHolder.setPreferredSize(new Dimension((800/16*jobCount), 150));
		tableHolder.setBorder(null);
		tableHolder.setViewportView(jobTable);
		
		latest = new JTextPane();
		latest.setText("Maximum Lateness: " + maxLate);
		latest.setEditable(false);
		if(maxLate>0)
		{
			latest.setForeground(Color.white);
			latest.setBackground(new Color(255,114,118));
		}
		
		jobPanel.add(latest, BorderLayout.SOUTH);
		
		algos = new JComboBox<String>();
		
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1100, 400);
		

//		frame.add(algos, BorderLayout.NORTH);	//FIXME uncomment when dropdown works/is useable
		frame.add(algoPanel, BorderLayout.NORTH);
	    frame.add(jobPanel, BorderLayout.CENTER);
	    frame.add(statPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
