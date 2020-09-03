package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaBeneficiariosType {

	private List<BeneficiarioType> beneficiario;

	public ListaBeneficiariosType(@JsonProperty("beneficiario") List<BeneficiarioType> beneficiario) {
		this.beneficiario = beneficiario;
	}

	public List<BeneficiarioType> getBeneficiario() {
		if (beneficiario == null) {
			beneficiario = new ArrayList<BeneficiarioType>();
		}
		return this.beneficiario;
	}

}
