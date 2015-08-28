package com.wonders.frame.console.controller.api;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.model.vo.Ticket;
import com.wonders.frame.console.service.PermissionService;
import com.wonders.frame.console.service.PrivilegeService;
import com.wonders.frame.console.service.ResourceService;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.TreeNode;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.service.RuleTypeService;

@Controller
@RequestMapping("/api/user")
public class UserApiController extends AbstractGenericRelationObjController<User>{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private RuleTypeService ruleTypeService;
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/login/{ruleType}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ReturnObj<LoginInfo> login(
			@PathVariable("ruleType") String ruleType,
			@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "password", required = true) String pwd) {
		logger.debug("userLogin {}",ruleType);

		//查找对应规则类型
		RuleType rt=ruleTypeService.findByName(ruleType);
		if(rt==null){
			return new ReturnObj<LoginInfo>("Has no matched ruleType with name:"+ruleType);
		}
		
		//根据登陆名查找用户
		List<Integer> matchedId=new ArrayList<Integer>();
		
		List<User> users=userService.findByLoginName(loginName);
		
		if(users==null || users.size()==0){
			return new ReturnObj<LoginInfo>("Has no matched user with loginName:"+loginName);
		}
		
		//验证密码，获取用户ID
		for(User user:users){
			if(user.getPassword().equals(pwd)){
				matchedId.add(user.getId());
			}				
		}
		
		if(matchedId.size()==0){
			return new ReturnObj<LoginInfo>("Has no matched user with loginName:"+loginName);
		}
		//效验通过验证用户在制定规则类型下是否有关联对象存在
		List<Relation> userRelations=getRelationService().findAllRelation(rt.getId(), "user", matchedId);
		if(userRelations==null||userRelations.size()==0){
			return new ReturnObj<LoginInfo>("The user with loginName:"+loginName+" has no related objs in ruleType:"+ruleType);
		}
		
		//校验是否有多个相同用户存在
		boolean hasDoubleUser=false;
		Integer loginUserId=-1;
		for(Relation userRelation:userRelations){
			Integer tmpUserId=null;
			if(userRelation.getPtype().equals("user")){
				tmpUserId=userRelation.getPid();
			}else{
				tmpUserId=userRelation.getNid();
				
			}
			
			if(loginUserId==-1){
				loginUserId=tmpUserId;
			}else{
				if(!tmpUserId.equals(loginUserId)){
					hasDoubleUser=true;
					break;
				}
			}
		}
		
		
		//有多个用户，则返回异常信息
        if(hasDoubleUser){
			return new ReturnObj<LoginInfo>("Find more than one user with loginName:"+loginName+" in ruleType:"+ruleType);
        }
        
		List<Permission> permissions=new ArrayList<Permission>();
		
		permissions=getPermissionService().findAllChildPermission(rt.getId(), "user", loginUserId,0,permissions,null);	
		
		List<String> objFilter=new ArrayList<String>();
		objFilter.add("privilege");
		objFilter.add("resource");		
		permissions=getPermissionService().findAllParentPermission(rt.getId(), "user", loginUserId,0,permissions,objFilter);
		
		permissions=getPrivilegeService().bindPrivilegeType(permissions);
		
		permissions=getResourceService().bindResourceInfo2Permission(permissions);
		

		LoginInfo loginUser=new LoginInfo();
		
		loginUser.setPermission(permissions);
		loginUser.setUser(userService.findUserById(loginUserId));
		loginUser.setTicket(new Ticket(rt.getId(),loginUserId).getTicketNo());
  
		return new ReturnObj<LoginInfo>(loginUser);

	}
	
	@RequestMapping("/toListApi")
	public String toList(){
		return "common/listApi";	
	}
	
	@RequestMapping("/toEditApi")
	public String toEditApi(){
		return "common/editApi";
	}
}
