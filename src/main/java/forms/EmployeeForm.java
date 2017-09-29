package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import org.hibernate.validator.constraints.NotBlank;


@Access(AccessType.PROPERTY)
public class EmployeeForm  extends ActorForm{

	private String job;
	
	@NotBlank
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	
	
}
