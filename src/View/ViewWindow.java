package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ViewWindow class which is a window for when a student interacts with the system, having only viewing priveledges
 * @author Manny Rodriguez
 *
 */
public class ViewWindow {

	private JFrame frame;
	private ArrayList<String> listOptions;
	
	public void setOptionList(ArrayList<String> listOptions)
	{
		this.listOptions = listOptions;
	}
	
	


	/**
	 * Create the application.
	 */
	public ViewWindow(String title, JFrame previousFrame) {
		initialize(title, previousFrame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String title, JFrame previousFrame) {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.getContentPane().setLayout(null);
		
		JList attributeList = new JList();
		attributeList.setBounds(new Rectangle(58, 69, 645, 689));
		frame.getContentPane().add(attributeList);
		
		Object selectionType = attributeList.getSelectedValue();
		String highlighted = (String) attributeList.getSelectedValue();
		
		JButton continueButton = new JButton("Continue");
		continueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				frame.setVisible(false);
				ViewWindow nextWindow = new ViewWindow(highlighted, frame);
			}
		});
		continueButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		continueButton.setBounds(715, 374, 155, 43);
		frame.getContentPane().add(continueButton);
		
		
		JButton backButton = new JButton("Back");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				frame.dispose();
				previousFrame.setVisible(true);
			}
		});

		backButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		backButton.setBounds(117, 785, 178, 43);
		frame.getContentPane().add(backButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		exitButton.setBounds(445, 785, 92, 43);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);
			}
		});
		frame.getContentPane().add(exitButton);
		
		frame.setVisible(true);
	}

}
