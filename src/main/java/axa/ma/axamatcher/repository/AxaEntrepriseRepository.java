package axa.ma.axamatcher.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.OmpicEntreprise;


@Repository
public interface AxaEntrepriseRepository  extends JpaRepository<AxaEntreprise, String>{
	
	
	
}
