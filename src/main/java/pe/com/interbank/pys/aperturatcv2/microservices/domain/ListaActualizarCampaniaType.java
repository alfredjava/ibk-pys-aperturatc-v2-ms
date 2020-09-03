package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaActualizarCampaniaType {

	List<ActualizarCampania> actualizaCampania;

	public ListaActualizarCampaniaType(@JsonProperty("actualizaCampania") List<ActualizarCampania> actualizaCampania) {
		this.actualizaCampania = actualizaCampania;
	}

	public List<ActualizarCampania> getActualizaCampania() {
		return actualizaCampania;
	}

	public void setActualizaCampania(List<ActualizarCampania> actualizaCampania) {
		this.actualizaCampania = actualizaCampania;
	}

}
