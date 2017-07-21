package service.app.model;

public class TaxiTranData extends AllSimData{
	private String carId;
	private Double dpCot;
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Double getDpCot() {
		return dpCot;
	}
	public void setDpCot(Double dpCot) {
		this.dpCot = dpCot;
	}
	
}
