package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pe.com.interbank.pys.altatarjeta.client.domain.BeneficiarioRespuesta;

public class DomainTest {
	AltaTCType altaTCType;
	AreaUpgradeType areaUpgrateType; 
	ListaBeneficiariosType beneficiarios;
	ActualizarCampania actualizarCampania;
	ListaProductosType listaProductos;
	Status status;
	AfiliarAon afiliarAon;
	MedioPago medioPago;
    Prima prima;
    CuentaDeposito numeroCuenta;
    Response response;
    HeaderResponse headerResponse;
    AperturarTCResponseType aperturarTCResponseType;
    HeaderResponseType headerResponseType;
    
    AperturarTCType aperturarTCType;
    ListaActualizarCampaniaType listaActualizarCampania;
    DatosCliente datosCliente;
    DireccionType direccionType;
    TelefonoType telefonoType;
    ListaIntegranteType integrantes;
    GrabarLPDP grabarLPDP;
    RegistrarADQ registrarADQ;
    MonitorCore monitorCore;
    RegistroMonitor registroMonitor;
    MonitorACF monitorACF;
    InformacionEntrega informacionEntrega;
		
	@Before
	public void init() {
		areaUpgrateType=new AreaUpgradeType("i001", "434343434343434");
		
		List<BeneficiarioType> beneficiario=new ArrayList<>();
		BeneficiarioType beneficiarioType=new 
				BeneficiarioType("beneficiarioCodigoUnico", 
						"beneficiarioTipoCliente", "beneficiarioNombreTarjeta",
						"beneficiarioPorcentaje", "beneficiarioMontoLinea",
						"importeDisponibleEfectivoAdicional", "porcentajeDisponibleEfectivoAdicional",
						"beneficiarioCol");
		beneficiario.add(beneficiarioType);
		
		beneficiarios=new ListaBeneficiariosType(beneficiario);
		
		
		altaTCType=new AltaTCType("identificadorProceso", "codigoMarca", 
				"indicadorTipo", "codigoMoneda", "importeLinea", 
				"importeLineaParalela", "diaPago", "codigoVendedor",
				"indicadorSeguroSepelio", "codigoOficinaCaptadora", 
				"codigoOficinaGestora", "disenoPlastico", "indicadorDobleMoneda",
				"indicadorTipoCliente", "nombrePlastico", "codigoFormaPago",
				"importeLineaAdicional", "porcentajeLineaAdicional", 
				"codigoProducto", "cuentaBancoSoles", "oficinaSoles", 
				"cuentaNumeroSoles", "cuentaTipoSoles", "cuentaBancoDolares",
				"oficinaDolares", "cuentaNumeroDolares", "cuentaTipoDolares",
				"importeDisponibleEfectivoTitular", "porcentajeDisponibleEfectivoTitular",
				beneficiarios, "codigoCorrespondencia", "indicadorValidacionCodigoUnico", 
				"codigoOfertaComercial", "codigoTipoGestion", "codigoClienteBase", 
				"codigoConvenio", 
				"indicadorGarantia", "indicadorDevolucionCashback", areaUpgrateType);
		
		altaTCType.setAreaUpgrade(areaUpgrateType);		
		altaTCType.setIdentificadorProceso("");
		altaTCType.setCodigoMarca("");
		altaTCType.setIndicadorTipo("");
		altaTCType.setCodigoMoneda("");
		altaTCType.setImporteLinea("");
		altaTCType.setImporteLineaParalela("");
		altaTCType.setDiaPago("");
		altaTCType.setCodigoVendedor("");
		altaTCType.setIndicadorSeguroSepelio("");
		altaTCType.setCodigoOficinaCaptadora("");
		altaTCType.setCodigoOficinaGestora("");
		altaTCType.setDisenoPlastico("");
		altaTCType.setIndicadorDobleMoneda("");
		altaTCType.setIndicadorTipoCliente("");
		altaTCType.setNombrePlastico("");
		altaTCType.setCodigoFormaPago("");
		altaTCType.setImporteLineaAdicional("");
		altaTCType.setPorcentajeLineaAdicional("");
		altaTCType.setCodigoProducto("");
		altaTCType.setCuentaBancoSoles("");
		altaTCType.setOficinaSoles("");
		altaTCType.setCuentaNumeroSoles("");
		altaTCType.setCuentaTipoSoles("");
		altaTCType.setCuentaBancoDolares("");
		altaTCType.setOficinaDolares("");
		altaTCType.setCuentaNumeroDolares("");
		altaTCType.setCuentaTipoDolares("");
		altaTCType.setImporteDisponibleEfectivoTitular("");
		altaTCType.setPorcentajeDisponibleEfectivoTitular("");
		altaTCType.setCodigoCorrespondencia("");
		altaTCType.setIndicadorValidacionCodigoUnico("");
		altaTCType.setCodigoOfertaComercial("");
		altaTCType.setCodigoTipoGestion("");
		altaTCType.setCodigoClienteBase("");
		altaTCType.setCodigoConvenio("");
		altaTCType.setIndicadorGarantia("");
		altaTCType.setIndicadorDevolucionCashback("");
		
		List<ProductoType> producto=new ArrayList<>();
		ProductoType productoType=new ProductoType("codigoProducto", 
				"core", "disponible", "entregaEsperada", "estadoSolicitud", "fechaAlta",
				"fechaAprobacion", "identificador", "monto", "nombreProducto",
				"numeroSolicitud", "paquete", "productoVendido", "productoVenta",
				"tamanio", "tipoTramite", "unidades");
		producto.add(productoType);		
		listaProductos=new ListaProductosType(producto);
		
		actualizarCampania=new ActualizarCampania("tipoRespuesta", "descripcion", 
				"estadoRespuesta", "aplicacion", "codigoEstadoLead", "codigoFuente", 
				"codigoOferta", "nombreOferta", "codigoTratamiento", "nombreTratamiento", 
				"core", "flagNuevoBus", "flagOperacion", "idMensaje", "motivo",
				listaProductos, "puntuacion",
				"resultado", "resumen", "codigoCampania",
				"nombreCampania");
		
		actualizarCampania.setTipoRespuesta("");
		actualizarCampania.setDescripcion("");
		actualizarCampania.setEstadoRespuesta("");
		actualizarCampania.setAplicacion("");
		actualizarCampania.setCodigoEstadoLead("");
		actualizarCampania.setCodigoFuente("");
		actualizarCampania.setCodigoOferta("");
		actualizarCampania.setNombreOferta("");
		actualizarCampania.setCodigoTratamiento("");
		actualizarCampania.setNombreTratamiento("");
		actualizarCampania.setCore("");
		actualizarCampania.setFlagNuevoBus("");
		actualizarCampania.setFlagOperacion("");
		actualizarCampania.setIdMensaje("");
		actualizarCampania.setMotivo("");
		actualizarCampania.setListaProductos(listaProductos);
		actualizarCampania.setPuntuacion("");
		actualizarCampania.setResultado("");
		actualizarCampania.setResumen("");
		actualizarCampania.setCodigoCampania("");
		actualizarCampania.setNombreCampania("");
		
		status = new Status("responseType", "busResponseCode", 
				"busResponseMessage", "srvResponseCode", "srvResponseMessage",
				"eqvResponseCode", "eqvResponseMessage", "messageIdResBus", 
				"apiResponseCode", "apiResponseMessage", "msResponseCode", 
				"msResponseMessage");
				
		status.setResponseType("responseType");
		status.setBusResponseCode("busResponseCode");
		status.setBusResponseMessage("busResponseMessage");
		status.setSrvResponseCode("srvResponseCode");
		status.setMessageIdResBus("messageIdResBus");
		status.setSrvResponseMessage("srvResponseMessage");
		numeroCuenta=new CuentaDeposito("codigoBanco", "codigoMoneda", 
				"codigoCategoria", "codigoTienda", "numeroCuenta");
		medioPago = new MedioPago("codigo", "tipo",numeroCuenta);
		prima=new Prima("monto", "moneda");
		afiliarAon=new AfiliarAon("codigoPro2121ducto","codigoPlan", 
				"codigoFrecuenciaPago",medioPago,prima, 
				"flagActualizarDireccion");
		
		response = new Response("asdas", "cons", "moduleId", "channelCode", "messageId", "timestamp", "countryCode", "groupMember", "referenceNumber");
		response.setServiceId("channelCode");
		response.setConsumerId("channelCode");
		response.setModuleId("channelCode");
		response.setTimestamp("channelCode");
		response.setCountryCode("channelCode");	
		response.setGroupMember("channelCode");	
		response.setReferenceNumber("channelCode");	
		response.setChannelCode("channelCode");
		response.setMessageId("messageId");
		
		headerResponse = new HeaderResponse(response, status);
		headerResponse.setResponse(response);
		headerResponse.setStatus(status);	
		
		List<BeneficiarioRespuesta> beneficiariosRespuesta=new ArrayList<>();
		BeneficiarioRespuesta beneficiarioRespuesta=new 
				BeneficiarioRespuesta("codigoUnico", 
						"numeroTarjeta",
						"fechaAlta",
						"fechaVencimiento");
		beneficiariosRespuesta.add(beneficiarioRespuesta);
		
		aperturarTCResponseType=new AperturarTCResponseType("codigoUnico",
				"numeroTarjeta", "numeroCuenta", 
				"fechaAlta", "fechaVencimiento", 
				beneficiariosRespuesta);
		
		headerResponseType=new HeaderResponseType(headerResponse);
		
		MedioContactoGeneral crearMedioContactoGeneral=new 
				MedioContactoGeneral("tipoMedioContacto", 
						"flagModificacion", "estado", 
						"codigoUso", "descripcion",
						"departamento", "prefijo", 
						"numeroAnexo", "idMedioContacto",
						"observaciones");
		
		MedioContactoGeneral actualizarMedioContactoGeneral=new 
				MedioContactoGeneral("tipoMedioContacto", 
						"flagModificacion", "estado", 
						"codigoUso", "descripcion",
						"departamento", "prefijo", 
						"numeroAnexo", "idMedioContacto",
						"observaciones");
		
		MedioContactoEspecializado crearMedioContactoEspecializado=new 
				MedioContactoEspecializado("codigoUso", "estado", 
						"tipoDireccion", "tipoVia", "nombreVia", 
						"numero", "manzana", "pisoLote", "interior", 
						"urbanizacion", "referencia", "departamento", 
						"provincia", "distrito", "pais", "ubigeo", "codigoPostal");
		
		OperacionMedioContacto operacionMedioContacto=new 
				OperacionMedioContacto(crearMedioContactoGeneral,
						actualizarMedioContactoGeneral,
						crearMedioContactoEspecializado);
		List<ActualizarCampania> listaactualizaCampania=new ArrayList<>();
		listaactualizaCampania.add(actualizarCampania);
		
		listaActualizarCampania =new ListaActualizarCampaniaType(listaactualizaCampania);
		
		
		
		direccionType=new DireccionType("tipoDireccion", "codigoTipoVivienda", 
				"codigoTipoVia",
				"nombreVia", "numeroCalle", "numeroManzana",
				"numeroInterior", "lote", "codigoUbigeo", 
				"sector", "urbanizacion", "referencia", 
				"tiempoResidenciaDomicilio", "departamento", 
				"provincia", "distrito");
		
		List<DireccionType> direccion =new ArrayList<>();
		direccion.add(direccionType);
		ListaDireccionType listaDireccion=new ListaDireccionType(direccion);
		
		List<TelefonoType> listaTelefono=new ArrayList<>();
		
		telefonoType=new TelefonoType("tipoTelefono", "codigoCiudad",
				"numeroTelefono", "numeroCelular", "numeroAnexo");
		listaTelefono.add(telefonoType);
		
		ListaTelefonoType listaTelefonoType=new ListaTelefonoType(listaTelefono);
		
		
		InformacionLaboralType informacionLaboral=new 
				InformacionLaboralType("situacionLaboral",
						"codigoOcupacion", "rucEmpresa", 
						"cargoActual", "codigoGiro", "codigoTipoContrato", 
						"fechaInicioContrato", "fechaFinContrato", 
						"ingresoFijoMensual", "ingresoVariableMensual",
						"patrimonio", "totalPatrimonio", "totalOtrosIngresos",
						"email", "razonSocial", listaTelefonoType, listaDireccion);
		
		 List<Cliente> listaintegrante=new ArrayList<>();
		 

		integrantes=new ListaIntegranteType(listaintegrante);
		
		
		 Cliente cliente=new Cliente("codigoTipoIntegrante", "idCliente", 
				 "tipoCliente", "tipoDocumento", "numeroDocumento",
				 "numeroPasaporte", "apellidoPaterno", "apellidoMaterno",
				 "primerNombre", "segundoNombre", "fechaNacimiento", "sexo",
				 "nivelEducacion", "estadoCivil", "numeroDependientes", 
				 "codigoPaisNacimiento", "codigoNacionalidad", "codigoPaisNacionalidad",
				 "codigoPaisResidencia", "tiempoResidenciaPeru", "email", listaTelefonoType,
				 listaDireccion, 
				 informacionLaboral, "codigoClientePotencial");
		 
		 
		
		datosCliente=new DatosCliente(cliente, integrantes);
		grabarLPDP=new GrabarLPDP("flagCliente", "flagLPD", "tipoConsentimiento", "idEmpresa");
		ExpedienteADQ expediente=new ExpedienteADQ("codigoCanal", "codigoPuntoVenta",
				"codigoTienda", "codigoPromotor", "codigoTipoGestion", 
				"codigoEstadoProceso", "codigoProceso", 
				"codigoSubProceso", "codigoTipoTramite");
		 List<ProductoADQ> ltsproductoaq=new ArrayList<>();
		
		 ListaProductosADQ listaProductosAdq=new ListaProductosADQ(ltsproductoaq);
		
		registrarADQ=new RegistrarADQ(expediente, listaProductosAdq);
		
		informacionEntrega=new InformacionEntrega
				("indicador", "direccion", "referencia", "codigoDistrito", "codigoProvincia",
						"codigoDepartamento", "codigoTiendaEntrega", "tipoDireccion",
						"numeroTelefono", "mail", "fechaEntrega", "horaInicio", "horaFin");
		
		monitorACF=new MonitorACF("tipoMensaje", "tarjetaLogin",
				"codigoTransaccion", "reverso", "numeroTransaccion", 
				"condicionPtoVenta", "codigoRespuesta", "razonRespuesta",
				"codigoServicio", "codigoEquipoCliente", "codigoTienda",
				"nombreComercio", "localidadComercio", "paisOrigen", 
				"direccionCorrespondencia", "codigoProcedencia");
		monitorCore=new MonitorCore("cuentaOrigen", "sucursalAperturaDestino",
		"direccionDomicilio", "telefonoResidencia", "statusCuentaProducto");
		
		registroMonitor=new RegistroMonitor(monitorACF, monitorCore);
		
		aperturarTCType=new AperturarTCType(operacionMedioContacto, altaTCType,
				datosCliente, listaActualizarCampania, afiliarAon, 
				grabarLPDP, 
				registrarADQ, informacionEntrega, registroMonitor);
	}
	@Test
	public void responseTest() {		
		Assert.assertNotNull(altaTCType.getAreaUpgrade());
		Assert.assertNotNull(altaTCType.getIdentificadorProceso());
		Assert.assertNotNull(actualizarCampania.getAplicacion());
		Assert.assertNotNull(actualizarCampania.getNombreCampania());
		Assert.assertNotNull(status.getBusResponseCode());
		Assert.assertNotNull(afiliarAon.getCodigoFrecuenciaPago());			
		Assert.assertNotNull(response.getChannelCode());	
		Assert.assertNotNull(headerResponseType.getHeaderResponse());
		Assert.assertNotNull(aperturarTCType.getAfiliarAon());
	}
	@Test
	public void setClienteTest(){
		 Cliente cliente=new Cliente("codigoTipoIntegrante", "idCliente", 
				 "tipoCliente", "tipoDocumento", "numeroDocumento",
				 "numeroPasaporte", "apellidoPaterno", "apellidoMaterno",
				 "primerNombre", "segundoNombre", "fechaNacimiento", "sexo",
				 "nivelEducacion", "estadoCivil", "numeroDependientes", 
				 "codigoPaisNacimiento", "codigoNacionalidad", "codigoPaisNacionalidad",
				 "codigoPaisResidencia", "tiempoResidenciaPeru", "email", null,
				 null, 
				 null, "codigoClientePotencial");
		
		cliente.setCodigoTipoIntegrante(cliente.getCodigoTipoIntegrante());
		cliente.setIdCliente(cliente.getIdCliente());
		cliente.setTipoCliente(cliente.getTipoCliente());
		cliente.setTipoDocumento(cliente.getTipoDocumento());
		cliente.setNumeroDocumento(cliente.getNumeroDocumento());
		cliente.setNumeroPasaporte(cliente.getNumeroPasaporte());
		cliente.setApellidoPaterno(cliente.getApellidoPaterno());
		cliente.setApellidoMaterno(cliente.getApellidoMaterno());
		cliente.setPrimerNombre(cliente.getPrimerNombre());
		cliente.setSegundoNombre(cliente.getSegundoNombre());
		cliente.setFechaNacimiento(cliente.getFechaNacimiento());
		cliente.setSexo(cliente.getSexo());
		cliente.setNivelEducacion(cliente.getNivelEducacion());
		cliente.setEstadoCivil(cliente.getEstadoCivil());
		cliente.setNumeroDependientes(cliente.getNumeroDependientes());
		cliente.setCodigoPaisNacimiento(cliente.getCodigoPaisNacimiento());
		cliente.setCodigoNacionalidad(cliente.getCodigoNacionalidad());
		cliente.setCodigoPaisNacionalidad(cliente.getCodigoPaisNacionalidad());
		cliente.setCodigoPaisResidencia(cliente.getCodigoPaisResidencia());
		cliente.setTiempoResidenciaPeru(cliente.getTiempoResidenciaPeru());
		cliente.setEmail(cliente.getEmail());
		cliente.setListaTelefono(cliente.getListaTelefono());
		cliente.setListaDireccion(cliente.getListaDireccion());
		cliente.setInformacionLaboral(cliente.getInformacionLaboral());
		cliente.setCodigoClientePotencial(cliente.getCodigoClientePotencial());
	}
	@Test
	public void setMedioContactoEspecializadoTest(){
		MedioContactoEspecializado medioContactoEspecializado=new 
				MedioContactoEspecializado("codigoUso", "estado",
						"tipoDireccion", "tipoVia", "nombreVia", 
						"numero", "manzana", "pisoLote", "interior",
						"urbanizacion", "referencia", "departamento",
						"provincia", "distrito", "pais", "ubigeo", "codigoPostal");
		medioContactoEspecializado.setCodigoUso(medioContactoEspecializado.getCodigoUso());
		medioContactoEspecializado.setEstado(medioContactoEspecializado.getEstado());
		medioContactoEspecializado.setTipoDireccion(medioContactoEspecializado.getTipoDireccion());
		medioContactoEspecializado.setTipoVia(medioContactoEspecializado.getTipoVia());
		medioContactoEspecializado.setNombreVia(medioContactoEspecializado.getNombreVia());
		medioContactoEspecializado.setNumero(medioContactoEspecializado.getNumero());
		medioContactoEspecializado.setManzana(medioContactoEspecializado.getManzana());
		medioContactoEspecializado.setPisoLote(medioContactoEspecializado.getPisoLote());
		medioContactoEspecializado.setInterior(medioContactoEspecializado.getInterior());
		medioContactoEspecializado.setUrbanizacion(medioContactoEspecializado.getUrbanizacion());
		medioContactoEspecializado.setReferencia(medioContactoEspecializado.getReferencia());
		medioContactoEspecializado.setDepartamento(medioContactoEspecializado.getDepartamento());
		medioContactoEspecializado.setProvincia(medioContactoEspecializado.getProvincia());
		medioContactoEspecializado.setDistrito(medioContactoEspecializado.getDistrito());
		medioContactoEspecializado.setPais(medioContactoEspecializado.getPais());
		medioContactoEspecializado.setUbigeo(medioContactoEspecializado.getUbigeo());
		medioContactoEspecializado.setCodigoPostal(medioContactoEspecializado.getCodigoPostal());
	}
	@Test
	public void setProductoTypeTest(){
		ProductoType productoType=new ProductoType("codigoProducto", "core",
				"disponible", "entregaEsperada", "estadoSolicitud", "fechaAlta",
				"fechaAprobacion", "identificador"," monto", "nombreProducto","numeroSolicitud", 
				"paquete", "productoVendido", "productoVenta",
				"tamanio", "tipoTramite", "unidades");
		
		productoType.setCodigoProducto("");
		productoType.setCore("");
		productoType.setDisponible("");
		productoType.setEntregaEsperada("");
		productoType.setEstadoSolicitud("");
		productoType.setFechaAlta("");
		productoType.setFechaAprobacion("");
		productoType.setIdentificador("");
		productoType.setMonto("");
		productoType.setNombreProducto("");
		productoType.setNumeroSolicitud("");
		productoType.setPaquete("");
		productoType.setProductoVendido("");
		productoType.setProductoVenta("");
		productoType.setTamanio("");
		productoType.setTipoTramite("");
		productoType.setUnidades("");
	}
	@Test
	public void setInformacionLaboralTypeTest(){
		
		InformacionLaboralType informacionLaboral=new 
				InformacionLaboralType("situacionLaboral",
						"codigoOcupacion", "rucEmpresa", 
						"cargoActual", "codigoGiro", "codigoTipoContrato", 
						"fechaInicioContrato", "fechaFinContrato", 
						"ingresoFijoMensual", "ingresoVariableMensual",
						"patrimonio", "totalPatrimonio", "totalOtrosIngresos",
						"email", "razonSocial", null, null);
		
		
		informacionLaboral.setSituacionLaboral("");
		informacionLaboral.setCodigoOcupacion("");
		informacionLaboral.setRucEmpresa("");
		informacionLaboral.setCargoActual("");
		informacionLaboral.setCodigoGiro("");
		informacionLaboral.setCodigoTipoContrato("");
		informacionLaboral.setFechaInicioContrato(""); 
		informacionLaboral.setFechaFinContrato("");
		informacionLaboral.setIngresoFijoMensual("");
		informacionLaboral.setIngresoVariableMensual("");
		informacionLaboral.setPatrimonio("");
		informacionLaboral.setTotalPatrimonio("");
		informacionLaboral.setTotalOtrosIngresos("");
		informacionLaboral.setEmail("");
		informacionLaboral.setRazonSocial("");
		informacionLaboral.setListaTelefono(null);
		informacionLaboral.setListaDireccion(null);
	}
	@Test
	public void setStatus(){
		status = new Status("responseType", "busResponseCode", 
				"busResponseMessage", "srvResponseCode", "srvResponseMessage",
				"eqvResponseCode", "eqvResponseMessage", "messageIdResBus", 
				"apiResponseCode", "apiResponseMessage", "msResponseCode", 
				"msResponseMessage");
		
		status.setResponseType(status.getResponseType());
		status.setBusResponseCode(status.getBusResponseCode());
		status.setBusResponseMessage(status.getBusResponseMessage());
		status.setSrvResponseCode(status.getSrvResponseCode());
		status.setSrvResponseMessage(status.getSrvResponseMessage());
		status.setEqvResponseCode(status.getEqvResponseCode());
		status.setEqvResponseMessage(status.getEqvResponseMessage());
		status.setMessageIdResBus(status.getMessageIdResBus());
		status.setApiResponseCode(status.getApiResponseCode());
		status.setApiResponseMessage(status.getApiResponseMessage());
		status.setMsResponseCode(status.getMsResponseCode());
		status.setMsResponseMessage(status.getMsResponseMessage());		
	}
	@Test
	public void setCuentaDepositoTest(){
		CuentaDeposito cuentaDeposito=new CuentaDeposito("codigoBanco",
				"codigoMoneda", "codigoCategoria", "codigoTienda", "numeroCuenta");
		
		cuentaDeposito.setCodigoBanco(cuentaDeposito.getCodigoBanco());
		cuentaDeposito.setCodigoMoneda(cuentaDeposito.getCodigoMoneda());
		cuentaDeposito.setCodigoCategoria(cuentaDeposito.getCodigoCategoria());
		cuentaDeposito.setCodigoTienda(cuentaDeposito.getCodigoTienda());
		cuentaDeposito.setNumeroCuenta(cuentaDeposito.getNumeroCuenta());
	}
	@Test
	public void setAperturarTCResponseTypeTest(){
		aperturarTCResponseType=new AperturarTCResponseType("codigoUnico",
				"numeroTarjeta", "numeroCuenta", 
				"fechaAlta", "fechaVencimiento", 
				null);
		aperturarTCResponseType.setCodigoUnico(aperturarTCResponseType.getCodigoUnico());
		aperturarTCResponseType.setNumeroTarjeta(aperturarTCResponseType.getNumeroTarjeta());
		aperturarTCResponseType.setNumeroCuenta(aperturarTCResponseType.getNumeroCuenta());
		aperturarTCResponseType.setFechaAlta(aperturarTCResponseType.getFechaAlta());
		aperturarTCResponseType.setFechaVencimiento(aperturarTCResponseType.getFechaVencimiento());
		aperturarTCResponseType.setBeneficiariosRespuesta(aperturarTCResponseType.getBeneficiariosRespuesta());
	}
	@Test
	public void setMedioContactoGeneralTest(){
		MedioContactoGeneral crearMedioContactoGeneral=new 
				MedioContactoGeneral("tipoMedioContacto", 
						"flagModificacion", "estado", 
						"codigoUso", "descripcion",
						"departamento", "prefijo", 
						"numeroAnexo", "idMedioContacto",
						"observaciones");
		
		crearMedioContactoGeneral.setTipoMedioContacto("");
		crearMedioContactoGeneral.setFlagModificacion("");
		crearMedioContactoGeneral.setEstado("");
		crearMedioContactoGeneral.setCodigoUso("");//campo1
		crearMedioContactoGeneral.setDescripcion("");//campo2
		crearMedioContactoGeneral.setDepartamento("");//campo3
		crearMedioContactoGeneral.setPrefijo("");//campo4
		crearMedioContactoGeneral.setNumeroAnexo("");//campo5
		crearMedioContactoGeneral.setIdMedioContacto("");
		crearMedioContactoGeneral.setObservaciones("");
	}
	@Test
	public void setAfiliarAonTest(){
		medioPago = new MedioPago("codigo", "tipo",numeroCuenta);
		prima=new Prima("monto", "moneda");
		afiliarAon=new AfiliarAon("codigoPro2121ducto","codigoPlan", 
				"codigoFrecuenciaPago",medioPago,prima, 
				"flagActualizarDireccion");
		
		afiliarAon.setCodigoProducto(afiliarAon.getCodigoProducto());
		afiliarAon.setCodigoPlan(afiliarAon.getCodigoPlan());
		afiliarAon.setCodigoFrecuenciaPago(afiliarAon.getCodigoFrecuenciaPago());
		afiliarAon.setMedioPago(afiliarAon.getMedioPago());
		afiliarAon.setPrima(afiliarAon.getPrima());
		afiliarAon.setFlagActualizarDireccion(null);
	}
}
