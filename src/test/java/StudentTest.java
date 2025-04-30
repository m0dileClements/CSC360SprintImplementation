import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest
{

	Student student;
	
	@BeforeEach
	void setUp() throws Exception
	{
		student = new Student("Billy Bob", "bBob", "IL0veCats");
	}

	
	@Test
	void testToString()
	{
		assertEquals("Student= Billy Bob", student.toString());
	}
	
	@Test
	void testCanFinalize()
	{
		assertEquals(false, student.getCanFinalize());
		
	}
	
	@Test
	void testCanReadPendingInfo()
	{
		assertEquals(false, student.getCanReadPendingInfo());
		
	}
	
	@Test
	void testCanEdit()
	{
		assertEquals(false, student.getCanEdit());
		
	}

	@Test
	void testCanCreate()
	{
		assertEquals(false, student.getCanCreate());
		
	}

	@Test
	void testCanDelete()
	{
		assertEquals(false, student.getCanDelete());
		
	}

}
