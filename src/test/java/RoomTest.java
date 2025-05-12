

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import source.*;

class RoomTest
{
	Room roomTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		roomTest = new Room("Olin",  "201");
	}

	@Test
	void testRoom()
	{
		assertEquals("Olin 201", roomTest.toString());
	}

	@Test
	void testGetBuilding()
	{
		assertEquals("Olin", roomTest.getBuilding());
	}

	@Test
	void testSetBuilding()
	{
		roomTest.setBuilding("Young");
		assertEquals("Young", roomTest.getBuilding());
	}

	@Test
	void testGetRoomNumber()
	{
		assertEquals("201", roomTest.getRoomNumber());
	}

	@Test
	void testSetRoomNumber()
	{
		roomTest.setRoomNumber("212");
		assertEquals("212", roomTest.getRoomNumber());
		roomTest.setRoomNumber("211");
		assertEquals("211", roomTest.getRoomNumber());
	}

}
