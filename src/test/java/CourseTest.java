

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class CourseTest
{
	Course testCourse;
	Department dept;
	ArrayList<String> tags;
	ArrayList<String> crossListings;
	ClassInstance classInstance1;
	ClassInstance classInstance2;
	
	
	@BeforeEach
	void setUp() throws Exception
	{
		tags = new ArrayList<String>();
		tags.add("E1");
		tags.add("A");
		testCourse = new Course("Bio110", "Intro to Biology", tags);
		
		
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		Department deptTest = new Department(deptHeadTest, "French");
		Instructor instructorTest = new Instructor("Allison Conelly", deptTest);
		Room roomTest = new Room("Crounse", 307);
		ArrayList<String> tagArray = new ArrayList<String>();
		
		tagArray.add("E3");
		tagArray.add("L");
		Term termTest = new Term("Spring", 2025);
		Course courseTest = new Course("FRE270", "Group Conversation", tagArray);
		classInstance1 = new ClassInstance(courseTest, termTest, instructorTest, "MWF 10:20 - 11:20AM", roomTest, deptTest);
		
		//setup for Class Instance 2
		DepartmentHead deptHeadTest2 = new DepartmentHead("Michael Bradshaw", "anonDragoLord", "IamAw3some");
		Department deptTest2 = new Department(deptHeadTest, "CSC");
		Instructor instructorTest2 = new Instructor("Michael Bradshaw", deptTest);
		Room roomTest2 = new Room("Olin", 212);
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("E1");
		tags2.add("G");
		Term termTest2 = new Term("Spring", 2025);
		Course courseTest2 = new Course("CSC360", "Software Design", tags2);
		classInstance2 = new ClassInstance(courseTest2, termTest2, instructorTest2, "TR 10:20AM - 12:40PM", roomTest2, deptTest2);
		
	}

	@Test
	void testCourse()
	{
		assertEquals("Bio110: Intro to Biology, Tags: E1, A", testCourse.toString());
	}

	
	@Test
	void testModifyCrossCourseCodes()
	{
		testCourse.createCrossListing("Bio210");
		assertEquals("Bio210", testCourse.getCrossListings().get(0));
		testCourse.modifyCrossCourseCodes("Bio210", "ENS110");
		assertEquals(1, testCourse.getCrossListings().size());
		assertEquals("ENS110", testCourse.getCrossListings().get(0));
	}

	@Test
	void testCreateCourseConstraint()
	{
		
		testCourse.createCourseConstraint("Can't be taught at the same time", classInstance1, classInstance2);
		testCourse.createCourseConstraint("The first one but switched", classInstance2, classInstance1);
		assertEquals(2, testCourse.getConstraints().size());
		
		assertEquals("Constraint [constraintName=Can't be taught at the same time, class1=Group Conversation, class2=Software Design]", testCourse.getConstraints().get(0).toString());
		assertEquals("Constraint [constraintName=The first one but switched, class1=Software Design, class2=Group Conversation]", testCourse.getConstraints().get(1).toString());
		
	}

	@Test
	void testRemoveConstraint()
	{
		testCourse.createCourseConstraint("Can't be taught at the same time", classInstance1, classInstance2);
		testCourse.createCourseConstraint("The first one but switched", classInstance2, classInstance1);

		assertEquals(2, testCourse.getConstraints().size());
		
		testCourse.removeConstraint(testCourse.getConstraints().get(0));
		
		assertEquals(1, testCourse.getConstraints().size());
		assertEquals("Constraint [constraintName=The first one but switched, class1=Software Design, class2=Group Conversation]", testCourse.getConstraints().get(0).toString());
		
	}

	@Test
	void testGetCourseCode()
	{
		assertEquals("Bio110", testCourse.getCourseCode());
	}

	@Test
	void testSetCourseCode()
	{
		testCourse.setCourseCode("Bio210");
		assertEquals("Bio210", testCourse.getCourseCode());
	}

	@Test
	void testGetTitle()
	{
		assertEquals("Intro to Biology", testCourse.getTitle());
	}

	@Test
	void testSetTitle()
	{
		testCourse.setTitle("Intro to Cell Bio");
		assertEquals("Intro to Cell Bio", testCourse.getTitle());
	}

	@Test
	void testGetTags()
	{
		assertEquals(true, tags.equals(testCourse.getTags()));
	}

	@Test
	void testSetTags()
	{
		ArrayList<String> newTags = new ArrayList<String>();
		newTags.add("E2");
		newTags.add("G");
		testCourse.setTags(newTags);
		assertEquals(true, newTags.equals(testCourse.getTags()));
	}

	@Test
	void testGetCrossListings()
	{
		testCourse.createCrossListing("BIO210");
		
		ArrayList<String> listings = new ArrayList<String>();
		listings.add("BIO210");
		ArrayList<String> addedListings = testCourse.getCrossListings();
		
		
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
		testCourse.setCrossListings(crossListings);
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
		testCourse.createCrossListing("Bio121");
		assertEquals(1, testCourse.getCrossListings().size());


		String crossListingsString = "";
		for(int i = 0; i < testCourse.getCrossListings().size(); i++) {
			crossListingsString += testCourse.getCrossListings().get(i);
			if (i != (testCourse.getCrossListings().size()- 1)) {
				crossListingsString += ", ";
			}
		}
		
		assertEquals("Bio121", crossListingsString);
	}


	@Test
	void testGetConstraints()
	{
		testCourse.createCourseConstraint("Can't be taught at the same time", classInstance1, classInstance2);	
		testCourse.createCourseConstraint("The first one but switched", classInstance2, classInstance1);
	
		String constraintTester = "";
		for(int i = 0; i < testCourse.getConstraints().size(); i++) {
			constraintTester += testCourse.getConstraints().get(i) + " ";
		}
		
		assertEquals("Constraint [constraintName=Can't be taught at the same time, class1=Group Conversation, class2=Software Design] "
				+ "Constraint [constraintName=The first one but switched, class1=Software Design, class2=Group Conversation] ", constraintTester);
		
	}
	
	@Test
	void testAddTags() {
		assertEquals(2, classInstance1.getCourse().getTags().size());
		classInstance1.getCourse().addTags("A");
		assertEquals(3, classInstance1.getCourse().getTags().size());
		assertEquals("A", classInstance1.getCourse().getTags().get(2));

	}
	
	@Test
	void testRemoveTags() {
		assertEquals(2, classInstance1.getCourse().getTags().size());
		assertEquals("E3", classInstance1.getCourse().getTags().get(0));
		classInstance1.getCourse().removeTags("E3");
		assertEquals(1, classInstance1.getCourse().getTags().size());
		assertEquals("L", classInstance1.getCourse().getTags().get(0));
	}
	

}
