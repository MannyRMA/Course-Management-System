package Model;

import java.util.ArrayList;

public class program {
	private String name;
	private String ID;
	public department itsdepartment;
	//private static ArrayList<Integer> deletedIDs;
	public ArrayList<course> courseSet = new ArrayList<course>();
	public static ArrayList<program> allPrograms = new ArrayList<program>();
	
	public program() {
		allPrograms.add(this);
	}
	
	public boolean setDepartment(department newdepartment) {
		department olddepartment = itsdepartment;
		itsdepartment = newdepartment;
		if(setName(this.name)&&setID(this.ID)) {
			return true;
		}
		itsdepartment = olddepartment;
		return false;
	}
	
	public boolean setName(String name) {
		if(itsdepartment == null) {
			this.name = name;
			return true;
		}
		String nameUpper = name.toUpperCase();
		itsdepartment.programSet.remove(this);
		for (int i = 0; i < itsdepartment.programSet.size();i++) {
			if(itsdepartment.programSet.get(i).getName().toUpperCase().equals(nameUpper)){
				itsdepartment.programSet.add(this);
				return false;
			}
		}
		this.name = name;
		itsdepartment.programSet.add(this);
		return true;	
	}
	
	public boolean setID(String ID) {
		if (ID.length()!=2) {
			return false;
		}
		//int intID;
		//try {
		//	intID = Integer.parseInt(ID);
		//}catch(Exception e) {
		//	return false;
		//}
		if (itsdepartment == null) {
			this.ID = ID;
			return true;
		}
		itsdepartment.programSet.remove(this);
		for(int i = 0; i < itsdepartment.programSet.size();i++) {
			if(itsdepartment.programSet.get(i).getID()== ID) {
				itsdepartment.programSet.add(this);
				return false;
			}
		}
		this.ID = ID;
		itsdepartment.programSet.add(this);
		return true;
	}
	
	public static boolean containsProgram(String name) {
		for(int i = 0; i < allPrograms.size(); i++) {
			if(allPrograms.get(i).getName().equals(name.replace(" ", "_"))) {
				return true;
			}
		}
		return false;
	}
	
	public String getID() {
		return this.ID;
		//return Integer.parseInt(this.ID);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFullLine(){
		return "\t\t+ " + name + " " + ID;
	    }

	/*
	 * This function assumes a faculty reference and will delete that faculty from the facultySet, then add its 
	 * deleted index to the list of deleted indexes.
	 */
	public void deleteProgram() {
		int tempIndex = 0;
		for(int i = 0; i < allPrograms.size(); i++) {
			if(allPrograms.get(i).equals(this)){
				tempIndex = i;
			}
		}
		for(int i = 0; i < courseSet.size(); i++) {
			courseSet.get(i).deleteCourse();
		}
		//deletedIDs.add(tempIndex);
		allPrograms.remove(this);  
		itsdepartment.deleteProgramFromSet(this);
	}
	
	 /**
	   * This method is for a program object looking to delete it's reference from the department above it
	   * 
	   * @param deleteDepartment
	   */
	  public void deleteCourseFromSet(course deleteCourse) {
		  for(int i = 0; i < courseSet.size(); i++) {
			  if(courseSet.get(i).equals(deleteCourse)) {
				courseSet.remove(i);
			  }
			}
	  }
}