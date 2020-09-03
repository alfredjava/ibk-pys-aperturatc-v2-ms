package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AfiliarAon {

    private String codigoProducto;
    private String codigoPlan;
    private String codigoFrecuenciaPago;
    private MedioPago medioPago;
    private Prima prima;
    private String flagActualizarDireccion;
    
    
	public AfiliarAon(@JsonProperty("codigoProducto") String codigoProducto, @JsonProperty("codigoPlan") String codigoPlan, @JsonProperty("codigoFrecuenciaPago") String codigoFrecuenciaPago,
			@JsonProperty("medioPago") MedioPago medioPago, @JsonProperty("prima") Prima prima, @JsonProperty("flagActualizarDireccion") String flagActualizarDireccion) {
		this.codigoProducto = codigoProducto;
		this.codigoPlan = codigoPlan;
		this.codigoFrecuenciaPago = codigoFrecuenciaPago;
		this.medioPago = medioPago;
		this.prima = prima;
		this.flagActualizarDireccion = flagActualizarDireccion;
	}


	public String getCodigoProducto() {
		return codigoProducto;
	}


	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}


	public String getCodigoPlan() {
		return codigoPlan;
	}


	public void setCodigoPlan(String codigoPlan) {
		this.codigoPlan = codigoPlan;
	}


	public String getCodigoFrecuenciaPago() {
		return codigoFrecuenciaPago;
	}


	public void setCodigoFrecuenciaPago(String codigoFrecuenciaPago) {
		this.codigoFrecuenciaPago = codigoFrecuenciaPago;
	}


	public MedioPago getMedioPago() {
		return medioPago;
	}


	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}


	public Prima getPrima() {
		return prima;
	}


	public void setPrima(Prima prima) {
		this.prima = prima;
	}


	public String getFlagActualizarDireccion() {
		return flagActualizarDireccion;
	}


	public void setFlagActualizarDireccion(String flagActualizarDireccion) {
		this.flagActualizarDireccion = flagActualizarDireccion;
	}

}
