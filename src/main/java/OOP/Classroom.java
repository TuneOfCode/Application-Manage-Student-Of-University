package OOP;

import java.io.Serializable;

public class Classroom extends Organization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int School_year;
	private String department_ID;
	public Classroom() {

	}
	public Classroom(String iD, String name, int school_year, String department_ID, Department department) {
		super(iD, name);
		School_year = school_year;
		this.department_ID = department_ID;
	}
	public int getSchool_year() {
		return School_year;
	}
	public void setSchool_year(int school_year) {
		School_year = school_year;
	}
	public String getDepartment_ID() {
		return department_ID;
	}
	public void setDepartment_ID(String department_ID) {
		this.department_ID = department_ID;
	}
	@Override
	public String toString() {
		return "Class [getID()=" + getID() + ", getName()=" + getName() + ", getSchool_year()=" + getSchool_year()
				+ ", getDepartment_ID()=" + getDepartment_ID() + "]";
	}
	@Override
	// Check if the ID is identical?
	public int compareTo(Organization o) {
		// TODO Auto-generated method stub
		if (this.getID().equals(o.getID())) {
			return 1;
		} else {
			return 0;
		}
	}
}
