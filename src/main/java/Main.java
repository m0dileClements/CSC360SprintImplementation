

import java.util.ArrayList;

public class Main
{

	ArrayList<User> allUsers;
	ArrayList<Term> allTerms;
	ArrayList<Department> allDepts;
	ArrayList<ClassInstance> allClasses;
	ArrayList<Instructor> allInstructors;
	ArrayList<Room> allRooms;
	User currentUser;
	Converter currentConverter;
	
	public Main() {
		allUsers = new ArrayList<User>();
		allTerms = new ArrayList<Term>();
		allDepts = new ArrayList<Department>();
		//allCourses = new ArrayList<Course>();
		allClasses = new ArrayList<ClassInstance>();
		allInstructors = new ArrayList<Instructor>();
		allRooms = new ArrayList<Room>();		
	}
	
	public void setArrayListConverter() {
		currentConverter = new ArrayListConverter();
	}
	
	public void setRestServerConverter() {
		currentConverter = new RestServerConverter();
	}
	
	public void launch() {
		Main main = new Main();
		currentConverter.clearData(main);
		currentConverter.loadData(main);
		this.allTerms = main.getAllTerms();
		this.allDepts = main.getAllDepts();
		this.allClasses = main.getAllClasses();
		this.allInstructors = main.getAllInstructors();
		this.allRooms = main.getAllRooms();
	}
	
	//returns a list of all of the previous semesters marked final
	public ArrayList<Term> getAllPreviousTerms(){
		ArrayList<Term> previousTerms = new ArrayList<Term>();
		for(int i = 0; i < allTerms.size(); i++) {
			if(allTerms.get(i).getIsFinalized() == true) {
				previousTerms.add(allTerms.get(i));
			}
		}
		return previousTerms;
	}
	
	
	//creates an additional term with the same semester name + " copy" that contains all the same classes
	public void createFromPrevTerm(Term prevTerm) {
		if(currentUser.getCanCreate()) {
			Term newTerm;
			for(int i = 0; i < allTerms.size(); i++) {
				if(prevTerm.toString().equals(allTerms.get(i).toString())) {
					String semester = prevTerm.getSemester() + " copy";
					int year = prevTerm.getYear();
					newTerm = new Term(semester, year);
					ArrayList<ClassInstance> prevTermClasses = prevTerm.getAllClasses();
					ArrayList<ClassInstance> newTermClasses = new ArrayList<ClassInstance>();
					
					for(int j = 0; j<prevTermClasses.size(); j++) {
						ClassInstance newClass = prevTermClasses.get(j).getDeepCopy(currentUser);
						newTermClasses.add(newClass);
					}
					newTerm.setAllClasses(newTermClasses);
					newTerm.setIsFinalized(false);
					allTerms.add(newTerm);
				}
			}
		}
	}
	
	//returns a list of all of the classes within a given department
	public ArrayList<ClassInstance> getClassesByDept(String dept){
		ArrayList<ClassInstance> classesInDept = new ArrayList<ClassInstance>();
		
		//finds the specific Department based on string
		Department targetDept = new Department();
		for (int i = 0; i < allDepts.size(); i++) {
			if(dept.equals(allDepts.get(i).getDeptName())) {
				targetDept = allDepts.get(i);
			}
		}
		
		//searches through list of all instructors and compares their department
		//to the specified department
		for (int i = 0; i < allClasses.size(); i++) {
			if(allClasses.get(i).getDept().toString().equals(targetDept.toString())) {
				classesInDept.add(allClasses.get(i));
			}
		}
		
		return classesInDept;
	}
	
