package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	private String serviceId;
	private String consumerId;
	private String moduleId;
	private String channelCode;
	private String messageId;
	private String timestamp;
	private String countryCode;
	private String groupMember;
	private String referenceNumber;

	@JsonCreator
	public Response(@JsonProperty("serviceId") String serviceId, @JsonProperty("consumerId") String consumerId,
			@JsonProperty("moduleId") String moduleId, @JsonProperty("channelCode") String channelCode,
			@JsonProperty("messageId") String messageId, @JsonProperty("timestamp") String timestamp,
			@JsonProperty("countryCode") String countryCode, @JsonProperty("groupMember") String groupMember,
			@JsonProperty("referenceNumber") String referenceNumber) {
		this.serviceId = serviceId;
		this.consumerId = consumerId;
		this.moduleId = moduleId;
		this.channelCode = channelCode;
		this.messageId = messageId;
		this.timestamp = timestamp;
		this.countryCode = countryCode;
		this.groupMember = groupMember;
		this.referenceNumber = referenceNumber;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getGroupMember() {
		return groupMember;
	}

	public void setGroupMember(String groupMember) {
		this.groupMember = groupMember;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}
