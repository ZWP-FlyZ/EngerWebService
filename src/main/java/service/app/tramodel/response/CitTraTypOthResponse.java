package service.app.tramodel.response;

import java.util.List;

import service.app.tramodel.items.CitTypOtherItem;

public class CitTraTypOthResponse extends BaseResponse{
	
	private String tranType;
	private List<CitTypOtherItem>  citTypOther;
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public List<CitTypOtherItem> getCitTypOther() {
		return citTypOther;
	}
	public void setCitTypOther(List<CitTypOtherItem> citTypOther) {
		this.citTypOther = citTypOther;
	}	
}
