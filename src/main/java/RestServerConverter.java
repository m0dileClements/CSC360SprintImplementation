import java.util.ArrayList;

import org.springframework.web.client.RestClient;
//put delete team in before all of tests 
public class RestServerConverter extends Converter
{
//	public static void main(String[] args)
//	{
//		
//	}
	
	Boolean isLaunched = false;
	RestClient defaultClient = RestClient.create();
	
	public record ArchiveWrapper(String request, Boolean successful, String message, ArrayList<Team> data) {};
	public record Team(String name, String description, String location) {};
	
	public record DeptList(String request, Boolean successful, String message, ArrayList<Dept> data) {};
	public record Dept(String name, String description, String location) {};
	public record DeptDetails(String request, Boolean successful, String message, DeptData data) {};
	public record DeptData(String dept, ArrayList<FacultyInDept> faculty) {};
	public record FacultyInDept(String name, String location) {};

	public record RoomList(String request, Boolean successful, String message, ArrayList<RoomInfo> data) {};
	public record RoomInfo(String name, String description, String location) {};
	public record SpecRoomWrapper(String request, Boolean successful, String message, SpecRoomInfo data) {};
	public record SpecRoomInfo(String building, String roomNumber, String id) {};
	
	
	public record CourseList(String request, Boolean successful, String message, ArrayList<CourseLocations> data) {};
	public record CourseLocations(String name, String description, String location) {};
	public record SpecificCourseInfo(String request, Boolean successful, String message, CourseData data) {};
	public record CourseData(String season, int year, String dept, String num, String section, String name, String instructor, String meetingTime, String building, String roomNumber, String id) {};
	
	public record FacultyList(String request, Boolean successful, String message, ArrayList<Faculty> data) {};
	public record Faculty(String name, String description, String location) {};
	
	public void clearData(Main main) {
		if(main.allUsers.size() != 0) {
			main.allUsers.clear();
		}
		if(main.allTerms.size() != 0) {
			main.allTerms.clear();
		}
		if(main.allDepts.size() != 0) {
			main.allDepts.clear();
		}
		if(main.allClasses.size() != 0) {
			main.allClasses.clear();
		}
		if(main.allInstructors.size() != 0) {
			main.allInstructors.clear();
		}
		if(main.allRooms.size() != 0) {
			main.allRooms.clear();
		}
		main.currentUser = null;
		
		//delete the new team info when clearData is called
		ArchiveWrapper teamsInfo = defaultClient.get() 
				.uri("http://localhost:9000/v1") 
				.retrieve()
				.body(ArchiveWrapper.class);
		if(teamsInfo.data().get(0).name().equals("new")) {
			defaultClient.delete()
					.uri("http://localhost:9000/v1/new")
					.retrieve()
					.body(Team.class);
		}
		
	}
	
	public void loadData(Main main) {
		if (this.isLaunched) {
			return;
		} else {
		this.isLaunched = true;
		Registrar permissionsToLoad = new Registrar("Loading Permissions", "", "");
		//defaultClient  = RestClient.create();
		
		//import all the instructors and depts
		//Archive dept section
		DeptList deptWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/dept")
				.retrieve()
				.body(DeptList.class);
		//Stores the list of dept
		ArrayList<Dept> deptAddresses = deptWrapper.data();
		
		//goes to the address of each course
		for (int i = 0; i < deptAddresses.size(); i++) {
			//find specific dept location
			DeptDetails specificDeptWrapper = defaultClient.get()
				.uri(deptAddresses.get(i).location())
				.retrieve()
				.body(DeptDetails.class);
			
			importDept(main, specificDeptWrapper);
			
		}
		
		
		//add all rooms
		RoomList roomWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/room")
				.retrieve()
				.body(RoomList.class);
		ArrayList<RoomInfo> rooms = roomWrapper.data();
		for(int i = 0; i <rooms.size(); i++) {
			SpecRoomWrapper specRoomWrapper  = defaultClient.get()
					.uri(rooms.get(i).location())
					.retrieve()
					.body(SpecRoomWrapper.class);
			importRoom(main, specRoomWrapper);
		}
		
		//add all Courses
		
		CourseList courseLocationWrapper  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/course")
				.retrieve()
				.body(CourseList.class);
		//Stores the list of courses
		ArrayList<CourseLocations> courseAddress = courseLocationWrapper.data();
		
		//goes to the address of each course and prints the name and instructor
		for (int i = 0; i < courseAddress.size(); i++) {
			SpecificCourseInfo specificCourseWrapper = defaultClient.get()
				.uri(courseAddress.get(i).location())
				.retrieve()
				.body(SpecificCourseInfo.class);
			
			CourseData courseInfo = specificCourseWrapper.data();
			importCourse(main, permissionsToLoad, courseInfo);
			
		}
		
		
		//create new spaces for info		
		createNewServerListHolders();
		}
	}
	
