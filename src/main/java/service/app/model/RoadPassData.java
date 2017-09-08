package service.app.model;

public class RoadPassData extends AllSimData{

	private String carId;
	private String carType;
	private Integer sitCot;
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Integer getSitCot() {
		return sitCot;
	}
	public void setSitCot(Integer sitCot) {
		this.sitCot = sitCot;
	}
}
