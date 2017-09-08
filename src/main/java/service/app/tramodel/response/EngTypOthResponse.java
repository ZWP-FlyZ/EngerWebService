package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.BaseTypOtherItem;

public class EngTypOthResponse extends BaseResponse{
	private  List<? extends BaseTypOtherItem> engTypOther;

	public List<? extends BaseTypOtherItem> getEngTypOther() {
		return engTypOther;
	}

	public void setEngTypOther(List<? extends BaseTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	
	


	
}
