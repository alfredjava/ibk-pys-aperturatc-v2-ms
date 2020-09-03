package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaProductosADQ {

	private List<ProductoADQ> producto;

	public ListaProductosADQ(@JsonProperty("producto") List<ProductoADQ> producto) {
		this.producto = producto;
	}

	public List<ProductoADQ> getProducto() {
		if (producto == null) {
			producto = new ArrayList<ProductoADQ>();
		}
		return this.producto;
	}

}
