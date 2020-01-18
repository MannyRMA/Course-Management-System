package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controller.database;
import Controller.guiWindowController;
import Model.*;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;

/**
 * EditWindow class
 * The window which appears after the user has selected an option of database 
 * objects to edit and pressed the Edit button
 * @author Kaynen Mitchell
 *
 */
public class EditWindow {
	
	//In this windows case, the buttons and menus to be displayed, i.e. faculties has less options and buttons than courses
	private static int windowType;
	//This is a flag for if the screen only had an add button or the edit and delete buttons instead.
	private static boolean addOrEdit;
	//The Height of the full screen window
	private static int H;
	//The width of the full screen window
	private static int W;
	
	//Strings for storing a previously selected drop down menu item from the window
	private String pastSelectedFacultyBox = "";
	private String pastSelectedDepartmentBox = "";
	
	//Boolean flags for triggering drop down menu changes in the window
	private boolean triggerDepartmentBoxEvent = true;
	private boolean triggerProgramBoxEvent = true;
	
	//Storing the frame of the window to be accessed all over the function and make visible
	private JFrame frmEditView;
	private guiWindowController backGUI;
	
	//Textfield Boxes to be stored and accessed outside the scope of if conditions and methods
	private JTextField textField_4;
	private JTextArea textField_8;
	private JTextField textField;
	private JTextArea textField_1;

