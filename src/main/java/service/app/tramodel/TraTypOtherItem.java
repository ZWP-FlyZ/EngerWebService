package service.app.tramodel;

import java.util.List;

public class TraTypOtherItem {
	private String traTyp;
	private List<TypeData> traTypMo;
	private List<TypeData> traTypEt;//能源类型
	public String getTraTyp() {
		return traTyp;
	}
	public void setTraTyp(String traTyp) {
		this.traTyp = traTyp;
	}
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
