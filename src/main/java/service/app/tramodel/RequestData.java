package service.app.tramodel;

public class RequestData implements Cloneable{
	private String username;
	private String password;
	private String roleName;
	private String roleType;
	private String place1;
	private String place2;
	private String timeRange;
	private String cityType;
	private String engerType;
	private String tranType;
	
	private String name;
	private String phone;
	
	private String typeName;
	private String typeS;
	
	private String passwordN;
	
	
	private String carId;
	private String shipId;
	private String companyId;
	
	private String upAuth;
	
	private String token;
	
	private String targeDay;
	
	public String getUpAuth() {
		return upAuth;
	}
	public void setUpAuth(String upAuth) {
		this.upAuth = upAuth;
	}
	public String getPlace1() {
		return place1;
	}
	public void setPlace1(String place1) {
		this.place1 = place1;
	}
	public String getPlace2() {
		return place2;
	}
	public void setPlace2(String place2) {
		this.place2 = place2;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public String getCityType() {
		return cityType;
	}
	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	public String getEngerType() {
		return engerType;
	}
	public void setEngerType(String engerType) {
		this.engerType = engerType;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeS() {
		return typeS;
	}
	public void setTypeS(String typeS) {
		this.typeS = typeS;
	}
	public String getPasswordN() {
		return passwordN;
	}
	public void setPasswordN(String passwordN) {
		this.passwordN = passwordN;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getShipId() {
		return shipId;
	}
	public void setShipId(String shipId) {
		this.shipId = shipId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getTargeDay() {
		return targeDay;
	}
	public void setTargeDay(String targeDay) {
		this.targeDay = targeDay;
	}
	
	public String getAllSimMessage(){
		
		StringBuffer sb = new StringBuffer();
		sb.append("username:"+username);
		sb.append(",roleType:"+roleType);
		sb.append(",roleName:"+roleName);
		sb.append(",place1:"+place1);
		sb.append(",place2:"+place2);
		
		return  sb.toString();
	}
	@Override
	public RequestData clone()  {
		// TODO Auto-generated method stub
		try {
			return (RequestData)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	

}
