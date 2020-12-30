package axa.ma.axamatcher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Matcher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	

	private String axaCleanName;
	private String algo;
	private double similarity =0.0;
	private String ice;
	private String newDenom;
	
	private String source;

	

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNewDenom() {
		return newDenom;
	}

	public void setNewDenom(String newDenom) {
		this.newDenom = newDenom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAxaCleanName() {
		return axaCleanName;
	}

	public void setAxaCleanName(String axaCleanName) {
		this.axaCleanName = axaCleanName;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

}
