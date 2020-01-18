/**
* The LoginFrame window will let the user login to the program

* and see its contents, make an administrator account, find
* password or id of existing user, and let students access
* the student version of the program.
*
* @author  Jung Kim
* @version 1.5
* @last worked   2019-04-08 
*/


package View;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import Controller.*;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.wb.swt.SWTResourceManager;

public class LoginFrame {
	
	protected Shell shlLogin;
	
	// User input
	private Text UserText;
	private Text UserPassword;
	
	private guiWindowController backGUI;
	
	//Setting the Width and Height of the Screen as Class attributes
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final int W = screenSize.width;
	final int H = screenSize.height;
	
	/**
	 * Launch the window.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public void runLoginFrame(guiWindowController gui) {
		try {
			backGUI = gui;
			this.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		shlLogin.open();
		shlLogin.forceActive();
		shlLogin.layout();
		while (!shlLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Display display) {
		shlLogin = new Shell();
		shlLogin.setSize(W, H);
		shlLogin.setText("Login");
		shlLogin.setLayout(null);
		
		// Centers window
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shlLogin.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    
	    shlLogin.setLocation(x, y);
		
		// Labels
		Label lblUsername = new Label(shlLogin, SWT.NONE);
		lblUsername.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblUsername.setBounds(W/3, H/5, 150, 40);
		lblUsername.setText("Username:");
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblPassword.setBounds(W/3, H/18 + H/4, 150, 40);
		lblPassword.setText("Password:");
		
		Label lblAdministratorLogin = new Label(shlLogin, SWT.NONE);
		lblAdministratorLogin.setFont(SWTResourceManager.getFont("Tahoma", 24, SWT.NORMAL));
		lblAdministratorLogin.setBounds(W*2 / 5, H/12, 360, 50);
		lblAdministratorLogin.setText("Administrator Login");

		
		Label lblForgotYourUsername = new Label(shlLogin, SWT.NONE);
		lblForgotYourUsername.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblForgotYourUsername.setBounds(W*2 /5 - W/40, H/2 + H/28, 450, 34);
		lblForgotYourUsername.setText("Forgot your username or password?");
		
		Label lblAreYouA = new Label(shlLogin, SWT.NONE);
		lblAreYouA.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblAreYouA.setBounds(W/3 - W/25, H/2 + H/5, 850, 34);
		lblAreYouA.setText("Are you a student? Access the portal by clicking the button below!");
		
		// User Input
		UserText = new Text(shlLogin, SWT.BORDER);
		UserText.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		UserText.setBounds(W/2, H/5, 200, 40);
		
		UserPassword = new Text(shlLogin, SWT.BORDER);
		UserPassword.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		UserPassword.setBounds(W/2, H/18 + H/4, 200, 40);
		
		// Buttons
		// It will compare id and password and let the user enter if they match. Otherwise an error message will appear.
		Button btnLogin = new Button(shlLogin, SWT.NONE);
		btnLogin.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnLogin.setBounds(W/3 + W/18, H/3 + H/10, 120, 40);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Boolean pass;
				UserAuthentication auth = new UserAuthentication();
				
				// checks to see if the user inputs are empty
				Boolean noInput = auth.checkEmptyInput(UserText.getText(), UserPassword.getText());
				if(noInput == true) {
					pass = false;
				} else {
					pass = auth.checkIdPass(UserText.getText(), UserPassword.getText());
				}
				
				// checks to see if the user name and the password match
				if(pass) {
					shlLogin.dispose();
					backGUI.setFlag(pass);
					backGUI.toggleForwardChange();
					backGUI.windowChange();
				} else {
					JOptionPane.showMessageDialog(null, "You have entered an invalid username/password");
				}
			}
		});
		btnLogin.setText("Login");
		
		// Opens a window for new users to register
		Button btnRegister = new Button(shlLogin, SWT.NONE);
		btnRegister.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnRegister.setBounds(W/2, H/3 + H/10, 120, 40);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlLogin.dispose();
				RegisterNewUser newUser = new RegisterNewUser();
				newUser.runRegistration();
			}
		});
		btnRegister.setText("Register");
		
		// Opens a window to find user password
		Button btnFindPassword = new Button(shlLogin, SWT.NONE);
		btnFindPassword.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnFindPassword.setBounds(W/3, H - H/2 + H/9, 200, 40);
		btnFindPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlLogin.dispose();
				FindPasswordFrame password = new FindPasswordFrame();
				password.runPasswordFinder();
			}
		});
		btnFindPassword.setText("Find Password");
		
		// Opens a window to find user id
		Button btnFindUsername = new Button(shlLogin, SWT.NONE);
		btnFindUsername.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnFindUsername.setBounds(W/2 + W/35, H - H/2 + H/9, 200, 40);
		btnFindUsername.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlLogin.dispose();
				FindUsernameFrame username = new FindUsernameFrame();
				username.runUsernameFinder();
			}
		});
		btnFindUsername.setText("Find Username");
		
		// No login needed for student access
		Button btnStudentAccess = new Button(shlLogin, SWT.NONE);
		btnStudentAccess.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				backGUI.setFlag(false);
				shlLogin.dispose();
				backGUI.toggleForwardChange();
				backGUI.windowChange();
			}
		});
		btnStudentAccess.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnStudentAccess.setBounds(W/3 + W/15, H - H/3 + H/8, 300, 100);
		btnStudentAccess.setText("Student Access");

	}
}
