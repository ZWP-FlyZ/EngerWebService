package service.app.tramodel;

import java.util.List;

public class TraTypOthResponse extends BaseResponse {
	
	
	private String cityType;
	private List<TraTypOtherItem> traTypOther;

	public List<TraTypOtherItem> getTraTypOther() {
		return traTypOther;
	}

	public void setTraTypOther(List<TraTypOtherItem> traTypOther) {
		this.traTypOther = traTypOther;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	
}