	public String reformattedClassTime(String classMeetingTime) {
		String formattedTimeString = "";
		if (classMeetingTime != null) {
			String[] separatedMeetingTimeList = classMeetingTime.split(" ");
			String days = separatedMeetingTimeList[0];
			String[] separatedTimeList = separatedMeetingTimeList[1].split("-");
			String startingTime = separatedTimeList[0];
			String endingTime = separatedTimeList[1];
			
			//converts starting time to integer values
			String[] startTimeParts = startingTime.split(":");
			String[] endTimeParts = endingTime.split(":");
			int startHour= Integer.parseInt(startTimeParts[0]);
			int startMins= Integer.parseInt(startTimeParts[1]);				
			int endHour = Integer.parseInt(endTimeParts[0]);
			int endMins = Integer.parseInt(endTimeParts[1]);
			String startTimeHalf = "";
			String endTimeHalf = "";
			
			
			if(8 <= startHour && startHour < 12) {
				startTimeHalf = "AM";
			} else {
				startTimeHalf = "PM";
			}
			
			if(8 <= endHour && endHour < 12) {
				endTimeHalf = "AM";
			} else {
				endTimeHalf = "PM";
			}
			
			
			String formattedStartMins = "";
			if(startMins == 0) {
				formattedStartMins = "00";
			} else {
				formattedStartMins = Integer.toString(startMins);
			}
			
			String formattedEndMins = "";
			if(endMins == 0) {
				formattedEndMins = "00";
			}else {
				formattedEndMins = Integer.toString(endMins);
			}
			
			formattedTimeString = days + " " + startHour + ":" + formattedStartMins + startTimeHalf + " - " + endHour + ":" + formattedEndMins + endTimeHalf;
		}
		return formattedTimeString;
	}
	
	public void importDept(Main main, DeptDetails specificDeptWrapper) {
		ArrayList<FacultyInDept> facultyList = specificDeptWrapper.data().faculty();
		
		//add new dept with first instructor listed as dept head
		DepartmentHead deptHead = new DepartmentHead(facultyList.get(0).name, "", "");
		Department dept = new Department(deptHead, specificDeptWrapper.data().dept);
		main.allDepts.add(dept);
		
		//add all instructors in specific dept
		for(int j = 0; j < facultyList.size(); j++) {
			Instructor tempInstructor = new Instructor(facultyList.get(j).name, dept);
			main.addInstructor(tempInstructor);
		}
		
	}
	
	public void importRoom(Main main, SpecRoomWrapper specRoomWrapper) {
		SpecRoomInfo roomData = specRoomWrapper.data();
		
		Room tempRoom = new Room(roomData.building(), roomData.roomNumber());
		main.addRoom(tempRoom);
		
	}
	
	public void importCourse(Main main, User permissionsToLoad, CourseData courseInfo) {
		String courseCode = courseInfo.num() + courseInfo.section();
		String courseTitle = courseInfo.name();
		
		ArrayList<String> courseTags = new ArrayList<String>();
		
		//check if term is already there
		String term = "Term [semester=" + courseInfo.season() + ", year=" + courseInfo.year() + "]";
		Boolean alreadyExists = false;
		int existingIndex = -1;
		for(int j = 0; j < main.getAllTerms().size(); j++) {
			if(main.getAllTerms().get(j).toString().equals(term)) {
				alreadyExists = true;
				existingIndex = j;
			}
		}
		Term courseTerm;
		if(main.getAllTerms().size() == 0 || !alreadyExists) {
			courseTerm = new Term(courseInfo.season(), courseInfo.year());
			main.addTerm(permissionsToLoad, courseTerm);
		} else {
			courseTerm = main.getAllTerms().get(existingIndex);
		}
		
		
		//search dept
		alreadyExists = false;
		existingIndex = -1;
		for(int j = 0; j < main.getAllDepts().size(); j++) {
			if(main.getAllDepts().get(j).getDeptName().equals(courseInfo.dept())) {
				alreadyExists = true;
				existingIndex = j;
			}
		}
		Department courseDept;
		if(main.getAllDepts().size() == 0 || !alreadyExists) {
			//if the dept does not exist yet, will have the dept head be the instructor listed
			DepartmentHead newDeptHead = new DepartmentHead(courseInfo.instructor(), "", "");
			courseDept = new Department(newDeptHead, courseInfo.dept());
			main.addDept(courseDept);
		} else {
			courseDept = main.getAllDepts().get(existingIndex);
		}
		
		
		//search instructor
		alreadyExists = false;
		existingIndex = -1;
		for(int j = 0; j < main.getAllInstructors().size(); j++) {
			if(main.getAllInstructors().get(j).getName().equals(courseInfo.instructor())) {
				alreadyExists = true;
				existingIndex = j;
			}
		}
		Instructor courseInst;
		if(main.getAllInstructors().size() == 0 || !alreadyExists) {
			//if the instructor does not exist yet, will take the dept from the course they are teaching
			courseInst = new Instructor(courseInfo.instructor(), courseDept);
			main.addInstructor(courseInst);
		} else {
			courseInst = main.getAllInstructors().get(existingIndex);
		}
		
		
		
		//search room
		alreadyExists = false;
		existingIndex = -1;
		for(int j = 0; j < main.getAllRooms().size(); j++) {
			String roomString = main.getAllRooms().get(j).toString();
			
			if(roomString.equals(courseInfo.building() + " " + courseInfo.roomNumber())) {
				alreadyExists = true;
				existingIndex = j;
			}
		}
		Room courseRoom;
		if(main.getAllRooms().size() == 0 || !alreadyExists) {
			courseRoom = new Room(courseInfo.building(), courseInfo.roomNumber());
			main.addRoom(courseRoom);
		} else {
			courseRoom = main.getAllRooms().get(existingIndex);
		}
		
		
		String courseTime = reformattedClassTime(courseInfo.meetingTime());
		
		ClassInstance tempCourse = new ClassInstance(courseCode, courseTitle, courseTags, courseTerm, courseInst, courseTime, courseRoom , courseDept);
		main.addClassInstance(tempCourse);
		
		
	}
	
	
	
