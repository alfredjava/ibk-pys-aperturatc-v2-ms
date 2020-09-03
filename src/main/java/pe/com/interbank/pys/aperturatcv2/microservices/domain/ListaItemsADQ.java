package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaItemsADQ {

	private List<String> item;

	public ListaItemsADQ(@JsonProperty("item") List<String> item) {
		this.item = item;
	}

	public List<String> getItem() {
		if (item == null) {
			item = new ArrayList<String>();
		}
		return this.item;
	}

}
