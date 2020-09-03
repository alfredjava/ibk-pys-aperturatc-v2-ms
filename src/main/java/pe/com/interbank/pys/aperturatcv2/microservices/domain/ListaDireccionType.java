package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaDireccionType {

	private List<DireccionType> direccion;

	public ListaDireccionType(@JsonProperty("direccion") List<DireccionType> direccion) {
		this.direccion = direccion;
	}

	public List<DireccionType> getDireccion() {
		if (direccion == null) {
			direccion = new ArrayList<DireccionType>();
		}
		return this.direccion;
	}

}
