package tgh.desktop.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="SC_USERS")
public class SC_USERS {

	@Id
	@Column(name = "USER_ID" )
	Integer userId;
	
	@Column(name = "USER_NAME" )
	String userName;
	
	@Column(name = "EMAIL_ADDRESS" )
	String emailAddress;
	
	@Column(name = "DISPLAY_NAME" )
	String displayName;
	
	@Column(name = "USER_IMG" )
	String userImg;
	
	@Column(name = "OWNERSHIP_ID" )
	Integer ownershipId;
	
	@Column(name = "LAST_MODIFIED_BY" )
	String lastModifiedBy;
	
	@Column(name = "BU_CODE" )
	String buCode;
	
	@Column(name = "TGH_COUNT" )
	Integer tghCount;
}
