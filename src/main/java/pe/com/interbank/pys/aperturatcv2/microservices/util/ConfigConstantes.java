package pe.com.interbank.pys.aperturatcv2.microservices.util;

/**
 * Constantes de Configuracion del Microservicio
 * 
 * @author Adrian Pareja
 *
 */
public class ConfigConstantes {
	// Service ID
	public static final String SERVICE_ID = "service.id";
	public static final String PATH_REST_APERTURA_TC = "aperturatcv2";
	public static final String PATH_REST_GENERAL = "/";
	public static final String PATH_READ = "read";

	// Request Response
	public static final String MESSAGE_TYPE_REQUEST = "request";
	public static final String MESSAGE_TYPE_RESPONSE = "response";

	public static final String TOPICO_LOG = "topico.log";
	public static final String TOPICO_ASINCRONO = "topico.asincrono";
	public static final String TOPICO_RECOVER = "topico.recover";

	public static final String MESSAGEHUB_BROKERS = "messagehub.brokers";
	public static final String MESSAGEHUB_KEY = "messagehub.key";

	public static final String CONVERSORS_MS_PATH = "conversors.ms.path";
	public static final String SORTERS_MS_PATH = "sorters.ms.path";

	// Ubicacion de recursos de la aplicacion
	public static final String APP_RESOURCES_LOCATION = "app.resources.location";

	// Valores Configuracion Server Customizer
	public static final String SERVER_MAX_THREADS = "server.max.threads";
	public static final String SERVER_MIN_THREADS = "server.min.threads";
	public static final String SERVER_IDLE_TIMEOUT = "server.idle.timeout";

	// Valores Configuracion Cliente Rest
	public static final String CONNECTION_MAX_SIZE = "connection.max.size";
	public static final String CONNECTION_MAX_PER_ROUTE = "connection.max.per.route";

	// Autenticacion servicios bus
	public static final String REST_USUARIO = "rest.usuario";
	public static final String REST_KEY = "rest.password";

	// Valores Properties de Productor Kafka
	public static final String SEND_TIMEOUT_MS = "send.timeout.ms";

	// Encriptacion AES
	public static final String AES_KEY = "aes.key";
	public static final String AES_VECTOR = "aes.initvector";

	public static final String CREAR_MEDIO_CONTACTO_GENERAL_HOST = "rest.crearmediocontactogeneral.host";
	public static final String ACTUALIZAR_MEDIO_CONTACTO_GENERAL_HOST = "rest.actualizarmediocontactogeneral.host";
	public static final String CREAR_MEDIO_CONTACTO_ESPECIFICO_HOST = "rest.crearmediocontactoespecifico.host";
	public static final String CREAR_CLIENTE_HOST = "rest.crearcliente.host";
	public static final String SERVICIO_BUS_HOST = "rest.host";
	public static final String SERVICIO_FRAMEWORK4_BUS_HOST = "rest.fwk4.host";
	public static final String CREAR_MEDIO_CONTACTO_GENERAL_PATH = "rest.crearmediocontactogeneral.path";
	public static final String ACTUALIZAR_MEDIO_CONTACTO_GENERAL_PATH = "rest.actualizarmediocontactogeneral.path";
	public static final String CREAR_MEDIO_CONTACTO_ESPECIFICO_PATH = "rest.crearmediocontactoespecifico.path";
	public static final String CREAR_CLIENTE_PATH = "rest.crearcliente.path";
	public static final String ALTA_TARJETA_PATH = "rest.altatarjeta.path";
	public static final String LISTAADICIONALES_PATH = "rest.listaadicionales.path";

	public static final String SERVICE_CONNECT_TIMEOUT = "service.connect.timeout";
	public static final String SERVICE_READ_TIMEOUT = "service.read.timeout";

	// variables orquestacion sincrona
	public static final String LISTA_ADICIONALES = "orquestar.listaadicionales";
	public static final String CREAR_MEDIO_CONTACTO_GENERAL = "orquestar.crearmcgeneral";
	public static final String ACTUALIZAR_MEDIO_CONTACTO_GENERAL = "orquestar.actualizarmcgeneral";
	public static final String CREAR_MEDIO_CONTACTO_ESPECIFICO = "orquestar.crearmcespecifico";
	public static final String CREAR_CLIENTE = "orquestar.crearcliente";
	public static final String CREAR_CLIENTE_RESPONSE = "orquestar.crearcliente.response";
	public static final String ALTA_TARJETA = "orquestar.altatarjeta";
	public static final String ALTA_TARJETA_RESPONSE = "orquestar.altatarjeta.response";

