

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import source.*;

class DepartmentHeadTest
{
	DepartmentHead deptHead;
	Department dept;
	@BeforeEach
	void setUp() throws Exception
	{
		
		deptHead = new DepartmentHead("Billy Bob", "bBob", "IL0veCats");
		dept = new Department(deptHead, "Bio");
		deptHead.setDepartment(dept);
	}

	
	@Test
	void testToString()
	{
		assertEquals("DepartmentHead [department=Bio]", deptHead.toString());
	}
	
	@Test
	void testGetDepartment()
	{
		assertEquals(dept, deptHead.getDepartment());
	}
	
	@Test
	void testSetDepartment()
	{
		Department newDept = new Department(deptHead, "Physics");
		deptHead.setDepartment(newDept);
		assertEquals(newDept, deptHead.getDepartment());
	}
	
	@Test
	void testCanFinalize()
	{
		assertEquals(false, deptHead.getCanFinalize());
		
	}
	
	@Test
	void testCanReadPendingInfo()
	{
		assertEquals(true, deptHead.getCanReadPendingInfo());
		
	}
	
	@Test
	void testCanEdit()
	{
		assertEquals(true, deptHead.getCanEdit());
		
	}

	@Test
	void testCanCreate()
	{
		assertEquals(true, deptHead.getCanCreate());
		
	}

	@Test
	void testCanDelete()
	{
		assertEquals(false, deptHead.getCanDelete());
		
	}
	
	


}
