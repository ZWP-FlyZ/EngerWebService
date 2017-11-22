package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.BaseTypOtherItem;

public class MonTypOthResponse extends BaseResponse {

	private List<? extends BaseTypOtherItem> monTypOther;
	private List<? extends BaseTypOtherItem> monTypOtherPP;
	
	public List<? extends BaseTypOtherItem> getMonTypOther() {
		return monTypOther;
	}

	public void setMonTypOther(List<? extends BaseTypOtherItem> monTypOther) {
		this.monTypOther = monTypOther;
	}

	public List<? extends BaseTypOtherItem> getMonTypOtherPP() {
		return monTypOtherPP;
	}

	public void setMonTypOtherPP(List<? extends BaseTypOtherItem> monTypOtherPP) {
		this.monTypOtherPP = monTypOtherPP;
	}
	
	
	
}