	//returns a list of all of the courses within a given term
	public ArrayList<ClassInstance> getClassessByTerm(Term term) {
		//finds the specific Term based on string
		
		ArrayList<ClassInstance> targetClasses = new ArrayList<ClassInstance>();
			for (int i = 0; i < allTerms.size(); i++) {
				if(term.toString().equals(allTerms.get(i).toString())) {
					targetClasses = allTerms.get(i).getAllClasses();
				}
			}
		return targetClasses;
		
	}
	
	
	//returns an arrayList of all of the instructors in a given department
	public ArrayList<Instructor> getAllInstructorsByDept(Department dept){
		ArrayList<Instructor> instructorInDept = new ArrayList<Instructor>();
		
		//finds the specific Department based on string
		Department targetDept = new Department();
		for (int i = 0; i < allDepts.size(); i++) {
			if(dept.toString().equals(allDepts.get(i).toString())) {
				targetDept = allDepts.get(i);
			}
		}
		
		//searches through list of all departments and compares their department name
		//to the specified department
		for (int i = 0; i < allInstructors.size(); i++) {
			if(allInstructors.get(i).getProfDept().toString().equals(targetDept.toString())) {
				instructorInDept.add(allInstructors.get(i));
			}
		}
		
		return instructorInDept;
	}
	
	
	//searches rooms by buildings and returns a list of rooms that are within that building
	public ArrayList<Room> getRoomsByBuilding(String room){
		ArrayList<Room> roomsInBuilding = new ArrayList<Room>();
		
		//scans through all rooms in catalog and add the ones with the same building name to a new list.
		for(int i = 0; i< allRooms.size(); i++) {
			if(allRooms.get(i).getBuilding().equals(room)) {
				roomsInBuilding.add(allRooms.get(i));
			}
		}
		
		return roomsInBuilding;
	}
	
	
	//Getter and adding methods for all relevant variables
		//the adding methods make sure that there are no duplicates already stores in their all___s ArrayLists.
	

	/**
	 * @return the allUsers
	 */
	public ArrayList<User> getAllUsers()
	{
		return allUsers;
	}
	
	public void addUser(User user) {
		Boolean alreadyExists = false;
		for(int i = 0; i < allUsers.size(); i++) {
			if(user.toString().equals(allUsers.get(i).toString())) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			allUsers.add(user);
		}
	}

	/**
	 * @return the allTerms
	 */
	public ArrayList<Term> getAllTerms()
	{
		return allTerms;
	}
	
	public void addTerm(User u, Term term) {
		if (u.getCanCreate()){
			Boolean alreadyExists = false;
			for(int i = 0; i < allTerms.size(); i++) {
				if(term.toString().equals(allTerms.get(i).toString())) {
					alreadyExists = true;
				}
			}
			if (!alreadyExists) {
				allTerms.add(term);
			}
		}
	}


	/**
	 * @return the allClasses
	 */
	public ArrayList<ClassInstance> getAllClasses()
	{
		return allClasses;
	}
	
	public void addClassInstance(ClassInstance classInstance) {
		Boolean alreadyExists = false;
		for(int i = 0; i < allClasses.size(); i++) {
			if(classInstance.toString().equals(allClasses.get(i).toString())) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			allClasses.add(classInstance);
		}
		
	}
	
	/**
	 * @return the allDepts
	 */
	public ArrayList<Department> getAllDepts()
	{
		return allDepts;
	}
	
	public void addDept(Department dept) {
		Boolean alreadyExists = false;
		for(int i = 0; i < allDepts.size(); i++) {
			if(dept.toString().equals(allDepts.get(i).toString())) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			allDepts.add(dept);
		}
	}
	
	/**
	 * @return the allInstructors
	 */
	public ArrayList<Instructor> getAllInstructors(){
		return allInstructors;
	}
	
	public void addInstructor(Instructor instructor) {
		Boolean alreadyExists = false;
		for(int i = 0; i < allInstructors.size(); i++) {
			if(instructor.toString().equals(allInstructors.get(i).toString())) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			allInstructors.add(instructor);
		}	
		
		
	}
	
	public ArrayList<Room> getAllRooms(){
		return allRooms;
	}
	
	public void addRoom(Room room) {
		Boolean alreadyExists = false;
		for(int i = 0; i < allRooms.size(); i++) {
			if(room.toString().equals(allRooms.get(i).toString())) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			allRooms.add(room);
		}	
		
	}

	/**
	 * @return the currentUser
	 */
	public User getCurrentUser()
	{
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}

	/**
	 * @return the currentConverter
	 */
	public Converter getConverter()
	{
		return currentConverter;
	}

	/**
	 * @param currentConverter the currentConverter to set
	 */
	public void setConverter(Converter currentConverter)
	{
		this.currentConverter = currentConverter;
	}
	
	
	
	
	
	
	
}
