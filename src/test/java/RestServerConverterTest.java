import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import source.*;
import source.RestServerConverter.ArchiveWrapper;
import source.RestServerConverter.ConstraintLocations;
import source.RestServerConverter.ConstraintsList;
import source.RestServerConverter.CourseData;
import source.RestServerConverter.CourseList;
import source.RestServerConverter.CourseLocations;
import source.RestServerConverter.Dept;
import source.RestServerConverter.DeptDetails;
import source.RestServerConverter.DeptList;
import source.RestServerConverter.InstructorData;
import source.RestServerConverter.InstructorList;
import source.RestServerConverter.InstructorLocations;
import source.RestServerConverter.NewDeptData;
import source.RestServerConverter.NewDeptDetails;
import source.RestServerConverter.RoomInfo;
import source.RestServerConverter.RoomList;
import source.RestServerConverter.SpecConstraint;
import source.RestServerConverter.SpecConstraintWrapper;
import source.RestServerConverter.SpecInstructorWrapper;
import source.RestServerConverter.SpecRoomInfo;
import source.RestServerConverter.SpecRoomWrapper;
import source.RestServerConverter.SpecificCourseInfo;
import source.RestServerConverter.SpecificTermInfo;
import source.RestServerConverter.TermData;

//import RestServerConverter.DeptList;


class RestServerConverterTest
{
	Main main;
	RestClient defaultClient;
	RestServerConverter testConverter;
	
	@BeforeEach
	void setUp() throws Exception
	{
		main = new Main();
		main.setRestServerConverter();
		defaultClient = RestClient.create();
		testConverter = (RestServerConverter)main.getConverter();
		
	}
	
	@Test
	void testLoadArchive()
	{
		//test loading
		main.launch();
		assertEquals(90, main.getAllRooms().size());
		assertEquals(44, main.getAllDepts().size());
		assertEquals(8, main.getAllTerms().size());
		assertEquals(2332, main.getAllClasses().size());
		assertEquals(364, main.getAllInstructors().size());
		
		//test clearing (in same method to reduce time spent relaunching in other test
		main.getConverter().clearData(main);
		assertEquals(0, main.getAllRooms().size());
		assertEquals(0, main.getAllDepts().size());
		assertEquals(0, main.getAllTerms().size());
		assertEquals(0, main.getAllClasses().size());
		
	}
	
	@Test
	void testImportDept()
	{
		
		DeptList deptWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/dept")
				.retrieve()
				.body(DeptList.class);
		//Stores the list of dept
		ArrayList<Dept> deptAddresses = deptWrapper.data();
		
		DeptDetails specificDeptWrapper = defaultClient.get()
				.uri(deptAddresses.get(0).location())
				.retrieve()
				.body(DeptDetails.class);
			
		testConverter.importDept(main, specificDeptWrapper);
		
		assertEquals(1, main.getAllDepts().size());
		
		Department addedDept = main.getAllDepts().get(0);
		
		assertEquals("CHE", addedDept.getDeptName());
		assertEquals("Hitron, John Andrew (Andrew)", addedDept.getDepartmentHead().getName());
		
		assertEquals(13, main.getAllInstructors().size());
		
		Instructor addedInstructor = main.getAllInstructors().get(0);
		
		assertEquals("Hitron, John Andrew (Andrew)", addedInstructor.getName());
	
	}
	
	@Test
	void testImportRoom()
	{
		RoomList roomWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/room")
				.retrieve()
				.body(RoomList.class);
		ArrayList<RoomInfo> rooms = roomWrapper.data();
	
		SpecRoomWrapper specRoomWrapper  = defaultClient.get()
				.uri(rooms.get(0).location())
				.retrieve()
				.body(SpecRoomWrapper.class);
		testConverter.importRoom(main, specRoomWrapper);
		
		assertEquals(1, main.getAllRooms().size());
		
		Room addedRoom = main.getAllRooms().get(0);
		
		assertEquals("Olin Hall", addedRoom.getBuilding());
		assertEquals("128", addedRoom.getRoomNumber());
	}
	
