package tgh.desktop.models;
import java.io.Serializable;

import javax.persistence.Embeddable;


import lombok.Data;
@Data
@Embeddable
public class SUBS_ID implements Serializable{
	
	private Integer teleNo;
	
	private String cityCode;
	
}
