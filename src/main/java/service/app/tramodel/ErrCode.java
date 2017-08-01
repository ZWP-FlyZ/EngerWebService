package service.app.tramodel;

public class ErrCode {
	
	
	public final static int DATA_AUTH_ERR = 44;//访问权限错误
	
	//登录
	public final static int LOGIN_OK = 10;//登录成功
	public final static int LOGIN_ERR_INFO = 11;//登录失败，错误的用户名或密码
	
		
	//数据统计部分传输
	public final static int DATA_OK = 30;//数据数据可用
	public final static int DATA_ERR = 31;//未知错误

	
	//信息设置部分
	public final static int SETTING_OK = 20;//成功可用
	public final static int SETTING_ERR = 21;//失败可用
	public final static int SETTING_REG_USER_ERR = 22;//用户名重复
	public final static int SETTING_PASS_ERR = 23;//原密码错误
	
	
	
	
}
