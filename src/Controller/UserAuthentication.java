package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class UserAuthentication {

	String fileName = "passwordCheck.txt";
	String line = "";
	Boolean authenticate = false;
	
	public Boolean checkIdPass(String userInputName, String userInputPassword) {
	
		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			line = bufferedReader.readLine();
			
			while(line != null) {
				String[] idPass = line.split(" ");
				
				if(idPass[0].contentEquals(userInputName) && idPass[1].contentEquals(userInputPassword)) {
					authenticate = true;
					line = null;
				} else {
					line = bufferedReader.readLine();
				}
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.print("No Such File");
		} catch(IOException ex) {
			System.out.print("Error reading file");
		}
		return authenticate;
	}
	
	public boolean checkEmptyInput(String newUserName, String newUserPassword) {
		boolean invalid = false;
		if(newUserName == null || newUserName == "" || newUserPassword == null || newUserPassword == "") {
			invalid = true;
		}
			
		return invalid;
	}
	
	public boolean checkUsername(String userInputName) {
		boolean exist = false;
		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			line = bufferedReader.readLine();
			
			while(line != null) {
				String[] idPass = line.split(" ");
				
				if(idPass[0].contentEquals(userInputName)) {
					exist = true;
					line = null;
				} else {
					line = bufferedReader.readLine();
				}
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.print("No Such File");
		} catch(IOException ex) {
			System.out.print("Error reading file");
		}
		
		return exist;
	}

	public void register(String newUserName, String newUserPassword, String userEmail) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			
			bufferWriter.newLine();
			bufferWriter.write(newUserName + " " + newUserPassword + " " + userEmail);
			
			bufferWriter.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public String passwordFinder(String userName, String userEmail) {
		String password = "";
		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			line = bufferedReader.readLine();
				
			while(line != null) {
				String[] idPass = line.split(" ");
				
				if(idPass[0].contentEquals(userName) && idPass[2].contentEquals(userEmail)) {
					password = idPass[1];
					line = null;
				} else {
					line = bufferedReader.readLine();
				}
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.print("No Such File");
		} catch(IOException ex) {
			System.out.print("Error reading file");
		}
		
		
		return password;
	}

	public String userNameFinder(String userEmail) {
		String username = "";
		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			line = bufferedReader.readLine();
				
			while(line != null) {
				String[] idPass = line.split(" ");
				
				if(idPass[2].contentEquals(userEmail)) {
					username = idPass[0];
					line = null;
				} else {
					line = bufferedReader.readLine();
				}
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.print("No Such File");
		} catch(IOException ex) {
			System.out.print("Error reading file");
		}
		
		
		return username;
	}

}