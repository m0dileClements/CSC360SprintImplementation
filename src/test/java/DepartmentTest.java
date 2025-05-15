

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import source.*;


class DepartmentTest
{
	DepartmentHead deptHead;
	DepartmentHead deptHead2;
	Department biology;

	@BeforeEach
	void setUp() throws Exception
	{
		deptHead = new DepartmentHead("Billy Bob", "bBob", "IL0veCats");
		deptHead2 = new DepartmentHead("Tommy Jones", "tJones", "IL0veDogs");
		biology= new Department(deptHead, "Bio");
	}

	@Test
	void testGetDepartmentHead()
	{
		DepartmentHead deptHeadTest = biology.getDepartmentHead();
		assertEquals(deptHeadTest, deptHead);
	}

	@Test
	void testSetDepartmentHead()
	{
		biology.setDepartmentHead(deptHead2);
		assertEquals(biology.getDepartmentHead(), deptHead2);
	}

	@Test
	void testGetDeptName()
	{
		assertEquals(biology.getDeptName(), "Bio");
	}

	@Test
	void testSetDeptName()
	{
		biology.setDeptName("physics");
		assertEquals(biology.getDeptName(), "physics");
	}
	
	@Test
	void testToString()
	{
		assertEquals(biology.toString(), "Billy Bob: Bio");
	}

}
