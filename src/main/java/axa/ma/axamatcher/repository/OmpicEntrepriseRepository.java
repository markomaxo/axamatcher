package axa.ma.axamatcher.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import axa.ma.axamatcher.entity.OmpicEntreprise;


@Repository
public interface OmpicEntrepriseRepository  extends JpaRepository<OmpicEntreprise, String>{
	
	@Query(value="select o from OmpicEntreprise o where o.ompicVille like ?1 and  o.ompicDenominationHasheeLength  between ?2  and   ?3  ")
	List<OmpicEntreprise> findByVille( String ville, Integer l1, Integer l2);
	
	
	
	@Query(value="select o from OmpicEntreprise o  where o.ompicDenominationHasheeLength  between ?1  and   ?2  ")
	List<OmpicEntreprise> findByLenght( Integer l1,  Integer l2);

}
