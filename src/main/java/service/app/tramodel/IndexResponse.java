package service.app.tramodel;

import java.util.List;

public class IndexResponse {
	private int errCode ;
	private String roleName;
	private String timeRange;
	
	private List<EngTypOtherItem> engTypOther;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
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

	public List<EngTypOtherItem> getEngTypOther() {
		return engTypOther;
	}

	public void setEngTypOther(List<EngTypOtherItem> engTypOther) {
		this.engTypOther = engTypOther;
	}
	
	

	
	
}
