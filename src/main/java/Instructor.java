

import java.util.ArrayList;

public class Instructor
{
	String name;
	Department profDept;
	double hours;
	
	public Instructor(String name, Department profDept) {
		this.name = name;
		this.profDept = profDept;
		this.hours = 0;
	}
	
	//calculates the hours an instructor completes over a term by adding all of their class lengths
	public double getHours(Term term) {
		ArrayList<ClassInstance> termClasses = term.getAllClasses();
		double hoursInSemester = 0;
		
		
		for(int i = 0; i < termClasses.size(); i++) {
			if(termClasses.get(i).instructor.getName() == name && termClasses.get(i).instructor.getProfDept() == profDept) {
				
				double classLength = termClasses.get(i).classLength;
				hoursInSemester += classLength;
			}
			
		}
		return hoursInSemester;
	}

	//Getter and Setter methods for all relevant variables
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the profDept
	 */
	public Department getProfDept()
	{
		return profDept;
	}

	/**
	 * @param profDept the profDept to set
	 */
	public void setProfDept(Department profDept)
	{
		this.profDept = profDept;
	}

	/**
	 * @return the hours
	 */
	public double getHours()
	{
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(double hours)
	{
		this.hours = hours;
	}

	@Override
	public String toString()
	{
		return "Instructor [name=" + name + ", profDept=" + profDept.getDeptName() + ", hours=" + hours + "]";
	}
	
	
}
