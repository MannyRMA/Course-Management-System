package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JTree;

import Controller.guiWindowController;
import Model.course;
import Model.department;
import Model.faculty;
import Model.program;

import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class SearchWindow {

	private JFrame frmSearch;
	private JTextField textField;
	
	//The Height of the full screen window
	private static int H;
	//The width of the full screen window
	private static int W;
	private guiWindowController backGUI;
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SearchWindow window = new SearchWindow();
//					window.frmSearch.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public SearchWindow(int width, int height, guiWindowController gui) {
		backGUI = gui;
		W = width;
		H = height;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearch = new JFrame();
		frmSearch.setTitle("Search");
		frmSearch.setBounds(0, 0, W, H);
		frmSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearch.getContentPane().setLayout(null);
		
		// list of all the attributes of the system
		String[] systemAttributes = {"Faculty", "Department", "Program", "Course"};
		
		// components of the exit button of the window
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Dialog", Font.PLAIN, H/40));
		exitButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				System.exit(0);
			}
		});
		exitButton.setBounds(W/50, H - (H/6), W/10, H/15);
		frmSearch.getContentPane().add(exitButton);
		
		//Components of the back button on the window, when clicked will terminate current window
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmSearch.dispose();
				backGUI.toggleBackChange();
				backGUI.windowChange();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		btnBack.setBounds(W/6, H - (H/6), W/10, H/15);
		frmSearch.getContentPane().add(btnBack);
		
		// simple label that lets the user know that the text field is for searching a name on the system
		JLabel lblNewLabel = new JLabel("Look for:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		lblNewLabel.setBounds(W/15, H/10, W/10, H/20);
		frmSearch.getContentPane().add(lblNewLabel);
		
		// the text field of the window which gets the input from the user
		textField = new JTextField();
		textField.setBounds(W/6, H/10, W/3, H/25);
		textField.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		frmSearch.getContentPane().add(textField);
		textField.setColumns(10);
		
		// the dropdown menu of the window
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(systemAttributes));
		comboBox.setBounds(W/6, H/6, W/3, H/20);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		frmSearch.getContentPane().add(comboBox);
		
		// a label that would display the name of what the user is searching for, in a stuctural diagram
		// only if the name was found on the database
		JLabel diagram = new JLabel();
		diagram.setFont(new Font("Tahoma", Font.PLAIN, H/48));
		diagram.setBounds(W/10, H/4, W/2, H/8);
		frmSearch.getContentPane().add(diagram);
		diagram.setVisible(false);
		
		// simple array that would hold data from an attribute in a "table"
		String[] tableData = new String[4];
		
		// components of the search button
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// the selection from the drop down 
				int selection = comboBox.getSelectedIndex();
				// input from the user
				String nameToBeSearched = textField.getText().replace(" ", "_");
				// initializing the index of where the input of the user, may lie on an array
				int index;
				//Running the search algorithm which returns the index of the item if found
				//If not found a NoSuchElementException is thrown and message is printed
				try {
					// perform the linear search, if the name was found then we move on to the if statements
					index = linearSearch(nameToBeSearched, selection);
					// faculty selection, we take the faculty name and leave the other data blank
					if(selection == 0)
					{
						tableData[0] = faculty.getFacultySet().get(index).getName();
						tableData[1] = "N/A";
						tableData[2] = "N/A";
						tableData[3] = "N/A";
					}
					// department selection, we take the department name and the name of its faculty and leave other data blank
					else if(selection == 1)
					{
						tableData[0] = department.allDepartments.get(index).itsfaculty.getName();
						tableData[1] = department.allDepartments.get(index).getName();
						tableData[2] = "N/A";
						tableData[3] = "N/A";
					}
					// program selection, we take the program name, its department name and faculty name and leave course data blank
					else if(selection == 2)
					{
						tableData[0] = program.allPrograms.get(index).itsdepartment.itsfaculty.getName();
						tableData[1] = program.allPrograms.get(index).itsdepartment.getName();
						tableData[2] = program.allPrograms.get(index).getName();
						tableData[3] = "N/A";
					}
					// course selection, we take the name of the course and names of the other attributes associated with the course
					else
					{
						tableData[0] = course.allCourses.get(index).itsprogram.itsdepartment.itsfaculty.getName();
						tableData[1] = course.allCourses.get(index).itsprogram.itsdepartment.getName();
						tableData[2] = course.allCourses.get(index).itsprogram.getName();
						tableData[3] = course.allCourses.get(index).getName();
					}
					for(int i = 0; i < 4; i++)
						System.out.println(tableData[i]);
				}
				catch(NoSuchElementException e0)
				{
					System.out.println("The searched faculty was not found in database");
				}
				// set text for the diagram label and make it visible
				diagram.setText(tableData[0] + " --> " + tableData[1] + " --> " + tableData[2] + " --> " + tableData[3]);
				diagram.setVisible(true);
				
				// we have the index of the name of what the user is looking for 
				// we just need to go to the list window of what the user searched for
				// need to handle the logic of switching between windows
			}
		});
		
		
		btnNewButton.setBounds((7*W)/12, H/10, W/10, H/9);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		frmSearch.getContentPane().add(btnNewButton);
		
		
		JButton btnExplore = new JButton("Explore");
		btnExplore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// go into the faculty list window, just like our program did during the demo with the TA
			}
		});
		btnExplore.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		btnExplore.setBounds((5*W)/6, H/2, W/8, H/15);
		frmSearch.getContentPane().add(btnExplore);
	}
	
	public JFrame getFrame() {
		return frmSearch;
	}
	
	
	/**
	 * method/algorithm that performs a linear search on the list passed as parameter
	 * it searches by the name of the attribute, that is why a name is also passed as a parameter
	 * @param name - the name the user is searching for
	 * @param selection - the option selected by the user from the dropdown menu
	 * needed to check which casting we may need to do
	 */
	public static int linearSearch(String name, int selection) throws NoSuchElementException
	{
		int index = 0;
		int length = 0;
		// switch case to handle the selection the user chose to search the name under
		// needed to determine the type of casting the while is to be performed in
		// the while loop basically just searches through the array while the name does not equal the element of the array at index
		// if they are the same then we have found the name and at what index it is located at in the array
		switch(selection)
		{
		// perform the while loop on the list of faculties
		case 0:
			length = faculty.getFacultySet().size();
			String nameToCompare = faculty.getFacultySet().get(index).getName();
			while((index < length) && !(faculty.getFacultySet().get(index).getName().toLowerCase().contains(name.toLowerCase())))
			{
				index++;
			}
			break;
		// perform the while loop on the list of departments
		case 1:
			length = department.allDepartments.size();
			while((index < length) && !(department.allDepartments.get(index)).getName().toLowerCase().contains(name.toLowerCase()))
			{
				index++;
			}
			break;
		// perform the while on the list of programs
		case 2:
			length = program.allPrograms.size();
			while((index < length) && !(program.allPrograms.get(index)).getName().toLowerCase().contains(name.toLowerCase()))
			{
				index++;
			}
			break;
		// perform the while loop on the course list
		case 3:
			length = course.allCourses.size();
			while((index < length) && !(course.allCourses.get(index)).getName().toLowerCase().contains(name.toLowerCase()))
			{
				index++;
			}
			break;
		// default case
		default:
			break;
		}
		
		// checking to see if the index would be valid, and the name was found
		if(index < length)
		{
			System.out.println(name + " was found successfully");
			return index;
		}
		// the name was not found, and throw the exception and pop up a warning message
		else
		{
			WarningMessage notFoundPopUp = new WarningMessage(name + " cannot be found on the system");
			throw new NoSuchElementException();
		}
	}
}