	@Test
	void testImportCourse()
	{
		Registrar permissionsToLoad = new Registrar("Loading Permissions", "", "");
		CourseList courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/course")
				.retrieve()
				.body(CourseList.class);
		//Stores the list of courses
		ArrayList<CourseLocations> courseAddress = courseLocationWrapper.data();
		
		//goes to the address of each course and prints the name and instructor
		SpecificCourseInfo specificCourseWrapper = defaultClient.get()
			.uri(courseAddress.get(0).location())
			.retrieve()
			.body(SpecificCourseInfo.class);
		
		CourseData courseInfo = specificCourseWrapper.data();
		testConverter.importCourse(main, permissionsToLoad, courseInfo);
		
		assertEquals(1, main.getAllClasses().size());
		
		ClassInstance addedClass = main.getAllClasses().get(0);
		
		assertEquals("404a", addedClass.getCourseCode());
		assertEquals("Advanced Special Topics Politics of the Occult", addedClass.getTitle());
		assertEquals(0, addedClass.getTags().size());
		assertEquals(0, addedClass.getTags().size());
		assertEquals("Paskewich, Christopher", addedClass.getInstructor().getName());
		assertEquals("MWF 10:20AM - 11:20AM", addedClass.getClassTime());
		assertEquals("10:20AM", addedClass.getStartTime());
		assertEquals("11:20AM", addedClass.getEndTime());
		assertEquals("Crounse Academic Center 102", addedClass.getRoom().toString());
		assertEquals("POL", addedClass.getDept().getDeptName());
		assertEquals(3, addedClass.getClassLength());
		assertEquals(false, addedClass.getHasFalseLimit());	
	}
	
	@Test
	void testCreateNewServerListHolders()
	{
		testConverter.createNewServerListHolders();
		
		ArchiveWrapper objectLists = defaultClient.get()
				.uri("http://localhost:9000/v1/new")
				.retrieve()
				.body(ArchiveWrapper.class);
		assertEquals(8, objectLists.data().size());
		assertEquals("rooms", objectLists.data().get(0).name());
		
	}
	
	@Test
	void testUploadClass() {
		testConverter.createNewServerListHolders();
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "FRE");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Crounse", "307");
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		Term termTest = new Term("Spring", 2025);
		//Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		ClassInstance classInstance1 = new ClassInstance("FRE270", "Group Conversation", tagArray, termTest, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		testConverter.uploadData(classInstance1);
		
		//check if data uploaded properly
		CourseList courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/classes")
				.retrieve()
				.body(CourseList.class);
		//Stores the list of courses
		ArrayList<CourseLocations> courseAddress = courseLocationWrapper.data();
		
		SpecificCourseInfo specificCourseWrapper = defaultClient.get()
				.uri(courseAddress.get(0).location())
				.retrieve()
				.body(SpecificCourseInfo.class);
			
