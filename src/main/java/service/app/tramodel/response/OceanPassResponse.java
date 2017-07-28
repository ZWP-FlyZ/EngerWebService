package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.BaseTypOtherItem;

public class OceanPassResponse extends BaseResponse {
	private List<? extends BaseTypOtherItem> engTypOther;
	private List<? extends BaseTypOtherItem> entTypOther;
	private List<? extends BaseTypOtherItem> disTypOther;
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
	public List<? extends BaseTypOtherItem> getDisTypOther() {
		return disTypOther;
	}
	public void setDisTypOther(List<? extends BaseTypOtherItem> disTypOther) {
		this.disTypOther = disTypOther;
	}
	
}
