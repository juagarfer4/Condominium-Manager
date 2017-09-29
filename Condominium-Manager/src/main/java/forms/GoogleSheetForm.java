package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Access(AccessType.PROPERTY)

public class GoogleSheetForm {
	
	private Integer communityId;
	private String urlSheet;

	@Min(1)
	@NotNull
	public Integer getCommunityId() {
		return communityId;
	}

	@NotBlank
	@URL
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	@NotBlank
	@URL
	public String getUrlSheet() {
		return urlSheet;
	}

	public void setUrlSheet(String urlSheet) {
		this.urlSheet = urlSheet;
	}
	

	
	
	
	
}
