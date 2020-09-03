package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cliente {

	private String codigoTipoIntegrante;
	private String idCliente;
	private String tipoCliente;
	private String tipoDocumento;
	private String numeroDocumento;
	private String numeroPasaporte;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	private String fechaNacimiento;
	private String sexo;
	private String nivelEducacion;
	private String estadoCivil;
	private String numeroDependientes;
	private String codigoPaisNacimiento;
	private String codigoNacionalidad;
	private String codigoPaisNacionalidad;
	private String codigoPaisResidencia;
	private String tiempoResidenciaPeru;
	private String email;
	private ListaTelefonoType listaTelefono;
	private ListaDireccionType listaDireccion;
	private InformacionLaboralType informacionLaboral;
	private String codigoClientePotencial;

	public Cliente(@JsonProperty("codigoTipoIntegrante") String codigoTipoIntegrante,//NOSONAR
			@JsonProperty("idCliente") String idCliente, @JsonProperty("tipoCliente") String tipoCliente,
			@JsonProperty("tipoDocumento") String tipoDocumento,
			@JsonProperty("numeroDocumento") String numeroDocumento,
			@JsonProperty("numeroPasaporte") String numeroPasaporte,
			@JsonProperty("apellidoPaterno") String apellidoPaterno,
			@JsonProperty("apellidoMaterno") String apellidoMaterno, @JsonProperty("primerNombre") String primerNombre,
			@JsonProperty("segundoNombre") String segundoNombre,
			@JsonProperty("fechaNacimiento") String fechaNacimiento, @JsonProperty("sexo") String sexo,
			@JsonProperty("nivelEducacion") String nivelEducacion, @JsonProperty("estadoCivil") String estadoCivil,
			@JsonProperty("numeroDependientes") String numeroDependientes,
			@JsonProperty("codigoPaisNacimiento") String codigoPaisNacimiento,
			@JsonProperty("codigoNacionalidad") String codigoNacionalidad,
			@JsonProperty("codigoPaisNacionalidad") String codigoPaisNacionalidad,
			@JsonProperty("codigoPaisResidencia") String codigoPaisResidencia,
			@JsonProperty("tiempoResidenciaPeru") String tiempoResidenciaPeru, @JsonProperty("email") String email,
			@JsonProperty("listaTelefono") ListaTelefonoType listaTelefono,
			@JsonProperty("listaDireccion") ListaDireccionType listaDireccion,
			@JsonProperty("informacionLaboral") InformacionLaboralType informacionLaboral,
			@JsonProperty("codigoClientePotencial") String codigoClientePotencial) {
		this.codigoTipoIntegrante = codigoTipoIntegrante;
		this.idCliente = idCliente;
		this.tipoCliente = tipoCliente;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.numeroPasaporte = numeroPasaporte;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.nivelEducacion = nivelEducacion;
		this.estadoCivil = estadoCivil;
		this.numeroDependientes = numeroDependientes;
		this.codigoPaisNacimiento = codigoPaisNacimiento;
		this.codigoNacionalidad = codigoNacionalidad;
		this.codigoPaisNacionalidad = codigoPaisNacionalidad;
		this.codigoPaisResidencia = codigoPaisResidencia;
		this.tiempoResidenciaPeru = tiempoResidenciaPeru;
		this.email = email;
		this.listaTelefono = listaTelefono;
		this.listaDireccion = listaDireccion;
		this.informacionLaboral = informacionLaboral;
		this.codigoClientePotencial = codigoClientePotencial;
	}

	public String getCodigoTipoIntegrante() {
		return codigoTipoIntegrante;
	}

	public void setCodigoTipoIntegrante(String codigoTipoIntegrante) {
		this.codigoTipoIntegrante = codigoTipoIntegrante;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroPasaporte() {
		return numeroPasaporte;
	}

	public void setNumeroPasaporte(String numeroPasaporte) {
		this.numeroPasaporte = numeroPasaporte;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNivelEducacion() {
		return nivelEducacion;
	}

	public void setNivelEducacion(String nivelEducacion) {
		this.nivelEducacion = nivelEducacion;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getNumeroDependientes() {
		return numeroDependientes;
	}

	public void setNumeroDependientes(String numeroDependientes) {
		this.numeroDependientes = numeroDependientes;
	}

	public String getCodigoPaisNacimiento() {
		return codigoPaisNacimiento;
	}

	public void setCodigoPaisNacimiento(String codigoPaisNacimiento) {
		this.codigoPaisNacimiento = codigoPaisNacimiento;
	}

	public String getCodigoNacionalidad() {
		return codigoNacionalidad;
	}

	public void setCodigoNacionalidad(String codigoNacionalidad) {
		this.codigoNacionalidad = codigoNacionalidad;
	}

	public String getCodigoPaisNacionalidad() {
		return codigoPaisNacionalidad;
	}

	public void setCodigoPaisNacionalidad(String codigoPaisNacionalidad) {
		this.codigoPaisNacionalidad = codigoPaisNacionalidad;
	}

	public String getCodigoPaisResidencia() {
		return codigoPaisResidencia;
	}

	public void setCodigoPaisResidencia(String codigoPaisResidencia) {
		this.codigoPaisResidencia = codigoPaisResidencia;
	}

	public String getTiempoResidenciaPeru() {
		return tiempoResidenciaPeru;
	}

	public void setTiempoResidenciaPeru(String tiempoResidenciaPeru) {
		this.tiempoResidenciaPeru = tiempoResidenciaPeru;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public InformacionLaboralType getInformacionLaboral() {
		return informacionLaboral;
	}

	public void setInformacionLaboral(InformacionLaboralType informacionLaboral) {
		this.informacionLaboral = informacionLaboral;
	}

	public String getCodigoClientePotencial() {
		return codigoClientePotencial;
	}

	public void setCodigoClientePotencial(String codigoClientePotencial) {
		this.codigoClientePotencial = codigoClientePotencial;
	}

}
