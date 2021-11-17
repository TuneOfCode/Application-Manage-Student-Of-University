package OOP;

import java.io.Serializable;

public class Classroom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID, Name;
	private int School_year;
	private String department_ID;
	public Classroom() {

	}
	public Classroom(String iD, String name, int school_year, String department_ID, Department department) {
		super();
		ID = iD;
		Name = name;
		School_year = school_year;
		this.department_ID = department_ID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
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
}
