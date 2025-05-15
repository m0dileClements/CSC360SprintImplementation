import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.*;

class ClassInstanceTest
{
	//Course testCourse;
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
		Room roomTest = new Room("Crounse", "307");
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		termTest = new Term("Spring", 2025);
		//Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		classInstance1 = new ClassInstance("FRE270", "Group Conversation", tagArray, termTest, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
	}

	@Test
	void testSetRoom()
	{
		assertEquals("Crounse 307", classInstance1.getRoom().toString());
		classInstance1.setRoom("Olin", "222");
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
		User noPermissions = new User("", "", "");
		Registrar allPermissions = new Registrar("", "", "");
		
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		deptTest = new Department(deptHeadTest, "French");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Grant", "307");
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		Term termTest2 = new Term("Fall", 2024);
		//Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		ClassInstance class2 = new ClassInstance("FRE270", "Group Conversation", tagArray, termTest2, instructorTest, "TR 9:40AM - 12:10PM", roomTest, deptTest);
		
		//classInstance1.modifyClassInstance(deptHeadTest, class2);
		
		//assertEquals(true, class2.getCourse().toString().equals(classInstance1.getCourse().toString()));
		assertEquals(false, class2.getTerm().toString().equals(classInstance1.getTerm().toString()));
		assertEquals(true, class2.getInstructor().toString().equals(classInstance1.getInstructor().toString()));
		assertEquals(true, class2.getClassTime().toString().equals(classInstance1.getClassTime().toString()));
		assertEquals(true, class2.getStartTime().toString().equals(classInstance1.getStartTime().toString()));
		assertEquals(true, class2.getEndTime().toString().equals(classInstance1.getEndTime().toString()));
		assertEquals(false, class2.getRoom().toString().equals(classInstance1.getRoom().toString()));
		assertEquals(true, class2.getDept().toString().equals(classInstance1.getDept().toString()));
		assertEquals(true, class2.getClassLength() == classInstance1.getClassLength());
		assertEquals(true, class2.getHasFalseLimit() == classInstance1.getHasFalseLimit());	
		
		classInstance1.modifyClassInstance(noPermissions, class2);
		
		assertEquals(false, class2.getTerm().toString().equals(classInstance1.getTerm().toString()));
		assertEquals(false, class2.getRoom().toString().equals(classInstance1.getRoom().toString()));
		
		classInstance1.modifyClassInstance(allPermissions, class2);
		
		assertEquals(true, class2.getTerm().toString().equals(classInstance1.getTerm().toString()));
		assertEquals(true, class2.getRoom().toString().equals(classInstance1.getRoom().toString()));
	}
	
	@Test
	void testModifyClassInstance()
	{
		Registrar allPermissions = new Registrar("", "", "");
		ClassInstance class2 = classInstance1.getDeepCopy(allPermissions);
		 
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
	
	

//	@Test
//	void testGetCourse()
//	{	
//		assertEquals("FRE270: Group Conversation, Tags: E3, L", classInstance1.getCourse().toString());
//	}
//
//	@Test
//	void testSetCourse()
//	{
//		assertEquals("FRE270: Group Conversation, Tags: E3, L", classInstance1.getCourse().toString());
//		ArrayList<String> tagList = new ArrayList<String>();
//		
//		tagList.add("P2");
//		tagList.add("S");
//		Course newCourse = new Course("FR271", "Group Conversations in Context", tagList);
//		classInstance1.setCourse(newCourse);
//		assertEquals("FR271: Group Conversations in Context, Tags: P2, S", classInstance1.getCourse().toString());
//		
//	}

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
		assertEquals("ClassInstance [courseCode=FRE270: Group Conversation, Tags: E3, L, "
				+ "term=Term [semester=Spring, year=2025], instructor=Instructor [name=Allison Conelly, profDept=French, hours=0.0], "
				+ "classTime=TR 9:40AM - 12:10PM, room=Crounse 307, dept=Alison Conelly: French, classLength=5.0, hasFalseLimit=false]", classInstance1.toString());
	}
	
	@Test
	void testModifyCrossCourseCodes()
	{
		ArrayList<String> listings = new ArrayList<String>();
		listings.add("Bio210");
		classInstance1.createCrossListing(listings);
		assertEquals("Bio210", classInstance1.getCrossListings().get(0));
		
		listings.add("ENS110");
		classInstance1.modifyCrossCourseCodes(listings);
		assertEquals(2, classInstance1.getCrossListings().size());
		assertEquals("ENS110", classInstance1.getCrossListings().get(1));
	}

	@Test
	void testGetCourseCode()
	{
		assertEquals("FRE270", classInstance1.getCourseCode());
	}

	@Test
	void testSetCourseCode()
	{
		classInstance1.setCourseCode("Bio210");
		assertEquals("Bio210", classInstance1.getCourseCode());
	}

	@Test
	void testGetTitle()
	{
		assertEquals("Group Conversation", classInstance1.getTitle());
	}

	@Test
	void testSetTitle()
	{
		classInstance1.setTitle("Intro to Cell Bio");
		assertEquals("Intro to Cell Bio", classInstance1.getTitle());
	}

	@Test
	void testGetTags()
	{
		assertEquals(2, classInstance1.getTags().size());
	}

	@Test
	void testSetTags()
	{
		ArrayList<String> newTags = new ArrayList<String>();
		newTags.add("E2");
		newTags.add("G");
		classInstance1.setTags(newTags);
		assertEquals(true, newTags.equals(classInstance1.getTags()));
	}

	@Test
	void testGetCrossListings()
	{
		
		ArrayList<String> listings = new ArrayList<String>();
		listings.add("BIO210");
		classInstance1.createCrossListing(listings);
		ArrayList<String> addedListings = classInstance1.getCrossListings();
		
		
		String listingsString= "";
		for(int i = 0; i < addedListings.size(); i++) {
			listingsString += addedListings.get(i) + " ";
		}
		assertEquals("BIO210 ", listingsString);
	}

	@Test
	void testSetCrossListings()
	{
		ArrayList<String> crossListings = new ArrayList<String>();
		crossListings.add("Bio210");
		crossListings.add("Bio121");
		
		String crossListingsString = "";
		classInstance1.setCrossListings(crossListings);
		for(int i = 0; i < crossListings.size(); i++) {
			crossListingsString += crossListings.get(i);
			if (i != (crossListings.size()- 1)) {
				crossListingsString += ", ";
			}
		}
		
		
		assertEquals("Bio210, Bio121", crossListingsString);
	}
	
	@Test
	void testCreateCrossListings()
	{
		
		ArrayList<String> listings = new ArrayList<String>();
		listings.add("Bio210");
		classInstance1.createCrossListing(listings);
		assertEquals(1, classInstance1.getCrossListings().size());


		String crossListingsString = "";
		for(int i = 0; i < classInstance1.getCrossListings().size(); i++) {
			crossListingsString += classInstance1.getCrossListings().get(i);
			if (i != (classInstance1.getCrossListings().size()- 1)) {
				crossListingsString += ", ";
			}
		}
		
		assertEquals("Bio210", crossListingsString);
	}
	
	@Test
	void testAddTags() {
		assertEquals(2, classInstance1.getTags().size());
		classInstance1.addTags("A");
		assertEquals(3, classInstance1.getTags().size());
		assertEquals("A", classInstance1.getTags().get(2));

	}
	
	@Test
	void testRemoveTags() {
		assertEquals(2, classInstance1.getTags().size());
		assertEquals("E3", classInstance1.getTags().get(0));
		classInstance1.removeTags("E3");
		assertEquals(1, classInstance1.getTags().size());
		assertEquals("L", classInstance1.getTags().get(0));
	}

}
