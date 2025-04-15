

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
		String userType = u.toString();
		assertEquals("No User Type Inputted: Billy Bob", userType);
	}

	@Test
	void testCanReadGeneralInfo()
	{
		assertEquals(true, u.getCanReadGeneralInfo());
		
	}
	
	@Test
	void testCanReadPendingInfo()
	{
		assertEquals(false, u.getCanReadPendingInfo());
		
	}
	
	@Test
	void testCcanEdit()
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

}
