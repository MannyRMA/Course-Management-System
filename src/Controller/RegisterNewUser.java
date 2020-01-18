/**
* RegisterNewUser window will let new users register
* and make an account
*
* @author  Jung Kim
* @version 1.5
* @last worked   2019-04-08 
*/

package Controller;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import View.LoginFrame;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class RegisterNewUser {

	protected Shell shell;
	private Text newUserName;
	private Text newUserPassword;
	private Text userEmail;

	//Setting the Width and Height of the Screen as Class attributes
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final int W = screenSize.width;
	final int H = screenSize.height;
	
	public void openLoginWindow(int W, int H) {
		LoginFrame login = new LoginFrame();
		login.runLoginFrame(null);
	}
	
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public void runRegistration() {
		try {
			RegisterNewUser window = new RegisterNewUser();
			window.open();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Display display) {
		shell = new Shell();
		shell.setSize(W, H);
		shell.setText("Registration");
		shell.setLayout(null);
		
		// Centers window
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
			    
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
			    
		shell.setLocation(x, y);
		
		// Label
		Label lblUserName = new Label(shell, SWT.NONE);
		lblUserName.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblUserName.setBounds(W/3, H/5, 165, 40);
		lblUserName.setText("New Username:");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblNewLabel.setBounds(W/3, H/18 + H/4, 165, 40);
		lblNewLabel.setText("New Password:");
		
		Label lblRegistrationForm = new Label(shell, SWT.NONE);
		lblRegistrationForm.setFont(SWTResourceManager.getFont("Tahoma", 24, SWT.NORMAL));
		lblRegistrationForm.setBounds(W*2 / 5, H/12, 300, 50);
		lblRegistrationForm.setText("Registration Form");
		
		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		lblEmail.setBounds(W/3, H/3 + H/12, 165, 40);
		lblEmail.setText("Email:");
		
		// User input
		newUserName = new Text(shell, SWT.BORDER);
		newUserName.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		newUserName.setBounds(W/2, H/5, 200, 40);
		
		newUserPassword = new Text(shell, SWT.BORDER);
		newUserPassword.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		newUserPassword.setBounds(W/2, H/18 + H/4, 200, 40);
		
		userEmail = new Text(shell, SWT.BORDER);
		userEmail.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		userEmail.setBounds(W/2, H/3 + H/12, 200, 40);
		
		// Button
		// Pressing confirm will make an account for a new user or give an error message
		Button btnConfirm = new Button(shell, SWT.NONE);
		btnConfirm.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UserAuthentication newUser = new UserAuthentication();
				boolean invalid = newUser.checkEmptyInput(newUserName.getText(), newUserPassword.getText());
				boolean existingId = newUser.checkUsername(newUserName.getText());
				if(invalid == true || existingId == true) {
					if(invalid == true) {
						JOptionPane.showMessageDialog(null, "You have entered an invalid username/password");
					} else {
						JOptionPane.showMessageDialog(null, "The Username you entered already exists");
					}
				} else {
					newUser.register(newUserName.getText(), newUserPassword.getText(), userEmail.getText());
					btnConfirm.setEnabled(false);
					shell.dispose();
				}
				
			}
		});
		btnConfirm.setBounds(W/3, H - H/2 + H/9, 170, 40);
		btnConfirm.setText("confirm");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.NORMAL));
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				openLoginWindow(W, H);
			}
		});
		btnCancel.setBounds(W/2 + W/25, H - H/2 + H/9, 170, 40);
		btnCancel.setText("cancel");
	}
}
