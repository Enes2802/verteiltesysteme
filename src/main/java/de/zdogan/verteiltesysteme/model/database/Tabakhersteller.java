package de.zdogan.verteiltesysteme.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Indexed
@Accessors(fluent = true)
@SequenceGenerator(allocationSize = 1,name = "idGenTabakhersteller",sequenceName = "Tabakherstellersq")
@Table(name="tabakhersteller")
public class Tabakhersteller extends RepresentationModel<Tabakhersteller>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "idGenTabakhersteller")
    @Column(name= "tabakhersteller_id",nullable = false)
    @JsonProperty
    private Integer id;

    @Column(name="tabakhersteller_name",nullable = false,unique = true)
    @JsonProperty
    private String name;

    @Column(name="tabakhersteller_adresse",nullable = false)
    @JsonProperty
    private String adresse;


    @JsonIgnore
    @OneToMany(mappedBy = "tabakhersteller",cascade = CascadeType.REMOVE)
    private List<Tabaksorte> tabaksorteList = new LinkedList<>();
}
