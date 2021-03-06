package service.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import service.app.nosql.RespResult;
import service.app.nosql.ResultRepository;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.response.BaseResponse;
import service.app.util.CacheManager;
import service.app.util.CacheNameTools;

@Aspect
@Component
@Order(4)
public class ResultCacheAspect {
	
	@Autowired
	ResultRepository rr;
	
	@Autowired
	CacheManager cm;
	
	
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(ResultCacheAspect.class);
	
	@Pointcut("execution(* service.app.controller.*.*(..)) && "
			+ " !execution(* service.app.controller.LogController.*(..)) && "
			+ " !execution(* service.app.controller.SetController.*(..)) && "
			+ " !execution(* service.app.controller.TestController.*(..))")
	public void executeCache(){};
	
	@Around("executeCache()")
	public Object checkCache(ProceedingJoinPoint pjp) throws Throwable{
		logger.debug("in cache aspect");
		RequestData rd = (RequestData)pjp.getArgs()[1];
		MethodSignature ms = (MethodSignature)pjp.getSignature();
		String cacheName = ms.getName()+"_"+CacheNameTools.getResultCacheName(rd);
		Object result = null;
		//mLRU.printAll();
		
		String tc = cm.mFrontCacheGet(cacheName);
		//tc = null;//debug here
		RespResult re=null;
		if(tc!=null){
			re = rr.findOne(""+cacheName.hashCode());
			if(re!=null){
				logger.debug("get Cache["+cacheName+"] cache="+re.toString());
				result = re.response;
				return result;
			}
		}
		
		logger.debug("Don't have Cache["+cacheName+"]");
		result = pjp.proceed();
		if(((BaseResponse)result).getErrCode()==ErrCode.DATA_OK){
			rr.save(new RespResult(cacheName, result));
			
			String cn =  cm.mFrontCacheAdd(cacheName, cacheName);
			
			if(cn!=null){
				rr.delete(cn.hashCode()+"");
				logger.debug("LRU full! Cache["+cn+"] deleted");
			}
		}	
		return result;
	}
		
}
