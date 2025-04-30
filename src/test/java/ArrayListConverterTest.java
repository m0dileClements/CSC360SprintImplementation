

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;


class ArrayListConverterTest
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
	ArrayListConverter converter;

	
	@BeforeEach
	void setUp() throws Exception
	{
		main = new Main();
		converter = new ArrayListConverter();
		
		User testerUser = new User("Rando McRando", "username", "password");
		main.addUser(testerUser);
		main.setCurrentUser(testerUser);
		
		Registrar createPermissions = new Registrar("", "", "");
		
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
		main.addTerm(createPermissions, testTerm);
		
		Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		main.addCourse(courseTest);
		
		class1 = new ClassInstance(courseTest, testTerm, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		testTerm.addClass(createPermissions, class1);
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
		testTerm.addClass(createPermissions, class2);
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
		main.addTerm(createPermissions,testTerm2);
		
		Course courseTest3 = new Course("DSC", "Impacts of Analytics on Human body", tags3);
		main.addCourse(courseTest3);
		
		class3 = new ClassInstance(courseTest3, testTerm2, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest3);
		testTerm2.addClass(createPermissions, class3);
		main.addClassInstance(class3);
		
		//DepartmentHead deptHeadTest4 = new DepartmentHead("TA", "ImJustAGraduateStudent", "Im drowning in work");
		//Department deptTest4 = new Department(deptHeadTest4, "dlm");
		//main.addDept(deptTest4);

		//Room roomTest4 = new Room("Olin", 211);
		//main.addRoom(roomTest4);
		
		ArrayList<String> tags4 = new ArrayList<String>();
		tags4.add("E1");
		tags4.add("D");
		
		testTerm3 = new Term("Winter", 2026);
		main.addTerm(createPermissions, testTerm3);
		
		Course courseTest4 = new Course("DLM110b", "How to cope with college", tags4);
		main.addCourse(courseTest4);
		
		class4 = new ClassInstance(courseTest4, testTerm3, instructorTest3, "MWF 10:20AM - 12:40PM", roomTest3, deptTest3);
		testTerm3.addClass(createPermissions, class4);
		main.addClassInstance(class4);
		
		
	}
	
	@Test
	void testClearData()
	{
		assertEquals(1, main.getAllUsers().size());
		assertEquals(3, main.getAllTerms().size());
		assertEquals(3, main.getAllDepts().size());
		assertEquals(4, main.getAllCourses().size());
		assertEquals(4, main.getAllClasses().size());
		assertEquals(3, main.getAllInstructors().size());
		assertEquals(3, main.getAllRooms().size());
		assertEquals("No User Type Inputted= Rando McRando", main.getCurrentUser().toString());
		
		converter.clearData(main);
		
		assertEquals(0, main.getAllUsers().size());
		assertEquals(0, main.getAllTerms().size());
		assertEquals(0, main.getAllDepts().size());
		assertEquals(0, main.getAllCourses().size());
		assertEquals(0, main.getAllClasses().size());
		assertEquals(0, main.getAllInstructors().size());
		assertEquals(0, main.getAllRooms().size());
		assertEquals(null, currentUser);
		
	}
	
	@Test
	void testLoadData()
	{
		//clears the data, makes sure to start with a clean slate
		converter.clearData(main);
		assertEquals(0, main.getAllUsers().size());
		assertEquals(0, main.getAllTerms().size());
		assertEquals(0, main.getAllDepts().size());
		assertEquals(0, main.getAllCourses().size());
		assertEquals(0, main.getAllClasses().size());
		assertEquals(0, main.getAllInstructors().size());
		assertEquals(0, main.getAllRooms().size());
		assertEquals(null, currentUser);
		
		converter.loadData(main);
		
		//loads the data, and use the same tests from the testClearData to check the previously 
		//loaded data in the beforeEach to make sure all of the loaded data is the same. 
		assertEquals(3, main.getAllTerms().size());
		assertEquals(3, main.getAllDepts().size());
		assertEquals(4, main.getAllCourses().size());
		assertEquals(4, main.getAllClasses().size());
		assertEquals(3, main.getAllInstructors().size());
		assertEquals(3, main.getAllRooms().size());
		
	}

}
