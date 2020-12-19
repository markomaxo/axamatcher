package axa.ma.axamatcher.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OmpicEntreprise {
	@Id
	private String ice;
	private String rc;
	private String ville;
	private String denom;
	private String denomSaisie;
	
	private String denomClean;
	private Integer denomCleanLength;
	
	
	public String getIce() {
		return ice;
	}
	public void setIce(String ice) {
		this.ice = ice;
	}
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
	public String getDenom() {
		return denom;
	}
	public void setDenom(String denom) {
		this.denom = denom;
	}
	public String getDenomSaisie() {
		return denomSaisie;
	}
	public void setDenomSaisie(String denomSaisie) {
		this.denomSaisie = denomSaisie;
	}
	public String getDenomClean() {
		return denomClean;
	}
	public void setDenomClean(String denomClean) {
		this.denomClean = denomClean;
	}
	
	public Integer getDenomCleanLength() {
		return denomCleanLength;
	}
	public void setDenomCleanLength(Integer denomCleanLength) {
		this.denomCleanLength = denomCleanLength;
	}

	
}	

