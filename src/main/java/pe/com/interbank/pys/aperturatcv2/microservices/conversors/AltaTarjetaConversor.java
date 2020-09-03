package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.com.interbank.pys.altatarjeta.client.domain.AltaTarjetaRequest;
import pe.com.interbank.pys.altatarjeta.client.domain.AreaUpgrade;
import pe.com.interbank.pys.altatarjeta.client.domain.Beneficiario;
import pe.com.interbank.pys.altatarjeta.client.domain.BeneficiarioAlias;
import pe.com.interbank.pys.altatarjeta.client.domain.Body;
import pe.com.interbank.pys.altatarjeta.client.domain.CrearTarjetaCredito;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AltaTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AreaUpgradeType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.BeneficiarioType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.MessageRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class AltaTarjetaConversor {

	private static final Logger logger = LoggerFactory.getLogger(AltaTarjetaConversor.class);

	protected AltaTarjetaConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static AltaTarjetaRequest obtenerAltaTarjetaRequest(MessageRequest requestAperturaTC,
			HeaderRequest headerRequest) throws MicroserviceException {
		String messageId = headerRequest.getHeaderRequest().getRequest().getMessageId();
		AperturarTCType aperturarTCType = requestAperturaTC.getBody().getAperturarTC();
		AltaTCType altaTCType = aperturarTCType.getAltaTC();
		AltaTarjetaRequest altaTarjetaRequest = null;
		try {
			pe.com.interbank.pys.altatarjeta.client.domain.HeaderRequestType header = RequestConversor
					.obtenerHeaderRequest(headerRequest,
							new TypeReference<pe.com.interbank.pys.altatarjeta.client.domain.HeaderRequestType>() {
							});
			// ACTUALIZANDO EL CAMPO CONSUMER ID
			header.getHeaderRequest().getRequest()
					.setConsumerId(PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CONSUMER_ID_CLOUD));

			CrearTarjetaCredito crearTarjetaCredito = obtenerCrearTarjetaCredito(requestAperturaTC, aperturarTCType,
					altaTCType);
			Body body = new Body(crearTarjetaCredito);
			pe.com.interbank.pys.altatarjeta.client.domain.MessageRequest messageRequest = new pe.com.interbank.pys.altatarjeta.client.domain.MessageRequest(
					header, body);
			altaTarjetaRequest = new AltaTarjetaRequest(messageRequest);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		logger.debug("Fin de obtenerAltaTarjetaRequest");
		return altaTarjetaRequest;
	}

	private static AreaUpgrade obtenerAreaUpgrade(AreaUpgradeType areaUpgradeType) {
		logger.debug("Inicio de obtenerAreaUpgrade");
		AreaUpgrade areaUpgrade = null;
		if (areaUpgradeType != null) {
			areaUpgrade = new AreaUpgrade(areaUpgradeType.getIndicadorUpgrade(),
					areaUpgradeType.getNumeroCuentaTarjeta());
		}
		logger.debug("Fin de obtenerAreaUpgrade");
		return areaUpgrade;
	}

	private static List<BeneficiarioAlias> obtenerBeneficiariosAlias(List<BeneficiarioType> beneficiariosType) {
		logger.debug("Inicio de obtenerBeneficiariosAlias");
		List<BeneficiarioAlias> beneficiarios = new ArrayList<>();
		if (beneficiariosType != null) {
			logger.debug("beneficiariosType no es nulo");
			Iterator<BeneficiarioType> iterator = beneficiariosType.iterator();
			while (iterator.hasNext()) {
				BeneficiarioType beneficiarioType = iterator.next();
				Beneficiario item = new Beneficiario(beneficiarioType.getBeneficiarioCodigoUnico(),
						beneficiarioType.getBeneficiarioTipoCliente(), beneficiarioType.getBeneficiarioNombreTarjeta(),
						beneficiarioType.getBeneficiarioPorcentaje(), beneficiarioType.getBeneficiarioMontoLinea(),
						beneficiarioType.getImporteDisponibleEfectivoAdicional(),
						beneficiarioType.getPorcentajeDisponibleEfectivoAdicional(),
						beneficiarioType.getBeneficiarioCol());
				BeneficiarioAlias alias = new BeneficiarioAlias(item);
				beneficiarios.add(alias);
			}
		}
		logger.debug("Fin de obtenerBeneficiariosAlias");
		return beneficiarios;
	}

	private static CrearTarjetaCredito obtenerCrearTarjetaCredito(MessageRequest requestAperturaTC,
			AperturarTCType aperturarTCType, AltaTCType altaTCType) {
		logger.debug("Inicio de obtenerCrearTarjetaCredito");
		AreaUpgrade areaUpgrade = obtenerAreaUpgrade(altaTCType.getAreaUpgrade());
		List<BeneficiarioAlias> beneficiarios = new ArrayList<>();
		if (altaTCType.getBeneficiarios() != null) {
			beneficiarios = obtenerBeneficiariosAlias(altaTCType.getBeneficiarios().getBeneficiario());
		}
		String codigoVendedor = altaTCType.getCodigoVendedor();
		String indicadorTipoCliente = altaTCType.getIndicadorTipoCliente();
		if (codigoVendedor == null || codigoVendedor.isEmpty()) {
			logger.debug("No existe codigoVendedor");
			codigoVendedor = requestAperturaTC.getHeader().getHeaderRequest().getIdentity().getUserId();
		}
		if (indicadorTipoCliente == null || indicadorTipoCliente.isEmpty()) {
			logger.debug("No existe indicadorTipoCliente");
			indicadorTipoCliente = aperturarTCType.getDatosCliente().getCliente().getTipoCliente();
		}
		logger.debug("Fin de obtenerCrearTarjetaCredito");
		return new CrearTarjetaCredito(altaTCType.getIdentificadorProceso(),
				aperturarTCType.getDatosCliente().getCliente().getIdCliente(), null, altaTCType.getCodigoMarca(),
				altaTCType.getIndicadorTipo(), altaTCType.getCodigoMoneda(), null, altaTCType.getImporteLinea(),
				altaTCType.getImporteLineaParalela(), altaTCType.getDiaPago(), codigoVendedor,
				altaTCType.getIndicadorSeguroSepelio(), altaTCType.getCodigoOficinaCaptadora(),
				altaTCType.getCodigoOficinaGestora(), altaTCType.getDisenoPlastico(),
				altaTCType.getIndicadorDobleMoneda(), indicadorTipoCliente, altaTCType.getNombrePlastico(),
				altaTCType.getCodigoFormaPago(), altaTCType.getImporteLineaAdicional(),
				altaTCType.getPorcentajeLineaAdicional(), altaTCType.getCodigoProducto(),
				altaTCType.getCuentaBancoSoles(), altaTCType.getOficinaSoles(), altaTCType.getCuentaNumeroSoles(),
				altaTCType.getCuentaTipoSoles(), altaTCType.getCuentaBancoDolares(), altaTCType.getOficinaDolares(),
				altaTCType.getCuentaNumeroDolares(), altaTCType.getCuentaTipoDolares(),
				altaTCType.getImporteDisponibleEfectivoTitular(), altaTCType.getPorcentajeDisponibleEfectivoTitular(),
				beneficiarios, altaTCType.getCodigoCorrespondencia(), altaTCType.getIndicadorValidacionCodigoUnico(),
				altaTCType.getCodigoOfertaComercial(), altaTCType.getCodigoTipoGestion(),
				altaTCType.getCodigoClienteBase(), altaTCType.getCodigoConvenio(), altaTCType.getIndicadorGarantia(),
				altaTCType.getIndicadorDevolucionCashback(), areaUpgrade);
	}

}
