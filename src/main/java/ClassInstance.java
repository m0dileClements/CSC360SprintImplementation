

import java.util.ArrayList;

//import java.util.ArrayList;

public class ClassInstance
{
	Course course;
	Term term;
	Instructor instructor;
	String classTime;
	String startTime;
	String endTime;
	Room room;
	Department dept;
	double classLength;
	Boolean hasFalseLimit;
	
	public ClassInstance(Course course, Term term, Instructor instructor, String classTime, Room room, Department dept) {
		//super();
		this.course = course;
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
		public void setRoom(String roomName)
		{
			String[] roomInfo = roomName.split(" ");
			Room newRoom = new Room(roomInfo[0], Integer.parseInt(roomInfo[1]));
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
				String test = endingTime.substring(0,2);
				
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
		//TODO
		
		
		//creates a deep copy of a class
		public ClassInstance getDeepCopy() {
			ClassInstance classCopy = new ClassInstance();
			
			classCopy.course = this.course;
			classCopy.term = this.term;
			classCopy.instructor = this.instructor;
			classCopy.classTime = this.classTime;
			classCopy.startTime = this.startTime;
			classCopy.endTime = this.endTime;
			classCopy.room = this.room;
			classCopy.dept = this.dept;
			classCopy.classLength = this.classLength;
			classCopy.hasFalseLimit = this.hasFalseLimit;
			
			return classCopy;
		}
	//
	
	
	//Getter and Setter methods for all relevant variables
	
	
	//@return the course
	 
	public Course getCourse()
	{
		return course;
	}
	
	
	//	 @param course the course to set
	 
	public void setCourse(Course course)
	{
		this.course = course;
	}
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
		return "ClassInstance [course=" + course + ", term=" + term.toString() + ", instructor=" + instructor + ", classTime="
				+ classTime + ", room=" + room + ", dept=" + dept + ", classLength=" + classLength + ", hasFalseLimit="
				+ hasFalseLimit + "]";
	}
	
	

	
	
}
