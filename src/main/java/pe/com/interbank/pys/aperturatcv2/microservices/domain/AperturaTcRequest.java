package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AperturaTcRequest {
	
	private MessageRequest messageRequest;

	@JsonCreator
	public AperturaTcRequest(@JsonProperty("MessageRequest") MessageRequest messageRequest) {
		this.messageRequest = messageRequest;
	}

	@JsonProperty("MessageRequest")
	public MessageRequest getMessageRequest() {
		return messageRequest;
	}

	public void setMessageRequest(MessageRequest messageRequest) {
		this.messageRequest = messageRequest;
	}

}
