package axa.ma.axamatcher.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import axa.ma.axamatcher.entity.OmpicEntreprise;


@Repository
public interface OmpicEntrepriseRepository  extends JpaRepository<OmpicEntreprise, String>{

}
