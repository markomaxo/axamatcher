package axa.ma.axamatcher.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class OmpicEntreprise {
	
//	@GenericGenerator(name="incgenerator" , strategy="increment")
//	@GeneratedValue(generator="incgenerator")
//	@Column(name = "idompic", unique = true, nullable = false)
	@Id
	//@Column(length = 1024, name="ompic_bilid")
	@Column(length = 1024)
	private Long bilid;
	//@Column(length = 1024, name="ompic_numero_ice")
	@Column(length = 1024)
	private String ompicIce;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_rc")
	private String ompicRc;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_denomination")
	private String ompicDenomination;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_code_tribunal")
	private String ompicCodeTribunal;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_forme_juridique")
	private String ompicFormeJuridique;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_sigle")
	private String ompicFlagSiege;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_capital")
	private String ompicCapital;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_date_immatriculation")
	private String ompicDateImmatriculation;	
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_date_radiation")
	private String ompicDateRadiation;	
	@Column(length = 2048)
	//@Column(length = 1024, name="ompic_adresse")
	private String ompicAdresse;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_code_ville")
	private String ompicCodeVille;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_activite")
	private String ompicCodesActivite;	
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_flag_siege")
	private String ompicSigle;
	@Column(length = 1024)
	private String ompicDenominationHashee;
	private Integer ompicDenominationHasheeLength;
	@Column(length = 4048)
	//@Column(length = 1024, name="ompic_codes_activite")
	private String ompicActivite;
	@Column(length = 1024)
	//@Column(length = 1024, name="ompic_flage_nma")
	private String ompicFlagNma;
	private String ompicVille;
	public Long getBilid() {
		return bilid;
	}
	public void setBilid(Long bilid) {
		this.bilid = bilid;
	}
	public String getOmpicIce() {
		return ompicIce;
	}
	public void setOmpicIce(String ompicIce) {
		this.ompicIce = ompicIce;
	}
	public String getOmpicRc() {
		return ompicRc;
	}
	public void setOmpicRc(String ompicRc) {
		this.ompicRc = ompicRc;
	}
	public String getOmpicDenomination() {
		return ompicDenomination;
	}
	public void setOmpicDenomination(String ompicDenomination) {
		this.ompicDenomination = ompicDenomination;
	}
	public String getOmpicCodeTribunal() {
		return ompicCodeTribunal;
	}
	public void setOmpicCodeTribunal(String ompicCodeTribunal) {
		this.ompicCodeTribunal = ompicCodeTribunal;
	}
	public String getOmpicFormeJuridique() {
		return ompicFormeJuridique;
	}
	public void setOmpicFormeJuridique(String ompicFormeJuridique) {
		this.ompicFormeJuridique = ompicFormeJuridique;
	}
	public String getOmpicFlagSiege() {
		return ompicFlagSiege;
	}
	public void setOmpicFlagSiege(String ompicFlagSiege) {
		this.ompicFlagSiege = ompicFlagSiege;
	}
	public String getOmpicCapital() {
		return ompicCapital;
	}
	public void setOmpicCapital(String ompicCapital) {
		this.ompicCapital = ompicCapital;
	}
	public String getOmpicDateImmatriculation() {
		return ompicDateImmatriculation;
	}
	public void setOmpicDateImmatriculation(String ompicDateImmatriculation) {
		this.ompicDateImmatriculation = ompicDateImmatriculation;
	}
	public String getOmpicDateRadiation() {
		return ompicDateRadiation;
	}
	public void setOmpicDateRadiation(String ompicDateRadiation) {
		this.ompicDateRadiation = ompicDateRadiation;
	}
	public String getOmpicAdresse() {
		return ompicAdresse;
	}
	public void setOmpicAdresse(String ompicAdresse) {
		this.ompicAdresse = ompicAdresse;
	}
	public String getOmpicCodeVille() {
		return ompicCodeVille;
	}
	public void setOmpicCodeVille(String ompicCodeVille) {
		this.ompicCodeVille = ompicCodeVille;
	}
	public String getOmpicCodesActivite() {
		return ompicCodesActivite;
	}
	public void setOmpicCodesActivite(String ompicCodesActivite) {
		this.ompicCodesActivite = ompicCodesActivite;
	}
	public String getOmpicSigle() {
		return ompicSigle;
	}
	public void setOmpicSigle(String ompicSigle) {
		this.ompicSigle = ompicSigle;
	}
	public String getOmpicDenominationHashee() {
		return ompicDenominationHashee;
	}
	public void setOmpicDenominationHashee(String ompicDenominationHashee) {
		this.ompicDenominationHashee = ompicDenominationHashee;
	}
	public Integer getOmpicDenominationHasheeLength() {
		return ompicDenominationHasheeLength;
	}
	public void setOmpicDenominationHasheeLength(Integer ompicDenominationHasheeLength) {
		this.ompicDenominationHasheeLength = ompicDenominationHasheeLength;
	}
	public String getOmpicActivite() {
		return ompicActivite;
	}
	public void setOmpicActivite(String ompicActivite) {
		this.ompicActivite = ompicActivite;
	}
	public String getOmpicFlagNma() {
		return ompicFlagNma;
	}
	public void setOmpicFlagNma(String ompicFlagNma) {
		this.ompicFlagNma = ompicFlagNma;
	}
	public String getOmpicVille() {
		return ompicVille;
	}
	public void setOmpicVille(String ompicVille) {
		this.ompicVille = ompicVille;
	}
	
	
	

}