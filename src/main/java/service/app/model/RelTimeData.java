package service.app.model;

public class RelTimeData extends AllSimData {
	private String shipId;
	private String carId;
	private Double fuelCo;
	private Double latitude;
	private Double longitude;
	
	
	public String getShipId() {
		return shipId;
	}
	public void setShipId(String shipId) {
		this.shipId = shipId;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Double getFuelCo() {
		return fuelCo;
	}
	public void setFuelCo(Double fuelCo) {
		this.fuelCo = fuelCo;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
}
