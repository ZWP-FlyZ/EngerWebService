package service.app.tramodel;

import java.util.List;

public class EngTypOthResponse extends BaseResponse{
	private  List<EngTypOtherItem> engTypOther;

	public List<EngTypOtherItem> getEngTypOther() {
		return engTypOther;
	}

	public void setEngTypOther(List<EngTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	
}