	public void createNewServerListHolders() {

		Team response = defaultClient.post() 
			.uri("http://localhost:9000/v1/new") 
			.body(new Team("new", "holder for all new information", "http://localhost:9000/v1/new")) 
			.retrieve()
			.body(Team.class);
		
		CourseLocations classTeam = defaultClient.post() 
				.uri("http://localhost:9000/v1/new/classes") 
				.body(new Team("classes", "holder for classes", "http://localhost:9000/v1/new/classes")) 
				.retrieve()
				.body(CourseLocations.class);
		
		
		RoomInfo roomTeam = defaultClient.post() 
				.uri("http://localhost:9000/v1/new/rooms") 
				.body(new Team("rooms", "holder for rooms", "http://localhost:9000/v1/new/rooms")) 
				.retrieve()
				.body(RoomInfo.class);
		
		
		Dept deptsTeam = defaultClient.post() 
				.uri("http://localhost:9000/v1/new/depts") 
				.body(new Team("depts", "holder for depts", "http://localhost:9000/v1/new/depts")) 
				.retrieve()
				.body(Dept.class);
		
		
		Faculty facultyTeam = defaultClient.post() 
				.uri("http://localhost:9000/v1/new/faculty") 
				.body(new Team("depts", "holder for depts", "http://localhost:9000/v1/new/faculty")) 
				.retrieve()
				.body(Faculty.class);
		
	}
	
//	public void uploadData(Object object) {
//		
//		if(object.getClass().equals(ClassInstance.class)) {
//			ClassInstance newObject = (ClassInstance) object;
//			uploadClass(newObject);
//		}
//		
//		if(object.getClass().equals(Term.class)) {
//			Term newObject = (Term) object;
//			uploadTerm(newObject);
//		}
//		
//		if(object.getClass().equals(Room.class)) {
//			Room newObject = (Room) object;
//			uploadRoom(newObject);		
//		}
//		
//		if(object.getClass().equals(Department.class)) {
//			Department newObject = (Department) object;
//			uploadDept(newObject);
//		}
//
//		if(object.getClass().equals(Instructor.class)) {
//			Instructor newObject = (Instructor) object;
//			uploadInstructor(newObject);
//		}
//		
//		if(object.getClass().equals(Constraint.class)) {
//			if(object.getClass().equals(MustBeOfferedConstraint.class)) {
//				MustBeOfferedConstraint newObject = (MustBeOfferedConstraint) object;
//				uploadMustBeOfferedConstraint(newObject);
//			}
//			if(object.getClass().equals(MustOverlapConstraint.class)) {
//				MustOverlapConstraint newObject = (MustOverlapConstraint) object;
//				uploadMustOverlapConstraint(newObject);
//			}
//			if(object.getClass().equals(NonOverlappingConstraint.class)) {
//				NonOverlappingConstraint newObject = (NonOverlappingConstraint) object;
//				uploadNonOverlappingConstraint(newObject);
//			}	
//		}
//	}
//	
//	
//	public void uploadClass(ClassInstance classInstance) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/classes/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadTerm(Term term) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/terms/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadRoom(Room room) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/rooms/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadDept(Department dept) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/depts/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadInstructor(Instructor instructor) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/instructors/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadMustBeOfferedConstraint(MustBeOfferedConstraint constraint) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/constraints/mustBeOffered/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadMustOverlapConstraint(MustOverlapConstraint constraint) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/constraints/mustOverlap/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
//	
//	public void uploadNonOverlappingConstraint(NonOverlappingConstraint constraint) {
//		String specificCourse = defaultClient.post() 
//				.uri("http://localhost:9000/v1/new/constraints/nonOverlapping/h") 
//				.body(new CourseData("Spring", 2025, "biology", "a", "b", "c", "d", "e", "f", "g", "h")) 
//				.retrieve()
//				.body(String.class);
//		System.out.println(specificCourse);
//		
//	}
}
