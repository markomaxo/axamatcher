package axa.ma.axamatcher.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;


@Repository
public interface MatcherRepository  extends JpaRepository<Matcher, Integer>{

}
