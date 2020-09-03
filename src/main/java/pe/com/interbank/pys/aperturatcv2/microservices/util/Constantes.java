package pe.com.interbank.pys.aperturatcv2.microservices.util;

public class Constantes {

	public static final String MEDIA_TYPE_JSON_UTF_8 = "application/json; charset=UTF-8";
	public static final String BEAN_ENVIRONMENT = "environment";

	public static final String PATH_MSG_HEADER = "header";
	public static final String PATH_MSG_HEADER_REQUEST = "HeaderRequest";
	public static final String PATH_MSG_MESSAGE_REQUEST = "MessageRequest";
	public static final String PATH_MSG_MESSAGE_ID = "messageId";
	public static final String PATH_MSG_SERVICE_ID = "serviceId";
	public static final String MSG_TYPE_RESPONSE = "response";
	public static final String MSG_TYPE_REQUEST = "request";
	public static final String PATH_MSG_BODY = "body";
	public static final String PATH_MSG_STATUS = "status";

	public static final String PATH_MSG_MESSAGE_RESPONSE = "MessageResponse";
	public static final String PATH_MSG_HEADER_RESPONSE = "HeaderResponse";
	public static final String MSG_TYPE_STATUS = "status";//NOSONAR
	public static final String MSG_TYPE_RESPONSE_TYPE = "responseType";
	
	public static final String PATH_REST_SERVICE = "aperturatcv2";
	public static final String PATH_REST_GENERAL = "/";

	public static final String MSG_ERROR_SERVICE = "Ocurrio un error inesperado";

	public static final String OK_CODE = "00";
	public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_YYYY_MM_DD_FORMAT = "YYYY-MM-dd";
	
	public static final String OPERATION = "operation";
	public static final String NUMERO_REINTENTOS = "numeroReintentos";
	
	public static final String ALTA_TARJETA_RESPONSE_BODY = "crearTarjetaCreditoResponse";
	
	public static final String NOTIFICACION_TIPO_PRINCIPAL = "Principal";
	public static final String NOTIFICACION_TIPO_SEGURO_CONSTANCIA = "Seguro";
	public static final String NOTIFICACION_TIPO_EMAIL = "Email";
	
	public static final String MONITOR_TIPO_ACF = "ACF";
	public static final String MONITOR_TIPO_CORE = "CORE";
	
	public static final String MESSAGE_ID_MAP = "messageId";//NOSONAR
	public static final String MESSAGE_ID_LOG = " messageId: ";
	public static final String SERVICE_ID_LOG = " serviceId: ";
	public static final String OFFSET_LOG = " offset: ";
	
	
	
	public static final String DETALLECUENTA = "detalleCuenta";
	public static final String IDCLIENTE = "idCliente";
	public static final String NUMBER = "number";
	public static final String DETALLETARJETA = "detalleTarjeta";
	public static final String FECHAALTA = "fechaAlta";
	public static final String TIPOBENEFICIARIO = "tipoBeneficiario";
	public static final String TIPOBENEFICIARIO_TI = "TI";
	public static final String TIPOBENEFICIARIO_BE = "BE";

	protected Constantes() {
		throw new IllegalAccessError("Clase Constantes");
	}

}
