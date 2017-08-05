package service.app.tramodel;

public class RtTypeData extends TypeData {
	
	private Double TypDatOfAllCo;

	
	public Double getTypDatOfAllCo() {
		return TypDatOfAllCo;
	}


	public void setTypDatOfAllCo(Double typDatOfAllCo) {
		TypDatOfAllCo = typDatOfAllCo;
	}


	public void addCoEng(Double co){
		if(TypDatOfAllCo==null)
			TypDatOfAllCo = 0.0;
		TypDatOfAllCo+=co;
	}
}
