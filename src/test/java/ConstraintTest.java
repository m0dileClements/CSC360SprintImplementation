

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class ConstraintTest
{
	Constraint constraintTest;
	ClassInstance classInstance1;
	ClassInstance classInstance2;
	Course courseTest;
	Instructor instructorTest;
	Room roomTest;
	Department deptTest;
	DepartmentHead deptHeadTest;
	ArrayList<String> tags;
	Term termTest;
	
	Course courseTest2;
	Instructor instructorTest2;
	Room roomTest2;
	Department deptTest2;
	DepartmentHead deptHeadTest2;
	ArrayList<String> tags2;
	User u;
	

	
	@BeforeEach
	void setUp() throws Exception
	{
		//setup for Class Instance 1
		u = new Registrar("Jacob Johnson", "username", "password");
		deptHeadTest = new DepartmentHead("Grand Master of All Lords", "OhExaltedOne", "1Rul3All");
		deptTest = new Department(deptHeadTest, "biology");
		instructorTest = new Instructor("Robin Williams", deptTest);
		roomTest = new Room("Young", 112);
		tags = new ArrayList<String>();
		tags.add("E1");
		tags.add("A");
		termTest = new Term("Spring", 2025);
		courseTest = new Course("Bio110", "Intro to Biology", tags);
		classInstance1 = new ClassInstance(courseTest, termTest, instructorTest, "MWF 10:20AM - 12:40PM", roomTest, deptTest);
		termTest.addClass(classInstance1);
		
		//setup for Class Instance 2
		deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		deptTest2 = new Department(deptHeadTest, "biology");
		instructorTest2 = new Instructor("The Genie", deptTest);
		roomTest2 = new Room("Olin", 201);
		tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		courseTest2 = new Course("Bio210", "Environmental Science", tags);
		classInstance2 = new ClassInstance(courseTest2, termTest, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
		termTest.addClass(classInstance2);
		constraintTest = new NonOverlappingConstraint(termTest,"No biologies together", termTest.getAllClasses());
		
	}

	@Test
	void testConstraint()
	{
		assertEquals("NonOverlappingConstraint [term=Term [semester=Spring, year=2025], constraintName=No biologies together]", constraintTest.toString());
	}

	//TODO
	@Test
	void testModifyDescription()
	{
		User p = new User("rando", "username", "password");
		constraintTest.modifyDescription("Must be taken together", p);
		assertEquals("No biologies together", constraintTest.getConstraintName());	
		constraintTest.modifyDescription("Must be taken together", u);
		assertEquals("Must be taken together", constraintTest.getConstraintName());
	}

	@Test
	void testModifyConstraint()
	{
		Term termTest2 = new Term("Fall", 2026);
		
		//setup for Class Instance 1
		deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		deptTest = new Department(deptHeadTest, "French");
		instructorTest = new Instructor("Allison Conelly", deptTest);
		roomTest = new Room("Crounse", 307);
		tags = new ArrayList<String>();
		tags.add("E3");
		tags.add("L");
		termTest = new Term("Spring", 2025);
		courseTest = new Course("FRE270", "Group Conversation", tags);
		ClassInstance modifiedClassInstance1 = new ClassInstance(courseTest, termTest, instructorTest, "MWF 10:20AM - 12:40PM", roomTest, deptTest);
		
		//setup for Class Instance 2
		deptHeadTest2 = new DepartmentHead("Michael Bradshaw", "anonDragoLord", "IamAw3some");
		deptTest2 = new Department(deptHeadTest, "CSC");
		instructorTest2 = new Instructor("Michael Bradshaw", deptTest);
		roomTest2 = new Room("Olin", 212);
		tags2 = new ArrayList<String>();
		tags2.add("E1");
		tags2.add("G");
		termTest2 = new Term("Fall", 2025);
		courseTest2 = new Course("CSC360", "Software Design", tags2);
		ClassInstance modifiedClassInstance2 = new ClassInstance(courseTest2, termTest2, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
		
		ArrayList<ClassInstance> constrainedCourses = new ArrayList<ClassInstance>();
		constrainedCourses.add(modifiedClassInstance1);
		constrainedCourses.add(modifiedClassInstance2);
		
		Constraint modifiedConstraint = new NonOverlappingConstraint(termTest, "New Constraint", constrainedCourses);
		
		User p = new User("random", "username", "password");
		constraintTest.modifyConstraint(modifiedConstraint, p);
		assertEquals("NonOverlappingConstraint [term=Term [semester=Spring, year=2025], constraintName=No biologies together]", constraintTest.toString());
		
		
		constraintTest.modifyConstraint(modifiedConstraint, u);
		assertEquals("NonOverlappingConstraint [term=Term [semester=Spring, year=2025], constraintName=New Constraint]", constraintTest.toString());
		
	}

	@Test
	void testGetConstraintName()
	{
		assertEquals("No biologies together", constraintTest.getConstraintName());
	}

	@Test
	void testSetTerm()
	{
		assertEquals("NonOverlappingConstraint [term=Term [semester=Spring, year=2025], constraintName=No biologies together]", constraintTest.toString());
		Term newTerm = new Term("Fall", 2026);
		constraintTest.setTerm(newTerm);
		assertEquals("NonOverlappingConstraint [term=Term [semester=Fall, year=2026], constraintName=No biologies together]", constraintTest.toString());
	}
	@Test
	void testSetClasses()
	{
		String classStrings = "";
		assertEquals(2, constraintTest.getClasses().size());
		for(int i = 0; i < constraintTest.getClasses().size(); i++) {
			classStrings += constraintTest.getClasses().get(i);
		}
		assertEquals("ClassInstance [course=Bio110: Intro to Biology, Tags: E1, A, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Robin Williams, "
				+ "profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Young 112, dept=Grand Master of All Lords: biology, classLength=7.0, hasFalseLimit=false]"
				+ "ClassInstance [course=Bio210: Environmental Science, Tags: E1, A, term=Term [semester=Spring, year=2025], instructor=Instructor [name=The Genie, profDept=biology,"
				+ " hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 201, dept=Grand Master of All Lords: biology, classLength=7.0, hasFalseLimit=false]", classStrings);
		
				deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
				deptTest = new Department(deptHeadTest, "French");
				instructorTest = new Instructor("Allison Conelly", deptTest);
				roomTest = new Room("Crounse", 307);
				tags = new ArrayList<String>();
				tags.add("E3");
				tags.add("L");
				termTest = new Term("Spring", 2025);
				courseTest = new Course("FRE270", "Group Conversation", tags);
				ClassInstance modifiedClassInstance1 = new ClassInstance(courseTest, termTest, instructorTest, "MWF 10:20AM - 12:40PM", roomTest, deptTest);
				
				
				deptHeadTest2 = new DepartmentHead("Michael Bradshaw", "anonDragoLord", "IamAw3some");
				deptTest2 = new Department(deptHeadTest, "CSC");
				instructorTest2 = new Instructor("Michael Bradshaw", deptTest);
				roomTest2 = new Room("Olin", 212);
				tags2 = new ArrayList<String>();
				tags2.add("E1");
				tags2.add("G");
				courseTest2 = new Course("CSC360", "Software Design", tags2);
				ClassInstance modifiedClassInstance2 = new ClassInstance(courseTest2, termTest, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
				
				ArrayList<ClassInstance> constrainedCourses = new ArrayList<ClassInstance>();
				constrainedCourses.add(modifiedClassInstance1);
				constrainedCourses.add(modifiedClassInstance2);
		
		
		
		
		constraintTest.setClasses(constrainedCourses);
		classStrings = "";
		assertEquals(2, constraintTest.getClasses().size());
		for(int i = 0; i < constraintTest.getClasses().size(); i++) {
			classStrings += constraintTest.getClasses().get(i);
		}
		assertEquals("ClassInstance [course=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor "
				+ "[name=Allison Conelly, profDept=French, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Crounse 307, dept=Alison Conelly: French, "
				+ "classLength=7.0, hasFalseLimit=false]ClassInstance [course=CSC360: Software Design, Tags: E1, G, term=Term [semester=Spring, year=2025], "
				+ "instructor=Instructor [name=Michael Bradshaw, profDept=French, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 212, dept=Alison Conelly: "
				+ "CSC, classLength=7.0, hasFalseLimit=false]", classStrings);
	}
	
	@Test
	void testSetConstraintName()
	{
		assertEquals("No biologies together", constraintTest.getConstraintName());
		constraintTest.setConstraintName("changed name");
		assertEquals("changed name", constraintTest.getConstraintName());
	}
	
}
