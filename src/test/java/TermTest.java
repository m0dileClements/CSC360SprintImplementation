

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import source.*;

class TermTest
{
	Term testTerm;
	ArrayList<ClassInstance> allTestClasses;
	Boolean isFinalized;
	ClassInstance class1;
	ClassInstance class2;
	ClassInstance class3;
	ClassInstance class4;
	Registrar allPermissions;
	Term finalTerm;
	
	@BeforeEach
	void setUp() throws Exception
	{
		allPermissions = new Registrar("", "", "");
		finalTerm = new Term("FinalizedTest", 2032);
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "French");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Crounse",  "307");
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		testTerm = new Term("Spring", 2025);
		//Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		class1 = new ClassInstance("FRE270", "Group Conversation", tagArray, testTerm, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		Instructor instructorTest2 = new Instructor("The Genie", deptTest2);
		Room roomTest2 = new Room("Olin",  "201");
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
	void testTerm()
	{
		assertEquals("Term [semester=Spring, year=2025]", testTerm.toString());
	}


	@Test
	void testAddClass()
	{
		User noPermissions = new User("", "", "");
		testTerm.addClass(noPermissions, class1);
		assertEquals(0, testTerm.getAllClasses().size());
		
		finalTerm.addClass(noPermissions, class1);
		assertEquals(0, testTerm.getAllClasses().size());
		
		finalTerm.addClass(allPermissions, class1);
		assertEquals(0, testTerm.getAllClasses().size());
		
		testTerm.addClass(allPermissions, class1);
		assertEquals(1, testTerm.getAllClasses().size());
	}

	@Test
	void testRemoveClassfromTerm()
	{
		testTerm.addClass(allPermissions, class1);
		assertEquals(1, testTerm.getAllClasses().size());
		
		User noPermissions = new User("", "", "");
		testTerm.removeClassfromTerm(noPermissions, class1);
		assertEquals(1, testTerm.getAllClasses().size());
		
		testTerm.removeClassfromTerm(allPermissions, class1);
		assertEquals(0, testTerm.getAllClasses().size());
	}

	@Test
	void testGetClassesByTime()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		

		testTerm.addClass(allPermissions, class3);
		ArrayList<ClassInstance> sameTimedClasses = new ArrayList<ClassInstance>();
		sameTimedClasses = testTerm.getClassesByTime("TR 9:40AM - 12:10PM");
		assertEquals(1, sameTimedClasses.size());
		assertEquals("ClassInstance [courseCode=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", sameTimedClasses.get(0).toString());
		
