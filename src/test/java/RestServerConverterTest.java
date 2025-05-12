import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

//import RestServerConverter.DeptList;


class RestServerConverterTest
{
	Main main;
	//public record DeptList(String request, Boolean successful, String message, ArrayList<Dept> data) {};
	//public record Dept(String name, String description, String location) {};
	//public record DeptDetails(String request, Boolean successful, String message, DeptData data) {};
	//public record DeptData(String dept, ArrayList<FacultyInDept> faculty) {};
	//public record FacultyInDept(String name, String location) {};
	
	@BeforeEach
	void setUp() throws Exception
	{
		main = new Main();
		main.setRestServerConverter();
		
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
		main.getConverter().clearData(main);;
		assertEquals(0, main.getAllRooms().size());
		assertEquals(0, main.getAllDepts().size());
		assertEquals(0, main.getAllTerms().size());
		assertEquals(0, main.getAllClasses().size());
		
	}
	
	@Test
	void testImportDept()
	{
//		RestClient defaultClient = RestClient.create();
//		RestServerConverter testConverter = (RestServerConverter)main.getConverter();
//		DeptList deptWrapper  = defaultClient.get()
//				.uri("http://localhost:9000/v1/Archive/dept")
//				.retrieve()
//				.body(DeptList.class);
//		//Stores the list of dept
//		ArrayList<Dept> deptAddresses = deptWrapper.data();
//		
//		DeptDetails specificDeptWrapper = defaultClient.get()
//				.uri(deptAddresses.get(0).location())
//				.retrieve()
//				.body(DeptDetails.class);
//			
//		testConverter.importDept(main, specificDeptWrapper);
	
	}
	
	@Test
	void testImportRoom()
	{
	
	}
	
	@Test
	void testImportCourse()
	{
	
	}

}
