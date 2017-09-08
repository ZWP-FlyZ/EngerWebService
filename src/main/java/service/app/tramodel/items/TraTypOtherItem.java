package service.app.tramodel.items;

import java.util.List;

import service.app.tramodel.TypeData;

public class TraTypOtherItem extends BaseTypOtherItem{

	private List<TypeData> traTypMo;
	private List<TypeData> traTypEt;//能源类型


	public List<TypeData> getTraTypMo() {
		return traTypMo;
	}
	public void setTraTypMo(List<TypeData> traTypMo) {
		this.traTypMo = traTypMo;
	}
	public List<TypeData> getTraTypEt() {
		return traTypEt;
	}
	public void setTraTypEt(List<TypeData> traTypEt) {
		this.traTypEt = traTypEt;
	}

}
