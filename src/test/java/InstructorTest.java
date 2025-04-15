

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;


class InstructorTest
{
	DepartmentHead deptHead;
	Department biology;
	Instructor instructor;
	@BeforeEach
	void setUp() throws Exception
	{
		deptHead = new DepartmentHead("Grand Master of All Lords", "OhExaltedOne", "1Rul3All");
		biology = new Department(deptHead, "biology");
		instructor = new Instructor("Robin Williams", biology);
	}

	@Test
	void testInstructor()
	{
		assertEquals("Instructor [name=Robin Williams, profDept=biology, hours=0.0]", instructor.toString());
	}

//	@Test
//	void testGetHoursTerm()
//	{
//		fail("Not yet implemented");
//	}

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

}
