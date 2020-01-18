package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.guiWindowController;
//import javafx.scene.image.Image;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
/**
 * WelcomeWindow class
 * the welcome window the user first sees when they first run the program
 * @author Manny Rodriguez
 *
 */
public class WelcomeWindow {

	// private instance variables for the class
	private JFrame welcomeFrame;
	private static int windowWidth;
	private static int windowHeight;
	private guiWindowController backGUI;

	
	/**
	 * Launch the application.
	 */
	public static void main(int displayType, int width, int height) {
		windowWidth = width;
		windowHeight = height;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//WelcomeWindow window = new WelcomeWindow();
					//window.welcomeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// constructor for the window
	public WelcomeWindow(int width, int height, guiWindowController gui) {
		windowWidth = width;
		windowHeight = height;
		backGUI = gui;
		initialize();
	}
	
	// getter for the frame
	public JFrame getFrame()
	{
		return welcomeFrame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		welcomeFrame = new JFrame();
		welcomeFrame.setTitle("Welcome to Simor Fraser Course Management");
		welcomeFrame.setSize(windowWidth, windowHeight);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.getContentPane().setLayout(null);
		
		// button that exits the window, and program since it is the first window to appear
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Dubai", Font.PLAIN, 18));
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				System.exit(0);
			}
		});
		exitButton.setBounds(2 * windowWidth / 7, 3 * windowHeight / 4, windowWidth / 10, windowHeight / 15);
		welcomeFrame.getContentPane().add(exitButton);
		
		
		// button that when clicked it goes to the next window
		JButton continueButton = new JButton("Continue");
		continueButton.setFont(new Font("Dubai", Font.PLAIN, 18));
		continueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				welcomeFrame.dispose();
				backGUI.toggleForwardChange();
				backGUI.windowChange();
			}
		});
		continueButton.setBounds(3 * windowWidth / 5, 3 * windowHeight / 4, windowWidth / 10, windowHeight / 15);
		welcomeFrame.getContentPane().add(continueButton);
		
		
		// a welcoming message for the user
		JLabel welcomeMessage = new JLabel("Simon Fraser University Course Managament");
		welcomeMessage.setFont(new Font("Monotype Corsiva", Font.PLAIN, 41));
		welcomeMessage.setBounds(windowWidth / 3, windowHeight / 25, windowWidth / 2, windowHeight / 3);
		welcomeFrame.getContentPane().add(welcomeMessage);
		
		
		// a crest for the window, nice image to have when the program runs
		JLabel SFUCrest = new JLabel("");
		SFUCrest.setBounds(2 * windowWidth / 7, windowHeight /3, 800, 150);
		java.awt.Image crest = new ImageIcon(this.getClass().getResource("/IconImages/Crest.gif")).getImage();
		SFUCrest.setIcon(new ImageIcon(crest));
		welcomeFrame.getContentPane().add(SFUCrest);
	}

}

