

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
	Term termTest2;
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
	
		
		//setup for Class Instance 2
		deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		deptTest2 = new Department(deptHeadTest, "biology");
		instructorTest2 = new Instructor("The Genie", deptTest);
		roomTest2 = new Room("Olin", 201);
		tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		termTest2 = new Term("Fall", 2025);
		courseTest2 = new Course("Bio210", "Environmental Science", tags);
		classInstance2 = new ClassInstance(courseTest2, termTest2, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
		
		constraintTest = new Constraint("No biologies together",classInstance1, classInstance2);
		
	}

	@Test
	void testConstraint()
	{
		assertEquals("Constraint [constraintName=No biologies together, class1=Intro to Biology, class2=Environmental Science]", constraintTest.toString());
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
		
		
		Constraint modifiedConstraint = new Constraint("New Constraint", modifiedClassInstance1, modifiedClassInstance2);
		
		User p = new User("random", "username", "password");
		constraintTest.modifyConstraint(modifiedConstraint, p);
		assertEquals("Constraint [constraintName=No biologies together, class1=Intro to Biology, class2=Environmental Science]", constraintTest.toString());
		
		
		constraintTest.modifyConstraint(modifiedConstraint, u);
		assertEquals("Constraint [constraintName=New Constraint, class1=Group Conversation, class2=Software Design]", constraintTest.toString());
		
	}

	@Test
	void testGetConstraintName()
	{
		assertEquals("No biologies together", constraintTest.getConstraintName());
	}

	@Test
	void testSetConstraintName()
	{
		
	}

}
