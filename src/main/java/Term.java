

import java.util.ArrayList;

public class Term
{
	String semester;
	int year;
	ArrayList<ClassInstance> allClasses;
	Boolean isFinalized;
	
	public Term() {}
	
	public Term(String semester, int year) {
		this.semester = semester;
		this.year = year;
		this.allClasses = new ArrayList<ClassInstance>();
		this.isFinalized = false;
		
	}
	
	public void addClass(ClassInstance addClass) {
		allClasses.add(addClass);		
	}
	
	public void removeClassfromTerm(ClassInstance remCourse) {
		allClasses.remove(remCourse);
	}
	
	//returns a list of classes based on their time
	public ArrayList<ClassInstance> getClassesByTime(String time) {
		ArrayList<ClassInstance> timedClasses = new ArrayList<ClassInstance>();
		
		for(int i = 0; i < allClasses.size(); i++) {
			
			if(allClasses.get(i).getClassTime().equals(time)) {
				timedClasses.add(allClasses.get(i));
			}
		}
		return timedClasses;
	}
	
	
	//returns a list of classes based on a given department
	public ArrayList<ClassInstance> getClassesByDept(String dept) {
		ArrayList<ClassInstance> classesByDept = new ArrayList<ClassInstance>();
		for(int i = 0; i < allClasses.size(); i++) {
			String classDept = allClasses.get(i).getDept().getDeptName();
			if(dept.equals(classDept)) {
				classesByDept.add(allClasses.get(i));
			}
		}
		return classesByDept;
	}
	
	//check by each instructor, get a list of all the courses they teach, check the start time and make sure none are the same
	public ArrayList<ClassInstance> getInstructorConflicts() {
		ArrayList<ClassInstance> instructorConflictList = new ArrayList<ClassInstance>();
		
		ArrayList<Instructor> instructors = new ArrayList<Instructor>();
		instructors = getAllTermInstructors();
		
		for(int i = 0; i < instructors.size(); i ++) {
			Instructor currentInstructor = instructors.get(i);
			ArrayList<ClassInstance> instructorCourses = getInstructorCourses(currentInstructor);
			
			for (int j = 0; j< instructorCourses.size(); j++) {
				for(int m= j; m< instructorCourses.size(); m++) {
					
					Boolean isSameTime = instructorCourses.get(j).getClassTime().toString().equals(instructorCourses.get(m).getClassTime().toString());
					Boolean endsDuring = false;
					Boolean startsDuring = false;
					Boolean isSameDay = hasOverlappingDays(instructorCourses.get(j).getClassTime(), instructorCourses.get(m).getClassTime());
					
					if((checkIsTimeBefore(instructorCourses.get(j).getEndTime(), instructorCourses.get(m).getEndTime()) && checkIsTimeBefore(instructorCourses.get(m).getStartTime(), instructorCourses.get(j).getEndTime())) || instructorCourses.get(m).getStartTime().equals(instructorCourses.get(j).getStartTime())) {
						endsDuring = true;
					}
					
					if((checkIsTimeBefore(instructorCourses.get(m).getStartTime(), instructorCourses.get(j).getStartTime()) && checkIsTimeBefore(instructorCourses.get(j).getStartTime(), instructorCourses.get(m).getEndTime())) || instructorCourses.get(m).getEndTime().equals(instructorCourses.get(j).getEndTime())) {
						startsDuring = true;
					}
					
					
					
					if(m != j && (isSameTime || endsDuring || startsDuring) && isSameDay) {
						if(!instructorConflictList.contains(instructorCourses.get(j))) {
							instructorConflictList.add(instructorCourses.get(j));
						}
						if(!instructorConflictList.contains(instructorCourses.get(m))) {
							instructorConflictList.add(instructorCourses.get(m));
						}
					}
				}
			}
				
		}
		
		
		return instructorConflictList;
	}
	
	
//	check by room, filter by room, check that there are no double start time in the same room
	public ArrayList<ClassInstance> getTimeRoomConflicts() {
		ArrayList<ClassInstance> timeRoomConflicts = new ArrayList<ClassInstance>();
		
		//generate a list of all classes
		ArrayList<Room> allRooms = new ArrayList<Room>();
		for(int i = 0; i < allClasses.size(); i ++) {
			if(!allRooms.contains(allClasses.get(i).getRoom())){
				allRooms.add(allClasses.get(i).getRoom());			
			}
		}
		
		for(int i = 0; i < allRooms.size(); i++) {
			Room currentRoom = allRooms.get(i);
			
			//find all of the classes in the current room
			ArrayList<ClassInstance> roomsClasses = new ArrayList<ClassInstance>();
			for(int j = 0; j < allClasses.size(); j++) {
				if(allClasses.get(j).getRoom().toString().equals(currentRoom.toString())) {
					roomsClasses.add(allClasses.get(j));
				}
			}
			
			
			for (int j = 0; j< roomsClasses.size(); j++) {
				for(int m= j; m< roomsClasses.size(); m++) {
					
					Boolean isSameTime = roomsClasses.get(j).getClassTime().toString().equals(roomsClasses.get(m).getClassTime().toString());
					Boolean endsDuring = false;
					Boolean startsDuring = false;
					Boolean isSameDay = hasOverlappingDays(roomsClasses.get(j).getClassTime(), roomsClasses.get(m).getClassTime());
					
					if((checkIsTimeBefore(roomsClasses.get(j).getEndTime(), roomsClasses.get(m).getEndTime()) && checkIsTimeBefore(roomsClasses.get(m).getStartTime(), roomsClasses.get(j).getEndTime())) || roomsClasses.get(m).getStartTime().equals(roomsClasses.get(j).getStartTime())) {
						endsDuring = true;
					}
					
					if((checkIsTimeBefore(roomsClasses.get(m).getStartTime(), roomsClasses.get(j).getStartTime()) && checkIsTimeBefore(roomsClasses.get(j).getStartTime(), roomsClasses.get(m).getEndTime())) || roomsClasses.get(m).getEndTime().equals(roomsClasses.get(j).getEndTime())) {
						startsDuring = true;
					}
					
					
					if(m != j && (isSameTime || endsDuring || startsDuring) && isSameDay) {
						if(!timeRoomConflicts.contains(roomsClasses.get(j))) {
							timeRoomConflicts.add(roomsClasses.get(j));
						}
						if(!timeRoomConflicts.contains(roomsClasses.get(m))) {
							timeRoomConflicts.add(roomsClasses.get(m));
						}
					}
				}
			}
			
		}
		
		return timeRoomConflicts;
	}
	
