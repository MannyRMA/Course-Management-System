package View;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * WarningMessage class
 * a window which would appear whenever the user chooses an option which may lead to "side effects"
 * @author Manny Rodriguez
 *
 */
public class WarningMessage {
	
	private JFrame frame;
	

	/**
	 * constructor for a WarningMessage window
	 * @param message - the message that is going to appear on the window
	 */
	public WarningMessage(String message) 
	{
		initialize(message);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param message - the message that is going to appear on the window
	 */
	private void initialize(String message) 
	{
		// the frame work for the window frame
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 350);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Warning!");
		frame.setVisible(true);
		
		// the components of the yes button of the window which would close the window and perform the action they wished
		JButton yesButton = new JButton("Ok");
		yesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				frame.dispose();				// having trouble disposing the window so for now it is just being set to invisible
				//remove or edit the object
			}
		});
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		yesButton.setFont(new Font("Dialog", Font.PLAIN, 16));
		yesButton.setBounds(100, 220, 70, 30);
		frame.getContentPane().add(yesButton);
		
		// components of the no button of the window, which would close the window with making changes
		JButton noButton = new JButton("Cancel");
		noButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				frame.dispose();			// having trouble disposing the window so for now it is just being set to invisible
			}
		});
		noButton.setFont(new Font("Dialog", Font.PLAIN, 16));
		noButton.setBounds(200, 220, 70, 30);
		frame.getContentPane().add(noButton);
		
		// components of the warning label which contains the warning message to the user
		Label warningLabel = new Label(message);
		warningLabel.setAlignment(Label.CENTER);
		frame.getContentPane().add(warningLabel);
				
	}

}