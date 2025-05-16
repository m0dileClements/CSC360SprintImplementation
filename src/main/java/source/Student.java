//class of a user that specifically operates as a student
package source;

public class Student extends User {
		
		public Student(String name, String loginInformation, String password) {
			//TODO super here
			super(name, loginInformation, password);
			this.canFinalize = false;
			this.canReadPendingInfo = true;
			this.canEdit = false;
			this.canCreate = false;
			this.canDelete = false;
			this.role = "Student";
		}
		
		public String toString() {
			return "Student= " + this.getName();
		}
		
}