	// Respuesta OK MS
	public static final String OK_CODE_MS = "codigo.ok.microservive";
	public static final String OK_RESPONSE_MS = "respuesta.ok.microservice";
	public static final String RESPONSE_CODE_MS = "campo.codigo.respuesta.bus";

	// CONSTANTES ERROR
	public static final String MICROSERVICE_NESTED_ERROR = "error.microservice.orquestar";
	public static final String MICROSERVICE_ERROR = "error.microservice.local";
	public static final String ERROR_TIPO = "error.microservice.tipo";

	// CAMPOS DE TRASPASO DE VARIBLES
	public static final String CAMPO_ID_SECUNDARIO = "campo.id.secundario";
	public static final String CAMPO_ID_SECUNDARIO_VALOR_EMAIL = "campo.id.secundario.valor.email";
	public static final String CAMPO_CONSUMER_ID_CLOUD = "campo.consumer.id.cloud";
	public static final String CAMPO_CONSUMER_ID_WEB = "campo.consumer.id.web";
	public static final String CAMPO_CODIGO_CORRESPONDENCIA_FISICA = "campo.codigo.correspondencia.valor.fisico";
	public static final String CAMPO_CODIGO_CORRESPONDENCIA_PRINCI = "campo.codigo.correspondencia.valor.princi";
	public static final String CAMPO_CODIGO_CORRESPONDENCIA_EMPE = "campo.codigo.correspondencia.valor.empe";
	public static final String CAMPO_CODIGO_CORRESPONDENCIA_EMJO = "campo.codigo.correspondencia.valor.emjo";

	// variables orquestacion asincrona
	public static final String SERVICE_ID_GRABAR_LDPD = "asincrono.grabarldpd";
	public static final String SERVICE_ID_ACTUALIZAR_CAMPANIA = "asincrono.actualizarcampania";
	public static final String SERVICE_ID_REGISTRAR_BD = "asincrono.registrarbd";
	public static final String SERVICE_ID_REGISTRAR_ADQ = "asincrono.registraradq";
	public static final String SERVICE_ID_AFILIAR_SEGURO_JLT = "asincrono.afiliarsegurojlt";
	public static final String SERVICE_ID_MS_NOTIFICACION = "asincrono.notificacion";
	public static final String SERVICE_ID_MS_REGISTRAR_MONITOR = "asincrono.registrarmonitor";
	public static final String SERVICE_ID_MS_FECHA_HORA_ENTREGA = "asincrono.fechahoraentrega";
	public static final String ORQ_SERVICE_ID_LIST = "asincrono.invoke.list";
	public static final String ORQ_SERVICE_ID = "asincrono.invoke.identifier";

	public static final String HEADER_RQ_TIMESTAMP_FORMATO = "asincrono.header.request.timestamp.format";
	public static final String ALTA_TC_FECHA_FORMATO = "asincrono.altatc.formato.fecha";
	public static final String GRABAR_LDPD_FECHA_FORMATO = "asincrono.grabarldpd.formato.fecha";
	public static final String GRABAR_LDPD_HORA_FORMATO = "asincrono.grabarldpd.formato.hora";
	public static final String ACTUALIZAR_CAMPANIA_FECHA_FORMATO = "asincrono.actualizarcampania.formato.fecha";
	public static final String REGISTRAR_BD_FECHA_FORMATO = "asincrono.registrarbd.formato.fecha";
	public static final String REGISTRAR_ADQ_FECHA_FORMATO = "asincrono.registraradq.formato.fecha";

	public static final String TIPO_EMAIL_CREAR_CLIENTE = "crear.cliente.tipo.email";
	public static final String CODIGO_CIIU_CREAR_CLIENTE = "crear.cliente.codigo.ciiu";

	public static final String CAMPOS_MAPEO_CONSUMERID_CAMPANIA = "campania.consumerid.mapping";

	// localizacion del volumen
	public static final String APP_VOLUMEN_LOCATION = "app.volumen.location";
	public static final String APP_MICROSERVICE_LOCATION = "app.microservice.location";

	public static final String REGISTRAR_ADQ_FECHA_CONTRATO_FORMATO = "registrar.adq.formato.fecha.contrato";
	
	public static final String CREAR_CLIENTE_FECHAS_FORMATO_INPUT = "crear.cliente.formato.fecha.in";
	
