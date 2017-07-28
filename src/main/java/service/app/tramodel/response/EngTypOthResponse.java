package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.EngTypOtherItem;

public class EngTypOthResponse extends BaseResponse{
	private  List<EngTypOtherItem> engTypOther;

	public List<EngTypOtherItem> getEngTypOther() {
		return engTypOther;
	}

	public void setEngTypOther(List<EngTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	
}
