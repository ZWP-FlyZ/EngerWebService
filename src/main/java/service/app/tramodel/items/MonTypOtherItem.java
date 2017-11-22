package service.app.tramodel.items;

import java.util.List;

import service.app.tramodel.TypeData;

public class MonTypOtherItem extends BaseTypOtherItem {
	
	private List<? extends TypeData> monTypEng;//能源类型
	private List<? extends TypeData> monTypCity;//城市类型
	private List<? extends TypeData> monTypTra;//运输类型
	public List<? extends TypeData> getMonTypEng() {
		return monTypEng;
	}
	public void setMonTypEng(List<? extends TypeData> monTypEng) {
		this.monTypEng = monTypEng;
	}
	public List<? extends TypeData> getMonTypCity() {
		return monTypCity;
	}
	public void setMonTypCity(List<? extends TypeData> monTypCity) {
		this.monTypCity = monTypCity;
	}
	public List<? extends TypeData> getMonTypTra() {
		return monTypTra;
	}
	public void setMonTypTra(List<? extends TypeData> monTypTra) {
		this.monTypTra = monTypTra;
	}
	
}
