package service.app.tramodel.response;

import java.util.List;

import service.app.model.RelTimeData;

public class RelTimMapDataResponse extends BaseResponse {
	private String cityType; 
	private String contry;
	
	private List<RelTimeData> data;

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public List<RelTimeData> getData() {
		return data;
	}

	public void setData(List<RelTimeData> data) {
		this.data = data;
	}
	
	
}
