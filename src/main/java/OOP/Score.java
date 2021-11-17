package OOP;

import java.io.Serializable;

public class Score implements calcCoefficient_score, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double Score_Midterm, Score_Endterm;
	private String subject_ID,student_ID;
	public Score() {
		
	}
	
	public Score(double score_Midterm, double score_Endterm, String subject_ID, String student_ID) {
		super();
		Score_Midterm = score_Midterm;
		Score_Endterm = score_Endterm;
		this.subject_ID = subject_ID;
		this.student_ID = student_ID;
	}

	public double getScore_Midterm() {
		return Score_Midterm;
	}
	public void setScore_Midterm(double score_Midterm) {
		Score_Midterm = score_Midterm;
	}
	public double getScore_Endterm() {
		return Score_Endterm;
	}
	public void setScore_Endterm(double score_Endterm) {
		Score_Endterm = score_Endterm;
	}
	public String getSubject_ID() {
		return subject_ID;
	}
	public void setSubject_ID(String subject_ID) {
		this.subject_ID = subject_ID;
	}
	public String getStudent_ID() {
		return student_ID;
	}
	public void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}
	
	@Override
	public String toString() {
		return "Score [getScore_Midterm()=" + getScore_Midterm() + ", getScore_Endterm()=" + getScore_Endterm()
				+ ", getSubject_ID()=" + getSubject_ID() + ", getStudent_ID()=" + getStudent_ID() + "]";
	}
	@Override
	public double Coefficient_4(double Coefficient_10) {
		// TODO Auto-generated method stub
		Coefficient_10 = Coefficient_10(getScore_Midterm(), getScore_Endterm());
		double result = Coefficient_10 * 4.0 / 10.0;
		return (double)Math.round(result * 100) / 100;
	}
	@Override
	public double Coefficient_10(double score_Midterm, double score_Endterm) {
		// TODO Auto-generated method stub
//		score_Midterm = getScore_Midterm();
//		score_Endterm = getScore_Endterm();
		double result = (score_Midterm + score_Endterm) / 2.0;
		return (double)Math.round(result * 100) / 100;
	}
}
