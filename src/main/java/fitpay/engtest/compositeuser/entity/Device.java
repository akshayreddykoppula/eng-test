package fitpay.engtest.compositeuser.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {

	@JsonProperty("deviceIdentifier")
	private String deviceId;
	private String state;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", state=" + state + "]";
	}
}
