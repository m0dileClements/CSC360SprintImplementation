

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;


class UserTest
{

	User u;

	@BeforeEach
	void setUp() throws Exception
	{
		u = new User("Billy Bob", "bBob", "IL0veCats");

	}

	@Test
	void testToString()
	{
		assertEquals("No User Type Inputted= Billy Bob", u.toString());
		u.setRole("Special User");
		assertEquals("Special User= Billy Bob", u.toString());
	}

	@Test
	void testGetRole()
	{
		assertEquals("", u.getRole());
		
	}
	@Test
	void testCanFinalize()
	{
		assertEquals(false, u.getCanFinalize());
		
	}
	
	@Test
	void testCanReadPendingInfo()
	{
		assertEquals(false, u.getCanReadPendingInfo());
		
	}
	
	@Test
	void testCanEdit()
	{
		assertEquals(false, u.getCanEdit());
		
	}

	@Test
	void testCanCreate()
	{
		assertEquals(false, u.getCanCreate());
		
	}

	@Test
	void testCanDelete()
	{
		assertEquals(false, u.getCanDelete());
		
	}
	
	@Test
	void testSetCanFinalize()
	{
		assertEquals(false, u.getCanFinalize());
		u.setCanFinalize(true);
		assertEquals(true, u.getCanFinalize());
	}
	
	@Test
	void testSetCanReadPendingInfo()
	{
		assertEquals(false, u.getCanReadPendingInfo());
		u.setCanReadPendingInfo(true);
		assertEquals(true, u.getCanReadPendingInfo());
	}
	
	@Test
	void testSetCanEdit()
	{
		assertEquals(false, u.getCanEdit());
		u.setCanEdit(true);
		assertEquals(true, u.getCanEdit());
		
	}

	@Test
	void testSetCanCreate()
	{
		assertEquals(false, u.getCanCreate());
		u.setCanCreate(true);
		assertEquals(true, u.getCanCreate());
		
	}

	@Test
	void testSetCanDelete()
	{
		assertEquals(false, u.getCanDelete());
		u.setCanDelete(true);
		assertEquals(true, u.getCanDelete());
		
	}
	@Test
	void testGetName()
	{
		assertEquals("Billy Bob", u.getName());
	}
	
	@Test
	void testSetName()
	{
		assertEquals("Billy Bob", u.getName());
		u.setName("Jim Bob");
		assertEquals("Jim Bob", u.getName());
	}
	@Test
	void testGetUsername()
	{
		assertEquals("bBob", u.getUsername());
	}
	
	@Test
	void testSetUsername()
	{
		assertEquals("bBob", u.getUsername());
		u.setUsername("C00lGuy");
		assertEquals("C00lGuy", u.getUsername());
	}
	@Test
	void testGetPassword()
	{
		assertEquals("IL0veCats", u.getPassword());
	}
	
	@Test
	void testSetPassword()
	{
		assertEquals("IL0veCats", u.getPassword());
		u.setPassword("IL0veDogs");
		assertEquals("IL0veDogs", u.getPassword());
	}
}
