

//class of a user that specifically operates as a department head
public class DepartmentHead extends User
{
	Department department; 
	
	public DepartmentHead(String name, String username, String password) {
		super(name, username, password);
		this.canReadGeneralInfo = true;
		this.canReadPendingInfo = true;
		this.canEdit = true;
		this.canCreate = true;
		this.canDelete = false;
		this.role = "DeptHead";

		
	}

	@Override
	public String toString()
	{
		return "DepartmentHead [department=" + department.getDeptName() + "]";
	}

	//Getter and Setter methods for all relevant variables
	
	/**
	 * @return the department
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}
	
}
