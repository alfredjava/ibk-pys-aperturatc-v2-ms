package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Everis
 * 
 * 
 */
public class AperturaTcResponse {
	private MessageResponse messageResponse;

	@JsonCreator
	public AperturaTcResponse(@JsonProperty("MessageResponse") MessageResponse messageResponse) {
		this.messageResponse = messageResponse;
	}

	@JsonProperty("MessageResponse")
	public MessageResponse getMessageResponse() {
		return messageResponse;
	}

	public void setMessageResponse(MessageResponse messageResponse) {
		this.messageResponse = messageResponse;
	}
}
