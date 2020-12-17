package axa.ma.axamatcher.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import axa.ma.axamatcher.entity.AxaEntreprise;


@Repository
public interface AxaEntrepriseRepository  extends JpaRepository<AxaEntreprise, String>{

}
