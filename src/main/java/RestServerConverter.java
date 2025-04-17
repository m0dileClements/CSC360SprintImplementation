import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.springframework.web.client.RestClient;

public class RestServerConverter extends Converter
{
	
	public record Archive(String request, Boolean successful, String message, ArrayList<Course> data) {};
	public record Course(String name, String description, String location) {};
	
	public record CourseDetails(String request, Boolean successful, String message, CourseData data) {};
	public record CourseData(String season, int year, String dept, String num, String section, String name, String instructor, String meetingTime, String building, String roomNumber, String id) {};
	
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
		if(main.allCourses.size() != 0) {
			main.allCourses.clear();
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
		
	}
	
	public void loadData(Main main) {
		try {
		InetAddress localhost = InetAddress.getLocalHost();
		RestClient defaultClient  = RestClient.create();
		Archive archiveInfo  = defaultClient.get()
				.uri("http://localhost:9000/v1/Archive/course")
				.retrieve()
				.body(Archive.class);
		//Stores the list of courses
		ArrayList<Course> courseAddress = archiveInfo.data();
		
		//goes to the address of each course
		for (int i = 0; i < courseAddress.size(); i++) {
			CourseDetails specificCourseWrapper = defaultClient.get()
				.uri(courseAddress.get(i).location())
				.retrieve()
				.body(CourseDetails.class);
			//put the actual course information here
			System.out.println(specificCourseWrapper.data().name + ": " + specificCourseWrapper.data().instructor);
			
			
			
			
		}
		} catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
	}
	
	
}