	/**
	 * Constructor for the window screen, which is the screen for editing data entries in the system. Calls
	 * inititalize which sets up the sindow and displays it.
	 * @param width is the screen width
	 * @param height is the screen height
	 * @param gui is the controller for windows in the GUI, used for when this window is finished with
	 * @param displayType is the current screen type that the Edit Window should be editing, which is 
	 * different for different data entries
	 * @param addEdit is a boolean flag for if the screen is an adding screen or an editing screen, which
	 * display different buttons and do different things
	 */
	public EditWindow(int width, int height, guiWindowController gui, int displayType, boolean addEdit) 
	{
		backGUI = gui;
		W = width;
		H = height;
		windowType = displayType;
		addOrEdit = addEdit;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		// the frame work for the window frame
		frmEditView = new JFrame();
		frmEditView.setTitle("Edit Selected Screen");
		frmEditView.setSize(W, H);
		frmEditView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEditView.getContentPane().setLayout(null);
		
		//These are drop down menus, which are initialized here to avoid scope issues
		JComboBox facultyBox = new JComboBox();
		JComboBox departmentBox = new JComboBox();
		JComboBox programBox = new JComboBox();
		JComboBox antiReqBox = new JComboBox();
		JComboBox preReqBox = new JComboBox();
		
		// Components of the exit button of the window
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
		frmEditView.getContentPane().add(exitButton); //First component added to window, index 0
		////////////////////////////////////////////////////////////////////
		
		//Components of the back button on the window, when clicked will terminate current window
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmEditView.dispose();
				backGUI.toggleBackChange();
				backGUI.windowChange();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		btnBack.setBounds(W/5, H - (H/6), W/10, H/15);
		frmEditView.getContentPane().add(btnBack); //Second component added to window, index 1
		////////////////////////////////////////////////////////////////////////////////////
		
		//Components of a label for the Name box for adding/editing database objects
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, H/40));
		lblName.setBounds(W/10, H/40, W/10, H/10);
		frmEditView.getContentPane().add(lblName); //Third component added to window, index 2
		
		if(addOrEdit) {
			textField = new JTextField();
		}
		else {
			textField = new JTextField(getName());
		}
		textField.setFont(new Font("Tahoma", Font.PLAIN, H/40)); //Can't see nameField outside of if block
		textField.setColumns(10);
		textField.setBounds(W/10, H/10, W/5, H/20);
		frmEditView.getContentPane().add(textField); //Fourth component added to window, index 3
		
		///////////////////////////////////////////////////////////////////////////////
		
		
		//This is for if the screen is either a department, program, or course editing screen, sets up buttons only available
		//on these screens
		if(backGUI.getListWindowType() == 1 | backGUI.getListWindowType() == 2 | backGUI.getListWindowType() == 3) {
			
			//Components of a label and drop down menu of possible faculties to be under for 
			//departments, programs, and courses for adding/editing database objects
			JLabel lblId = new JLabel("Faculty");
			lblId.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblId.setBounds(W/2, H/40, W/10, H/10);
			frmEditView.getContentPane().add(lblId); //Fifth component added to window, index 4
			
			for(int i = 0; i < faculty.getFacultySet().size(); i++) {
				facultyBox.addItem(faculty.getFacultySet().get(i).getName().replace("_", " "));
			}
			facultyBox.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			facultyBox.setBounds(W/2, H/10, W/5, H/20);
			facultyBox.setSelectedItem(faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).getName().replace("_",  " "));
			frmEditView.getContentPane().add(facultyBox); //Sixth component added to window, index 5
			pastSelectedFacultyBox = facultyBox.getSelectedItem().toString();
			
			//This Action Listener handles if the facultyBox ComboBox has a change in selections, it needs to display different 
			//departments and programs accordingly
			facultyBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(facultyBox.getSelectedItem() != pastSelectedFacultyBox) {
						triggerDepartmentBoxEvent = false;
						//If the windowType is 2, then the screen will only have a department comboBox to modify
						if(windowType == 2 || windowType == 3) {
							faculty tempFaculty = faculty.getFaculty(facultyBox.getSelectedItem().toString().replace(" ", "_"));
							departmentBox.removeAllItems();
							triggerDepartmentBoxEvent = true;
							ArrayList<String> departmentNames = new ArrayList<String>();
							for(int i = 0; i < tempFaculty.getDepartments().size(); i++) {
								departmentBox.addItem(tempFaculty.getDepartments().get(i).getName().replace("_", " "));
							}
						}
					//Make sure to set the previous selected comboBox option to be the current one, to trigger new changes
					pastSelectedFacultyBox = facultyBox.getSelectedItem().toString();
					triggerDepartmentBoxEvent = true;
					}
					
				}
				
			});

			
		}
		
		//This is for if the screen is either a program or course editing screen, sets up buttons only available
		//on these screens
		if(backGUI.getListWindowType() == 2 | backGUI.getListWindowType() == 3) {
			
			//Components of a label and drop down menu for the drop down menu of possible departments to be under for 
			//programs and courses for adding/editing database objects
			JLabel lblDepartment = new JLabel("Department");
			lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblDepartment.setBounds(W/2, (7*H)/40, W/10, H/10);
			frmEditView.getContentPane().add(lblDepartment); //Seventh component added to window, index 6
			
			faculty tempFaculty = faculty.getFacultySet().get(ListWindow.getCurrentFaculty());
			for(int i = 0; i < tempFaculty.getDepartments().size(); i++) {
				departmentBox.addItem(tempFaculty.getDepartments().get(i).getName().replace("_", " "));
			}
			departmentBox.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			departmentBox.setBounds(W/2, H/4, W/5, H/20);
			departmentBox.setSelectedItem(tempFaculty.getDepartments().get(ListWindow.getCurrentDepartment()).getName().replace("_",  " "));
			frmEditView.getContentPane().add(departmentBox); //Eighth component added to window, index 7
			pastSelectedDepartmentBox = departmentBox.getSelectedItem().toString();
			
			//This Action Listener handles if the departmentBox ComboBox has a change in selections, it needs to display different 
			//programs accordingly
			departmentBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg1) {
					if(departmentBox.getSelectedItem() != pastSelectedDepartmentBox) {
						triggerProgramBoxEvent = false;
						//If the windowType is 2, then the screen will only have a department comboBox to modify
						if(windowType == 3 && triggerDepartmentBoxEvent) {
							faculty tempFaculty = faculty.getFaculty(facultyBox.getSelectedItem().toString().replace(" ", "_"));
							department tempDepartment = faculty.getFaculty(tempFaculty).
									getDepartment(departmentBox.getSelectedItem().toString().replace(" ", "_"));
							programBox.removeAllItems();
							triggerProgramBoxEvent = true;
							for(int i = 0; i < tempDepartment.programSet.size(); i++) {
								programBox.addItem(tempDepartment.programSet.get(i).getName().replace("_", " "));
							}
							//Make sure to set the previous selected comboBox option to be the current one, to trigger new changes
							pastSelectedDepartmentBox = departmentBox.getSelectedItem().toString();
							triggerProgramBoxEvent = true;
						}
					}
					
				}
				
			});
		}
		
		//This is for if the screen is a course editing screen, sets up buttons only available on a course screen
		if(backGUI.getListWindowType() == 3) {
			
			//Components of a label and drop down menu for the drop down menu of possible programs to be under for 
			//courses for adding/editing database objects
			JLabel lblProgram = new JLabel("Program");
			lblProgram.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblProgram.setBounds(W/2, (13*H)/40, W/10, H/10);
			frmEditView.getContentPane().add(lblProgram); //Ninth component added to window, index 8
			
			department tempDepartment = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
					getDepartments().get(ListWindow.getCurrentDepartment());
			for(int i = 0; i < tempDepartment.programSet.size(); i++) {
				programBox.addItem(tempDepartment.programSet.get(i).getName().replace("_", " "));
			}
			programBox.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			programBox.setBounds(W/2, (2*H)/5, W/5, H/20);
			programBox.setSelectedItem(tempDepartment.programSet.get(ListWindow.getCurrentProgram()).getName().replace("_",  " "));
			frmEditView.getContentPane().add(programBox); //Tenth component added to window, index 9
			//////////////////////////////////////////////////////////////////////////
			
			/////Components of a label and text input bar for the ID of a course
			JLabel lblId_1 = new JLabel("ID");
			lblId_1.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblId_1.setBounds(W/10, (7*H)/40, W/10, H/10);
			frmEditView.getContentPane().add(lblId_1); //Eleventh component added to window, index 10
			
			if(addOrEdit) {
				textField_4 = new JTextField();
			}
			else {
				textField_4 = new JTextField(getID());
			}
			textField_4.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			textField_4.setColumns(10);
			textField_4.setBounds(W/10, H/4, W/5, H/20);
			frmEditView.getContentPane().add(textField_4); //Twelfth component added to window, index 11
			/////////////////////////////////////////////////////////////////////
			
			/////Components of a label and drop down menu for anti-requisites of a course
			JLabel lblAntirequisites = new JLabel("Anti-Requisites");
			lblAntirequisites.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblAntirequisites.setBounds(W/2, (19*H)/40, W/6, H/10);
			frmEditView.getContentPane().add(lblAntirequisites); //Thirteenth component added to window, index 12
			
			program tempProgram = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
					getDepartments().get(ListWindow.getCurrentDepartment()).
					programSet.get(ListWindow.getCurrentProgram());
			for(int i = 0; i < tempProgram.courseSet.size(); i++) {
				antiReqBox.addItem(faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
				getDepartments().get(ListWindow.getCurrentDepartment()).
				programSet.get(ListWindow.getCurrentProgram()).courseSet.get(i).getName().replace("_", " "));
			}
			antiReqBox.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			antiReqBox.setBounds(W/2, (11*H)/20, W/5, H/20);
			frmEditView.getContentPane().add(antiReqBox); //Fourteenth component added to window, index 13
			/////////////////////////////////////////////////////////////////////
			
			/////Components of a label and drop down menu for pre-requisites of a course
			JLabel lblPrerequisites = new JLabel("Pre-Requisites");
			lblPrerequisites.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblPrerequisites.setBounds(W/2, (5*H)/8, W/6, H/10);
			frmEditView.getContentPane().add(lblPrerequisites); //Fifteenth component added to window, index 14
			
			for(int i = 0; i < tempProgram.courseSet.size(); i++) {
				preReqBox.addItem(faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
				getDepartments().get(ListWindow.getCurrentDepartment()).
				programSet.get(ListWindow.getCurrentProgram()).courseSet.get(i).getName().replace("_", " "));
			}
			preReqBox.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			preReqBox.setBounds(W/2, (7*H)/10, W/5, H/20);
			frmEditView.getContentPane().add(preReqBox); //Sixteenth component added to window, index 15
			/////////////////////////////////////////////////////////////////////
			
			/////Components of a label and text input bar for the Course Description of a course
			JLabel lblCourseDescription = new JLabel("Course Description");
			lblCourseDescription.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblCourseDescription.setBounds(W/10, (13*H)/40, W/6, H/10);
			frmEditView.getContentPane().add(lblCourseDescription); //Seventeenth component added to window, index 16
			
			textField_1 = new JTextArea();
			textField_1.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			textField_1.setColumns(10);
			textField_1.setBounds(W/10, (8*H)/20, W/3, H/8);
			textField_1.setLineWrap(true);
			frmEditView.getContentPane().add(textField_1); //Eighteenth component added to window, index 17
			///////////////////////////////////////////////////////////////////
			
			/////Components of a label and text input bar for any additional notes of a course
			JLabel lblNotes = new JLabel("Notes");
			lblNotes.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			lblNotes.setBounds(W/10, (21*H)/40, W/6, H/10);
			frmEditView.getContentPane().add(lblNotes); //Nineteenth component added to window, index 18
			
			textField_8 = new JTextArea();
			textField_8.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			textField_8.setColumns(10);
			textField_8.setBounds(W/10, (12*H)/20, W/3, H/8);
			textField_8.setLineWrap(true);
			frmEditView.getContentPane().add(textField_8); //Twentieth component added to window, index 19
			////////////////////////////////////////////////////////////////////
			
		}
		
		//Condition for displaying which buttons for the add window, or the edit window
		if(backGUI.getAddOrEdit()) {
			
			//Components for the add button, which will add an input to the database
			JButton btnAdd = new JButton("Add");
			btnAdd.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			btnAdd.setBounds((19*W)/50, H - (H/6), W/10, H/15);
			frmEditView.getContentPane().add(btnAdd); //Twenty-first component added to window, index 20
			
			btnAdd.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					addButtonEvent(facultyBox, departmentBox, programBox, antiReqBox, preReqBox);
					frmEditView.dispose();
					backGUI.toggleBackChange();
					backGUI.windowChange();
				}
				
			});
			
		} 
		else {
			
			//Components for the edit button, which will change an objects data in the database
			JButton btnChange = new JButton("Change");
			btnChange.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			btnChange.setBounds((19*W)/50, H - (H/6), W/10, H/15);
			frmEditView.getContentPane().add(btnChange); //Twenty-second component added to window, index 21
			btnChange.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					editButtonEvent(facultyBox, departmentBox, programBox, antiReqBox, preReqBox);
					frmEditView.dispose();
					backGUI.toggleBackChange();
					backGUI.windowChange();
				}
				
			});
			
			
			//Components for the delete button, which will delete an objects data in the database
			JButton btnDelete = new JButton("Delete");
			btnDelete.setFont(new Font("Tahoma", Font.PLAIN, H/40));
			btnDelete.setBounds((14*W)/25, H - (H/6), W/10, H/15);
			frmEditView.getContentPane().add(btnDelete); //Twenty-third component added to window, index 22
			btnDelete.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					deleteButtonEvent();
					backGUI.toggleBackChange();
					backGUI.windowChange();
					frmEditView.dispose();
				}
				
			});
		}
	}
	
	 /**
	  * This method handles the event when the add button is pressed. It will add the changes made on screen to the objects
	  * in the program and the database
	  * @param facultyBox is a JComboBox/drop down menu for the faculty drop down menu on the window
	  * @param departmentBox is a JComboBox/drop down menu for the department drop down menu on the window
	  * @param programBox is a JComboBox/drop down menu for the program drop down menu on the window
	  * @param antiReqBox is a JComboBox/drop down menu for the anti requisite drop down menu on the window
	  * @param preReqBox is a JComboBox/drop down menu for the prerequisite drop down menu on the window
	  */
	private void addButtonEvent(JComboBox facultyBox, JComboBox departmentBox, JComboBox programBox, JComboBox antiReqBox, JComboBox preReqBox) {
		//Gets any text written in text boxes on GUI
		String[] textBoxes = getTextBoxes();
		
		// This section retrieves any selected item in the drop down menus, then takes the strings
		// loaded in the previous function to write the newly made faculty, department... to the
		// database.
		switch(windowType) {
		//For if the window is for editing faculties
		case 0: 
			//Checks to make sure the data is not already added to avoid errors
			if(!faculty.containsFaculty(textBoxes[0])){
				//Sets up a new faculty to be made using the data entered in the GUI
				faculty dataSendFaculty = new faculty();
				dataSendFaculty.setName(textBoxes[0].replace(" ", "_"));
				if(faculty.getFacultySet().size() < 10) {
					dataSendFaculty.setID("0" + String.valueOf(faculty.getFacultySet().size() - 1));
				}
				else {
					dataSendFaculty.setID(String.valueOf(faculty.getFacultySet().size() - 1));
				}
				//Writing the made data to the database, the new object has already entered itself into local objects 
				//in the program and GUI
				database.write("src/Model/database.txt", dataSendFaculty);
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " has been added to the system");
			}
			else {
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " already exists in the system");
			}
			break;
		//For if the window is for editing departments
		case 1: 
			//Checks to make sure the data is not already added to avoid errors
			if(!department.containsDepartment(textBoxes[0])){
				//Sets up a new department to be made using the data entered in the GUI
				department dataSendDepartment = new department();
				faculty departmentSelectedFaculty = faculty.getFaculty(facultyBox.getSelectedItem().toString().replace(" ", "_"));
				dataSendDepartment.setName(textBoxes[0].replace(" ", "_"));
				if(departmentSelectedFaculty.getDepartments().size() < 10) {
					dataSendDepartment.setID("0" + String.valueOf(departmentSelectedFaculty.getDepartments().size()));
				}
				else {
					dataSendDepartment.setID(String.valueOf(String.valueOf(departmentSelectedFaculty.getDepartments().size())));
				}
				dataSendDepartment.setFaculty(departmentSelectedFaculty);
				//Writing the made data to the database, the new object has already entered itself into local objects 
				//in the program and GUI
				database.write("src/Model/database.txt", dataSendDepartment);
			}
			else {
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " already exists in the system");
			}
			break;
		//For if the window is for editing programs
		case 2: 
			//Checks to make sure the data is not already added to avoid errors
			if(!program.containsProgram(textBoxes[0])){
				//Sets up a new program to be made using the data entered in the GUI
				program dataSendProgram = new program();
				faculty programSelectedFaculty = faculty.getFaculty(facultyBox.getSelectedItem().toString().replace(" ", "_"));
				department programSelectedDepartment = programSelectedFaculty.getDepartment(departmentBox.getSelectedItem().toString().replace(" ", "_"));
				dataSendProgram.setName(textBoxes[0].replace(" ", "_"));
				if(programSelectedDepartment.programSet.size() < 10) {
					dataSendProgram.setID("0" + String.valueOf(programSelectedDepartment.programSet.size()));
				}
				else {
					dataSendProgram.setID(String.valueOf(programSelectedDepartment.programSet.size()));
				}
				dataSendProgram.setDepartment(programSelectedDepartment);
				//Writing the made data to the database, the new object has already entered itself into local objects 
				//in the program and GUI
				database.write("src/Model/database.txt", dataSendProgram);
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " has been added to the system");
			}
			else {
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " already exists in the system");
			}
				
			break;
		//For if the window is for editing courses
		case 3: 
			//Checks to make sure the data is not already added to avoid errors
			if(!course.containsCourse(textBoxes[0])){
				//Sets up a new course to be made using the data entered in the GUI
				course dataSendCourse = new course();
				faculty courseSelectedFaculty = faculty.getFaculty(facultyBox.getSelectedItem().toString().replace(" ", "_"));
				department courseSelectedDepartment = courseSelectedFaculty.getDepartment(departmentBox.getSelectedItem().toString().replace(" ", "_"));
				program courseSelectedProgram = courseSelectedDepartment.getProgram(programBox.getSelectedItem().toString().replace(" ", "_"));
				if(courseSelectedProgram.courseSet.size() < 10) {
					dataSendCourse.setID("0" + String.valueOf(String.valueOf(courseSelectedProgram.courseSet.size())));
				}
				else {
					dataSendCourse.setID(String.valueOf(String.valueOf(courseSelectedProgram.courseSet.size())));
				}
				dataSendCourse.setName(textBoxes[0].replace(" ", "_"), "101"); // This will need to be deleted when the ID is fixed being the same as name part 2
				dataSendCourse.setProgram(courseSelectedProgram);
				//Writing the made data to the database, the new object has already entered itself into local objects 
				//in the program and GUI
				database.write("src/Model/database.txt", dataSendCourse);
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " has been added to the system");
			}
			else {
				WarningMessage addWarning = new WarningMessage(textBoxes[0] + " already exists in the system");
			}
			break;
		default: System.out.println("The windowType value was an invalid value"); break;
		}
	}
	
	/**
	 * This method handles the event when the edit/change button is pressed. It will add the changes made on screen to the objects
	 * in the program and the database
	 * @param facultyBox is a JComboBox/drop down menu for the faculty drop down menu on the window
	 * @param departmentBox is a JComboBox/drop down menu for the department drop down menu on the window
	 * @param programBox is a JComboBox/drop down menu for the program drop down menu on the window
	 * @param antiReqBox is a JComboBox/drop down menu for the anti requisite drop down menu on the window
	 * @param preReqBox is a JComboBox/drop down menu for the prerequisite drop down menu on the window
	 */
	private void editButtonEvent(JComboBox facultyBox, JComboBox departmentBox, JComboBox programBox, JComboBox antiReqBox, JComboBox preReqBox) {
		//Gets any text written in text boxes on GUI
		String[] textBoxes = getTextBoxes();
		//Setting up the current objects based on the windows that are being edited
		faculty currentFaculty = faculty.getFacultySet().get(ListWindow.getCurrentFaculty());
		department currentDepartment = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
				getDepartments().get(ListWindow.getCurrentDepartment());
		program currentProgram = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
				getDepartments().get(ListWindow.getCurrentDepartment()).
				programSet.get(ListWindow.getCurrentProgram());
		switch(windowType) {
		case 0:
			database.edit("src/Model/database.txt", textBoxes[0].replace(" ", "_"), currentFaculty);
			currentFaculty.setName(textBoxes[0].replace(" ", "_"));
			break;
		case 1: 
			String facultyBoxSelect = facultyBox.getSelectedItem().toString().replace(" ", "_");
			//This condition is for seperating if the user has selected a different faculty than the 
			//department belonged to before, will delete all programs belonging to it
			if(currentFaculty.getName().equals(facultyBoxSelect)) {
				database.edit("src/Model/database.txt", textBoxes[0], currentDepartment);
				currentDepartment.setName(textBoxes[0].replace(" ", "_"));
			}
			else {
				deleteButtonEvent();
				addButtonEvent(facultyBox, departmentBox, programBox, antiReqBox, preReqBox);
			}
			
			break;
		case 2:
			String departmentBoxSelect = departmentBox.getSelectedItem().toString().replace(" ", "_");
			//This condition is for seperating if the user has selected a different department than the 
			//program belonged to before, will delete all courses under it
			if(currentDepartment.getName().equals(departmentBoxSelect)) {
				database.edit("src/Model/database.txt", textBoxes[0], currentProgram);
				currentProgram.setName(textBoxes[0].replace(" ", "_"));
			}
			else {
				deleteButtonEvent();
				addButtonEvent(facultyBox, departmentBox, programBox, antiReqBox, preReqBox);
			}

			break;
		case 3:
			course currentCourse = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
											getDepartments().get(ListWindow.getCurrentDepartment()).
											programSet.get(ListWindow.getCurrentProgram()).
											courseSet.get(ListWindow.getCurrentCourse());
			String programBoxSelect = programBox.getSelectedItem().toString().replace(" ", "_");
			//This condition is for seperating if the user has selected a different program than the 
			//course belonged to before
			if(currentProgram.getName().equals(programBoxSelect)) {
				database.edit("src/Model/database.txt", textBoxes[0], currentCourse);
				currentCourse.setName(textBoxes[0].replace(" ", "_"), textBoxes[1]);
			}
			else {
				deleteButtonEvent();
				addButtonEvent(facultyBox, departmentBox, programBox, antiReqBox, preReqBox);
			}
			break;
		default: System.out.println("The windowType value was an invalid value"); break;
		}
	}
	
	/**
	 * Will delete a data entry from the local objects and database
	 */
	private void deleteButtonEvent() {
		//Gets any text written in text boxes on GUI
		switch(windowType) {
		case 0:
			faculty facultyDelete = faculty.getFacultySet().get(ListWindow.getCurrentFaculty());
			database.delete("src/Model/database.txt", facultyDelete);
			facultyDelete.deleteFaculty();
			break;
		case 1:
			department departmentDelete = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
					getDepartments().get(ListWindow.getCurrentDepartment());
			database.delete("src/Model/database.txt", departmentDelete);
			departmentDelete.deleteDepartment();
			break;
		case 2:
			program programDelete = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
					getDepartments().get(ListWindow.getCurrentDepartment()).
					programSet.get(ListWindow.getCurrentProgram());
			database.delete("src/Model/database.txt", programDelete);
			programDelete.deleteProgram();
			break;
		case 3:
			course courseDelete = faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
					getDepartments().get(ListWindow.getCurrentDepartment()).
					programSet.get(ListWindow.getCurrentProgram()).
					courseSet.get(ListWindow.getCurrentCourse());
			database.delete("src/Model/database.txt", courseDelete);
			courseDelete.deleteCourse();
			break;
		default: System.out.println("The windowType value was an invalid value"); break;
		}
	}
	
	/**
	 * This method will look through the text boxes on the screen and return a string array of what is in them
	 * @return a String array of text box entries in the GUI window
	 */
	private String[] getTextBoxes() {
		String name = "";
		String ID = "";
		String courseDescription = "";
		String notes = "";
		//This section retrieves any text in the text boxes on the edit screen
		Component[] components = frmEditView.getContentPane().getComponents();
		for(int i = 0; i < components.length; i++) {
			if(components[i].getAccessibleContext().getAccessibleEditableText() != null) {
				components[i].getAccessibleContext().getAccessibleEditableText().selectText(0, 100);
				if(components[i].getAccessibleContext().getAccessibleEditableText().getCharCount() > 0) {
					switch(i) {
					case 3: 
						name = components[i].getAccessibleContext().getAccessibleEditableText().getSelectedText();	
						break;
					case 11: 
						ID = components[i].getAccessibleContext().getAccessibleEditableText().getSelectedText(); 
						break;
					case 17: 
						courseDescription = components[i].getAccessibleContext().getAccessibleEditableText().getSelectedText(); 
						break;
					case 19: 
						notes = components[i].getAccessibleContext().getAccessibleEditableText().getSelectedText();
						break;
					}
				}
			}
		}
		String[] retTextBoxes = {name, ID, courseDescription, notes};
		return retTextBoxes;
	}
	
	/**
	 * This method is a getter for the frame of the window
	 * @return the frame of the window
	 */
	public JFrame getFrame() {
		return frmEditView;
	}
	
	/**
	 * This method will get the name of the object being edited
	 * @return a String of the name of the object being edited
	 */
	private String getName() {
		//Retrieving the selected Faculty, Department, Program, or Course
		if(windowType == 0) {
			return faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).getName().replace("_", " ");
		}
		else if(windowType == 1) {
			return faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
			getDepartments().get(ListWindow.getCurrentDepartment()).getName().replace("_", " ");
		}
		else if(windowType == 2) {
			return faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
			getDepartments().get(ListWindow.getCurrentDepartment()).
			programSet.get(ListWindow.getCurrentProgram()).getName().replace("_", " ");
		}
		else {
			return faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
			getDepartments().get(ListWindow.getCurrentDepartment()).
			programSet.get(ListWindow.getCurrentProgram()).
			courseSet.get(ListWindow.getCurrentCourse()).getName().replace("_", " ");
		}
	}
	
	/**
	 * This method returns the ID of the course being edited
	 * @return A String object representing the ID of the course being edited
	 */
	private String getID() {
		//Retrieving the selected Faculty, Department, Program, or Course
		return faculty.getFacultySet().get(ListWindow.getCurrentFaculty()).
		getDepartments().get(ListWindow.getCurrentDepartment()).
		programSet.get(ListWindow.getCurrentProgram()).
		courseSet.get(ListWindow.getCurrentCourse()).getID();
	}
	
}