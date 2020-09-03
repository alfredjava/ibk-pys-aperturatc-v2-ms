package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Identity {

	private String netId;
	private String userId;
	private String supervisorId;
	private String deviceId;
	private String serverId;
	private String branchCode;

	@JsonCreator
	public Identity(@JsonProperty("netId") String netId, @JsonProperty("userId") String userId,
			@JsonProperty("supervisorId") String supervisorId, @JsonProperty("deviceId") String deviceId,
			@JsonProperty("serverId") String serverId, @JsonProperty("branchCode") String branchCode) {
		this.netId = netId;
		this.userId = userId;
		this.supervisorId = supervisorId;
		this.deviceId = deviceId;
		this.serverId = serverId;
		this.branchCode = branchCode;

	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}
