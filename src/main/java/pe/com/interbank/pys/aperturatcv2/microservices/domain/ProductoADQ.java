package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoADQ {

	private String codigoProducto;
	private ListaItemsADQ listaItems;

	public ProductoADQ(@JsonProperty("codigoProducto") String codigoProducto,
			@JsonProperty("listaItems") ListaItemsADQ listaItems) {
		this.codigoProducto = codigoProducto;
		this.listaItems = listaItems;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public ListaItemsADQ getListaItems() {
		return listaItems;
	}

	public void setListaItems(ListaItemsADQ listaItems) {
		this.listaItems = listaItems;
	}

}
