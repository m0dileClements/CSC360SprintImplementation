

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class MainTest
{
	Main main;
	Term testTerm;
	Term testTerm2;
	Term testTerm3;
	ArrayList<ClassInstance> allTestClasses;
	Boolean isFinalized;
	ClassInstance class1;
	ClassInstance class2;
	ClassInstance class3;
	ClassInstance class4;
	User currentUser;
	
	@BeforeEach
	void setUp() throws Exception
	{	
		main = new Main();
		currentUser = new Registrar("Jacob Johnson", " ", " ");
		main.setCurrentUser(currentUser);
		DepartmentHead userDeptHead = new DepartmentHead("Michael Bradshaw", "CSDeptHead", "dragonsAreCool");
		Department userHolder = new Department(userDeptHead, "CSC");
		userDeptHead.setDepartment(userHolder);
		
		
		
		
		main = new Main();
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "French");
		main.addDept(deptTest);
		
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		main.addInstructor(instructorTest);
		
		Room roomTest = new Room("Crounse", 307);
		main.addRoom(roomTest);
		
		ArrayList<String> tagArray = new ArrayList<String>();
		tagArray.add("E3");
		tagArray.add("L");
		
		testTerm = new Term("Spring", 2025);
		main.addTerm(testTerm);
		
		Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		main.addCourse(courseTest);
		
		class1 = new ClassInstance(courseTest, testTerm, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		testTerm.addClass(class1);
		main.addClassInstance(class1);
		
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		main.addDept(deptTest2);
		
		Instructor instructorTest2 = new Instructor("The Genie", deptTest2);
		main.addInstructor(instructorTest2);
		
		Room roomTest2 = new Room("Olin", 201);
		main.addRoom(roomTest2);
		
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		
		Course courseTest2 = new Course("Bio210", "Environmental Science", tags2);
		main.addCourse(courseTest2);
		
		class2 = new ClassInstance(courseTest2, testTerm, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
		testTerm.addClass(class2);
		main.addClassInstance(class2);
		
		DepartmentHead deptHeadTest3 = new DepartmentHead("HeadMan", "BigBos", "IHaveSevereImposterSyndrome");
		Department deptTest3 = new Department(deptHeadTest3, "biology");
		main.addDept(deptTest3);
		
		Instructor instructorTest3 = new Instructor("Dr. Doctor", deptTest3);
		main.addInstructor(instructorTest3);
		
		Room roomTest3 = new Room("Olin", 222);
		main.addRoom(roomTest3);
		
		ArrayList<String> tags3 = new ArrayList<String>();
		tags3.add("E1");
		tags3.add("D");
		
		testTerm2 = new Term("Fall", 2025);
		main.addTerm(testTerm2);
		
		Course courseTest3 = new Course("DSC", "Impacts of Analytics on Human body", tags3);
		main.addCourse(courseTest3);
		
		class3 = new ClassInstance(courseTest3, testTerm2, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest3);
		testTerm2.addClass(class3);
		main.addClassInstance(class3);
		
		DepartmentHead deptHeadTest4 = new DepartmentHead("TA", "ImJustAGraduateStudent", "Im drowning in work");
		Department deptTest4 = new Department(deptHeadTest4, "dlm");
		main.addDept(deptTest4);

		//Room roomTest4 = new Room("Olin", 211);
		//main.addRoom(roomTest4);
		
		ArrayList<String> tags4 = new ArrayList<String>();
		tags4.add("E1");
		tags4.add("D");
		
		testTerm3 = new Term("Winter", 2026);
		main.addTerm(testTerm3);
		
		Course courseTest4 = new Course("DLM110b", "How to cope with college", tags4);
		main.addCourse(courseTest4);
		
		class4 = new ClassInstance(courseTest4, testTerm3, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest4);
		testTerm3.addClass(class4);
		main.addClassInstance(class4);
	}


	@Test
	void testGetAllPreviousTerms()
	{
		
		testTerm2.markAsFinal();
		testTerm3.markAsFinal();
		
		ArrayList<Term> previousTerms = main.getAllPreviousTerms();
		
		assertEquals(2, previousTerms.size());
		assertEquals("Term [semester=Fall, year=2025]", previousTerms.get(0).toString());
		assertEquals("Term [semester=Winter, year=2026]", previousTerms.get(1).toString());
		
		
	}

	@Test
	void testCreateFromPrevTerm()
	{
		main.setCurrentUser(currentUser);
		assertEquals(3, main.getAllTerms().size());
		assertEquals("Term [semester=Spring, year=2025]", main.getAllTerms().get(0).toString());

		assertEquals(2, main.getAllTerms().get(0).getAllClasses().size());
		main.createFromPrevTerm(testTerm);
		assertEquals(4, main.getAllTerms().size());
		assertEquals("Term [semester=Spring copy, year=2025]", main.getAllTerms().get(3).toString());
		assertEquals(2, main.getAllTerms().get(3).getAllClasses().size());
	}

	@Test
	void testGetClassesByDept()
	{
		assertEquals(4, main.getAllDepts().size());
		
		ArrayList<ClassInstance> classesInDept = main.getClassesByDept(main.getAllDepts().get(0).getDeptName());
		assertEquals(1, classesInDept.size());
		assertEquals("French", classesInDept.get(0).getDept().getDeptName());
		
		classesInDept = main.getClassesByDept(main.getAllDepts().get(1).getDeptName());
		assertEquals(1, classesInDept.size());
		assertEquals("biology", classesInDept.get(0).getDept().getDeptName());
		
		classesInDept = main.getClassesByDept(main.getAllDepts().get(2).getDeptName());
		assertEquals(1, classesInDept.size());
		assertEquals("biology", classesInDept.get(0).getDept().getDeptName());
		
		classesInDept = main.getClassesByDept(main.getAllDepts().get(3).getDeptName());
		assertEquals(1, classesInDept.size());
		assertEquals("dlm", classesInDept.get(0).getDept().getDeptName());
	}

	@Test
	void testAddDept()
	{
		assertEquals(4, main.getAllDepts().size());
		DepartmentHead deptHeadTester = new DepartmentHead("HeadMan", "BigBos", "IHaveSevereImposterSyndrome");
		Department deptTester = new Department(deptHeadTester, "biology");
		main.addDept(deptTester);
		assertEquals(4, main.getAllDepts().size());
		
		
	}
	
	
	@Test
	void testGetCoursesByTerm()
	{
		main.setCurrentUser(currentUser);
		assertEquals(3, main.getAllTerms().size());
		
		Term tester = new Term("Spring", 2025);
		assertEquals(2, main.getCoursesByTerm(tester).size());
	}

	@Test
	void testGetAllInstructorsByDept()
	{		
		DepartmentHead deptHead = new DepartmentHead("Alison Connolly", "FrenchGal", "Oui0ui");

		Department dept1 = new Department(deptHead, "Frenchish");
		main.addDept(dept1);
		
		Instructor instructor1 = new Instructor("Dr. Doctor1", dept1);
		Instructor instructor2 = new Instructor("Dr. Doctor2", dept1);
		Instructor instructor3 = new Instructor("Dr. Doctor3", dept1);
		main.addInstructor(instructor1);
		main.addInstructor(instructor2);
		main.addInstructor(instructor3);
		
		ArrayList<Instructor> instructorInDept = main.getAllInstructorsByDept(dept1);
		assertEquals(3, instructorInDept.size());
		assertEquals("Dr. Doctor1", instructorInDept.get(0).getName());
		assertEquals("Dr. Doctor2", instructorInDept.get(1).getName());
		assertEquals("Dr. Doctor3", instructorInDept.get(2).getName());
	}

	@Test
	void testGetRoomsByBuilding()
	{
		ArrayList<Room> roomsInBuilding = main.getRoomsByBuilding("Olin");
		assertEquals(2, roomsInBuilding.size());
		assertEquals("Olin 201", roomsInBuilding.get(0).toString());
		assertEquals("Olin 222", roomsInBuilding.get(1).toString());
		
		roomsInBuilding = main.getRoomsByBuilding("Crounse");
		assertEquals(1, roomsInBuilding.size());
		assertEquals("Crounse 307", roomsInBuilding.get(0).toString());
	}
	
	@Test
	void testAddRoom()
	{
		ArrayList<Room> roomsInBuilding = main.getRoomsByBuilding("Olin");
		assertEquals(2, roomsInBuilding.size());
		
		assertEquals("Olin 201", roomsInBuilding.get(0).toString());
		
		Room duplicateRoom = new Room("Olin", 201);
		main.addRoom(duplicateRoom);
		roomsInBuilding = main.getRoomsByBuilding("Olin");
		assertEquals(2, roomsInBuilding.size());
		
	}
	
	@Test
	void testAddInstructor()
	{
		
		Department dept1 = class1.getDept();
		ArrayList<Instructor> instructorInDept = main.getAllInstructorsByDept(dept1);
		assertEquals(1, instructorInDept.size());
		
		Instructor instructor1 = new Instructor("Allison Conelly", dept1);
		main.addInstructor(instructor1);
		instructorInDept = main.getAllInstructorsByDept(dept1);
		assertEquals(1, instructorInDept.size());
		
		
	}
	
	@Test
	void testAddClassInstance()
	{
		ArrayList<ClassInstance> classInstances = main.getAllClasses();
		assertEquals(4, classInstances.size());
		
		ClassInstance duplicatedClass = class1.getDeepCopy();
		
		main.addClassInstance(duplicatedClass);
		assertEquals(4, classInstances.size());
		
		
	}
	
	@Test
	void testAddCourse()
	{
		Course duplicateCourse = class1.getCourse();
		ArrayList<ClassInstance> courses = main.getAllClasses();
		assertEquals(4, courses.size());
		
		main.addCourse(duplicateCourse);
		assertEquals(4, courses.size());
		
	}
	
	
	@Test
	void testAddTerm()
	{
		Term duplicateTerm = class1.getTerm();
		ArrayList<Term> terms = main.getAllTerms();
		assertEquals(3, terms.size());
		
		main.addTerm(duplicateTerm);
		assertEquals(3, terms.size());
		
	}
	
	@Test
	void testAddUsers()
	{
		DepartmentHead testerDeptHead = new DepartmentHead("Michael Bradshaw", "CSDeptHead", "dragonsAreCool");
		Department tester = new Department(testerDeptHead, "CSC");
		testerDeptHead.setDepartment(tester);
		Registrar testerRegistrar = new Registrar("Jacob Johnson", "registrarMan", "password");
		User testerUser = new User("Rando McRando", "username", "password");
		
		assertEquals(0, main.getAllUsers().size());
		main.addUser(testerDeptHead);
		main.addUser(testerRegistrar);
		main.addUser(testerUser);

		assertEquals(3, main.getAllUsers().size());
		
		main.addUser(testerUser);

		assertEquals(3, main.getAllUsers().size());
		
	}
	
	@Test
	void testGetAllUsers()
	{
		DepartmentHead testerDeptHead = new DepartmentHead("Michael Bradshaw", "CSDeptHead", "dragonsAreCool");
		Department tester = new Department(testerDeptHead, "CSC");
		testerDeptHead.setDepartment(tester);
		Registrar testerRegistrar = new Registrar("Jacob Johnson", "registrarMan", "password");
		User testerUser = new User("Rando McRando", "username", "password");
		
		assertEquals(0, main.getAllUsers().size());
		main.addUser(testerDeptHead);
		main.addUser(testerRegistrar);
		main.addUser(testerUser);

		assertEquals(3, main.getAllUsers().size());
		
	}
	

}