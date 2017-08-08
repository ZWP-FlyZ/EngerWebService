package service.app.tramodel.response;

import service.app.model.UserInfo;

public class LogInResponse extends BaseResponse{

	private UserInfo userInfo;
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}


	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


}
