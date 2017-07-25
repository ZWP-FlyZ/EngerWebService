package service.app.tramodel;

import java.util.List;

public class PerDisEngResponse extends BaseResponse {
	
	private List<TraTypOtherItem> traTypOther;

	public List<TraTypOtherItem> getTraTypOther() {
		return traTypOther;
	}

	public void setTraTypOther(List<TraTypOtherItem> traTypOther) {
		this.traTypOther = traTypOther;
	}
	
}
