package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;

public class PortProductResponse extends BaseResponse{

	private List<EngTypOtherItem> engTypOther;
	private List<EntTypOtherItem> entTypOther;

	public List<EngTypOtherItem> getEngTypOther() {
		return engTypOther;
	}
	public void setEngTypOther(List<EngTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	public List<EntTypOtherItem> getEntTypOther() {
		return entTypOther;
	}
	public void setEntTypOther(List<EntTypOtherItem> entTypOther) {
		this.entTypOther = entTypOther;
	}
	
}
