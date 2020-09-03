package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosCliente {

	private Cliente cliente;
	private ListaIntegranteType integrantes;
	
	public DatosCliente(@JsonProperty("cliente") Cliente cliente,
	@JsonProperty("integrantes") ListaIntegranteType integrantes) {
		this.cliente = cliente;
		this.integrantes = integrantes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ListaIntegranteType getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(ListaIntegranteType integrantes) {
		this.integrantes = integrantes;
	}

}
