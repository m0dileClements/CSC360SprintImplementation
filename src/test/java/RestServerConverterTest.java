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
import source.RestServerConverter.SpecUserWrapper;
import source.RestServerConverter.SpecificCourseInfo;
import source.RestServerConverter.SpecificTermInfo;
import source.RestServerConverter.TermData;
import source.RestServerConverter.UserData;
import source.RestServerConverter.UserList;
import source.RestServerConverter.UserLocations;

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
		assertEquals("Fieberg, Jeffrey E.", addedDept.getDepartmentHead().getName());
		
		assertEquals(13, main.getAllInstructors().size());
		
		Instructor addedInstructor = main.getAllInstructors().get(0);
		
		assertEquals("Fieberg, Jeffrey E.", addedInstructor.getName());
		assertEquals(0, testConverter.getNewItemsInRest().size());
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
		assertEquals(0, testConverter.getNewItemsInRest().size());
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
		assertEquals(0, testConverter.getNewItemsInRest().size());
	}
	
	@Test
	void testCreateNewServerListHolders()
	{
		testConverter.createNewServerListHolders();
		
		ArchiveWrapper objectLists = defaultClient.get()
				.uri("http://localhost:9000/v1/new")
				.retrieve()
				.body(ArchiveWrapper.class);
		assertEquals(9, objectLists.data().size());
		assertEquals("rooms", objectLists.data().get(0).name());
		
	}
	
	@Test
	void testUploadClass() {
		testConverter.clearData(main);
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
		ClassInstance classInstance1 = new ClassInstance("FRE270a", "Group Conversation", tagArray, termTest, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
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
			assertEquals("a", courseInfo.section());
			assertEquals("Group Conversation", courseInfo.name());
			assertEquals("Allison Conelly", courseInfo.instructor());
			assertEquals("TR 9:40AM - 12:10PM", courseInfo.meetingTime());
			assertEquals("Crounse", courseInfo.building());
			assertEquals("307", courseInfo.roomNumber());
			assertEquals("FRE270a-Sp2025", courseInfo.id());
			
			assertEquals(1, testConverter.getNewItemsInRest().size());
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
			
			assertEquals(1, testConverter.getNewItemsInRest().size());
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
		
		assertEquals(1, testConverter.getNewItemsInRest().size());
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
		
		ArrayList<Dept> deptAddress = deptLocationWrapper.data();
		
		NewDeptDetails specificDeptWrapper = defaultClient.get()
				.uri(deptAddress.get(0).location())
				.retrieve()
				.body(NewDeptDetails.class);
			
		NewDeptData deptInfo = specificDeptWrapper.data();
		assertEquals("FRE", deptInfo.dept());
		assertEquals("Alison Conelly", deptInfo.deptHead());
		
		assertEquals(1, testConverter.getNewItemsInRest().size());
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
		ArrayList<InstructorLocations> instructorAddress = instructorLocationWrapper.data();
		
		SpecInstructorWrapper specificInstructorWrapper = defaultClient.get()
				.uri(instructorAddress.get(0).location())
				.retrieve()
				.body(SpecInstructorWrapper.class);
			
		InstructorData instInfo = specificInstructorWrapper.data();
		
		assertEquals("John Doe", instInfo.name());
		assertEquals("FRE", instInfo.dept());
		
		assertEquals(1, testConverter.getNewItemsInRest().size());
	}
	
	@Test
	void testUploadMustBeOfferedConstraint() {
		testConverter.createNewServerListHolders();
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
		
		assertEquals(2, testConverter.getNewItemsInRest().size());
	}
	
	@Test
	void testUploadMustOverlapConstraint() {
		testConverter.createNewServerListHolders();
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
	
		assertEquals(2, testConverter.getNewItemsInRest().size());
	}
	
	@Test
	void testUploadNonOverlappingConstraint() {
		testConverter.createNewServerListHolders();
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
	
		assertEquals(2, testConverter.getNewItemsInRest().size());
	}
	
	@Test
	void testUploadUser() {
		testConverter.createNewServerListHolders();
		User userTest = new User("John Doe", "johnDo3", "password");
		testConverter.uploadData(userTest);
		
		Registrar registrarTest = new Registrar("Jacob Johnson", "jjohnson", "password");
		testConverter.uploadData(registrarTest);
		
		DepartmentHead deptHeadTest = new DepartmentHead("Michael Bradshaw", "anonDragonlord", "password");
		testConverter.uploadData(deptHeadTest);
		
		Student studentTest = new Student("Jane Doe", "janeDo3", "password");
		testConverter.uploadData(studentTest);
		
		
		UserList userLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/users")
				.retrieve()
				.body(UserList.class);
		
		ArrayList<UserLocations> userAddress = userLocationWrapper.data();
		
		SpecUserWrapper specificUserWrapper = defaultClient.get()
				.uri(userAddress.get(1).location())
				.retrieve()
				.body(SpecUserWrapper.class);
			
		UserData userData = specificUserWrapper.data();
		assertEquals("John Doe", userData.name());
		assertEquals("johnDo3", userData.username());
		assertEquals("password", userData.password());
		assertEquals("", userData.role());
		
		specificUserWrapper = defaultClient.get()
				.uri(userAddress.get(0).location())
				.retrieve()
				.body(SpecUserWrapper.class);
			
		userData = specificUserWrapper.data();
		assertEquals("Jacob Johnson", userData.name());
		assertEquals("jjohnson", userData.username());
		assertEquals("password", userData.password());
		assertEquals("Registrar", userData.role());
		
		specificUserWrapper = defaultClient.get()
				.uri(userAddress.get(3).location())
				.retrieve()
				.body(SpecUserWrapper.class);
			
		userData = specificUserWrapper.data();
		assertEquals("Michael Bradshaw", userData.name());
		assertEquals("anonDragonlord", userData.username());
		assertEquals("password", userData.password());
		assertEquals("DeptHead", userData.role());
		
		specificUserWrapper = defaultClient.get()
				.uri(userAddress.get(2).location())
				.retrieve()
				.body(SpecUserWrapper.class);
			
		userData = specificUserWrapper.data();
		assertEquals("Jane Doe", userData.name());
		assertEquals("janeDo3", userData.username());
		assertEquals("password", userData.password());
		assertEquals("Student", userData.role());
	
		assertEquals(4, testConverter.getNewItemsInRest().size());
	}
	
	@Test
	void testDeleteClass() {
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
		ClassInstance classInstance1 = new ClassInstance("FRE270a", "Group Conversation", tagArray, termTest, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		testConverter.uploadData(classInstance1);
		CourseList courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/classes")
				.retrieve()
				.body(CourseList.class);
		ArrayList<CourseLocations> courseAddress = courseLocationWrapper.data();
		
		assertEquals(1, courseAddress.size());
		
		testConverter.deleteData(classInstance1);
		
		courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/classes")
				.retrieve()
				.body(CourseList.class);
		courseAddress = courseLocationWrapper.data();
		assertEquals(0, courseAddress.size());
		
		classInstance1 = new ClassInstance("FRE270", "Group Conversation", tagArray, termTest, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		testConverter.uploadData(classInstance1);
		courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/classes")
				.retrieve()
				.body(CourseList.class);
		courseAddress = courseLocationWrapper.data();
		
		assertEquals(1, courseAddress.size());
		
		testConverter.deleteData(classInstance1);
		
		courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/classes")
				.retrieve()
				.body(CourseList.class);
		courseAddress = courseLocationWrapper.data();
		assertEquals(0, courseAddress.size());
		
	}
	
	@Test
	void testDeleteTerm() {
		testConverter.createNewServerListHolders();
		
		Term termTest = new Term("Spring", 2025);
		testConverter.uploadData(termTest);
		
		
		CourseList termLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/terms")
				.retrieve()
				.body(CourseList.class);
		ArrayList<CourseLocations> termAddress = termLocationWrapper.data();
		
		assertEquals(1, termAddress.size());
		
		testConverter.deleteData(termTest);
		
		termLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/terms")
				.retrieve()
				.body(CourseList.class);
		termAddress = termLocationWrapper.data();
		assertEquals(0, termAddress.size());
		
	}
	
	@Test
	void testDeleteRoom() {
		testConverter.createNewServerListHolders();
		
		Room roomTest = new Room("Olin", "201");
		testConverter.uploadData(roomTest);
		
		RoomList roomLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/rooms")
				.retrieve()
				.body(RoomList.class);
		//Stores the list of courses
		ArrayList<RoomInfo> roomAddress = roomLocationWrapper.data();
		
		assertEquals(1, roomAddress.size());
		
		testConverter.deleteData(roomTest);
		
		roomLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/rooms")
				.retrieve()
				.body(RoomList.class);
		//Stores the list of courses
		roomAddress = roomLocationWrapper.data();
		assertEquals(0, roomAddress.size());
		
	}
	
	@Test
	void testDeleteDept() {
		testConverter.createNewServerListHolders();
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "", "");
		Department deptTest = new Department(deptHeadTest, "FRE");
		testConverter.uploadData(deptTest);
		
		DeptList deptLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/depts")
				.retrieve()
				.body(DeptList.class);
		
		ArrayList<Dept> deptAddress = deptLocationWrapper.data();
		
		assertEquals(1, deptAddress.size());
		
		testConverter.deleteData(deptTest);
		
		deptLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/depts")
				.retrieve()
				.body(DeptList.class);
		
		deptAddress = deptLocationWrapper.data();
		assertEquals(0, deptAddress.size());
		
	}
	
	@Test
	void testDeleteInstructor() {
		testConverter.createNewServerListHolders();
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "", "");
		Department deptTest = new Department(deptHeadTest, "FRE");
		Instructor testInst = new Instructor("John Doe", deptTest);
		testConverter.uploadData(testInst);
		
		InstructorList instructorLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/faculty")
				.retrieve()
				.body(InstructorList.class);
		ArrayList<InstructorLocations> instructorAddress = instructorLocationWrapper.data();
		
		assertEquals(1, instructorAddress.size());
		
		testConverter.deleteData(testInst);
		
		instructorLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/faculty")
				.retrieve()
				.body(InstructorList.class);
		instructorAddress = instructorLocationWrapper.data();
		
		assertEquals(0, instructorAddress.size());
		
	}
	
	@Test
	void testDeleteMustBeOfferedConstraint() {
		testConverter.createNewServerListHolders();
		
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
		
		ConstraintsList constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustBeOffered")
				.retrieve()
				.body(ConstraintsList.class);
		ArrayList<ConstraintLocations> constraintAddress = constraintsLocationWrapper.data();
		
		assertEquals(1, constraintAddress.size());
		
		testConverter.deleteData(testConstraint);
		
		constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustBeOffered")
				.retrieve()
				.body(ConstraintsList.class);
		constraintAddress = constraintsLocationWrapper.data();
		
		assertEquals(0, constraintAddress.size());
		
	}
	
	@Test
	void testDeleteMustOverlapConstraint() {
		testConverter.createNewServerListHolders();
		
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
		
		
		ConstraintsList constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustOverlap")
				.retrieve()
				.body(ConstraintsList.class);
		ArrayList<ConstraintLocations> constraintAddress = constraintsLocationWrapper.data();
		
		assertEquals(1, constraintAddress.size());
		
		testConverter.deleteData(testConstraint);
		
		constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustOverlap")
				.retrieve()
				.body(ConstraintsList.class);
		constraintAddress = constraintsLocationWrapper.data();
		
		assertEquals(0, constraintAddress.size());
		
	}
	
	@Test
	void testDeleteNonOverlappingConstraint() {
		testConverter.createNewServerListHolders();
		
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
		
		
		ConstraintsList constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustNotOverlap")
				.retrieve()
				.body(ConstraintsList.class);
		ArrayList<ConstraintLocations> constraintAddress = constraintsLocationWrapper.data();
		
		assertEquals(1, constraintAddress.size());
		
		testConverter.deleteData(testConstraint);
		
		constraintsLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/mustNotOverlap")
				.retrieve()
				.body(ConstraintsList.class);
		constraintAddress = constraintsLocationWrapper.data();
		
		assertEquals(0, constraintAddress.size());
		
	}
	
	@Test
	void testDeleteUser() {
		testConverter.createNewServerListHolders();
		User userTest = new User("John Doe", "johnDo3", "password");
		testConverter.uploadData(userTest);
		
		Registrar registrarTest = new Registrar("Jacob Johnson", "jjohnson", "password");
		testConverter.uploadData(registrarTest);
		
		DepartmentHead deptHeadTest = new DepartmentHead("Michael Bradshaw", "anonDragonlord", "password");
		testConverter.uploadData(deptHeadTest);
		
		Student studentTest = new Student("Jane Doe", "janeDo3", "password");
		testConverter.uploadData(studentTest);
		
		
		UserList userLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/users")
				.retrieve()
				.body(UserList.class);
		
		ArrayList<UserLocations> userAddress = userLocationWrapper.data();
		
		SpecUserWrapper specificUserWrapper = defaultClient.get()
				.uri(userAddress.get(1).location())
				.retrieve()
				.body(SpecUserWrapper.class);
			
		UserData userData = specificUserWrapper.data();
		
		assertEquals(4, userAddress.size());
		
		testConverter.deleteData(userTest);
		
		userLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/users")
				.retrieve()
				.body(UserList.class);
		
		userAddress = userLocationWrapper.data();	
		
		assertEquals(3, userAddress.size());
		
	}
	
	@Test
	void testUpdateRestServer() {
		testConverter.createNewServerListHolders();
				
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
		classInstance1.setRoom("Young", "312");
		
		testConverter.updateRestServer();

		courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/new/classes")
				.retrieve()
				.body(CourseList.class);
		
		courseAddress = courseLocationWrapper.data();
		
		specificCourseWrapper = defaultClient.get()
				.uri(courseAddress.get(0).location())
				.retrieve()
				.body(SpecificCourseInfo.class);
		
		courseInfo = specificCourseWrapper.data();
		
		assertEquals("Spring", courseInfo.season());
		assertEquals(2025, courseInfo.year());
		assertEquals("FRE", courseInfo.dept());
		assertEquals("270", courseInfo.num());
		assertEquals("", courseInfo.section());
		assertEquals("Group Conversation", courseInfo.name());
		assertEquals("Allison Conelly", courseInfo.instructor());
		assertEquals("TR 9:40AM - 12:10PM", courseInfo.meetingTime());
		assertEquals("Young", courseInfo.building());
		assertEquals("312", courseInfo.roomNumber());
		assertEquals("FRE270-Sp2025", courseInfo.id());
				
	}
}
