

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class ClassInstanceTest
{
	Course testCourse;
	Department dept;
	ArrayList<String> tags;
	ArrayList<String> crossListings;
	ClassInstance classInstance1;
	Department deptTest;
	Term termTest; 
	
	@BeforeEach
	void setUp() throws Exception
	{
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		deptTest = new Department(deptHeadTest, "French");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Crounse", 307);
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		termTest = new Term("Spring", 2025);
		Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		classInstance1 = new ClassInstance(courseTest, termTest, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
	}

	@Test
	void testSetRoom()
	{
		assertEquals("Crounse 307", classInstance1.getRoom().toString());
		classInstance1.setRoom("Olin 222");
		assertEquals("Olin 222", classInstance1.getRoom().toString());

	}

	@Test
	void testSetTime()
	{
		assertEquals("TR 9:40AM - 12:10PM", classInstance1.getClassTime());
		assertEquals("9:40AM", classInstance1.getStartTime());
		assertEquals("12:10PM", classInstance1.getEndTime());
		classInstance1.setClassTime("MWF 9:10 - 10:10AM");
		assertEquals("MWF 9:10 - 10:10AM", classInstance1.getClassTime());
		assertEquals("9:10AM", classInstance1.getStartTime());
		assertEquals("10:10AM", classInstance1.getEndTime());
	}

	@Test
	void testGenerateClassTimeLength()
	{
		assertEquals(5.0, classInstance1.getClassLength());
		assertEquals("9:40AM", classInstance1.getStartTime());
		assertEquals("12:10PM", classInstance1.getEndTime());
		
		classInstance1.setClassTime("MWF 9:10 - 10:10AM");
		assertEquals(3.0, classInstance1.getClassLength());
		assertEquals("9:10AM", classInstance1.getStartTime());
		assertEquals("10:10AM", classInstance1.getEndTime());
		
		classInstance1.setClassTime("MWF 11:10AM - 12:10PM");
		assertEquals(3.0, classInstance1.getClassLength());
		assertEquals("11:10AM", classInstance1.getStartTime());
		assertEquals("12:10PM", classInstance1.getEndTime());
		
		classInstance1.setClassTime("MWF 1:15 - 2:15PM");
		assertEquals(3.0, classInstance1.getClassLength());
		assertEquals("1:15PM", classInstance1.getStartTime());
		assertEquals("2:15PM", classInstance1.getEndTime());
		
		classInstance1.setClassTime("MWF 1:15PM - 2:15PM");
		assertEquals(3.0, classInstance1.getClassLength());
		assertEquals("1:15PM", classInstance1.getStartTime());
		assertEquals("2:15PM", classInstance1.getEndTime());
	}

	@Test
	void testGetDeepCopy()
	{
		ClassInstance class2 = classInstance1.getDeepCopy();
		
		assertEquals(true, class2.getCourse().toString().equals(classInstance1.getCourse().toString()));
		assertEquals(true, class2.getTerm().toString().equals(classInstance1.getTerm().toString()));
		assertEquals(true, class2.getInstructor().toString().equals(classInstance1.getInstructor().toString()));
		assertEquals(true, class2.getClassTime().toString().equals(classInstance1.getClassTime().toString()));
		assertEquals(true, class2.getStartTime().toString().equals(classInstance1.getStartTime().toString()));
		assertEquals(true, class2.getEndTime().toString().equals(classInstance1.getEndTime().toString()));
		assertEquals(true, class2.getRoom().toString().equals(classInstance1.getRoom().toString()));
		assertEquals(true, class2.getDept().toString().equals(classInstance1.getDept().toString()));
		assertEquals(true, class2.getClassLength() == classInstance1.getClassLength());
		assertEquals(true, class2.getHasFalseLimit() == classInstance1.getHasFalseLimit());	
	}

	@Test
	void testGetCourse()
	{	
		assertEquals("FRE270: Group Conversation, Tags: E3, L", classInstance1.getCourse().toString());
	}

	@Test
	void testSetCourse()
	{
		assertEquals("FRE270: Group Conversation, Tags: E3, L", classInstance1.getCourse().toString());
		ArrayList<String> tagList = new ArrayList<String>();
		
		tagList.add("P2");
		tagList.add("S");
		Course newCourse = new Course("FR271", "Group Conversations in Context", tagList);
		classInstance1.setCourse(newCourse);
		assertEquals("FR271: Group Conversations in Context, Tags: P2, S", classInstance1.getCourse().toString());
		
	}

	@Test
	void testGetTerm()
	{
		assertEquals("Term [semester=Spring, year=2025]", classInstance1.getTerm().toString());
	}

	@Test
	void testSetTerm()
	{
		assertEquals("Term [semester=Spring, year=2025]", classInstance1.getTerm().toString());
		Term modifiedTerm = new Term("Fall", 2032);
		
		classInstance1.setTerm(modifiedTerm);
		assertEquals("Term [semester=Fall, year=2032]", classInstance1.getTerm().toString());

	}

	
	//TODO make sure eventually this gets fixed so hours get updated accordingly
	@Test
	void testGetInstructor()
	{
		classInstance1.getInstructor().getTermHours(termTest);
		assertEquals("Instructor [name=Allison Conelly, profDept=French, hours=0.0]", classInstance1.getInstructor().toString());
	}

	@Test
	void testSetInstructor()
	{
		classInstance1.getInstructor().getTermHours(termTest);
		assertEquals("Instructor [name=Allison Conelly, profDept=French, hours=0.0]", classInstance1.getInstructor().toString());
		
		Instructor newProf = new Instructor("Christian Wood", deptTest);
		classInstance1.setInstructor(newProf);
		classInstance1.getInstructor().getTermHours(termTest);
		assertEquals("Instructor [name=Christian Wood, profDept=French, hours=0.0]", classInstance1.getInstructor().toString());
		
	}

	
	//this stays here as a fail safe, but is extensively tested in the generation test case
	@Test
	void testGetStartTime()
	{
		assertEquals("9:40AM", classInstance1.getStartTime());
	}


	@Test
	void testGetEndTime()
	{
		assertEquals("12:10PM", classInstance1.getEndTime());
	}

	@Test
	void testGetClassLength()
	{
		assertEquals(5.0, classInstance1.getClassLength());
	}

	@Test
	void testGetRoom()
	{
		assertEquals("Crounse 307", classInstance1.getRoom().toString());
	}


	@Test
	void testGetDept()
	{
		assertEquals("Alison Conelly: French", classInstance1.getDept().toString());	}

	@Test
	void testSetDept()
	{
		DepartmentHead newDeptHead = new DepartmentHead("John Smith", "mostBoringName", "iluvCandles");
		Department newDept = new Department(newDeptHead, "Medicine");
		
		assertEquals("Alison Conelly: French", classInstance1.getDept().toString());
		classInstance1.setDept(newDept);
		assertEquals("John Smith: Medicine", classInstance1.getDept().toString());
	}

	@Test
	void testGetHasFalseLimit()
	{
		classInstance1.activateFalseLimit();
		assertEquals(true, classInstance1.getHasFalseLimit());
		classInstance1.resetFalseLimit();
		assertEquals(false, classInstance1.getHasFalseLimit());
	}

	@Test
	void testActivateFalseLimit()
	{
		classInstance1.activateFalseLimit();
		assertEquals(true, classInstance1.getHasFalseLimit());	}

	@Test
	void testResetFalseLimits()
	{
		classInstance1.activateFalseLimit();
		assertEquals(true, classInstance1.getHasFalseLimit());
		classInstance1.resetFalseLimit();
		assertEquals(false, classInstance1.getHasFalseLimit());
	}
	
	@Test 
	void testToString(){
		assertEquals("ClassInstance [course=FRE270: Group Conversation, Tags: E3, L, "
				+ "term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], "
				+ "classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", classInstance1.toString());
		
		
	
	}

}
