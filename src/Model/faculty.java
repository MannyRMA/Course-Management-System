package Model;

import java.util.ArrayList;

public class faculty {

  private static ArrayList<faculty> facultySet = new ArrayList<faculty>();
  private String name;
  private String ID;
  private static int newID;
  //private static ArrayList<Integer> deletedIDs;
  private ArrayList<department> departmentSet = new ArrayList<department>();
  
  public faculty() {
	  facultySet.add(this);
	  newID = facultySet.size();
  }
  
  public boolean setName(String name) {
	  facultySet.remove(this);
	  String nameUpper = name.toUpperCase();
	  for (int i = 0 ; i < facultySet.size();i++) {
		  if (facultySet.get(i).getName().toUpperCase().equals(nameUpper)) {
			  facultySet.add(this);
			  return false;
		  }
	  }
	  this.name = name;
	  facultySet.add(this);
	  return true;
  }
  
  
  
  public String getName() {
	  return name;
  }
  
  public String getID() {
	  return this.ID;
  }
  
  public boolean setID(String ID) {
	  if (ID.length()!=2) {
		  return false;
	  }
	  try {
		  Integer.parseInt(ID);
	  }catch(Exception e) {
		  return false;
	  }
	  facultySet.remove(this);
	  for(int i = 0; i < facultySet.size();i++) {
		  if(facultySet.get(i).getID().equals(ID)) {
			  facultySet.add(this);
			  return false;
		  }
	  }
	  this.ID = ID;
	  facultySet.add(this);
	  return true;
	  
  }
  
  public static ArrayList<faculty> getFacultySet(){
	  return facultySet;
  }

  
  public void removeFaculty() {
	  facultySet.remove(this);
  }
  
  public ArrayList<department> getDepartments(){
	  return departmentSet;
  }
  
  public String getFullLine(){
		return "> " + name + " " + ID;
	    }
  
  public static faculty getFaculty(String name) {
	  for(int i = 0; i < facultySet.size(); i++) {
		  if(facultySet.get(i).getName().equals(name)) {
			  return facultySet.get(i);
		  }
	  }
	  return null;
  }
  
  public static faculty getFaculty(faculty searchFaculty) {
	  for(int i = 0; i < facultySet.size(); i++) {
		  if(facultySet.get(i) == searchFaculty) {
			  return facultySet.get(i);
		  }
	  }
	  return null;
  }
  
  public static boolean containsFaculty(String name) {
	  for(int i = 0; i < facultySet.size(); i++) {
		  if(facultySet.get(i).getName().equals(name.replace(" ", "_"))) {
			  return true;
		  }
	  }
	  return false;
  }
  
  public department getDepartment(String name) {
	  for(int i = 0; i < departmentSet.size(); i++) {
		  if(departmentSet.get(i).getName().equals(name)) {
			  return departmentSet.get(i);
		  }
	  }
	  return null;
  }
  
  /*
   * This function will delete the faculty from the facultySet, then add its 
   * deleted index to the list of deleted indexes.
   */
  public void deleteFaculty() {
	  int tempIndex = 0;
	  for(int i = 0; i < departmentSet.size(); i++) {
		  System.out.println(departmentSet.get(i).getName());
		  for(int j = 0; j < departmentSet.get(i).programSet.size(); j++) {
			  System.out.println(departmentSet.get(i).programSet.get(j).getName());
			  for(int k = 0; k < departmentSet.get(i).programSet.get(j).courseSet.size(); k++) {
				  System.out.println(departmentSet.get(i).programSet.get(j).courseSet.get(k).getName());
			  }
		  }
	  }
	  for(int i = 0; i < facultySet.size(); i++) {
		  if(facultySet.get(i).equals(this)){
			  tempIndex = i;
		  }
	  }
	  for(int i = 0; i < departmentSet.size(); i++) {
			departmentSet.get(i).deleteDepartment();
		}
	  //deletedIDs.add(tempIndex);
	  facultySet.remove(this);
	  newID = facultySet.size();
	  
  }
  
  /**
   * This method is for a department object looking to delete it's reference from the faculty above it
   * 
   * @param deleteDepartment is the department to be deleted
   */
  public void deleteDepartmentFromSet(department deleteDepartment) {
	  for(int i = 0; i < departmentSet.size(); i++) {
		  if(departmentSet.get(i).equals(deleteDepartment)) {
			departmentSet.remove(i);
		  }
		}
  }
  
  public static String getFacultyNewID() {
	  if(newID < 10) {
		  return "0" + String.valueOf(newID);
	  }
	  else {
		  return String.valueOf(newID);
	  }
	  
  }
  
//  public static ArrayList<Integer> getFacultyDeletedID() {
//	  return deletedIDs;
//  }
// 
}