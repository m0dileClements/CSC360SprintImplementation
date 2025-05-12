

//import java.util.ArrayList;

//import java.util.ArrayList;

import java.util.ArrayList;

public class ClassInstance
{
	//Course course;
	String courseCode;
	String title;
	ArrayList<String> tags;
	ArrayList<String> crossListings;
	Term term;
	Instructor instructor;
	String classTime;
	String startTime;
	String endTime;
	Room room;
	Department dept;
	double classLength;
	Boolean hasFalseLimit;
	
	public ClassInstance(String courseCode, String title, ArrayList<String> GETags, Term term, Instructor instructor, String classTime, Room room, Department dept) {
		this.courseCode = courseCode;
		this.title = title;
		this.tags = GETags;	
		this.crossListings = new ArrayList<String>();
		this.term = term;
		this.instructor = instructor;
		this.classTime = classTime;
		generateClassTimeLength();
		this.room = room;
		this.dept = dept;
		this.hasFalseLimit = false;
	}
		
	public ClassInstance() {}
		//takes a string of a room name (i.e. "Olin 222") into corresponding parts needed for the Room class
		public void setRoom(String roomName, String roomNumber)
		{
			//String[] roomInfo = roomName.split(" ");
			Room newRoom = new Room(roomName, roomNumber);
			this.room = newRoom;
		}
		
		
		//sets a class time and calls method to calculate the start time, end time, and class length
		public void setClassTime(String classTime) {
			this.classTime = classTime;
			generateClassTimeLength();
		}
		
		
		public String getClassTime() {
			return this.classTime;
		}
		
		
		//calculates the start time, end time, and class length based on string of classTime
		public void generateClassTimeLength() {
			if (classTime != null) {
				String[] separatedTimeList = classTime.split(" ");
				String days = separatedTimeList[0];
				String startingTime = separatedTimeList[1];
				String endingTime = separatedTimeList[3];
				
				//converts starting time to integer values
				String[] startTimeParts = startingTime.split(":");
				String[] endTimeParts = endingTime.split(":");
				int startHour= 0;
				int startMins= 0;				
				int endHour = 0;
				int endMins = 0;
				
				//converts the ending time to integer values
				//check to see if the start time defines their own AM or PM, 
				if(startingTime.length() >= 6) {
					if((!startingTime.substring(0,2).equals("12")) && (startingTime.charAt(4) == 'P' || startingTime.charAt(5) == 'P')) {
						startHour = Integer.parseInt(startTimeParts[0]) + 12;
						startMins = Integer.parseInt(startTimeParts[1].substring(0, 2));
					} else {
						startHour = Integer.parseInt(startTimeParts[0]);
						startMins = Integer.parseInt(startTimeParts[1].substring(0, 2));
					}
					
				}else if (startingTime.length() <= 5) {
					//checks if AM or PM from the ending time as it does not define its own
					if((!startingTime.substring(0,2).equals("12")) && (endingTime.charAt(5) == 'P'|| endingTime.charAt(4) == 'P')) {
						startHour = Integer.parseInt(startTimeParts[0]) + 12;
						startMins = Integer.parseInt(startTimeParts[1].substring(0, 2));
					} else {
						startHour = Integer.parseInt(startTimeParts[0]);
						startMins = Integer.parseInt(startTimeParts[1]);
					}
				}
				
				//converts the ending time to integer values
				if(endingTime.charAt(5) == 'P'|| endingTime.charAt(4) == 'P') {
					if(!endingTime.substring(0,2).equals("12")) {
						endHour = Integer.parseInt(endTimeParts[0]) + 12;
						endMins = Integer.parseInt(endTimeParts[1].substring(0,2));
					}else {
						endHour = Integer.parseInt(endTimeParts[0]);
						endMins = Integer.parseInt(endTimeParts[1].substring(0,2));
					}
					
				} else {
					endHour = Integer.parseInt(endTimeParts[0]);
					endMins = Integer.parseInt(endTimeParts[1].substring(0,2));
				}
				
				//calculated the passed time
				int hoursElapsed = endHour - startHour;
				int minutesElapsed = endMins-startMins;
				
				//calculates minutes overlapped if it is not a full hour, but changed the hour integer to change 
				//(i.e. 12:30 - 1:20 is not a hour and -10 minutes, but 50 minutes). 
				if (endMins < startMins) {
					minutesElapsed = 60 - (startMins - endMins);
					hoursElapsed -=1;
				}
				
				//puts number into a decimal form in base 10 decimal
				double minsDecimal = (double)minutesElapsed / 60;
				
				this.classLength = (days.length())*((double)hoursElapsed + minsDecimal);
				//adds the startTime and endTime each with their own AM and PM
				if(startingTime.length() <= 5) {
					if (endingTime.length() == 7) {
						this.startTime = startingTime + endingTime.substring(5);
					} 
					if (endingTime.length() == 6) {
						this.startTime = startingTime + endingTime.substring(4);
					}
				} else {
					this.startTime = startingTime;
				}
				this.endTime = endingTime;
			}
			
			//double profHours = instructor.getHours();
			//instructor.setHours(profHours+ this.classLength);
		}
		
		
		
