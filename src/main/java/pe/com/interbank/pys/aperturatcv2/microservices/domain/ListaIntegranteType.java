package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaIntegranteType {

	private List<Cliente> integrante;

	public ListaIntegranteType(@JsonProperty("integrante") List<Cliente> integrante) {
		this.integrante = integrante;
	}

	public List<Cliente> getIntegrante() {
		if (integrante == null) {
			integrante = new ArrayList<>();
		}
		return this.integrante;
	}
	
	public void setIntegrante(List<Cliente> integrante) {
		this.integrante = integrante;
	}

}
