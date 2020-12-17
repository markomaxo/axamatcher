package axa.ma.axamatcher.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AxaEntreprise {
	@Id
	private String nPolice;
	private String rc;
	private String ville;
	private String nom;
	private String dateexpiration;
	private String nomClean;
	
	
	
	
	

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}



	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomClean() {
		return nomClean;
	}

	public void setNomClean(String nomClean) {
		this.nomClean = nomClean;
	}

	public String getDateexpiration() {
		return dateexpiration;
	}

	public void setDateexpiration(String dateexpiration) {
		this.dateexpiration = dateexpiration;
	}

	public String getnPolice() {
		return nPolice;
	}

	public void setnPolice(String nPolice) {
		this.nPolice = nPolice;
	}
}
