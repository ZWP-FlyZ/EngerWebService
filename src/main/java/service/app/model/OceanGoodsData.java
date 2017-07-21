package service.app.model;

public class OceanGoodsData extends AllSimData{
	private String shipId;
	private String shipType;
	private Double tonnage;
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
	public Double getTonnage() {
		return tonnage;
	}
	public void setTonnage(Double tonnage) {
		this.tonnage = tonnage;
	}
	
	
}
