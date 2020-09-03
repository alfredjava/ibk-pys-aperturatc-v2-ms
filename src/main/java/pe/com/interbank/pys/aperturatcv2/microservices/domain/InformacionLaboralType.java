package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InformacionLaboralType {

	private String situacionLaboral;
	private String codigoOcupacion;
	private String rucEmpresa;
	private String cargoActual;
	private String codigoGiro;
	private String codigoTipoContrato;
	private String fechaInicioContrato; 
	private String fechaFinContrato;
	private String ingresoFijoMensual;
	private String ingresoVariableMensual;
	private String patrimonio;
	private String totalPatrimonio;
	private String totalOtrosIngresos;
	private String email;
	private String razonSocial;
	private ListaTelefonoType listaTelefono;
	private ListaDireccionType listaDireccion;
	
	public InformacionLaboralType(@JsonProperty("situacionLaboral") String situacionLaboral,//NOSONAR
	@JsonProperty("codigoOcupacion") String codigoOcupacion,
	@JsonProperty("rucEmpresa") String rucEmpresa,
	@JsonProperty("cargoActual") String cargoActual,
	@JsonProperty("codigoGiro") String codigoGiro,
	@JsonProperty("codigoTipoContrato") String codigoTipoContrato,
	@JsonProperty("fechaInicioContrato") String fechaInicioContrato, 
	@JsonProperty("fechaFinContrato") String fechaFinContrato,
	@JsonProperty("ingresoFijoMensual") String ingresoFijoMensual,
	@JsonProperty("ingresoVariableMensual") String ingresoVariableMensual,
	@JsonProperty("patrimonio") String patrimonio,
	@JsonProperty("totalPatrimonio") String totalPatrimonio,
	@JsonProperty("totalOtrosIngresos") String totalOtrosIngresos,
	@JsonProperty("email") String email,
	@JsonProperty("razonSocial") String razonSocial,
	@JsonProperty("listaTelefono") ListaTelefonoType listaTelefono,
	@JsonProperty("listaDireccion") ListaDireccionType listaDireccion) {
		this.situacionLaboral = situacionLaboral;
		this.codigoOcupacion = codigoOcupacion;
		this.rucEmpresa = rucEmpresa;
		this.cargoActual = cargoActual;
		this.codigoGiro = codigoGiro;
		this.codigoTipoContrato = codigoTipoContrato;
		this.fechaInicioContrato = fechaInicioContrato;
		this.fechaFinContrato = fechaFinContrato;
		this.ingresoFijoMensual = ingresoFijoMensual;
		this.ingresoVariableMensual = ingresoVariableMensual;
		this.patrimonio = patrimonio;
		this.totalPatrimonio = totalPatrimonio;
		this.totalOtrosIngresos = totalOtrosIngresos;
		this.email = email;
		this.razonSocial = razonSocial;
		this.listaTelefono = listaTelefono;
		this.listaDireccion = listaDireccion;
	}

	public String getSituacionLaboral() {
		return situacionLaboral;
	}

	public void setSituacionLaboral(String situacionLaboral) {
		this.situacionLaboral = situacionLaboral;
	}

	public String getCodigoOcupacion() {
		return codigoOcupacion;
	}

	public void setCodigoOcupacion(String codigoOcupacion) {
		this.codigoOcupacion = codigoOcupacion;
	}

	public String getRucEmpresa() {
		return rucEmpresa;
	}

	public void setRucEmpresa(String rucEmpresa) {
		this.rucEmpresa = rucEmpresa;
	}

	public String getCargoActual() {
		return cargoActual;
	}

	public void setCargoActual(String cargoActual) {
		this.cargoActual = cargoActual;
	}

	public String getCodigoGiro() {
		return codigoGiro;
	}

	public void setCodigoGiro(String codigoGiro) {
		this.codigoGiro = codigoGiro;
	}

	public String getCodigoTipoContrato() {
		return codigoTipoContrato;
	}

	public void setCodigoTipoContrato(String codigoTipoContrato) {
		this.codigoTipoContrato = codigoTipoContrato;
	}

	public String getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(String fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public String getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(String fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public String getIngresoFijoMensual() {
		return ingresoFijoMensual;
	}

	public void setIngresoFijoMensual(String ingresoFijoMensual) {
		this.ingresoFijoMensual = ingresoFijoMensual;
	}

	public String getIngresoVariableMensual() {
		return ingresoVariableMensual;
	}

	public void setIngresoVariableMensual(String ingresoVariableMensual) {
		this.ingresoVariableMensual = ingresoVariableMensual;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public String getTotalPatrimonio() {
		return totalPatrimonio;
	}

	public void setTotalPatrimonio(String totalPatrimonio) {
		this.totalPatrimonio = totalPatrimonio;
	}

	public String getTotalOtrosIngresos() {
		return totalOtrosIngresos;
	}

	public void setTotalOtrosIngresos(String totalOtrosIngresos) {
		this.totalOtrosIngresos = totalOtrosIngresos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public ListaTelefonoType getListaTelefono() {
		return listaTelefono;
	}

	public void setListaTelefono(ListaTelefonoType listaTelefono) {
		this.listaTelefono = listaTelefono;
	}

	public ListaDireccionType getListaDireccion() {
		return listaDireccion;
	}

	public void setListaDireccion(ListaDireccionType listaDireccion) {
		this.listaDireccion = listaDireccion;
	}

}