	//check if courses that have course constraints do not have the same time
	public Boolean checkClassConstraints() {
		Boolean haveConflicts = false;
		
		for(int i = 0; i < allClasses.size(); i++) {
			
			ArrayList<Constraint> courseConstraints = allClasses.get(i).getCourse().getConstraints();
			
			for(int j= 0; j < courseConstraints.size(); j++) {
				
				Boolean isSameTime = courseConstraints.get(j).getClass1().getClassTime().toString().equals(courseConstraints.get(j).getClass2().getClassTime().toString());
				Boolean endsDuring = false;
				Boolean startsDuring = false;
				Boolean isSameDay = hasOverlappingDays(courseConstraints.get(j).getClass1().getClassTime(), courseConstraints.get(j).getClass2().getClassTime());
				
				if((checkIsTimeBefore(courseConstraints.get(j).getClass1().getEndTime(), courseConstraints.get(j).getClass2().getEndTime()) && checkIsTimeBefore(courseConstraints.get(j).getClass2().getStartTime(), courseConstraints.get(j).getClass1().getEndTime())) || courseConstraints.get(j).getClass2().getStartTime().equals(courseConstraints.get(j).getClass1().getStartTime())) {
					endsDuring = true;
				}
				
				if((checkIsTimeBefore(courseConstraints.get(j).getClass2().getStartTime(), courseConstraints.get(j).getClass1().getStartTime()) && checkIsTimeBefore(courseConstraints.get(j).getClass1().getStartTime(), courseConstraints.get(j).getClass2().getEndTime())) || courseConstraints.get(j).getClass2().getEndTime().equals(courseConstraints.get(j).getClass1().getEndTime())) {
					startsDuring = true;
				}
				
				
				if((isSameTime || endsDuring || startsDuring) && isSameDay) {
					haveConflicts = true;
				}
			}
			
		}
		
		return haveConflicts;
	}
	
	
	//checks start and ending times to make sure the events do not overlap
	public Boolean checkIsTimeBefore(String time1, String time2) {
		Boolean isBefore = false;
		String[] time1Parts = time1.split(":");
		String[] time2Parts = time2.split(":");
		
		int time1Hour = Integer.parseInt(time1Parts[0]);
		int time2Hour = Integer.parseInt(time2Parts[0]);
		int time1Min = Integer.parseInt(time1Parts[1].substring(0,2));
		int time2Min = Integer.parseInt(time2Parts[1].substring(0,2));
		String time1Period = time1Parts[1].substring(2,4);
		String time2Period = time2Parts[1].substring(2,4);
		
		if (time1Hour < time2Hour && ((time1Period.equals("AM") && time2Period.equals("AM")) ||  (time1Period.equals("PM") && time2Period.equals("PM")) ) ) {
			return true;
		}
		
		if (time1Hour == time2Hour && time1Min < time2Min && ((time1Period.equals("AM") && time2Period.equals("AM")) ||  (time1Period.equals("PM") && time2Period.equals("PM")) )) {
			return true;
		}
		
		if((time1Period.equals("AM") && time2Period.equals("PM"))) {
			return true;
		}
		
		if(time1Period.equals(time2Period) && time1Hour== time2Hour && time1Min == time2Min) {
			return isBefore;
		}
		
		
		return isBefore;
	}
	
