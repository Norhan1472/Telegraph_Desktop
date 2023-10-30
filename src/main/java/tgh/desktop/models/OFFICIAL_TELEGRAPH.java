package tgh.desktop.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="OFFICIAL_TELEGRAPH")
public class OFFICIAL_TELEGRAPH {
    @Id
    @Column(name = "TGH_ID")
    private long tgh_id;

}
