import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NonOverlappingConstraintTest
{

	Term testTerm;
	ArrayList<ClassInstance> allTestClasses;
	Boolean isFinalized;
	ClassInstance class1;
	ClassInstance class2;
	ClassInstance class3;
	ClassInstance class4;
	Registrar allPermissions;
	
	@BeforeEach
	void setUp() throws Exception
	{
		allPermissions = new Registrar("", "", "");
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "French");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Crounse", "307");
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		testTerm = new Term("Spring", 2025);
		//Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		class1 = new ClassInstance("FRE270", "Group Conversation", tagArray, testTerm, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		Instructor instructorTest2 = new Instructor("The Genie", deptTest2);
		Room roomTest2 = new Room("Olin", "201");
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		//Course courseTest2 = new Course("Bio210", "Environmental Science", tags2);
		class2 = new ClassInstance("Bio210", "Environmental Science", tags2, testTerm, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);

		DepartmentHead deptHeadTest3 = new DepartmentHead("HeadMan", "BigBos", "IHaveSevereImposterSyndrome");
		Department deptTest3 = new Department(deptHeadTest3, "biology");
		Instructor instructorTest3 = new Instructor("Dr. Doctor", deptTest3);
		Room roomTest3 = new Room("Olin", "222");
		ArrayList<String> tags3 = new ArrayList<String>();
		tags3.add("E1");
		tags3.add("D");
		//Course courseTest3 = new Course("DSC", "Impacts of Analytics on Human body", tags3);
		class3 = new ClassInstance("DSC", "Impacts of Analytics on Human body", tags3, testTerm, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest3);
		
		DepartmentHead deptHeadTest4 = new DepartmentHead("TA", "ImJustAGraduateStudent", "Im drowning in work");
		Department deptTest4 = new Department(deptHeadTest4, "dlm");
		//Room roomTest4 = new Room("Olin", 211);
		ArrayList<String> tags4 = new ArrayList<String>();
		tags4.add("E1");
		tags4.add("D");
		//Course courseTest4 = new Course("DLM110b", "How to cope with college", tags4);
		class4 = new ClassInstance("DLM110b", "How to cope with college", tags4, testTerm, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest4);
	}

	
	@Test
	void testEvaluateConstraints()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class1);
		constrainedClasses.add(class2);
		
		NonOverlappingConstraint constraint1 = new NonOverlappingConstraint(testTerm, "must not be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint1);
		assertEquals("must not be offered at same time", constraint1.getConstraintName());
		
		//class 1:  TR 9:40AM - 12:10PM
		assertEquals(2, constraint1.getClasses().size());
		constrainedClasses.add(class3);
		constraint1.setClasses(constrainedClasses);
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		constrainedClasses.remove(class3);
		constraint1.setClasses(constrainedClasses);
		
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		User noPermissions = new User("", "", "");
		assertEquals(false, constraint1.evaluateConstraint(noPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:30AM - 12:20PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:50AM - 12:00PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 12:40PM - 2:10PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 8:00AM - 9:30AM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:40AM - 12:10PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:40AM - 12:20PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:40AM - 1:00PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:50AM - 12:10PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:30AM - 12:10PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:40AM - 12:10PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:40AM - 12:20PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:40AM - 1:00PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:50AM - 12:10PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:30AM - 12:10PM");
		assertEquals(false, constraint1.evaluateConstraint(allPermissions));
		


		testTerm.getAllClasses().get(1).setClassTime("MWF 9:40AM - 12:10PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:40AM - 12:20PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:40AM - 1:00PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:50AM - 12:10PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:30AM - 12:10PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:30AM - 12:20PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:50AM - 12:00PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 10:20AM - 12:40PM");
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
	}

	@Test
	void testToString()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class2);
		constrainedClasses.add(class3);
		
		NonOverlappingConstraint constraint1 = new NonOverlappingConstraint(testTerm, "must not be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint1);
		assertEquals("NonOverlappingConstraint [term=Term [semester=Spring, year=2025], constraintName=must not be offered at same time]", constraint1.toString());
		
	}
}
