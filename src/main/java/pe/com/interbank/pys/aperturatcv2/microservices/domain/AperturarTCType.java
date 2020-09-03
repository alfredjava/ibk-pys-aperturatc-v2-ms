package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AperturarTCType {

	private OperacionMedioContacto operacionMedioContacto;
	private AltaTCType altaTC;
	private DatosCliente datosCliente;
	private ListaActualizarCampaniaType listaActualizarCampania;
	private AfiliarAon afiliarAon;
	private GrabarLPDP grabarLPDP;
	private RegistrarADQ registrarADQ;
	private InformacionEntrega informacionEntrega;
	private RegistroMonitor registroMonitor;

	public AperturarTCType(@JsonProperty("operacionMedioContacto") OperacionMedioContacto operacionMedioContacto,//NOSONAR
			@JsonProperty("altaTC") AltaTCType altaTC, @JsonProperty("datosCliente") DatosCliente datosCliente,
			@JsonProperty("listaActualizarCampania") ListaActualizarCampaniaType listaActualizarCampania,
			@JsonProperty("afiliarAon") AfiliarAon afiliarAon, @JsonProperty("grabarLPDP") GrabarLPDP grabarLPDP,
			@JsonProperty("registrarADQ") RegistrarADQ registrarADQ,
			@JsonProperty("informacionEntrega") InformacionEntrega informacionEntrega,
			@JsonProperty("registroMonitor") RegistroMonitor registroMonitor) {
		this.operacionMedioContacto = operacionMedioContacto;
		this.altaTC = altaTC;
		this.datosCliente = datosCliente;
		this.listaActualizarCampania = listaActualizarCampania;
		this.afiliarAon = afiliarAon;
		this.grabarLPDP = grabarLPDP;
		this.registrarADQ = registrarADQ;
		this.informacionEntrega = informacionEntrega;
		this.registroMonitor = registroMonitor;
	}

	public DatosCliente getDatosCliente() {
		return datosCliente;
	}

	public void setDatosCliente(DatosCliente datosCliente) {
		this.datosCliente = datosCliente;
	}

	public OperacionMedioContacto getOperacionMedioContacto() {
		return operacionMedioContacto;
	}

	public void setOperacionMedioContacto(OperacionMedioContacto operacionMedioContacto) {
		this.operacionMedioContacto = operacionMedioContacto;
	}

	public AltaTCType getAltaTC() {
		return altaTC;
	}

	public void setAltaTC(AltaTCType altaTC) {
		this.altaTC = altaTC;
	}

	public ListaActualizarCampaniaType getListaActualizarCampania() {
		return listaActualizarCampania;
	}

	public void setListaActualizarCampania(ListaActualizarCampaniaType listaActualizarCampania) {
		this.listaActualizarCampania = listaActualizarCampania;
	}

	public AfiliarAon getAfiliarAon() {
		return afiliarAon;
	}

	public void setAfiliarAon(AfiliarAon afiliarAon) {
		this.afiliarAon = afiliarAon;
	}

	public GrabarLPDP getGrabarLPDP() {
		return grabarLPDP;
	}

	public void setGrabarLPDP(GrabarLPDP grabarLPDP) {
		this.grabarLPDP = grabarLPDP;
	}

	public RegistrarADQ getRegistrarADQ() {
		return registrarADQ;
	}

	public void setRegistrarADQ(RegistrarADQ registrarADQ) {
		this.registrarADQ = registrarADQ;
	}

	public InformacionEntrega getInformacionEntrega() {
		return informacionEntrega;
	}

	public void setInformacionEntrega(InformacionEntrega informacionEntrega) {
		this.informacionEntrega = informacionEntrega;
	}

	public RegistroMonitor getRegistroMonitor() {
		return registroMonitor;
	}

	public void setRegistroMonitor(RegistroMonitor registroMonitor) {
		this.registroMonitor = registroMonitor;
	}
}
