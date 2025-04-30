import java.util.ArrayList;

public class MustBeOfferedConstraint extends Constraint
{
	
//	public MustBeOfferedConstraint(){
//		super(null, null, null);
//	}
	
	public MustBeOfferedConstraint(Term term, String desc, ArrayList<ClassInstance> classes) {
		super(term, desc, classes);

	}
	
	public void addConstraintClass(ClassInstance classInstance) {
		this.classes.add(classInstance);
	}

	@Override
	public Boolean evaluateConstraint(User u)
	{
		Boolean isOffered = true;
		if(u.getCanFinalize()) {
			ArrayList<ClassInstance> termClasses = term.getAllClasses();
			ArrayList<ClassInstance> classesOffered = new ArrayList<ClassInstance>();
			
			for(int i = 0; i < classes.size(); i++) {
				Boolean classOffered = false;
				for(int j = 0; j < termClasses.size(); j++) {
					if(termClasses.get(j).toString().equals(classes.get(i).toString())) {
						classOffered = true;
					}
				}
				if(!classOffered) {
					isOffered = false;
				}
				
			}
		}
		return isOffered;
	}

	@Override
	public String toString()
	{
		return "MustBeOfferedConstraint [term=" + term + ", constraintName=" + constraintName + "]";
	}
	
}