		//creates a deep copy of a class
		public ClassInstance getDeepCopy(User u) {
			ClassInstance classCopy = new ClassInstance();
			
			if(u.getCanCreate()) {
				classCopy.courseCode = this.courseCode;
				classCopy.title = this.title;
				classCopy.tags = this.tags;
				classCopy.crossListings = this.crossListings;
				classCopy.term = this.term;
				classCopy.instructor = this.instructor;
				classCopy.classTime = this.classTime;
				classCopy.startTime = this.startTime;
				classCopy.endTime = this.endTime;
				classCopy.room = this.room;
				classCopy.dept = this.dept;
				classCopy.classLength = this.classLength;
				classCopy.hasFalseLimit = this.hasFalseLimit;
			}
			return classCopy;
		}
	//
	
	public void modifyClassInstance(User u, ClassInstance updatedClass) {
		if(u.getCanEdit()) {
			courseCode = updatedClass.getCourseCode();
			title = updatedClass.getTitle();
			tags = updatedClass.getTags();
			crossListings = updatedClass.getCrossListings();
			term = updatedClass.getTerm();
			instructor = updatedClass.getInstructor();
			classTime = updatedClass.getClassTime();
			startTime = updatedClass.getStartTime();
			endTime = updatedClass.getEndTime();
			room = updatedClass.getRoom();
			dept = updatedClass.getDept();
			classLength = updatedClass.getClassLength();
			hasFalseLimit = updatedClass.getHasFalseLimit();
			
		}
	}
	
	//Getter and Setter methods for all relevant variables
	
	//@return the term
	 
	public Term getTerm()
	{
		return term;
	}
	
	
	//@param term the term to set
	
	public void setTerm(Term term)
	{
		this.term = term;
	}
	//@return the instructor
	 
	public Instructor getInstructor()
	{
		return instructor;
	}
	
	
	//@param instructor the instructor to set
	 
	public void setInstructor(Instructor instructor)
	{
		this.instructor = instructor;
	}
	
	
	//the start and end times are calculated by the generateClassTimeLength method
	// and will be automatically calculated. If the time must be reset, the 
	//setClassTime method will update the information. 
	//@return the startTime
	
	public String getStartTime()
	{
		return startTime;
	}
	
	
	// @return the endTime
	 
	public String getEndTime()
	{
		return endTime;
	}
	
	

	
	//@return the room
	 
	public Room getRoom()
	{
		return room;
	}

	/**
 * @return the classLength
 */
	public double getClassLength()
	{
		return classLength;
	}

	/**
	 * @return the dept
	 */
	public Department getDept()
	{
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(Department dept)
	{
		this.dept = dept;
	}

	/**
	 * @return the hasFalseLimit
	 */
	public Boolean getHasFalseLimit()
	{
		return hasFalseLimit;
	}

	/**
	 * @param hasFalseLimit the hasFalseLimit to set
	 */
	public void activateFalseLimit()
	{
		this.hasFalseLimit = true;
	}
	
	public void resetFalseLimit() {
		hasFalseLimit = false;
	}


	@Override
	public String toString()
	{
		String tagString = "";
		for (int i = 0; i < tags.size(); i++) {
			tagString += tags.get(i);
			if(i != tags.size()-1) {
				tagString += ", ";
			}
		}
		
		return "ClassInstance [courseCode=" + courseCode + ": " + title + ", Tags: " + tagString + ", term=" + term.toString() + ", instructor=" + instructor + ", classTime="
				+ classTime + ", room=" + room + ", dept=" + dept + ", classLength=" + classLength + ", hasFalseLimit="
				+ hasFalseLimit + "]";
	}
	
	
	public void modifyCrossCourseCodes(ArrayList<String> crossListingsReplacement) {
		crossListings.clear();
		for (int i = 0; i < crossListingsReplacement.size(); i++) {
			crossListings.add(crossListingsReplacement.get(i));
		}
		
		
	}
	
	//Getter and Setter methods for all relevant variables
	
	/**
	 * @return the courseCode
	 */
	public String getCourseCode()
	{
		return courseCode;
	}

	/**
	 * @param courseCode the courseCode to set
	 */
	public void setCourseCode(String courseCode)
	{
		this.courseCode = courseCode;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the tags
	 */
	public ArrayList<String> getTags()
	{
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(ArrayList<String> tags)
	{
		this.tags.clear();
		this.tags = tags;
	}
	
	public void addTags(String tag)
	{
		this.tags.add(tag);
	}
	
	//removes specific tag from the tag lists
	public void removeTags(String tag)
	{
		for(int i = 0; i< tags.size(); i++) {
			if(tags.get(i) == tag) {
				this.tags.remove(i);
			}
		}
	}

	/**
	 * @return the crossListings
	 */
	public ArrayList<String> getCrossListings()
	{
		return crossListings;
	}

	/**
	 * @param crossListings the crossListings to set
	 */
	public void setCrossListings(ArrayList<String> crossListings)
	{
		this.crossListings = crossListings;
	}
	
	public void createCrossListing(ArrayList<String> listings) {
		for(int i = 0; i < listings.size(); i++) {
			crossListings.add(listings.get(i));
		}
	}
	

	
	
}