		sameTimedClasses = testTerm.getClassesByTime("MWF 10:20AM - 12:40PM");
		assertEquals(2, sameTimedClasses.size());
	}
	
	@Test
	void testGetClassesByDept()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		
		ArrayList<ClassInstance> sameDeptClasses = new ArrayList<ClassInstance>();
		sameDeptClasses = testTerm.getClassesByDept("French");
		assertEquals(1, sameDeptClasses.size());
		assertEquals("ClassInstance [courseCode=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", sameDeptClasses.get(0).toString());
		
		
		sameDeptClasses = testTerm.getClassesByDept("biology");
		assertEquals(2, sameDeptClasses.size());
		assertEquals("biology", sameDeptClasses.get(0).getDept().getDeptName());
		assertEquals("biology", sameDeptClasses.get(1).getDept().getDeptName());

	}

	@Test
	void testGetInstrutorConflicts()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		testTerm.addClass(allPermissions, class4);
		
		ArrayList<ClassInstance> instructorConflicts = testTerm.getInstructorConflicts();
		assertEquals(2, instructorConflicts.size());
		assertEquals("ClassInstance [courseCode=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", instructorConflicts.get(0).toString());
		assertEquals("ClassInstance [courseCode=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", instructorConflicts.get(1).toString());
		
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
		//assertEquals(2, instructorConflicts.size());
	}
	
	@Test
	void testGetTimeRoomConflicts()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		testTerm.addClass(allPermissions, class4);
		
		ArrayList<ClassInstance> timeRoomConflicts = testTerm.getTimeRoomConflicts();
		
		assertEquals(2, timeRoomConflicts.size());
		assertEquals("ClassInstance [courseCode=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", timeRoomConflicts.get(0).toString());
		assertEquals("ClassInstance [courseCode=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", timeRoomConflicts.get(1).toString());
		
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
	void testCheckTimeConflict()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		
		//class 1:  TR 9:40AM - 12:10PM
		testTerm.getAllClasses().get(0).setClassTime("TR 9:40AM - 12:10PM");
		
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:30AM - 12:20PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:50AM - 12:00PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 12:40PM - 2:10PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 8:00AM - 9:30AM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:40AM - 12:10PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:40AM - 12:20PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:40AM - 1:00PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:50AM - 12:10PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("TR 9:30AM - 12:10PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:40AM - 12:10PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:40AM - 12:20PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:40AM - 1:00PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:50AM - 12:10PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("T 9:30AM - 12:10PM");
		assertEquals(true, testTerm.checkTimeConflict(class1, class2));
		


		testTerm.getAllClasses().get(1).setClassTime("MWF 9:40AM - 12:10PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:40AM - 12:20PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:40AM - 1:00PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:50AM - 12:10PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:30AM - 12:10PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:30AM - 12:20PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
		testTerm.getAllClasses().get(1).setClassTime("MWF 9:50AM - 12:00PM");
		assertEquals(false, testTerm.checkTimeConflict(class1, class2));
		
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
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		testTerm.addClass(allPermissions, class4); 
		assertEquals(false, testTerm.checkCorrectness(allPermissions));
		User noPermissions = new User("", "", "");
		assertEquals(false, testTerm.checkCorrectness(noPermissions));
		
		testTerm.getAllClasses().get(2).setClassTime("TR 10:20AM - 12:40PM");
		assertEquals(true, testTerm.checkCorrectness(allPermissions));
		
		
		
		
	}

	@Test
	void testgetAllTermInstructors()
	{
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		testTerm.addClass(allPermissions, class4);
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
		testTerm.addClass(allPermissions, class3);
		testTerm.addClass(allPermissions, class4);
		
		Instructor instructor = testTerm.getAllClasses().get(0).getInstructor();
		
		ArrayList<ClassInstance> instructorCourses = testTerm.getInstructorCourses(instructor);
		assertEquals(2, instructorCourses.size());
		assertEquals("ClassInstance [courseCode=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", instructorCourses.get(0).toString());
		assertEquals("ClassInstance [courseCode=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", instructorCourses.get(1).toString());
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
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		testTerm.addClass(allPermissions, class3);
		assertEquals(3, testTerm.getAllClasses().size());
		
		ArrayList<ClassInstance> testList = new ArrayList<ClassInstance>();
		
		testList.add(class3);
		testList.add(class4);
		
		testTerm.setAllClasses(testList);
		assertEquals(2, testTerm.getAllClasses().size());
		assertEquals("ClassInstance [courseCode=DSC: Impacts of Analytics on Human body, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=HeadMan: biology, classLength=7.0, hasFalseLimit=false]", testTerm.getAllClasses().get(0).toString());
		assertEquals("ClassInstance [courseCode=DLM110b: How to cope with college, Tags: E1, D, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Dr. Doctor, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 222, dept=TA: dlm, classLength=7.0, hasFalseLimit=false]", testTerm.getAllClasses().get(1).toString());
		
	}
	
	@Test
	void testMarkAsFinal()
	{
		assertEquals(false, testTerm.getIsFinalized());
		User noPermissions = new User("", "", "");
		testTerm.markAsFinal(noPermissions);
		assertEquals(false, testTerm.getIsFinalized());
		testTerm.markAsFinal(allPermissions);
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
		testTerm.addClass(allPermissions, class1);
		testTerm.addClass(allPermissions, class2);
		assertEquals(2, testTerm.getAllClasses().size());
		assertEquals("ClassInstance [courseCode=FRE270: Group Conversation, Tags: E3, L, term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", testTerm.getAllClasses().get(0).toString());
		assertEquals("ClassInstance [courseCode=Bio210: Environmental Science, Tags: E2, S, term=Term [semester=Spring, year=2025], instructor=Instructor [name=The Genie, profDept=biology, hours=0.0], classTime=MWF 10:20AM - 12:40PM, room=Olin 201, dept=HeadWoman: biology, classLength=7.0, hasFalseLimit=false]", testTerm.getAllClasses().get(1).toString());
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
	
	
	
	
	@Test
	void testGetConstraints()
	{
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class2);
		constrainedClasses.add(class3);
		NonOverlappingConstraint constraint1 = new NonOverlappingConstraint(testTerm, "must not be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint1);
		
		MustOverlapConstraint constraint2 = new MustOverlapConstraint(testTerm, "must be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint2);
		
		MustBeOfferedConstraint constraint3 = new MustBeOfferedConstraint(testTerm, "must be offered", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint3);
	
		assertEquals(3, testTerm.getConstraints().size());
		
		String constraintTester = "";
		for(int i = 0; i < testTerm.getConstraints().size(); i++) {
			constraintTester += testTerm.getConstraints().get(i);
		}
		
		assertEquals("NonOverlappingConstraint [term=Term [semester=Spring, year=2025], constraintName=must not be offered at same time]"
				+ "MustOverlapConstraint [term=Term [semester=Spring, year=2025], constraintName=must be offered at same time]"+
				"MustBeOfferedConstraint [term=Term [semester=Spring, year=2025], constraintName=must be offered]", constraintTester);
		
	}
	
	@Test
	void testRemoveConstraint()
	{
		User noPermissions = new User("", "", "");
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class2);
		constrainedClasses.add(class3);
		NonOverlappingConstraint constraint1 = new NonOverlappingConstraint(testTerm, "must not be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint1);
		
		MustOverlapConstraint constraint2 = new MustOverlapConstraint(testTerm, "must be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint2);
				
		MustBeOfferedConstraint constraint3 = new MustBeOfferedConstraint(testTerm, "must be offered", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint3);

		assertEquals(3, testTerm.getConstraints().size());
		
		testTerm.removeConstraint(noPermissions, testTerm.getConstraints().get(0));
		assertEquals(3, testTerm.getConstraints().size());

		testTerm.removeConstraint(allPermissions, testTerm.getConstraints().get(0));
		assertEquals(2, testTerm.getConstraints().size());
		
	}
	
	@Test
	void testAddConstraint()
	{
		User noPermissions = new User("", "", "");
		
		assertEquals(0, testTerm.getConstraints().size());
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class2);
		constrainedClasses.add(class3);
		NonOverlappingConstraint constraint1 = new NonOverlappingConstraint(testTerm, "must not be offered at same time", constrainedClasses);
		
		testTerm.addConstraint(noPermissions, constraint1);
		assertEquals(0, testTerm.getConstraints().size());
		
		testTerm.addConstraint(allPermissions, constraint1);
		assertEquals(1, testTerm.getConstraints().size());
		
	}
	
	@Test
	void testCheckClassConstraints()
	{
		ArrayList<ClassInstance> constrainedClasses = new ArrayList<ClassInstance>();
		constrainedClasses.add(class1);
		constrainedClasses.add(class2);
		
		NonOverlappingConstraint constraint1 = new NonOverlappingConstraint(testTerm, "must not be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint1);
		assertEquals("TR 9:40AM - 12:10PM", class1.getClassTime());
		assertEquals("MWF 10:20AM - 12:40PM", class2.getClassTime());
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		constrainedClasses.remove(class1);
		constrainedClasses.add(class3);
		MustOverlapConstraint constraint2 = new MustOverlapConstraint(testTerm, "must be offered at same time", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint2);
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
		
		
		
		MustBeOfferedConstraint constraint3 = new MustBeOfferedConstraint(testTerm, "must be offered", constrainedClasses);
		testTerm.addConstraint(allPermissions, constraint3);
		assertEquals(true, constraint1.evaluateConstraint(allPermissions));
	
		assertEquals(3, testTerm.getConstraints().size());
		
		assertEquals(true, testTerm.checkClassConstraints(allPermissions));
		
	}


}