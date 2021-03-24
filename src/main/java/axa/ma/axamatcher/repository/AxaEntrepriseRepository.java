package axa.ma.axamatcher.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.OmpicEntreprise;


@Repository
public interface AxaEntrepriseRepository  extends JpaRepository<AxaEntreprise, String>{
	
	
	List<AxaEntreprise> findByAxaDenominationHacheeIn(List<String> list);
	
	List<AxaEntreprise> findByAxaDenominationHacheeNotIn(List<String> list);
	
	@Query(value = "SELECT * FROM public.axa_entreprise left join public.ompic_entreprise on ompic_entreprise.ompic_denomination_hashee=axa_entreprise.axa_denomination_hachee", nativeQuery = true)
	List<AxaEntreprise> findExact();
}
