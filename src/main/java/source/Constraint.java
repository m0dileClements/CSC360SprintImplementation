package source;
import java.util.ArrayList;

public abstract class Constraint
{
	Term term;
	String constraintName;
	ArrayList<ClassInstance> classes;
	
	public Constraint(Term term, String desc, ArrayList<ClassInstance> constrained) {
		this.term = term;
		this.constraintName = desc;
		classes = new ArrayList<ClassInstance>();
		for(int i = 0; i < constrained.size(); i++) {
			this.classes.add(constrained.get(i));
		}
	}
	public void modifyDescription(String newDescription, User u) {
		if (u.getCanEdit()) {
			this.constraintName = newDescription;
		}
	}
	
	public void modifyConstraint(Constraint constraint, User u) {
		if (u.getCanEdit()) {
			this.term = constraint.getTerm();
			this.constraintName = constraint.getConstraintName();
			this.classes = constraint.getClasses();
		}
	}

	//Getter and Setter methods for all relevant variables
	
	/**
	 * @return the constraintName
	 */
	public String getConstraintName()
	{
		return constraintName;
	}

	public abstract Boolean evaluateConstraint(User u);
	
	/**
	 * @return the term
	 */
	public Term getTerm()
	{
		return term;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(Term term)
	{
		this.term = term;
	}
	/**
	 * @return the classes
	 */
	public ArrayList<ClassInstance> getClasses()
	{
		return classes;
	}
	/**
	 * @param classes the classes to set
	 */
	public void setClasses(ArrayList<ClassInstance> classes)
	{
		this.classes = classes;
	}
	/**
	 * @param constraintName the constraintName to set
	 */
	public void setConstraintName(String constraintName)
	{
		this.constraintName = constraintName;
	}
	
	
	
	
}
