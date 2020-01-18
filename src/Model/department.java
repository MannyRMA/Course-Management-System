package Model;

import java.util.ArrayList;

public class department {
	private String name;
	private String ID;
	public faculty itsfaculty;
	//private static ArrayList<Integer> deletedIDs;
	public ArrayList<program> programSet = new ArrayList<program>();
	public static ArrayList<department> allDepartments = new ArrayList<department>();
	
	public department() {
		allDepartments.add(this);
	}
	
	public boolean setFaculty(faculty newfaculty) {
		faculty oldfaculty = itsfaculty;
		itsfaculty = newfaculty;
		if(setName(this.name)&&setID(this.ID)) {
			return true;
		}
		itsfaculty = oldfaculty;
		return false;
	}
	
	public boolean setName(String name) {
		if(itsfaculty == null) {
			this.name = name;
			return true;
		}
		String nameUpper = name.toUpperCase();
		itsfaculty.getDepartments().remove(this);
		for (int i = 0; i < itsfaculty.getDepartments().size();i++) {
			if(itsfaculty.getDepartments().get(i).getName().toUpperCase().equals(nameUpper)){
				itsfaculty.getDepartments().add(this);
				return false;
			}
		}
		this.name = name;
		itsfaculty.getDepartments().add(this);
		return true;	
	}
	
	public boolean setID(String id) {
		if (id.length()!=2) {
			return false;
		}
		try {
			Integer.parseInt(id);
		}catch(Exception e) {
			return false;
		}
		if (itsfaculty == null) {
			this.ID = id;
			return true;
		}
		itsfaculty.getDepartments().remove(this);
		for(int i = 0; i < itsfaculty.getDepartments().size();i++) {
			if(itsfaculty.getDepartments().get(i).getID().equals(id)) {
				itsfaculty.getDepartments().add(this);
				return false;
			}
		}
		this.ID = id;
		itsfaculty.getDepartments().add(this);
		return true;
	}
	
	public static boolean containsDepartment(String name) {
		for(int i = 0; i < allDepartments.size(); i++) {
			if(allDepartments.get(i).getName().equals(name.replace(" ", "_"))) {
				return true;
			}
		}
		return false;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFullLine(){
		return "\t= " + name + " " + ID;
	    }
	
	public program getProgram(String name) {
		  for(int i = 0; i < programSet.size(); i++) {
			  if(programSet.get(i).getName().equals(name)) {
				  return programSet.get(i);
			  }
		  }
		  return null;
	  }
	
	/*
	* This function assumes a faculty reference and will delete that faculty from the facultySet, then add its 
	* deleted index to the list of deleted indexes.
	*/
	public void deleteDepartment() {
		int tempIndex = 0;
		for(int i = 0; i < allDepartments.size(); i++) {
			if(allDepartments.get(i).equals(this)){
				tempIndex = i;
			}
		}
		for(int i = 0; i < programSet.size(); i++) {
			programSet.get(i).deleteProgram();
		}
		//deletedIDs.add(tempIndex);
		allDepartments.remove(this);	
		itsfaculty.deleteDepartmentFromSet(this);
	}
	
	 /**
	   * This method is for a program object looking to delete it's reference from the department above it
	   * 
	   * @param deleteProgram is the program to be deleted
	   */
	  public void deleteProgramFromSet(program deleteProgram) {
		  for(int i = 0; i < programSet.size(); i++) {
			  if(programSet.get(i).equals(deleteProgram)) {
				programSet.remove(i);
			  }
			}
	  }

}