package service.app.tramodel;

import java.util.List;

public class RoadOPassResponse extends BaseResponse {

	private List<? extends BaseTypOtherItem> engTypOther;
	private List<? extends BaseTypOtherItem> entTypOther;
	private List<? extends BaseTypOtherItem> disTypOther;
	private List<? extends BaseTypOtherItem> carTypOther;



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

	public List<? extends BaseTypOtherItem> getCarTypOther() {
		return carTypOther;
	}

	public void setCarTypOther(List<? extends BaseTypOtherItem> carTypOther) {
		this.carTypOther = carTypOther;
	}
	
	
}
