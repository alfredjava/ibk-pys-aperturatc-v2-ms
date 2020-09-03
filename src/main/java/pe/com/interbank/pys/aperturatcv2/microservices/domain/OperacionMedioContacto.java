package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperacionMedioContacto {

	private MedioContactoGeneral crearMedioContactoGeneral;
	private MedioContactoGeneral actualizarMedioContactoGeneral;
	private MedioContactoEspecializado crearMedioContactoEspecializado;

	public OperacionMedioContacto(@JsonProperty("crearMedioContactoGeneral") MedioContactoGeneral crearMedioContactoGeneral,
			@JsonProperty("actualizarMedioContactoGeneral") MedioContactoGeneral actualizarMedioContactoGeneral,
			@JsonProperty("crearMedioContactoEspecializado") MedioContactoEspecializado crearMedioContactoEspecializado) {
		this.crearMedioContactoGeneral = crearMedioContactoGeneral;
		this.actualizarMedioContactoGeneral = actualizarMedioContactoGeneral;
		this.crearMedioContactoEspecializado = crearMedioContactoEspecializado;
	}

	public MedioContactoGeneral getCrearMedioContactoGeneral() {
		return crearMedioContactoGeneral;
	}

	public void setCrearMedioContactoGeneral(MedioContactoGeneral crearMedioContactoGeneral) {
		this.crearMedioContactoGeneral = crearMedioContactoGeneral;
	}

	public MedioContactoGeneral getActualizarMedioContactoGeneral() {
		return actualizarMedioContactoGeneral;
	}

	public void setActualizarMedioContactoGeneral(MedioContactoGeneral actualizarMedioContactoGeneral) {
		this.actualizarMedioContactoGeneral = actualizarMedioContactoGeneral;
	}

	public MedioContactoEspecializado getCrearMedioContactoEspecializado() {
		return crearMedioContactoEspecializado;
	}

	public void setCrearMedioContactoEspecializado(MedioContactoEspecializado crearMedioContactoEspecializado) {
		this.crearMedioContactoEspecializado = crearMedioContactoEspecializado;
	}

}
