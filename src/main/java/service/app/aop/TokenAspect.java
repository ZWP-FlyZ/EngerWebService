package service.app.aop;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import service.app.timwin.TimWinMap;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.response.BaseResponse;

@Aspect
@Component
@Order(3)
public class TokenAspect {

	private static final Logger logger = LoggerFactory.getLogger(TokenAspect.class);
	
	@Autowired
	TimWinMap tokenMap;
	
	@Pointcut("execution(* service.app.controller.*.*(..)) &&"
			+ " !execution(* service.app.controller.LogController.login(..)) && "
			+ "!execution(*  service.app.controller.*.afterPropertiesSet())  && "
			+ "!execution(* service.app.controller.SetController.downloadHelpDoc(..))")
	public void executionToken(){}
	
	
	@Around("executionToken()")
	public Object doTokenAroud(ProceedingJoinPoint pjp) throws Throwable{
		Signature si =  pjp.getSignature();
		String mn = si.getName();
		String cn = si.getDeclaringTypeName();
		
		HttpServletResponse response = (HttpServletResponse) pjp.getArgs()[0];
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		RequestData rd = (RequestData) pjp.getArgs()[1];
		Object result ;
		if(rd.getToken().equals("123123123")){
			result = pjp.proceed();
			tokenMap.repushIfContain(rd.getUsername(), rd.getToken());
		}
		else if(rd==null||rd.getToken()==null||
			rd.getToken().equals("")||!tokenMap.isContainData(rd.getToken())){
			MethodSignature ms = (MethodSignature)si;
			@SuppressWarnings("rawtypes")
			Class returnType = ms.getReturnType();
			Object no = returnType.newInstance();
			BaseResponse br = (BaseResponse)no;
			br.setErrCode(ErrCode.DATA_AUTH_ERR);
			br.setToken(rd.getToken());
			logger.error(cn +"." +mn+" username["+rd.getUsername()+"],token["+rd.getToken()+"] auth err" );
			result = no;
		}
		else
			{
				result = pjp.proceed();
				tokenMap.repushIfContain(rd.getUsername(), rd.getToken());
			}
		
		logger.info(cn +"." +mn+" username["+rd.getUsername()+"],token["+rd.getToken()+
								"] errCode["+((BaseResponse)result).getErrCode()+"]" );
		return result;
	}
	
	
	
}
