package service.app.tramodel;

import java.util.List;

public class IndexResponse extends BaseResponse{
	private  List<EngTypOtherItem> engTypOther;

	public List<EngTypOtherItem> getEngTypOther() {
		return engTypOther;
	}

	public void setEngTypOther(List<EngTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	
}
