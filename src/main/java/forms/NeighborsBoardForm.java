package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Owner;

@Access(AccessType.PROPERTY)
public class NeighborsBoardForm {
	
	private Integer neighborsBoardId;
	private Owner attendant;
	
	@Min(1)
	@NotNull
	public Integer getNeighborsBoardId() {
		return neighborsBoardId;
	}
	public void setNeighborsBoardId(Integer neighborsBoardId) {
		this.neighborsBoardId = neighborsBoardId;
	}
	
	@Valid
	@NotNull
	public Owner getAttendant() {
		return attendant;
	}
	public void setAttendant(Owner attendant) {
		this.attendant = attendant;
	}

}
