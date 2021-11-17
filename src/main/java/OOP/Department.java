package OOP;

import java.io.Serializable;

public class Department implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID, Name, Phone;
	
	public Department() {
		
	}

	public Department(String iD, String name, String phone) {
		super();
		ID = iD;
		Name = name;
		Phone = phone;
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
}
