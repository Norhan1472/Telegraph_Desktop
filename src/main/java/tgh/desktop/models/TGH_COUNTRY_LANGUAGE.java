package tgh.desktop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="TGH_COUNTRY_LANGUAGE")
@IdClass(TGH_COUNTRY_LANGUAGE.class)
public class TGH_COUNTRY_LANGUAGE implements Serializable {
    @Id
    @Column(name = "COUNTRY_CODE" )
    private String countryCode;

    @Id
    @Column(name = "LANG_CODE" )
    private String langCode;
}
