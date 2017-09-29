package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Lob;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Owner;

@Access(AccessType.PROPERTY)

public class CommunityForm {
	
	private Integer communityId;
	private byte[] csv;

	@Min(1)
	@NotNull
	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	@Lob
	public byte[] getCsv() {
		return csv;
	}

	public void setCsv(byte[] csv) {
		this.csv = csv;
	}
	
	
	
}
