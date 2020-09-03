package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrarADQ {

	private ExpedienteADQ expediente;
	private ListaProductosADQ listaProductos;

	public RegistrarADQ(@JsonProperty("expediente") ExpedienteADQ expediente,
			@JsonProperty("listaProductos") ListaProductosADQ listaProductos) {
		this.expediente = expediente;
		this.listaProductos = listaProductos;
	}

	public ExpedienteADQ getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedienteADQ expediente) {
		this.expediente = expediente;
	}

	public ListaProductosADQ getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ListaProductosADQ listaProductos) {
		this.listaProductos = listaProductos;
	}

}
