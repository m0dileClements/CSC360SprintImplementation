

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class RegistrarTest
{
	Registrar registrar;
	@BeforeEach
	void setUp() throws Exception
	{
		registrar = new Registrar("Jacob Jones", "JJones", "c0ol Guy");
	}

	@Test
	void testToString()
	{
		assertEquals("Registrar= Jacob Jones", registrar.toString());
	}
	
	
	@Test
	void testCanFinalize()
	{
		assertEquals(true, registrar.getCanFinalize());
		
	}
	
	@Test
	void testCanReadPendingInfo()
	{
		assertEquals(true, registrar.getCanReadPendingInfo());
		
	}
	
	@Test
	void testCcanEdit()
	{
		assertEquals(true, registrar.getCanEdit());
		
	}

	@Test
	void testCanCreate()
	{
		assertEquals(true, registrar.getCanCreate());
		
	}

	@Test
	void testCanDelete()
	{
		assertEquals(true, registrar.getCanDelete());
		
	}

}
