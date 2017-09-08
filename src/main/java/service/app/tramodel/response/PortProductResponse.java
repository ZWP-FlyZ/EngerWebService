package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.BaseTypOtherItem;

public class PortProductResponse extends BaseResponse{

	private List<? extends BaseTypOtherItem> engTypOther;
	private List<? extends BaseTypOtherItem> entTypOther;
	public List<? extends BaseTypOtherItem> getEngTypOther() {
		return engTypOther;
	}
	public void setEngTypOther(List<? extends BaseTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	public List<? extends BaseTypOtherItem> getEntTypOther() {
		return entTypOther;
	}
	public void setEntTypOther(List<? extends BaseTypOtherItem> entTypOther) {
		this.entTypOther = entTypOther;
	}
}
