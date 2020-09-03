package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AreaUpgradeType {

    private String indicadorUpgrade;
    private String numeroCuentaTarjeta;
    
	public AreaUpgradeType(@JsonProperty("indicadorUpgrade") String indicadorUpgrade, 
			@JsonProperty("numeroCuentaTarjeta") String numeroCuentaTarjeta) {
		this.indicadorUpgrade = indicadorUpgrade;
		this.numeroCuentaTarjeta = numeroCuentaTarjeta;
	}

	public String getIndicadorUpgrade() {
		return indicadorUpgrade;
	}

	public void setIndicadorUpgrade(String indicadorUpgrade) {
		this.indicadorUpgrade = indicadorUpgrade;
	}

	public String getNumeroCuentaTarjeta() {
		return numeroCuentaTarjeta;
	}

	public void setNumeroCuentaTarjeta(String numeroCuentaTarjeta) {
		this.numeroCuentaTarjeta = numeroCuentaTarjeta;
	}
 
}
