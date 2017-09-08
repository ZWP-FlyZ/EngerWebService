package service.app.model;

public class OceanPassData extends AllSimData{
	private String shipId;
	private String shipType;
	private int sitCot;
	public String getShipId() {
		return shipId;
	}
	public void setShipId(String shipId) {
		this.shipId = shipId;
	}
	public String getShipType() {
		return shipType;
	}
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}
	public int getSitCot() {
		return sitCot;
	}
	public void setSitCot(int sitCot) {
		this.sitCot = sitCot;
	}
	
}
