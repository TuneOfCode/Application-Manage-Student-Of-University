package OOP;

import java.io.Serializable;

public class Subject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID, Name;
	private int Credits, Lessons;
	public Subject() {
		
	}
	public Subject(String iD, String name, int credits, int lessons) {
		super();
		ID = iD;
		Name = name;
		Credits = credits;
		Lessons = lessons;
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
	public int getCredits() {
		return Credits;
	}
	public void setCredits(int credits) {
		Credits = credits;
	}
	public int getLessons() {
		return Lessons;
	}
	public void setLessons(int lessons) {
		Lessons = lessons;
	}
	@Override
	public String toString() {
		return "Subject [getID()=" + getID() + ", getName()=" + getName() + ", getCredits()=" + getCredits()
				+ ", getLessons()=" + getLessons() + "]";
	}
	
}
