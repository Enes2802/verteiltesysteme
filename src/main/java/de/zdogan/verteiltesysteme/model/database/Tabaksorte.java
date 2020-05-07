package de.zdogan.verteiltesysteme.model.database;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;


@Entity
@Data
@Indexed
@Accessors(fluent=true)
@SequenceGenerator(allocationSize = 1,name = "idGenTabaksorte",sequenceName = "Tabaksortesq")
@Table(name="tabaksorte")
public class Tabaksorte extends RepresentationModel<Tabaksorte> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "idGenTabaksorte")
    @Column(name= "tabaksorte_id",nullable = false)
    @JsonProperty
    private Integer id;

    @Column(name="tabaksorte_name",nullable = false,unique = true)
    @JsonProperty
    private String name;

    @Column(name="tabaksorte_geschmack",nullable = false)
    @JsonProperty
    private String geschmack;

    @Column(name="tabaksorte_preis",nullable = false)
    @JsonProperty
    private int preis;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="tabakhersteller_id",nullable = false)
    private Tabakhersteller tabakhersteller;
}
