

//a default user with preset permissions. Initialization of different types of users will change their permissions.
public class User
{
	String name;
	String username;
	String password;
	Boolean canReadGeneralInfo;
	Boolean canReadPendingInfo;
	Boolean canEdit;
	Boolean canCreate;
	Boolean canDelete;
	String role;	
	
	public User(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
		canReadGeneralInfo = true;
		canReadPendingInfo = false;
		canEdit = false;
		canCreate = false;
		canDelete = false;
		role = "";
	}
	
	
	public String toString()
	{
		if(role == "") {
			return ("No User Type Inputted= " + name);
		}
		else {
			return (role + "= " + name);
		}
	}

	//Getters and Setters of relevant variables
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}


	/**
	 * @return the canReadGeneralInfo
	 */
	public Boolean getCanReadGeneralInfo()
	{
		return canReadGeneralInfo;
	}


	/**
	 * @param canReadGeneralInfo the canReadGeneralInfo to set
	 */
	public void setCanReadGeneralInfo(Boolean canReadGeneralInfo)
	{
		this.canReadGeneralInfo = canReadGeneralInfo;
	}


	/**
	 * @return the canReadPendingInfo
	 */
	public Boolean getCanReadPendingInfo()
	{
		return canReadPendingInfo;
	}


	/**
	 * @param canReadPendingInfo the canReadPendingInfo to set
	 */
	public void setCanReadPendingInfo(Boolean canReadPendingInfo)
	{
		this.canReadPendingInfo = canReadPendingInfo;
	}


	/**
	 * @return the canEdit
	 */
	public Boolean getCanEdit()
	{
		return canEdit;
	}


	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(Boolean canEdit)
	{
		this.canEdit = canEdit;
	}


	/**
	 * @return the canCreate
	 */
	public Boolean getCanCreate()
	{
		return canCreate;
	}


	/**
	 * @param canCreate the canCreate to set
	 */
	public void setCanCreate(Boolean canCreate)
	{
		this.canCreate = canCreate;
	}


	/**
	 * @return the canDelete
	 */
	public Boolean getCanDelete()
	{
		return canDelete;
	}


	/**
	 * @param canDelete the canDelete to set
	 */
	public void setCanDelete(Boolean canDelete)
	{
		this.canDelete = canDelete;
	}


	/**
	 * @return the role
	 */
	public String getRole()
	{
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role)
	{
		this.role = role;
	}
	
	
}