	public static final String REGISTRAR_MONITOR_FECHA_TRANSMISION = "registrar.monitor.fecha.transmision.formato";
	public static final String REGISTRAR_MONITOR_FECHA_TRANSACCION = "registrar.monitor.fecha.transaccion.formato";
	public static final String REGISTRAR_MONITOR_HORA_TRANSACCION = "registrar.monitor.hora.transaccion.formato";
	public static final String REGISTRAR_MONITOR_FECHAHORA_TRANSACCION = "registrar.monitor.fechahora.transaccion.formato";
	public static final String REGISTRAR_MONITOR_FECHA_NACIMIENTO = "registrar.monitor.fecha.nacimiento.formato";
	public static final String REGISTRAR_MONITOR_CODIGO_ALERTA = "registrar.monitor.codigo.alerta";
	public static final String REGISTRAR_MONITOR_CORE_CODIGO_ALERTA = "registrar.monitor.core.codigo.alerta";

	public static final String INFORMACION_ENTREGA_FECHA_ENTREGA_IN = "informacionentrega.formato.fechaentrega.in";
	public static final String INFORMACION_ENTREGA_FECHA_ENTREGA_OUT = "informacionentrega.formato.fechaentrega.out";
	public static final String INFORMACION_ENTREGA_HORA_ENTREGA = "informacionentrega.formato.horaentrega";

	public static final String ENVIO_CORREO_MARCATIPO = "envio.correo.marcatipo.";
	public static final String ENVIO_CORREO_EMAIL = "envio.correo.email";
	public static final String ENVIO_CORREO_APPSOURCE = "envio.correo.appSource";
	public static final String ENVIO_CORREO_NOMBRE_ARCHIVO_CONTRATO = "envio.correo.nombre.archivo.contrato";
	public static final String ENVIO_CORREO_NOMBRE_ARCHIVO_MARCATIPO = "envio.correo.nombre.archivo.marcatipo";
	public static final String ENVIO_CORREO_NOMBRE_ARCHIVO_DESGRAVAMEN = "envio.correo.nombre.archivo.desgravamen";
	public static final String ENVIO_CORREO_TEMPLATENAME_CAMBIO_EMAIL = "envio.correo.templateName.mail";
	public static final String ENVIO_CORREO_TEMPLATENAME_SEGURO = "envio.correo.templateName.seguro";
	public static final String ENVIO_CORREO_TEMPLATENAME_PRINCIPAL = "envio.correo.templateName.principal";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_NOMBRE = "envio.correo.parameters.key.nombre";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_LINEACREDITO = "envio.correo.parameters.key.lineaCredito";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_FECHAPAGO = "envio.correo.parameters.key.fechaPago";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_MARCATIPO = "envio.correo.parameters.key.marcaTipo";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_FECHA = "envio.correo.parameters.key.fecha";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_LINEA = "envio.correo.parameters.key.linea";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_TASA = "envio.correo.parameters.key.tasa";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_DIRECCION = "envio.correo.parameters.key.direccion";
	public static final String ENVIO_CORREO_PARAMETERS_KEY_EMAIL = "envio.correo.parameters.key.email";
	public static final String ENVIO_CORREO_DIRECCION_MAPPING = "envio.correo.direccion.mapping";
	public static final String ENVIO_CORREO_DIRECCION_FISICO = "envio.correo.direccion.fisico";
	public static final String ENVIO_CORREO_FORMATO_FECHA_LARGA = "envio.correo.formato.fecha.larga";
	public static final String ENVIO_CORREO_FORMATO_NUMERICO = "envio.correo.formato.numerico";
	public static final String ENVIO_CORREO_TEMPLATENAME_SIN_SEGURO = "envio.correo.templateName.sinseguro";

	public static final String TIPO_ROL_MEDIO_CONTACTO = "medio.contacto.tipo.rol";

	public static final String BODY_ERROR = "body.error";
	
	// Valores Configuracion Server Customizer
	public static final String AUDITORIA_MAX_THREADS = "audit.max.threads";
	public static final String AUDITORIA_MIN_THREADS = "audit.min.threads";
	public static final String AUDITORIA_QUEUE_SIZE = "audit.queue.size";

	// Key Store Secure Gateway
	public static final String KEYSTORE_FILE = "keystore.file";
	public static final String KEYSTORE_TYPE = "keystore.type";
	public static final String KEYSTORE_VALUE = "keystore.value";

	public static final String JWT_ENABLED = "jwt.validation.enabled";
	public static final String JWT_SECRET_KEY = "jwt.secret.key";
	public static final String JWT_MS_SECRET_KEY = "jwt.secret.ms.key";
	
	public static final String SRVRESPONSECODE_OK = "srv.response.code.correcto";
	
	protected ConfigConstantes() {
		throw new IllegalAccessError("Clase Constantes");
	}
}
