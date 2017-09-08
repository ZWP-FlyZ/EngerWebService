package service.app.tramodel;

public class TypeData implements MyAdd{
	String type;
	Double TypDatOfAllEng;
	Double TypDatOfAllLen;
	
	public TypeData(){
		TypDatOfAllEng = 0.0;
		TypDatOfAllLen = 0.0;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Double getTypDatOfAllEng() {
		return TypDatOfAllEng;
	}
	public void setTypDatOfAllEng(Double typDatOfAllEng) {
		TypDatOfAllEng = typDatOfAllEng;
	}
	public Double getTypDatOfAllLen() {
		return TypDatOfAllLen;
	}
	public void setTypDatOfAllLen(Double typDatOfAllLen) {
		TypDatOfAllLen = typDatOfAllLen;
	}
	public void addEng(Double eng ){
		TypDatOfAllEng += eng;
	}
	
	public void addLen(Double len ){
		TypDatOfAllLen += len;
	}

	@Override
	public Object add(Object o) {
		// TODO Auto-generated method stub
		if(o==null||!(o instanceof TypeData)) return this;
		TypeData td = (TypeData) o;
		//if(!this.type.equals(td.type))  return this;
		TypDatOfAllEng += td.TypDatOfAllEng;
		TypDatOfAllLen += td.TypDatOfAllLen;
		return this;
	}
}
