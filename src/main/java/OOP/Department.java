package OOP;

import java.io.Serializable;

public class Department extends Organization implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Phone;
	
	public Department() {
		
	}

	public Department(String iD, String name, String phone) {
		super(iD, name);
		Phone = phone;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	@Override
	public String toString() {
		return "Department [getID()=" + getID() + ", getName()=" + getName() + ", getPhone()=" + getPhone() + "]";
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