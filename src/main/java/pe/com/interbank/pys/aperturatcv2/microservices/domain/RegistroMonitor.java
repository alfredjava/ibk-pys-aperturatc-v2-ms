package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistroMonitor {
	
	private MonitorACF monitorACF;
	private MonitorCore monitorCore;
	
	public RegistroMonitor(@JsonProperty("monitorACF") MonitorACF monitorACF, @JsonProperty("monitorCore") MonitorCore monitorCore) {
		this.monitorACF = monitorACF;
		this.monitorCore = monitorCore;
	}
	
	public MonitorACF getMonitorACF() {
		return monitorACF;
	}
	public void setMonitorACF(MonitorACF monitorACF) {
		this.monitorACF = monitorACF;
	}
	public MonitorCore getMonitorCore() {
		return monitorCore;
	}
	public void setMonitorCore(MonitorCore monitorCore) {
		this.monitorCore = monitorCore;
	}
	
}
