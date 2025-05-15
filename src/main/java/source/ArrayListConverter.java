package source;

import java.util.ArrayList;

public class ArrayListConverter extends Converter
{
	//clears all data to provide a clean slate for new data loading
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
//		if(main.allCourses.size() != 0) {
//			main.allCourses.clear();
//		}
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
	
	
	//load in data using one ArrayList of String arrays(each containing info for a single class)
	public void loadData(Main main){
		Registrar loadDataPermissions = new Registrar("", "", "");
		//these department heads would be stored in a different location as the list information for each class
		//would not include that information.
		DepartmentHead deptHeadTest = new DepartmentHead("Alison Conelly", "FrenchGal", "Oui0ui");
		DepartmentHead deptHeadTest2 = new DepartmentHead("HeadWoman", "BossBabe", "IamAw3some");
		DepartmentHead deptHeadTest3 = new DepartmentHead("HeadMan", "BigBos", "IHaveSevereImposterSyndrome");
		DepartmentHead deptHeadTest4 = new DepartmentHead("TA", "ImJustAGraduateStudent", "Im drowning in work");
		
		ArrayList<DepartmentHead> deptHeads = new ArrayList<DepartmentHead>();
		deptHeads.add(deptHeadTest);
		deptHeads.add(deptHeadTest2);
		deptHeads.add(deptHeadTest3);
		deptHeads.add(deptHeadTest4);
		
		String[] class1 = {"Alison Conelly", "French", "Allison Conelly", "Crounse",  "307", "Spring", "2025", "FRE270", "Group Conversation",  "TR 9:40AM - 12:10PM", "E3", "L"};
		String[] class2 = {"HeadWoman", "biology", "The Genie", "Olin", "201", "Fall", "2025", "Bio210", "Environmental Science",  "MWF 10:20AM - 12:40PM", "E2", "S"};
		String[] class3 = {"HeadMan", "biology", "Dr. Doctor", "Olin",  "222", "Winter", "2026", "DSC", "Impacts of Analytics on Human body",  "MWF 10:20AM - 12:40PM", "E1", "D"};
		String[] class4 = {"HeadMan", "biology", "Dr. Doctor", "Olin",  "222", "Winter", "2026", "DLM110b", "How to cope with college",  "MWF 10:20AM - 12:40PM", "E1", "D"};
		//test if no tags works
		//String[] class5 = {"HeadMan", "biology", "Dr. Doctor", "Olin", "222", "Winter", "2026", "DLM110b", "How to cope with college",  "MWF 10:20AM - 12:40PM"};
		
		ArrayList<String[]> classes = new ArrayList<String[]>();
		classes.add(class1);
		classes.add(class2);
		classes.add(class3);
		classes.add(class4);
		//classes.add(class5);
		
		for (int i = 0; i < classes.size(); i++) {
			DepartmentHead deptHead = new DepartmentHead(" ", " ", " ");
			
			
			for(int j = 0; j< deptHeads.size(); j++) {
				if(classes.get(i)[0].equals(deptHeads.get(j).getName())) {
					deptHead = deptHeads.get(j);
				}
			}
			
			Department dept = new Department(deptHead, classes.get(i)[1]);
			Instructor instructor = new Instructor(classes.get(i)[2], dept);
			Room room = new Room(classes.get(i)[3], classes.get(i)[4]);
			Term term = new Term(classes.get(i)[5], Integer.parseInt(classes.get(i)[6]));
			
			
			String classTime = classes.get(i)[9];
			ArrayList<String> tags = new ArrayList<String>();
			
			for(int j = 10; j< classes.get(i).length; j++) {
				tags.add(classes.get(i)[j]);
			}
			//Course course = new Course(classes.get(i)[7], classes.get(i)[8], tags);
			
			ClassInstance fullClass = new ClassInstance(classes.get(i)[7], classes.get(i)[8], tags, term, instructor, classTime, room, dept);
			
			
			main.addDept(dept);
			main.addInstructor(instructor);
			main.addRoom(room);
			main.addTerm(loadDataPermissions, term);
			//main.addCourse(course);
			main.addClassInstance(fullClass);
		}
		
	}
	
	
	
}
