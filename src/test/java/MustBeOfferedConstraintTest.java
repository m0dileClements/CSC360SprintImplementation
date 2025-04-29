import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MustBeOfferedConstraintTest
{
	Term testTerm;
	ArrayList<ClassInstance> allTestClasses;
	Boolean isFinalized;
	ClassInstance class1;
	ClassInstance class2;
	ClassInstance class3;
	
	@BeforeEach
	void setUp() throws Exception
	{
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "French");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Crounse", 307);
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		testTerm = new Term("Spring", 2025);
		Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		class1 = new ClassInstance(courseTest, testTerm, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		Instructor instructorTest2 = new Instructor("The Genie", deptTest2);
		Room roomTest2 = new Room("Olin", 201);
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		Course courseTest2 = new Course("Bio210", "Environmental Science", tags2);
		class2 = new ClassInstance(courseTest2, testTerm, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);

		DepartmentHead deptHeadTest3 = new DepartmentHead("HeadMan", "BigBos", "IHaveSevereImposterSyndrome");
		Department deptTest3 = new Department(deptHeadTest3, "biology");
		Instructor instructorTest3 = new Instructor("Dr. Doctor", deptTest3);
		Room roomTest3 = new Room("Olin", 222);
		ArrayList<String> tags3 = new ArrayList<String>();
		tags3.add("E1");
		tags3.add("D");
		Course courseTest3 = new Course("DSC", "Impacts of Analytics on Human body", tags3);
		class3 = new ClassInstance(courseTest3, testTerm, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest3);
		

	}

	@Test
	void testEvaluateConstraints()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class2);
		
		MustBeOfferedConstraint constraint1 = new MustBeOfferedConstraint(testTerm, "must be offered", constrainedClasses);
		testTerm.addConstraint(constraint1);
		assertEquals("must be offered", constraint1.getConstraintName());
		
		
		assertEquals(true, constraint1.evaluateConstraint());
		testTerm.removeClassfromTerm(class2);
		
		assertEquals(false, constraint1.evaluateConstraint());
		
		testTerm.addClass(class2);
		
		constraint1.addConstraintClass(class1);
		assertEquals(true, constraint1.evaluateConstraint());
		testTerm.removeClassfromTerm(class2);
		
		assertEquals(false, constraint1.evaluateConstraint());
	}
	
	void testToString()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class2);
		constrainedClasses.add(class3);
		
		MustBeOfferedConstraint constraint1 = new MustBeOfferedConstraint(testTerm, "must be offered", constrainedClasses);
		testTerm.addConstraint(constraint1);
		assertEquals("MustBeOfferedConstraint [term=Term [semester=Spring, year=2025], constraintName=must be offered]", constraint1.toString());
		
	}

}
