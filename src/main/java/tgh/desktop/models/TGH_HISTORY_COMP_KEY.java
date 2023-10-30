package tgh.desktop.models;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TGH_HISTORY_COMP_KEY implements Serializable {
	
	private Integer TGH_ID;
	
	private Date ACTION_DATE;
	

}
