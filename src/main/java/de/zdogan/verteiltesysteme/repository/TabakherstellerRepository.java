package de.zdogan.verteiltesysteme.repository;

import de.zdogan.verteiltesysteme.model.database.Tabakhersteller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TabakherstellerRepository extends JpaRepository<Tabakhersteller,Integer> {

    Tabakhersteller getOne(int herstellerId);

    void delete(Tabakhersteller tabakhersteller);


    List<Tabakhersteller> findAll();
}
