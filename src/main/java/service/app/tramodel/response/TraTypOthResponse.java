package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.BaseTypOtherItem;

public class TraTypOthResponse extends BaseResponse {
	
	
	private String cityType;
	private List<? extends BaseTypOtherItem> traTypOther;


	
	public List<? extends BaseTypOtherItem> getTraTypOther() {
		return traTypOther;
	}

	public void setTraTypOther(List<? extends BaseTypOtherItem> traTypOther) {
		this.traTypOther = traTypOther;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	
}
