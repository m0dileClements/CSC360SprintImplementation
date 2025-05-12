

public class Room
{
	String building;
	String roomNumber;
	
	public Room(String building, String roomNumber) {
		this.building = building;
		this.roomNumber = roomNumber;
	}

	//Getters and Setters for relevant variables
	
	/**
	 * @return the building
	 */
	public String getBuilding()
	{
		String[] buildingParts = building.split("-");
		
		return buildingParts[0];
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
	
	public String getRoomNumber()
	{
		return roomNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber)
	{
		this.roomNumber = roomNumber;
	}
	
	public String toString() {
		return (building + " " + roomNumber);
	}
	
}
