package OOP;

import java.io.Serializable;

public abstract class Organization implements Comparable<Organization>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID, Name;

	public Organization() {
		super();
	}
	public Organization(String iD, String name) {
		super();
		ID = iD;
		Name = name;
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
}
