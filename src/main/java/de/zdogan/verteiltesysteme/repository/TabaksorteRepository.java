package de.zdogan.verteiltesysteme.repository;

import de.zdogan.verteiltesysteme.model.database.Tabakhersteller;
import de.zdogan.verteiltesysteme.model.database.Tabaksorte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabaksorteRepository extends JpaRepository<Tabaksorte,Integer> {

    Tabaksorte getOne(int tabaksortenId);
}
