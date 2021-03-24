package axa.ma.axamatcher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class AxaEntreprise {
	
//
//@GenericGenerator(name="incgenerator" , strategy="increment")
//@GeneratedValue(generator="incgenerator")
//@Column(name = "idaxa", unique = true, nullable = false)
	@Id
    private Long idaxa;
	//@Column(name="police")
	private String police;
	@Column(length = 1024)
	private String axa_produit;
	@Column(length = 1024)
	private String axaSituationPolice;
	@Column(length = 1024)
	private String axa_date_premier_effet;
	@Column(length = 1024)
	private String dateResiliation;
	@Column(length = 1024)
	private String axaDenomination;
	@Column(length = 1024)
	private String axaAbreviation;
	@Column(length = 1024)
	private String axaAdresse;
	@Column(length = 1024)
	private String axa_suite_adresse;
	@Column(length = 1024)
	private String axa_code_ville;
	@Column(length = 1024)
	private String axaVille;
	@Column(length = 1024)
	private String axaRc;
	@Column(length = 1024)
	private String axaDenominationHachee;
	public Long getIdaxa() {
		return idaxa;
	}
	public void setIdaxa(Long idaxa) {
		this.idaxa = idaxa;
	}
	public String getPolice() {
		return police;
	}
	public void setPolice(String police) {
		this.police = police;
	}
	public String getAxa_produit() {
		return axa_produit;
	}
	public void setAxa_produit(String axa_produit) {
		this.axa_produit = axa_produit;
	}
	public String getAxaSituationPolice() {
		return axaSituationPolice;
	}
	public void setAxaSituationPolice(String axaSituationPolice) {
		this.axaSituationPolice = axaSituationPolice;
	}
	public String getAxa_date_premier_effet() {
		return axa_date_premier_effet;
	}
	public void setAxa_date_premier_effet(String axa_date_premier_effet) {
		this.axa_date_premier_effet = axa_date_premier_effet;
	}
	public String getDateResiliation() {
		return dateResiliation;
	}
	public void setDateResiliation(String dateResiliation) {
		this.dateResiliation = dateResiliation;
	}
	public String getAxaDenomination() {
		return axaDenomination;
	}
	public void setAxaDenomination(String axaDenomination) {
		this.axaDenomination = axaDenomination;
	}
	
	public String getAxaAbreviation() {
		return axaAbreviation;
	}
	public void setAxaAbreviation(String axaAbreviation) {
		this.axaAbreviation = axaAbreviation;
	}
	public String getAxaAdresse() {
		return axaAdresse;
	}
	public void setAxaAdresse(String axaAdresse) {
		this.axaAdresse = axaAdresse;
	}
	public String getAxa_suite_adresse() {
		return axa_suite_adresse;
	}
	public void setAxa_suite_adresse(String axa_suite_adresse) {
		this.axa_suite_adresse = axa_suite_adresse;
	}
	public String getAxa_code_ville() {
		return axa_code_ville;
	}
	public void setAxa_code_ville(String axa_code_ville) {
		this.axa_code_ville = axa_code_ville;
	}
	public String getAxaVille() {
		return axaVille;
	}
	public void setAxaVille(String axaVille) {
		this.axaVille = axaVille;
	}
	public String getAxaRc() {
		return axaRc;
	}
	public void setAxaRc(String axaRc) {
		this.axaRc = axaRc;
	}
	public String getAxaDenominationHachee() {
		return axaDenominationHachee;
	}
	public void setAxaDenominationHachee(String axaDenominationHachee) {
		this.axaDenominationHachee = axaDenominationHachee;
	}

	
	}
