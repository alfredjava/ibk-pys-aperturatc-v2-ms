package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaProductosType {

	private List<ProductoType> producto;

	public ListaProductosType(@JsonProperty("producto") List<ProductoType> producto) {
		this.producto = producto;
	}

	public List<ProductoType> getProducto() {
		if (producto == null) {
			producto = new ArrayList<ProductoType>();
		}
		return this.producto;
	}

}
