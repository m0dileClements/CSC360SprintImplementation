

public class Department
{
	DepartmentHead departmentHead;
	String deptName;
	/**
	 * @return the departmentHead
	 */
	public Department() {};
	public Department(DepartmentHead departmentHead, String deptName) {
		this.departmentHead = departmentHead;
		this.deptName = deptName;
	}
	
	//Getter and Setter methods for all relevant variables
	
	public DepartmentHead getDepartmentHead()
	{
		return departmentHead;
	}
	/**
	 * @param departmentHead the departmentHead to set
	 */
	public void setDepartmentHead(DepartmentHead departmentHead)
	{
		this.departmentHead = departmentHead;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName()
	{
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	
	public String toString() {
		return (departmentHead.getName() + ": " + deptName);
	}
}
