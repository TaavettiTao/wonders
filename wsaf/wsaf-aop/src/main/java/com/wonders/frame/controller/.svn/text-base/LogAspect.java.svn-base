package com.wonders.frame.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.model.Log;
import com.wonders.frame.service.LogService;



/**
 * 系统日志AOP
 * @author Administrator
 *
 */
@Component
@Aspect
public class LogAspect {

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	Calendar ca = Calendar.getInstance();
	String operDate = df.format(ca.getTime());
	String operation = "";
	String oldValue = "";
	String newValue = "";
	
	@Resource
	BasicCrudService basicCrudService;
	
	@Autowired
	private LogService logService;
	
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	/**
	 * 在切点方法集合执行前，执行前置通知,只有这样才能获取到操作是新增还是修改
	 * @param joinPoint
	 */
	@Before("execution(* com.wonders.frame.core.dao.*.save(..)) ")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("要进入切入点方法了.......\n");
		 //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
        //获取操作内容  
        getOperation(joinPoint.getArgs(), methodName);  
	}
	
	/**
	 * 添加业务逻辑方法切入点 
	 */
	@Pointcut("execution(* com.wonders.frame.*.dao.*.save(..)) ")
//	@Pointcut("execution(* com.wonders.frame.core.jpa.*.*.save(..)) ")
	public void insertCell(){}
		
	/**
	 * 删除业务逻辑方法切入点 
	 */
	@Pointcut("execution(* com.wonders.frame.*.dao.*.removeById(..))")
	public void deleteCell(){}
	
	/**
	 * 添加操作日志(后置通知) 
	 * @param joinPoint
	 * @param rtv
	 */
	@SuppressWarnings("unused")
	@AfterReturning(value="insertCell()",argNames="rtv", returning="rtv")
	public void insertLog(JoinPoint joinPoint, Object rtv) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
		String ip=getIpAddress(request);
		
		//获取登录管理员
//    	User user = logService.loginUser();
    	//获取登录管理员id
//    	Integer userId = user.getId();
//    	String username = user.getName();
		Integer userId = 1;
		String username="admin";
		
		if(userId == null){//没有管理员登录
			return;
		}
		//判断参数  
		if(joinPoint.getArgs() == null){//没有参数  
			return ;
		}
		//获取方法名 
		String methodName = joinPoint.getSignature().getName();
		//获取操作内容
		String opContent = optionContent(joinPoint.getArgs(),methodName);
		
		Log log = new Log();
		log.setOperatorId(userId);
		log.setOperatorName(username);
		log.setContent(opContent);
		log.setOperation(operation);
		log.setOperateTime(operDate);
		log.setLogIp(ip);
		logService.saveLog(log);
		System.out.println("切入点方法执行完了....... \n");

	}
	
    /** 
     * 删除操作
     * @param joinPoint 
     * @param rtv 
     */  
    @SuppressWarnings("unused")
	@AfterReturning(value="deleteCell()",argNames="rtv", returning="rtv")
    public void deleteLog(JoinPoint joinPoint, Object rtv) throws Throwable{
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
		String ip=getIpAddress(request);
    /*	//获取登录管理员
    	User user = logService.loginUser();
    	//获取登录管理员id
    	Integer userId = user.getId();
    	String username = user.getName();*/
		Integer userId = 1;
		String username="admin";
		if(userId == null){//没有管理员登录
			return;
		}
        //判断参数  
        if(joinPoint.getArgs() == null){//没有参数  
            return;  
        }  
        //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
        
        StringBuffer rs = new StringBuffer();
		rs.append(methodName);
		String className = null;
		for(Object info : joinPoint.getArgs()){
        	//获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数1，类型:" + className + "，值:(id:" + joinPoint.getArgs()[0]+")");
        }
          
        //创建日志对象  
		Log log = new Log();
		log.setOperatorId(userId);
		log.setOperatorName(username);
		log.setContent(rs.toString());
        log.setOperation("删除");//操作  
    	log.setOperateTime(operDate);
		log.setLogIp(ip);
        logService.saveLog(log);
    }
	
	/**
	 * 使用Java反射来获取被拦截方法的参数值， 将参数值拼接为操作内容
	 * @param args
	 * @param mName
	 * @return
	 */
	public String optionContent(Object[] args, String mName){
		if(args == null){
			return null;
		}
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		//遍历参数对象 
		for(Object info : args){
			//获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数"+index+"，类型:" + className + "，值:");
			//获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			// 遍历方法，判断get方法 
			for(Method method : methods){
				String methodName = method.getName();
				// 判断是不是get方法
				if(methodName.indexOf("get") == -1){//不是get方法 
					continue;//不处理
				}
				Object rsValue = null;
				try{
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
				}catch (Exception e) {
					continue;
				}
				//将值加入内容中
				rs.append("(" + methodName+ ":" + rsValue + ")");
			}
			rs.append("]");
			index ++;
		}
		return rs.toString();
	}
	
	
	/**
	 * 使用反射来获取被拦截方法的参数值，判断是否新增
	 * @param args
	 * @param mName
	 * @return
	 */
	public String getOperation(Object[] args, String mName){
		if(args == null){
			return null;
		}
		//遍历参数对象 
		for(Object info : args){
			//获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			// 遍历方法，判断get方法 
			for(Method method : methods){
				String methodName = method.getName();
				// 判断是不是get方法
				if(methodName.indexOf("get") == -1){//不是get方法 
					continue;//不处理
				}
				Object rsValue = null;
				try{
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
				}catch (Exception e) {
					continue;
				}
				if(methodName.equalsIgnoreCase("getId")){
					if(rsValue==null || rsValue.equals("")){
						operation="新增";
					}else{
						operation="修改";
						Class<?> clazz = info.getClass();
						Object obj = basicCrudService.executeMethod(StringUtils.uncapitalize(clazz.getSimpleName()),
								"findById", new Class[] {Integer.class}, new Object[] {(Integer)rsValue});
						Field[] fields = clazz.getDeclaredFields();  
						for (Field field : fields) {
							if(field.getName().equalsIgnoreCase("status")){
								field.setAccessible(true);  
								try {
									oldValue = field.get(obj).toString();
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				}
				if(methodName.equalsIgnoreCase("getStatus")){
					if(!operation.equals("新增")){
						newValue=(String) rsValue;
						if(!newValue.equals(oldValue)){
							operation="状态改变为："+newValue;
						}
					}
				}
			}
		}
		return operation;
	}
	
	  public String getIpAddress(HttpServletRequest request)
	    {
	    	String ip = request.getHeader("x-forwarded-for");
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getRemoteAddr();
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    	  ip = request.getHeader("http_client_ip");  
	    	}  
	    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    	  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    	}  
	    	if (ip != null && ip.indexOf(",") != -1) {  
			  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
			} 
	        if("0:0:0:0:0:0:0:1".equals(ip))
	        {
	        	ip="127.0.0.1";
	        }
	        return ip;
	    }
}
