package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AltaTCType {

	private String identificadorProceso;
	private String codigoMarca;
	private String indicadorTipo;
	private String codigoMoneda;
	private String importeLinea;
	private String importeLineaParalela;
	private String diaPago;
	private String codigoVendedor;
	private String indicadorSeguroSepelio;
	private String codigoOficinaCaptadora;
	private String codigoOficinaGestora;
	private String disenoPlastico;
	private String indicadorDobleMoneda;
	private String indicadorTipoCliente;
	private String nombrePlastico;
	private String codigoFormaPago;
	private String importeLineaAdicional;
	private String porcentajeLineaAdicional;
	private String codigoProducto;
	private String cuentaBancoSoles;
	private String oficinaSoles;
	private String cuentaNumeroSoles;
	private String cuentaTipoSoles;
	private String cuentaBancoDolares;
	private String oficinaDolares;
	private String cuentaNumeroDolares;
	private String cuentaTipoDolares;
	private String importeDisponibleEfectivoTitular;
	private String porcentajeDisponibleEfectivoTitular;
	private ListaBeneficiariosType beneficiarios;
	private String codigoCorrespondencia;
	private String indicadorValidacionCodigoUnico;
	private String codigoOfertaComercial;
	private String codigoTipoGestion;
	private String codigoClienteBase;
	private String codigoConvenio;
	private String indicadorGarantia;
	private String indicadorDevolucionCashback;
	private AreaUpgradeType areaUpgrade;

	public AltaTCType(@JsonProperty("identificadorProceso") String identificadorProceso,//NOSONAR
			@JsonProperty("codigoMarca") String codigoMarca, @JsonProperty("indicadorTipo") String indicadorTipo,
			@JsonProperty("codigoMoneda") String codigoMoneda,
			@JsonProperty("importeLinea") String importeLinea,
			@JsonProperty("importeLineaParalela") String importeLineaParalela, @JsonProperty("diaPago") String diaPago,
			@JsonProperty("codigoVendedor") String codigoVendedor,
			@JsonProperty("indicadorSeguroSepelio") String indicadorSeguroSepelio,
			@JsonProperty("codigoOficinaCaptadora") String codigoOficinaCaptadora,
			@JsonProperty("codigoOficinaGestora") String codigoOficinaGestora,
			@JsonProperty("disenoPlastico") String disenoPlastico,
			@JsonProperty("indicadorDobleMoneda") String indicadorDobleMoneda,
			@JsonProperty("indicadorTipoCliente") String indicadorTipoCliente,
			@JsonProperty("nombrePlastico") String nombrePlastico,
			@JsonProperty("codigoFormaPago") String codigoFormaPago,
			@JsonProperty("importeLineaAdicional") String importeLineaAdicional,
			@JsonProperty("porcentajeLineaAdicional") String porcentajeLineaAdicional,
			@JsonProperty("codigoProducto") String codigoProducto,
			@JsonProperty("cuentaBancoSoles") String cuentaBancoSoles,
			@JsonProperty("oficinaSoles") String oficinaSoles,
			@JsonProperty("cuentaNumeroSoles") String cuentaNumeroSoles,
			@JsonProperty("cuentaTipoSoles") String cuentaTipoSoles,
			@JsonProperty("cuentaBancoDolares") String cuentaBancoDolares,
			@JsonProperty("oficinaDolares") String oficinaDolares,
			@JsonProperty("cuentaNumeroDolares") String cuentaNumeroDolares,
			@JsonProperty("cuentaTipoDolares") String cuentaTipoDolares,
			@JsonProperty("importeDisponibleEfectivoTitular") String importeDisponibleEfectivoTitular,
			@JsonProperty("porcentajeDisponibleEfectivoTitular") String porcentajeDisponibleEfectivoTitular,
			@JsonProperty("beneficiarios") ListaBeneficiariosType beneficiarios,
			@JsonProperty("codigoCorrespondencia") String codigoCorrespondencia,
			@JsonProperty("indicadorValidacionCodigoUnico") String indicadorValidacionCodigoUnico,
			@JsonProperty("codigoOfertaComercial") String codigoOfertaComercial,
			@JsonProperty("codigoTipoGestion") String codigoTipoGestion,
			@JsonProperty("codigoClienteBase") String codigoClienteBase,
			@JsonProperty("codigoConvenio") String codigoConvenio,
			@JsonProperty("indicadorGarantia") String indicadorGarantia,
			@JsonProperty("indicadorDevolucionCashback") String indicadorDevolucionCashback,
			@JsonProperty("areaUpgrade") AreaUpgradeType areaUpgrade) {
		this.identificadorProceso = identificadorProceso;
		this.codigoMarca = codigoMarca;
		this.indicadorTipo = indicadorTipo;
		this.codigoMoneda = codigoMoneda;
		this.importeLinea = importeLinea;
		this.importeLineaParalela = importeLineaParalela;
		this.diaPago = diaPago;
		this.codigoVendedor = codigoVendedor;
		this.indicadorSeguroSepelio = indicadorSeguroSepelio;
		this.codigoOficinaCaptadora = codigoOficinaCaptadora;
		this.codigoOficinaGestora = codigoOficinaGestora;
		this.disenoPlastico = disenoPlastico;
		this.indicadorDobleMoneda = indicadorDobleMoneda;
		this.indicadorTipoCliente = indicadorTipoCliente;
		this.nombrePlastico = nombrePlastico;
		this.codigoFormaPago = codigoFormaPago;
		this.importeLineaAdicional = importeLineaAdicional;
		this.porcentajeLineaAdicional = porcentajeLineaAdicional;
		this.codigoProducto = codigoProducto;
		this.cuentaBancoSoles = cuentaBancoSoles;
		this.oficinaSoles = oficinaSoles;
		this.cuentaNumeroSoles = cuentaNumeroSoles;
		this.cuentaTipoSoles = cuentaTipoSoles;
		this.cuentaBancoDolares = cuentaBancoDolares;
		this.oficinaDolares = oficinaDolares;
		this.cuentaNumeroDolares = cuentaNumeroDolares;
		this.cuentaTipoDolares = cuentaTipoDolares;
		this.importeDisponibleEfectivoTitular = importeDisponibleEfectivoTitular;
		this.porcentajeDisponibleEfectivoTitular = porcentajeDisponibleEfectivoTitular;
		this.beneficiarios = beneficiarios;
		this.codigoCorrespondencia = codigoCorrespondencia;
		this.indicadorValidacionCodigoUnico = indicadorValidacionCodigoUnico;
		this.codigoOfertaComercial = codigoOfertaComercial;
		this.codigoTipoGestion = codigoTipoGestion;
		this.codigoClienteBase = codigoClienteBase;
		this.codigoConvenio = codigoConvenio;
		this.indicadorGarantia = indicadorGarantia;
		this.indicadorDevolucionCashback = indicadorDevolucionCashback;
		this.areaUpgrade = areaUpgrade;
	}

	public String getIdentificadorProceso() {
		return identificadorProceso;
	}

	public void setIdentificadorProceso(String identificadorProceso) {
		this.identificadorProceso = identificadorProceso;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getIndicadorTipo() {
		return indicadorTipo;
	}

	public void setIndicadorTipo(String indicadorTipo) {
		this.indicadorTipo = indicadorTipo;
	}

	public String getCodigoMoneda() {
		return codigoMoneda;
	}

	public void setCodigoMoneda(String codigoMoneda) {
		this.codigoMoneda = codigoMoneda;
	}

	public String getImporteLinea() {
		return importeLinea;
	}

	public void setImporteLinea(String importeLinea) {
		this.importeLinea = importeLinea;
	}

	public String getImporteLineaParalela() {
		return importeLineaParalela;
	}

	public void setImporteLineaParalela(String importeLineaParalela) {
		this.importeLineaParalela = importeLineaParalela;
	}

	public String getDiaPago() {
		return diaPago;
	}

	public void setDiaPago(String diaPago) {
		this.diaPago = diaPago;
	}

	public String getCodigoVendedor() {
		return codigoVendedor;
	}

	public void setCodigoVendedor(String codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
	}

	public String getIndicadorSeguroSepelio() {
		return indicadorSeguroSepelio;
	}

	public void setIndicadorSeguroSepelio(String indicadorSeguroSepelio) {
		this.indicadorSeguroSepelio = indicadorSeguroSepelio;
	}

	public String getCodigoOficinaCaptadora() {
		return codigoOficinaCaptadora;
	}

	public void setCodigoOficinaCaptadora(String codigoOficinaCaptadora) {
		this.codigoOficinaCaptadora = codigoOficinaCaptadora;
	}

	public String getCodigoOficinaGestora() {
		return codigoOficinaGestora;
	}

	public void setCodigoOficinaGestora(String codigoOficinaGestora) {
		this.codigoOficinaGestora = codigoOficinaGestora;
	}

	public String getDisenoPlastico() {
		return disenoPlastico;
	}

	public void setDisenoPlastico(String disenoPlastico) {
		this.disenoPlastico = disenoPlastico;
	}

	public String getIndicadorDobleMoneda() {
		return indicadorDobleMoneda;
	}

	public void setIndicadorDobleMoneda(String indicadorDobleMoneda) {
		this.indicadorDobleMoneda = indicadorDobleMoneda;
	}

	public String getIndicadorTipoCliente() {
		return indicadorTipoCliente;
	}

	public void setIndicadorTipoCliente(String indicadorTipoCliente) {
		this.indicadorTipoCliente = indicadorTipoCliente;
	}

	public String getNombrePlastico() {
		return nombrePlastico;
	}

	public void setNombrePlastico(String nombrePlastico) {
		this.nombrePlastico = nombrePlastico;
	}

	public String getCodigoFormaPago() {
		return codigoFormaPago;
	}

	public void setCodigoFormaPago(String codigoFormaPago) {
		this.codigoFormaPago = codigoFormaPago;
	}

	public String getImporteLineaAdicional() {
		return importeLineaAdicional;
	}

	public void setImporteLineaAdicional(String importeLineaAdicional) {
		this.importeLineaAdicional = importeLineaAdicional;
	}

	public String getPorcentajeLineaAdicional() {
		return porcentajeLineaAdicional;
	}

	public void setPorcentajeLineaAdicional(String porcentajeLineaAdicional) {
		this.porcentajeLineaAdicional = porcentajeLineaAdicional;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getCuentaBancoSoles() {
		return cuentaBancoSoles;
	}

	public void setCuentaBancoSoles(String cuentaBancoSoles) {
		this.cuentaBancoSoles = cuentaBancoSoles;
	}

	public String getOficinaSoles() {
		return oficinaSoles;
	}

	public void setOficinaSoles(String oficinaSoles) {
		this.oficinaSoles = oficinaSoles;
	}

	public String getCuentaNumeroSoles() {
		return cuentaNumeroSoles;
	}

	public void setCuentaNumeroSoles(String cuentaNumeroSoles) {
		this.cuentaNumeroSoles = cuentaNumeroSoles;
	}

	public String getCuentaTipoSoles() {
		return cuentaTipoSoles;
	}

	public void setCuentaTipoSoles(String cuentaTipoSoles) {
		this.cuentaTipoSoles = cuentaTipoSoles;
	}

	public String getCuentaBancoDolares() {
		return cuentaBancoDolares;
	}

	public void setCuentaBancoDolares(String cuentaBancoDolares) {
		this.cuentaBancoDolares = cuentaBancoDolares;
	}

	public String getOficinaDolares() {
		return oficinaDolares;
	}

	public void setOficinaDolares(String oficinaDolares) {
		this.oficinaDolares = oficinaDolares;
	}

	public String getCuentaNumeroDolares() {
		return cuentaNumeroDolares;
	}

	public void setCuentaNumeroDolares(String cuentaNumeroDolares) {
		this.cuentaNumeroDolares = cuentaNumeroDolares;
	}

	public String getCuentaTipoDolares() {
		return cuentaTipoDolares;
	}

	public void setCuentaTipoDolares(String cuentaTipoDolares) {
		this.cuentaTipoDolares = cuentaTipoDolares;
	}

	public String getImporteDisponibleEfectivoTitular() {
		return importeDisponibleEfectivoTitular;
	}

	public void setImporteDisponibleEfectivoTitular(String importeDisponibleEfectivoTitular) {
		this.importeDisponibleEfectivoTitular = importeDisponibleEfectivoTitular;
	}

	public String getPorcentajeDisponibleEfectivoTitular() {
		return porcentajeDisponibleEfectivoTitular;
	}

	public void setPorcentajeDisponibleEfectivoTitular(String porcentajeDisponibleEfectivoTitular) {
		this.porcentajeDisponibleEfectivoTitular = porcentajeDisponibleEfectivoTitular;
	}

	public ListaBeneficiariosType getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(ListaBeneficiariosType beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getCodigoCorrespondencia() {
		return codigoCorrespondencia;
	}

	public void setCodigoCorrespondencia(String codigoCorrespondencia) {
		this.codigoCorrespondencia = codigoCorrespondencia;
	}

	public String getIndicadorValidacionCodigoUnico() {
		return indicadorValidacionCodigoUnico;
	}

	public void setIndicadorValidacionCodigoUnico(String indicadorValidacionCodigoUnico) {
		this.indicadorValidacionCodigoUnico = indicadorValidacionCodigoUnico;
	}

	public String getCodigoOfertaComercial() {
		return codigoOfertaComercial;
	}

	public void setCodigoOfertaComercial(String codigoOfertaComercial) {
		this.codigoOfertaComercial = codigoOfertaComercial;
	}

	public String getCodigoTipoGestion() {
		return codigoTipoGestion;
	}

	public void setCodigoTipoGestion(String codigoTipoGestion) {
		this.codigoTipoGestion = codigoTipoGestion;
	}

	public String getCodigoClienteBase() {
		return codigoClienteBase;
	}

	public void setCodigoClienteBase(String codigoClienteBase) {
		this.codigoClienteBase = codigoClienteBase;
	}

	public String getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(String codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	public String getIndicadorGarantia() {
		return indicadorGarantia;
	}

	public void setIndicadorGarantia(String indicadorGarantia) {
		this.indicadorGarantia = indicadorGarantia;
	}

	public String getIndicadorDevolucionCashback() {
		return indicadorDevolucionCashback;
	}

	public void setIndicadorDevolucionCashback(String indicadorDevolucionCashback) {
		this.indicadorDevolucionCashback = indicadorDevolucionCashback;
	}

	public AreaUpgradeType getAreaUpgrade() {
		return areaUpgrade;
	}

	public void setAreaUpgrade(AreaUpgradeType areaUpgrade) {
		this.areaUpgrade = areaUpgrade;
	}

}
