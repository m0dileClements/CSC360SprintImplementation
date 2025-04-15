

public class Room
{
	String building;
	int roomNumber;
	
	public Room(String building, int roomNumber) {
		this.building = building;
		this.roomNumber = roomNumber;
	}

	//Getters and Setters for relevant variables
	
	/**
	 * @return the building
	 */
	public String getBuilding()
	{
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building)
	{
		this.building = building;
	}

	/**
	 * @return the roomNumber
	 */
	public int getRoomNumber()
	{
		return roomNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber)
	{
		this.roomNumber = roomNumber;
	}
	
	public String toString() {
		return (building + " " + roomNumber);
	}
	
}
