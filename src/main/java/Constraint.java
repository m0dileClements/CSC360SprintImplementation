

public class Constraint
{
	String constraintName;
	ClassInstance class1;
	ClassInstance class2;
	
	public Constraint (String desc, ClassInstance c1, ClassInstance c2) {
		this.constraintName = desc;
		this.class1 = c1;
		this.class2 = c2;
	}
	public void modifyDescription(String newDescription, User u) {
		if (u.getCanEdit()) {
			this.constraintName = newDescription;
		}
	}
	
	public void modifyConstraint(Constraint constraint, User u) {
		if (u.getCanEdit()) {
			this.constraintName = constraint.constraintName;
			this.class1 = constraint.class1;
			this.class2 = constraint.class2;
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
	
	public ClassInstance getClass1() {
		return class1;
	}
	
	public ClassInstance getClass2() {
		return class2;
	}

	@Override
	public String toString()
	{
		return "Constraint [constraintName=" + constraintName + ", class1=" + class1.getCourse().getTitle() + ", class2=" + class2.getCourse().getTitle() + "]";
	}
	
	
}
