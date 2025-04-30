import java.util.ArrayList;

public class NonOverlappingConstraint extends Constraint
{
//	Term term;
//	ArrayList<ClassInstance> classes;
	
//	public NonOverlappingConstraint(){
//		super(null, null, null);
//		term = null;
//	}
	
	public NonOverlappingConstraint(Term term, String desc, ArrayList<ClassInstance> classes) {
		super(term, desc, classes);

	}
	

	@Override
	public String toString()
	{
		return "NonOverlappingConstraint [term=" + term + ", constraintName=" + constraintName + "]";
	}


	@Override
	public Boolean evaluateConstraint(User u) {
		Boolean haveConflicts = false;
		
		if(u.getCanFinalize()) {
			if(classes.size()== 2) {
				if(term.checkTimeConflict(classes.get(0), classes.get(1))) {
					haveConflicts = true;
				}
				return !haveConflicts;
			}
		}
		return haveConflicts;
		
	}
	
	
	
	
	
	
}