	//check instructor hours, check class constraints, get time room conflicts, and get instructor conflicts
	public Boolean checkCorrectness() {
		ArrayList<ClassInstance> instructorConflicts = getInstructorConflicts();
		ArrayList<ClassInstance> timeRoomConflicts = getTimeRoomConflicts();
		Boolean hasClassConstraintsConflict = checkClassConstraints();
		
		if(instructorConflicts.size() == 0 && timeRoomConflicts.size() == 0 && !hasClassConstraintsConflict) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//returns a list of all of the instructors in one term
	public ArrayList<Instructor> getAllTermInstructors(){
		ArrayList<Instructor> instructors = new ArrayList<Instructor>();
		
		for(int i = 0; i< allClasses.size(); i++) {
			Boolean isDuplicate = false;
			for(int j = 0; j < instructors.size(); j++) {
				if (allClasses.get(i).getInstructor().toString().equals(instructors.get(j).toString())) {
					isDuplicate = true;
				}
			}
			
			if(!isDuplicate) {
				instructors.add(allClasses.get(i).getInstructor());
			}
		}
		
		return instructors;
	}
	
	//returns all the classes of a given individual instructor
	public ArrayList<ClassInstance> getInstructorCourses(Instructor instructor){
		ArrayList<ClassInstance> classes = new ArrayList<ClassInstance>();
		
		for(int i = 0; i < allClasses.size(); i++) {
			if(allClasses.get(i).getInstructor().toString().equals(instructor.toString())) {
				classes.add(allClasses.get(i));
				
			}
		}
		return classes;
	}
	
	//method to calculate if the days the classes are scheduled has any overlap on a given day
	public Boolean hasOverlappingDays(String classTime1, String classTime2) {
		Boolean hasOverlap = false;
		String[] class1Split = classTime1.split(" ");
		String[] class2Split = classTime2.split(" ");
		String class1Days = class1Split[0];
		String class2Days = class2Split[0];
		
		if(class1Days.equals(class2Days)) {
			return true;
		}
		
		for(int i = 0; i < class1Days.length(); i++) {
			for(int j = 0; j < class2Days.length(); j++) {
				if(class1Days.charAt(i) != ' ' && class1Days.charAt(i) == class2Days.charAt(j)) {
					hasOverlap = true;
				}
			}
		}
		
		return hasOverlap;
	}
	
	//sets all classes at once
	public void setAllClasses(ArrayList<ClassInstance> classes) {
		if(this.getAllClasses()!= null) {
			this.allClasses.clear();
		}
		
		this.allClasses = classes;
	}
	
	
	//marks a term as final
	public void markAsFinal() {
		this.isFinalized = true;
	}
	
	//Getter and Setter methods for relevant variables

	/**
	 * @return the semester
	 */
	public String getSemester()
	{
		return semester;
	}

	/**
	 * @param semester the semester to set
	 */
	public void setSemester(String semester)
	{
		this.semester = semester;
	}

	/**
	 * @return the year
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

	/**
	 * @return the allClasses
	 */
	public ArrayList<ClassInstance> getAllClasses()
	{
		return allClasses;
	}

	/**
	 * @return the isFinalized
	 */
	public Boolean getIsFinalized()
	{
		return isFinalized;
	}
	
	/**
	 * @param year the year to set
	 */
	public void setIsFinalized(Boolean isFinalized)
	{
		this.isFinalized = isFinalized;
	}

	@Override
	public String toString()
	{
		return "Term [semester=" + semester + ", year=" + year + "]";
	}
	
	
	
}
