package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaTelefonoType {

	private List<TelefonoType> telefono;

	public ListaTelefonoType(@JsonProperty("telefono") List<TelefonoType> telefono) {
		this.telefono = telefono;
	}

	public List<TelefonoType> getTelefono() {
		if (telefono == null) {
			telefono = new ArrayList<TelefonoType>();
		}
		return this.telefono;
	}

}
