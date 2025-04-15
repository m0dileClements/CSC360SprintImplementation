

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class TermTest
{
	Term testTerm;
	ArrayList<ClassInstance> allTestClasses;
	Boolean isFinalized;
	ClassInstance class1;
	ClassInstance class2;
	ClassInstance class3;
	ClassInstance class4;
	
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
		
		DepartmentHead deptHeadTest4 = new DepartmentHead("TA", "ImJustAGraduateStudent", "Im drowning in work");
		Department deptTest4 = new Department(deptHeadTest4, "dlm");
		//Room roomTest4 = new Room("Olin", 211);
		ArrayList<String> tags4 = new ArrayList<String>();
		tags4.add("E1");
		tags4.add("D");
		Course courseTest4 = new Course("DLM110b", "How to cope with college", tags4);
		class4 = new ClassInstance(courseTest4, testTerm, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest4);
	}

	@Test
	void testTerm()
	{
		assertEquals("Term [semester=Spring, year=2025]", testTerm.toString());
	}


	@Test
	void testAddClass()
	{
		testTerm.addClass(class1);
		assertEquals(1, testTerm.getAllClasses().size());
	}

	@Test
	void testRemoveClassfromTerm()
	{
		testTerm.addClass(class1);
		assertEquals(1, testTerm.getAllClasses().size());
		testTerm.removeClassfromTerm(class1);
		assertEquals(0, testTerm.getAllClasses().size());
	}

	@Test
	void testGetClassesByTime()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		

		testTerm.addClass(class3);
		ArrayList<ClassInstance> sameTimedClasses = new ArrayList<ClassInstance>();
		sameTimedClasses = testTerm.getClassesByTime("TR 9:40AM - 12:10PM");
		assertEquals(1, sameTimedClasses.size());
		assertEquals("ClassInstance [course=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", sameTimedClasses.get(0).toString());
		
		sameTimedClasses = testTerm.getClassesByTime("MWF 10:20AM - 12:40PM");
		assertEquals(2, sameTimedClasses.size());
	}
	
	@Test
	void testGetClassesByDept()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		
		ArrayList<ClassInstance> sameDeptClasses = new ArrayList<ClassInstance>();
		sameDeptClasses = testTerm.getClassesByDept("French");
		assertEquals(1, sameDeptClasses.size());
		assertEquals("ClassInstance [course=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", sameDeptClasses.get(0).toString());
		
		
		sameDeptClasses = testTerm.getClassesByDept("biology");
		assertEquals(2, sameDeptClasses.size());
		assertEquals("biology", sameDeptClasses.get(0).getDept().getDeptName());
		assertEquals("biology", sameDeptClasses.get(1).getDept().getDeptName());

	}

	@Test
	void testGetInstrutorConflicts()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		testTerm.addClass(class4);
		
		ArrayList<ClassInstance> instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(2, instructorConflicts.size());
		assertEquals("ClassInstance [course=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", instructorConflicts.get(0).toString());
		assertEquals("ClassInstance [course=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", instructorConflicts.get(1).toString());
		
		//MWF 10:20AM - 12:40PM
		testTerm.getAllClasses().get(2).setClassTime("TR 10:20AM - 12:40PM");
		instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(0, instructorConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:20AM - 12:20PM");
		instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(2, instructorConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:40AM - 12:40PM");
		instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(2, instructorConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:40AM - 12:20PM");
		instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(2, instructorConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:00AM - 1:20PM");
		instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(2, instructorConflicts.size());
	}
	
	@Test
	void testGetTimeRoomConflicts()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		testTerm.addClass(class4);
		
		ArrayList<ClassInstance> timeRoomConflicts = testTerm.getTimeRoomConflicts();
		
		assertEquals(2, timeRoomConflicts.size());
		assertEquals("ClassInstance [course=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", timeRoomConflicts.get(0).toString());
		assertEquals("ClassInstance [course=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", timeRoomConflicts.get(1).toString());
		
		testTerm.getAllClasses().get(2).setClassTime("TR 10:20AM - 12:40PM");
		timeRoomConflicts = testTerm.getTimeRoomConflicts();
		assertEquals(0, timeRoomConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:20AM - 12:20PM");
		timeRoomConflicts = testTerm.getTimeRoomConflicts();
		assertEquals(2, timeRoomConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:40AM - 12:40PM");
		timeRoomConflicts = testTerm.getTimeRoomConflicts();
		assertEquals(2, timeRoomConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:40AM - 12:20PM");
		timeRoomConflicts = testTerm.getTimeRoomConflicts();
		assertEquals(2, timeRoomConflicts.size());
		
		testTerm.getAllClasses().get(2).setClassTime("MWF 10:00AM - 1:20PM");
		timeRoomConflicts = testTerm.getTimeRoomConflicts();
		assertEquals(2, timeRoomConflicts.size());
		
		
	}

	@Test
	void testCheckClassConstraints()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		testTerm.addClass(class4);
		class3.getCourse().createCourseConstraint("This is a test Constraint", class3, class4);
		
		assertEquals(true, testTerm.checkClassConstraints());
		
		testTerm.getAllClasses().get(2).setClassTime("TR 10:20AM - 12:40PM");
		assertEquals(false, testTerm.checkClassConstraints());
	}

	@Test
	void testCheckIsTimeBefore()
	{
		assertEquals(true, testTerm.checkIsTimeBefore("10:30AM", "10:40AM"));
		assertEquals(true, testTerm.checkIsTimeBefore("10:30AM", "11:30AM"));
		assertEquals(true, testTerm.checkIsTimeBefore("10:30PM", "10:40PM"));
		assertEquals(true, testTerm.checkIsTimeBefore("10:30PM", "11:30PM"));
		
		assertEquals(false, testTerm.checkIsTimeBefore("10:30PM", "10:30PM"));
		assertEquals(false, testTerm.checkIsTimeBefore("10:30PM", "11:30AM"));
		assertEquals(false, testTerm.checkIsTimeBefore("10:30PM", "10:20PM"));
		assertEquals(false, testTerm.checkIsTimeBefore("10:30AM", "10:20AM"));
		
		assertEquals(true, testTerm.checkIsTimeBefore("11:30AM", "12:30PM"));
		assertEquals(false, testTerm.checkIsTimeBefore("11:30PM", "12:30AM"));
		}
	
	@Test
	void testCheckCorrectness()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		testTerm.addClass(class4);
		assertEquals(false, testTerm.checkCorrectness());
		
		testTerm.getAllClasses().get(2).setClassTime("TR 10:20AM - 12:40PM");
		assertEquals(true, testTerm.checkCorrectness());
	}

	@Test
	void testgetAllTermInstructors()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		testTerm.addClass(class4);
		ArrayList<Instructor> instructors = new ArrayList<Instructor>();
		instructors = testTerm.getAllTermInstructors();
		
		
		assertEquals(3, instructors.size());
		assertEquals("Instructor [name=Allison Conelly, profDept=French, hours=0.0]", instructors.get(0).toString());
		assertEquals("Instructor [name=The Genie, profDept=biology, hours=0.0]", instructors.get(1).toString());
		assertEquals("Instructor [name=Dr. Doctor, profDept=biology, hours=0.0]", instructors.get(2).toString());
	}
	
	@Test
	void testGetInstructorCourses()
	{
		testTerm.addClass(class3);
		testTerm.addClass(class4);
		
		Instructor instructor = testTerm.getAllClasses().get(0).getInstructor();
		
		ArrayList<ClassInstance> instructorCourses = testTerm.getInstructorCourses(instructor);
		assertEquals(2, instructorCourses.size());
		assertEquals("ClassInstance [course=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", instructorCourses.get(0).toString());
		assertEquals("ClassInstance [course=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", instructorCourses.get(1).toString());
	}

	@Test
	void testHasOverlappingDays()
	{
		assertEquals(true, testTerm.hasOverlappingDays("TR 9:40AM - 12:10PM", "R 9:40AM - 12:10PM"));
		assertEquals(true, testTerm.hasOverlappingDays("TR 9:40AM - 12:10PM", "T 9:40AM - 12:10PM"));
		assertEquals(true, testTerm.hasOverlappingDays("TR 9:40AM - 12:10PM", "TR 9:40AM - 12:10PM"));
		assertEquals(false, testTerm.hasOverlappingDays("MWF 9:40AM - 12:10PM", "TR 9:40AM - 12:10PM"));
		assertEquals(true, testTerm.hasOverlappingDays("MWF 9:40AM - 12:10PM", "MWF 9:40AM - 12:10PM"));
	}
	
	@Test
	void testSetAllClasses()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		testTerm.addClass(class3);
		assertEquals(3, testTerm.getAllClasses().size());
		
		ArrayList<ClassInstance> testList = new ArrayList<ClassInstance>();
		
		testList.add(class3);
		testList.add(class4);
		
		testTerm.setAllClasses(testList);
		assertEquals(2, testTerm.getAllClasses().size());
		assertEquals("ClassInstance [course=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", testTerm.getAllClasses().get(0).toString());
		assertEquals("ClassInstance [course=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", testTerm.getAllClasses().get(1).toString());
	}
	
	@Test
	void testMarkAsFinal()
	{
		assertEquals(false, testTerm.getIsFinalized());
		testTerm.markAsFinal();
		assertEquals(true, testTerm.getIsFinalized());

	}

	@Test
	void testGetSemester()
	{
		assertEquals("Spring", testTerm.getSemester());
	}

	@Test
	void testSetSemester()
	{
		assertEquals("Spring", testTerm.getSemester());
		testTerm.setSemester("Fall");
		assertEquals("Fall", testTerm.getSemester());
	}

	@Test
	void testGetYear()
	{
		assertEquals(2025, testTerm.getYear());
	}

	@Test
	void testSetYear()
	{
		assertEquals(2025, testTerm.getYear());
		testTerm.setYear(2032);
		assertEquals(2032, testTerm.getYear());
	}
	
	@Test
	void testGetAllClasses()
	{
		testTerm.addClass(class1);
		testTerm.addClass(class2);
		assertEquals(2, testTerm.getAllClasses().size());
		assertEquals("ClassInstance [course=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", testTerm.getAllClasses().get(0).toString());
		assertEquals("ClassInstance [course=Bio210: Environmental Science, Tags: E2, S, term=Term [semester=Spring, year=2025], instructor=Instructor [name=The Genie, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 201, dept=HeadWoman: biology, classLength=7.0, hasFalseLimit=false]", testTerm.getAllClasses().get(1).toString());
	}

	@Test
	void testGetIsFinalized()
	{
		assertEquals(false, testTerm.getIsFinalized());
	}
	
	@Test
	void testSetIsFinalized()
	{
		
		testTerm.setIsFinalized(true);
		assertEquals(true, testTerm.getIsFinalized());
		testTerm.setIsFinalized(false);
		assertEquals(false, testTerm.getIsFinalized());
	}


}