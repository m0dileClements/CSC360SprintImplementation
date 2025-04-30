

//class of a user that specifically operates as a registrar
public class Registrar extends User
{
	
	public Registrar(String name, String loginInformation, String password) {
		//TODO super here
		super(name, loginInformation, password);
		this.canFinalize = true;
		this.canReadPendingInfo = true;
		this.canEdit = true;
		this.canCreate = true;
		this.canDelete = true;
		this.role = "Registrar";
	}
	
	public String toString() {
		return "Registrar= " + this.getName();
	}
	
}
