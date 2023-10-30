package tgh.desktop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="WFM_EMP")
public class WFM_EMP {
    @Id
    @Column(name = "EMP_ID" )
    @JsonProperty("userCode")
    private Integer EMP_ID;

    @JsonProperty("userName")
    @Column(name = "FIRST_NAME" )
    private String FIRST_NAME;
}