		CourseData courseInfo = specificCourseWrapper.data();
			
			
			assertEquals("Spring", courseInfo.season());
			assertEquals(2025, courseInfo.year());
			assertEquals("FRE", courseInfo.dept());
			assertEquals("270", courseInfo.num());
			assertEquals("", courseInfo.section());
			assertEquals("Group Conversation", courseInfo.name());
			assertEquals("Allison Conelly", courseInfo.instructor());
			assertEquals("TR 9:40AM - 12:10PM", courseInfo.meetingTime());
			assertEquals("Crounse", courseInfo.building());
			assertEquals("307", courseInfo.roomNumber());
			assertEquals("FRE270-Sp2025", courseInfo.id());
	}
	
	@Test
	void testUploadTerm() {
		testConverter.createNewServerListHolders();
		Term termTest = new Term("Spring", 2025);
		testConverter.uploadData(termTest);
		
		//check if data uploaded properly
		CourseList termLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/terms")
				.retrieve()
				.body(CourseList.class);
		//Stores the list of courses
		ArrayList<CourseLocations> termAddress = termLocationWrapper.data();
		
		SpecificTermInfo specificTermWrapper = defaultClient.get()
				.uri(termAddress.get(0).location())
				.retrieve()
				.body(SpecificTermInfo.class);
			
		TermData termInfo = specificTermWrapper.data();
			
			
			assertEquals("Spring", termInfo.season());
			assertEquals(2025, termInfo.year());
			assertEquals(false, termInfo.isFinalized());
			
	}
	
	@Test
	void testUploadRoom() {
		testConverter.createNewServerListHolders();
		Room roomTest = new Room("Olin", "201");
		testConverter.uploadData(roomTest);
		
		//check if data uploaded properly
		RoomList roomLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/rooms")
				.retrieve()
				.body(RoomList.class);
		//Stores the list of courses
		ArrayList<RoomInfo> roomAddress = roomLocationWrapper.data();
		
		SpecRoomWrapper specificRoomWrapper = defaultClient.get()
				.uri(roomAddress.get(0).location())
				.retrieve()
				.body(SpecRoomWrapper.class);
			
		SpecRoomInfo roomInfo = specificRoomWrapper.data();
		assertEquals("Olin", roomInfo.building());
		assertEquals("201", roomInfo.roomNumber());
		assertEquals("Olin-201", roomInfo.id());
	}
	
	@Test
	void testUploadDept() {
		testConverter.createNewServerListHolders();
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "", "");
		Department deptTest = new Department(deptHeadTest, "FRE");
		testConverter.uploadData(deptTest);
		
		//check if data uploaded properly
		DeptList deptLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/depts")
				.retrieve()
				.body(DeptList.class);
		//Stores the list of courses
		ArrayList<Dept> deptAddress = deptLocationWrapper.data();
		
		NewDeptDetails specificDeptWrapper = defaultClient.get()
				.uri(deptAddress.get(0).location())
				.retrieve()
				.body(NewDeptDetails.class);
			
		NewDeptData deptInfo = specificDeptWrapper.data();
		assertEquals("FRE", deptInfo.dept());
		assertEquals("Alison Conelly", deptInfo.deptHead());
	}
	
	@Test
	void testUploadInstructor() {
		testConverter.createNewServerListHolders();
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "", "");
		Department deptTest = new Department(deptHeadTest, "FRE");
		Instructor testInst = new Instructor("John Doe", deptTest);
		testConverter.uploadData(testInst);
		
		//check if data uploaded properly
		InstructorList instructorLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/faculty")
				.retrieve()
				.body(InstructorList.class);
		//Stores the list of courses
		ArrayList<InstructorLocations> instructorAddress = instructorLocationWrapper.data();
		
		SpecInstructorWrapper specificInstructorWrapper = defaultClient.get()
				.uri(instructorAddress.get(0).location())
				.retrieve()
				.body(SpecInstructorWrapper.class);
			
		InstructorData instInfo = specificInstructorWrapper.data();
		
		assertEquals("John Doe", instInfo.name());
		assertEquals("FRE", instInfo.dept());
	}
	
	@Test
	void testUploadMustBeOfferedConstraint() {
		//User u = new Registrar("Jacob Johnson", "username", "password");
		DepartmentHead deptHeadTest = new DepartmentHead("Grand Master of All Lords", "OhExaltedOne", "1Rul3All");
		Department deptTest = new Department(deptHeadTest, "biology");
		Instructor instructorTest = new Instructor("Robin Williams", deptTest);
		Room roomTest = new Room("Young", "112");
		ArrayList<String>tags = new ArrayList<String>();
		tags.add("E1");
		tags.add("A");
		Term termTest = new Term("Spring", 2025);
		ClassInstance classInstance1 = new ClassInstance("Bio110", "Intro to Biology", tags, termTest, instructorTest, "MWF 10:20AM - 12:40PM", roomTest, deptTest);
		
		ArrayList<ClassInstance> classConstraints= new ArrayList<ClassInstance>();
		classConstraints.add(classInstance1);
		
		testConverter.uploadData(classInstance1);
		MustBeOfferedConstraint testConstraint = new MustBeOfferedConstraint(termTest, "must offer", classConstraints);
		
		testConverter.uploadData(testConstraint);
		
		//check if data uploaded properly
		ConstraintsList constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustBeOffered")
				.retrieve()
				.body(ConstraintsList.class);
		//Stores the list of courses
		ArrayList<ConstraintLocations> constraintAddress = constraintsLocationWrapper.data();
		
		SpecConstraintWrapper specificConstraintWrapper = defaultClient.get()
				.uri(constraintAddress.get(0).location())
				.retrieve()
				.body(SpecConstraintWrapper.class);
			
		SpecConstraint constInfo = specificConstraintWrapper.data();
		
		assertEquals("Spring", constInfo.semester());
		assertEquals(2025, constInfo.year());
		assertEquals("must offer", constInfo.desc());
		assertEquals("http://localhost:9000/v1/new/classes/Bio110-Sp2025", constInfo.class1());
		assertEquals("", constInfo.class2());
	}
	
	@Test
	void testUploadMustOverlapConstraint() {
		//User u = new Registrar("Jacob Johnson", "username", "password");
		DepartmentHead deptHeadTest = new DepartmentHead("Grand Master of All Lords", "OhExaltedOne", "1Rul3All");
		Department deptTest = new Department(deptHeadTest, "biology");
		Instructor instructorTest = new Instructor("Robin Williams", deptTest);
		Room roomTest = new Room("Young", "112");
		ArrayList<String>tags = new ArrayList<String>();
		tags.add("E1");
		tags.add("A");
		Term termTest = new Term("Spring", 2025);
		ClassInstance classInstance1 = new ClassInstance("Bio110", "Intro to Biology", tags, termTest, instructorTest, "MWF 10:20AM - 12:40PM", roomTest, deptTest);
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		Instructor instructorTest2 = new Instructor("The Genie", deptTest2);
		Room roomTest2 = new Room("Olin",  "201");
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		//Course courseTest2 = new Course("Bio210", "Environmental Science", tags2);
		ClassInstance classInstance2 = new ClassInstance("Bio210", "Environmental Science", tags2, termTest, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
		
		ArrayList<ClassInstance> classConstraints= new ArrayList<ClassInstance>();
		classConstraints.add(classInstance1);
		classConstraints.add(classInstance2);
		
		testConverter.uploadData(classInstance1);
		MustOverlapConstraint testConstraint = new MustOverlapConstraint(termTest, "must overlap", classConstraints);
		
		testConverter.uploadData(testConstraint);
		
		//check if data uploaded properly
		ConstraintsList constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustOverlap")
				.retrieve()
				.body(ConstraintsList.class);
		//Stores the list of courses
		ArrayList<ConstraintLocations> constraintAddress = constraintsLocationWrapper.data();
		
		SpecConstraintWrapper specificConstraintWrapper = defaultClient.get()
				.uri(constraintAddress.get(0).location())
				.retrieve()
				.body(SpecConstraintWrapper.class);
			
		SpecConstraint constInfo = specificConstraintWrapper.data();
		
		assertEquals("Spring", constInfo.semester());
		assertEquals(2025, constInfo.year());
		assertEquals("must overlap", constInfo.desc());
		assertEquals("http://localhost:9000/v1/new/classes/Bio110-Sp2025", constInfo.class1());
		assertEquals("http://localhost:9000/v1/new/classes/Bio210-Sp2025", constInfo.class2());
	}
	
	@Test
	void testUploadNonOverlappingConstraint() {
		//User u = new Registrar("Jacob Johnson", "username", "password");
		DepartmentHead deptHeadTest = new DepartmentHead("Grand Master of All Lords", "OhExaltedOne", "1Rul3All");
		Department deptTest = new Department(deptHeadTest, "biology");
		Instructor instructorTest = new Instructor("Robin Williams", deptTest);
		Room roomTest = new Room("Young", "112");
		ArrayList<String>tags = new ArrayList<String>();
		tags.add("E1");
		tags.add("A");
		Term termTest = new Term("Spring", 2025);
		ClassInstance classInstance1 = new ClassInstance("Bio110", "Intro to Biology", tags, termTest, instructorTest, "MWF 10:20AM - 12:40PM", roomTest, deptTest);
		
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest2, "biology");
		Instructor instructorTest2 = new Instructor("The Genie", deptTest2);
		Room roomTest2 = new Room("Olin",  "201");
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E2");
		tags2.add("S");
		//Course courseTest2 = new Course("Bio210", "Environmental Science", tags2);
		ClassInstance classInstance2 = new ClassInstance("Bio210", "Environmental Science", tags2, termTest, instructorTest2, "MWF 10:20AM - 12:40PM", roomTest2, deptTest2);
		
		ArrayList<ClassInstance> classConstraints= new ArrayList<ClassInstance>();
		classConstraints.add(classInstance1);
		classConstraints.add(classInstance2);
		
		testConverter.uploadData(classInstance1);
		NonOverlappingConstraint testConstraint = new NonOverlappingConstraint(termTest, "must not overlap", classConstraints);
		
		testConverter.uploadData(testConstraint);
		
		//check if data uploaded properly
		ConstraintsList constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustNotOverlap")
				.retrieve()
				.body(ConstraintsList.class);
		//Stores the list of courses
		ArrayList<ConstraintLocations> constraintAddress = constraintsLocationWrapper.data();
		
		SpecConstraintWrapper specificConstraintWrapper = defaultClient.get()
				.uri(constraintAddress.get(0).location())
				.retrieve()
				.body(SpecConstraintWrapper.class);
			
		SpecConstraint constInfo = specificConstraintWrapper.data();
		
		assertEquals("Spring", constInfo.semester());
		assertEquals(2025, constInfo.year());
		assertEquals("must not overlap", constInfo.desc());
		assertEquals("http://localhost:9000/v1/new/classes/Bio110-Sp2025", constInfo.class1());
		assertEquals("http://localhost:9000/v1/new/classes/Bio210-Sp2025", constInfo.class2());
	}
}
