

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;


class InstructorTest
{
	DepartmentHead deptHead;
	Department biology;
	Instructor instructor;
	Term testTerm;
	ClassInstance class1;
	ClassInstance class2;
	Registrar createPermissions;
	
	@BeforeEach
	void setUp() throws Exception
	{
		createPermissions = new Registrar("", "", "");
		deptHead = new DepartmentHead("Grand Master of All Lords", "OhExaltedOne", "1Rul3All");
		biology = new Department(deptHead, "biology");
		instructor = new Instructor("Robin Williams", biology);
		
		
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "French");
		Room roomTest = new Room("Crounse", 307);
		ArrayList<String> tagArray = new ArrayList<String>();
		tagArray.add("E3");
		tagArray.add("L");
		testTerm = new Term("Spring", 2025);
		Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		class1 = new ClassInstance(courseTest, testTerm, instructor, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		Room roomTest2 = new Room("Olin", 201);
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		Course courseTest2 = new Course("Bio210", "Environmental Science", tags2);
		class2 = new ClassInstance(courseTest2, testTerm, instructor, "MWF 10:20AM - 12:20PM", roomTest2, deptTest2);
		
		testTerm.addClass(createPermissions, class1);
		testTerm.addClass(createPermissions, class2);
	}

	@Test
	void testInstructor()
	{
		assertEquals("Instructor [name=Robin Williams, profDept=biology, hours=0.0]", instructor.toString());
	}

	@Test
	void testGetName()
	{
		assertEquals("Robin Williams", instructor.getName());
	}

	@Test
	void testSetName()
	{
		instructor.setName("Brad Pitt");
		assertEquals("Brad Pitt", instructor.getName());
	}

	@Test
	void testGetProfDept()
	{
		assertEquals("Grand Master of All Lords: biology", instructor.getProfDept().toString());
		
	}

	@Test
	void testSetProfDept()
	{
		DepartmentHead newDeptHead = new DepartmentHead("Minor Lord", "OhMinorOne", "1Rul3All");
		Department newDept = new Department(newDeptHead, "Computer Science");
		instructor.setProfDept(newDept);
		assertEquals("Minor Lord: Computer Science", instructor.getProfDept().toString());

	}

	@Test
	void testGetHours()
	{
		assertEquals(0, instructor.getHours());
	}

	@Test
	void testSetHours()
	{
		instructor.setHours(12.0);
		assertEquals(12.0, instructor.getHours());
	}
	
	@Test
	void testGetTermHours()
	{
		double instructorHours = instructor.getTermHours(testTerm);
		assertEquals(11.0, instructorHours);
		
		DepartmentHead deptHeadTest3 = new DepartmentHead("HeadMan", "BigBos", "IHaveSevereImposterSyndrome");
		Department deptTest3 = new Department(deptHeadTest3, "biology");
		Instructor instructorTest3 = new Instructor("Dr. Doctor", deptTest3);
		Room roomTest3 = new Room("Olin", 222);
		ArrayList<String> tags3 = new ArrayList<String>();
		tags3.add("E1");
		tags3.add("D");
		Course courseTest3 = new Course("DSC", "Impacts of Analytics on Human body", tags3);
		ClassInstance class3 = new ClassInstance(courseTest3, testTerm, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest3);
		
		testTerm.addClass(createPermissions, class3);
		
		instructorHours = instructor.getTermHours(testTerm);
		assertEquals(11.0, instructorHours);
		
		User noPermissions = new User("", "", "");
		testTerm.addClass(noPermissions, class3);
		
		instructorHours = instructor.getTermHours(testTerm);
		assertEquals(11.0, instructorHours);
		
		
		
	}

}
