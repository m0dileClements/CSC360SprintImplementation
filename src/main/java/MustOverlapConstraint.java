import java.util.ArrayList;

public class MustOverlapConstraint extends Constraint
{
	
//	public MustOverlapConstraint(){
//		super(null, null, null);
//	}
	
	public MustOverlapConstraint(Term term, String desc, ArrayList<ClassInstance> classes) {
		super(term, desc, classes);
	}
	
	

	@Override
	public String toString()
	{
		return "MustOverlapConstraint [term=" + term + ", constraintName=" + constraintName + "]";
	}

	@Override
	public Boolean evaluateConstraint()
	{
		Boolean haveConflicts = false;
		
		if(classes.size()== 2) {
			if(term.checkTimeConflict(classes.get(0), classes.get(1))) {
				haveConflicts = true;
			}
		}
		
		return haveConflicts;
		
	}
}
