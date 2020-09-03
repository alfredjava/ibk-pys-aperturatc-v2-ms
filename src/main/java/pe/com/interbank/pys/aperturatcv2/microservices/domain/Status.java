package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {

	private String responseType;
	private String busResponseCode;
	private String busResponseMessage;
	private String srvResponseCode;
	private String srvResponseMessage;
	private String eqvResponseCode;
	private String eqvResponseMessage;
	private String messageIdResBus;
	private String apiResponseCode;
	private String apiResponseMessage;
	private String msResponseCode;
	private String msResponseMessage;

	@JsonCreator
	public Status(@JsonProperty("responseType") String responseType,
			@JsonProperty("busResponseCode") String busResponseCode,
			@JsonProperty("busResponseMessage") String busResponseMessage,
			@JsonProperty("srvResponseCode") String srvResponseCode,
			@JsonProperty("srvResponseMessage") String srvResponseMessage,
			@JsonProperty("eqvResponseCode") String eqvResponseCode,
			@JsonProperty("eqvResponseMessage") String eqvResponseMessage,
			@JsonProperty("messageIdResBus") String messageIdResBus,
			@JsonProperty("apiResponseCode") String apiResponseCode,
			@JsonProperty("apiResponseMessage") String apiResponseMessage,
			@JsonProperty("msResponseCode") String msResponseCode,
			@JsonProperty("msResponseMessage") String msResponseMessage) {
		this.responseType = responseType;
		this.busResponseCode = busResponseCode;
		this.busResponseMessage = busResponseMessage;
		this.srvResponseCode = srvResponseCode;
		this.srvResponseMessage = srvResponseMessage;
		this.eqvResponseCode = eqvResponseCode;
		this.eqvResponseMessage = eqvResponseMessage;
		this.messageIdResBus = messageIdResBus;
		this.apiResponseCode = apiResponseCode;
		this.apiResponseMessage = apiResponseMessage;
		this.msResponseCode = msResponseCode;
		this.msResponseMessage = msResponseMessage;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getBusResponseCode() {
		return busResponseCode;
	}

	public void setBusResponseCode(String busResponseCode) {
		this.busResponseCode = busResponseCode;
	}

	public String getBusResponseMessage() {
		return busResponseMessage;
	}

	public void setBusResponseMessage(String busResponseMessage) {
		this.busResponseMessage = busResponseMessage;
	}

	public String getSrvResponseCode() {
		return srvResponseCode;
	}

	public void setSrvResponseCode(String srvResponseCode) {
		this.srvResponseCode = srvResponseCode;
	}

	public String getSrvResponseMessage() {
		return srvResponseMessage;
	}

	public void setSrvResponseMessage(String srvResponseMessage) {
		this.srvResponseMessage = srvResponseMessage;
	}

	public String getEqvResponseCode() {
		return eqvResponseCode;
	}

	public void setEqvResponseCode(String eqvResponseCode) {
		this.eqvResponseCode = eqvResponseCode;
	}

	public String getEqvResponseMessage() {
		return eqvResponseMessage;
	}

	public void setEqvResponseMessage(String eqvResponseMessage) {
		this.eqvResponseMessage = eqvResponseMessage;
	}

	public String getMessageIdResBus() {
		return messageIdResBus;
	}

	public void setMessageIdResBus(String messageIdResBus) {
		this.messageIdResBus = messageIdResBus;
	}

	public String getApiResponseCode() {
		return apiResponseCode;
	}

	public void setApiResponseCode(String apiResponseCode) {
		this.apiResponseCode = apiResponseCode;
	}

	public String getApiResponseMessage() {
		return apiResponseMessage;
	}

	public void setApiResponseMessage(String apiResponseMessage) {
		this.apiResponseMessage = apiResponseMessage;
	}

	public String getMsResponseCode() {
		return msResponseCode;
	}

	public void setMsResponseCode(String msResponseCode) {
		this.msResponseCode = msResponseCode;
	}

	public String getMsResponseMessage() {
		return msResponseMessage;
	}

	public void setMsResponseMessage(String msResponseMessage) {
		this.msResponseMessage = msResponseMessage;
	}
}
