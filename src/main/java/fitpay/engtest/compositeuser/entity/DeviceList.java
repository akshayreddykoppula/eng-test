package fitpay.engtest.compositeuser.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceList {

	@JsonProperty("results")
	private List<Device> devices;

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}
