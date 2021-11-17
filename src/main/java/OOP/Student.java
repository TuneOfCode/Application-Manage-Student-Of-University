package OOP;

import java.io.Serializable;

public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID, Firstname, Lastname, Gender, Address;
	private int Acadamic_year;
	private String Class_ID;
	public Student() {
		
	}
	
	public Student(String iD, String firstname, String lastname, String gender, String address, int acadamic_year,
			String class_ID) {
		super();
		ID = iD;
		Firstname = firstname;
		Lastname = lastname;
		Gender = gender;
		Address = address;
		Acadamic_year = acadamic_year;
		Class_ID = class_ID;
	}

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getAcadamic_year() {
		return Acadamic_year;
	}
	public void setAcadamic_year(int acadamic_year) {
		Acadamic_year = acadamic_year;
	}
	public String getClass_ID() {
		return Class_ID;
	}

	public void setClass_ID(String class_ID) {
		Class_ID = class_ID;
	}

	@Override
	public String toString() {
		return "Student [getID()=" + getID() + ", getFirstname()=" + getFirstname() + ", getLastname()=" + getLastname()
				+ ", getGender()=" + getGender() + ", getAddress()=" + getAddress() + ", getAcadamic_year()="
				+ getAcadamic_year() + ", getClass_ID()=" + getClass_ID() + "]";
	}
	
}
