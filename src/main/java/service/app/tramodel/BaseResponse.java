package service.app.tramodel;

import java.util.List;

public class BaseResponse {
	protected int errCode ;
	protected String roleName;
	protected String timeRange;
	protected List<List<String>> xs;
	
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

	public List<List<String>> getXs() {
		return xs;
	}

	public void setXs(List<List<String>> xs) {
		this.xs = xs;
	}
	
	
}
