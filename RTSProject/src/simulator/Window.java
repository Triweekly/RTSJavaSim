package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	private JPanel algoLabelPanel;
	private JPanel algoChangePanel;
	
	//dropDown
	private JComboBox<String> algos;
	
	//JTextPane
	private JTextPane latest;
	
	//Buttons
	private JButton change;
	
	//labels
	private JLabel currentAlgo;
	
	//table
	private JTable jobTable;
	
	//scroll pane
	JScrollPane tableHolder;
	
	
	
	public Window(List<Job> jobsInOrder, int jobCount, int maxLate, boolean sync, String algorithm)  //EDF constructor
	{
		newWindow(jobsInOrder, jobCount, maxLate, sync, algorithm);
	}
	
	
	public void newWindow(List<Job> jobsInOrder, int jobCount, int maxLate, boolean sync, String algorithm) //EDF constructor
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
			temp.setBackground(new Color(173,216,230));
			if(jobsInOrder.get(i).getLateness()>0)//Job is late
			{
				temp.setBackground(new Color(255,114,118));
				temp.setForeground(Color.white);
			}
			
			visualJobs.add(temp);	//if I don't do this roundabout way, the next loops affect Every job's width.
			jobPanel.add(visualJobs.get(i));
		}
		
		algoPanel = new JPanel();
		algoPanel.setLayout(new GridLayout(2,1));
		
		
		
		
		currentAlgo = new JLabel("Earliest Due Date");	//synchronous
		currentAlgo.setFont(new Font("Verdana",Font.BOLD, 18));
		currentAlgo.setHorizontalTextPosition(SwingConstants.CENTER);
		
		algoLabelPanel = new JPanel();
		algoLabelPanel.add(currentAlgo);
		algoPanel.add(algoLabelPanel);
		
		int rows = 5;
			if(!sync)rows++;
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
		
			int varRow = 0;
		Object[][] tableData = new Object[rows][cols];
			tableData[varRow++][0] = "Ci"; 
			tableData[varRow++][0] = "Di";
			if(!sync)tableData[varRow++][0] = "Ai";
			tableData[varRow++][0] = "Si";
			tableData[varRow++][0] = "Fi";
			tableData[varRow++][0] = "Li";
				
		for(int i = 1 ; i <=  jobCount ; i++)
		{
			varRow=0;
			System.out.println("Job " + jobsInOrder.get(i-1).getjobNum());
			tableData[varRow++][i] = jobsInOrder.get(i-1).getCompletion();	//completion
			tableData[varRow++][i] = jobsInOrder.get(i-1).getDeadline();	//deadline
			if(!sync)tableData[varRow++][i] = jobsInOrder.get(i-1).getArrival();	//arrival
			tableData[varRow++][i] = jobsInOrder.get(i-1).getStart();		//start time
			tableData[varRow++][i] = jobsInOrder.get(i-1).getFinish();		//finish time
			tableData[varRow++][i] = jobsInOrder.get(i-1).getLateness();	//lateness
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
		algos.addItem("EDF");
		algos.addItem("EDD");
		
		algos.setSelectedItem(algorithm);
		
		String compString = algorithm.toString();
		if(compString=="EDF")currentAlgo.setText("Earliest Deadline First");
		if(compString=="EDD")currentAlgo.setText("Earliest Due Date");
		
		
		change = new JButton("Use this Algorithm");
		change.addActionListener((ActionListener) new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				int localMaxLate;
				if(algos.getSelectedItem().toString()=="EDF") 
				{
					Collections.sort(jobsInOrder, Comparator.comparing(Job::getArrival).thenComparing(Job::getDeadline));
					
					Simulator.calcJobStats(jobCount, jobsInOrder);
					
					Collections.sort(jobsInOrder, Comparator.comparing(Job::getMaxLate));
					localMaxLate = jobsInOrder.get(jobCount-1).getMaxLate();
					
					Collections.sort(jobsInOrder, Comparator.comparing(Job::getArrival).thenComparing(Job::getDeadline));
					
					new Window(jobsInOrder, jobCount, localMaxLate, false, algos.getSelectedItem().toString());
					frame.dispose();
				}
				
				if(algos.getSelectedItem().toString()=="EDD") 
				{
					Collections.sort(jobsInOrder, Comparator.comparing(Job::getDeadline));
					
					Simulator.calcJobStats(jobCount, jobsInOrder);
					
					Collections.sort(jobsInOrder, Comparator.comparing(Job::getMaxLate));
					localMaxLate = jobsInOrder.get(jobCount-1).getMaxLate();
					
					Collections.sort(jobsInOrder, Comparator.comparing(Job::getDeadline));
					
					new Window(jobsInOrder, jobCount, localMaxLate, true, algos.getSelectedItem().toString());
					frame.dispose();
				}
				
				
//				new Window(jobsInOrder, jobCount, maxLate, sync, algos.getSelectedItem().toString());
//				frame.dispose();
			}
			
		});
		
		algoChangePanel = new JPanel();
		
		algoChangePanel.add(algos, BorderLayout.WEST);
		algoChangePanel.add(change, BorderLayout.EAST);
		
		algoPanel.add(algoChangePanel);
		
		
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1100, 400);
		

		frame.add(algoPanel, BorderLayout.NORTH);
	    frame.add(jobPanel, BorderLayout.CENTER);
	    frame.add(statPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
		
	}

	private class changeAlgoListener implements ActionListener{
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent ae) {
	    	System.out.println(algos.getSelectedItem().toString());
	    
	    }
	}










}

