package View;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controller.guiWindowController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Label;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Frame;

/**
 * StartWindow class
 * the window which appears as soon as the program runs
 * @author Manny Rodriguez
 *
 */
public class StartWindow {
	
	//The Height of the full screen window
	private static int H;
	//The width of the full screen window
	private static int W;
	//The Frame of StartWindow for returning to make visible
	private JFrame frmWelcomeToSfu;
	private guiWindowController backGUI;

	/**
	 * Create the application.
	 */
	public StartWindow(int windowWidth, int windowHeight, guiWindowController gui) 
	{
		backGUI = gui;
		W = windowWidth;
		H = windowHeight;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		// the frame work for the window frame
		frmWelcomeToSfu = new JFrame();
		frmWelcomeToSfu.setTitle("Welcome to SFU Course and Program Management ");
		frmWelcomeToSfu.setSize(W, H);
		frmWelcomeToSfu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcomeToSfu.getContentPane().setLayout(null);
		
		// the combo box which is a drop down menu which displays the options of which "attribute" a user may wish to view
		String[] dropDownOptions = {"Faculty", "Department", "Program", "Courses"};
		JComboBox<String> comboBox = new JComboBox<String>(dropDownOptions);             // comment this line to work with window builder
		//JComboBox comboBox = new JComboBox();
		comboBox.setBounds(170, 183, 249, 22);
		frmWelcomeToSfu.getContentPane().add(comboBox);
		
		// components of the exit button of the window
		Button exitButton = new Button("Exit");
		exitButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				System.exit(0);
			}
		});
		exitButton.setBounds(252, 394, 79, 24);
		frmWelcomeToSfu.getContentPane().add(exitButton);
		
		// components of the go button of the window, which would take you to the next window which belongs to the drop down option the user chose
		// the combo box for the option to view the system as a student or an admin
				String[] roleOptions = {"Administrator", "Student"};
				JComboBox<String> roleComboBox = new JComboBox<String>(roleOptions);      		   // comment this line to work with window builder
				//JComboBox roleComboBox = new JComboBox();
				roleComboBox.setBounds(170, 262, 249, 32);
				frmWelcomeToSfu.getContentPane().add(roleComboBox);
				
				JLabel viewAs = new JLabel("View As");
				viewAs.setFont(new Font("Dubai Light", Font.PLAIN, 20));
				viewAs.setBounds(100, 265, 64, 24);
				frmWelcomeToSfu.getContentPane().add(viewAs);
				
				// components of the go button of the window, which would take you to the next window which belongs to the drop down option the user chose
				JButton goButton = new JButton("Go");
				goButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						// getting the selections that the user picks from each combo box
						int componentSelection = comboBox.getSelectedIndex();
						int roleSelection = roleComboBox.getSelectedIndex();
						// checking if the user chose administrator
						if(roleSelection == 0)
						{
							frmWelcomeToSfu.dispose();
							backGUI.toggleForwardChange();
							backGUI.windowChange();
						
							//frmWelcomeToSfu.setVisible(false);
							//ListWindow facultyWindow = new ListWindow();
							//facultyWindow.main(componentSelection, W, H);
							//frmWelcomeToSfu.setVisible(true);
						}
						
						// if user chose student
						else
						{
							frmWelcomeToSfu.dispose();
							backGUI.toggleForwardChange();
							backGUI.windowChange();
							/*
							// small for loop that checks which system attribute a student may wish to view
							for(int i = 0; i < dropDownOptions.length; i++)
							{
								if(i == componentSelection)
								{
									
									//frmWelcomeToSfu.setVisible(false);
									//ViewWindow windowToView = new ViewWindow(dropDownOptions[i], frmWelcomeToSfu);
									
								}
							}
							*/
						}
					}
				});
		goButton.setBounds(467, 183, 55, 24);
		frmWelcomeToSfu.getContentPane().add(goButton);
		
		// components of the view label of the window
		Label viewLabel = new Label("View");
		viewLabel.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		viewLabel.setBounds(109, 175, 55, 32);
		frmWelcomeToSfu.getContentPane().add(viewLabel);
		
		
		
	}
	
	public JFrame getFrame() {
		return frmWelcomeToSfu;
	}
}
