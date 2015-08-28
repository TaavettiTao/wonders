package com.wonders.frame.console.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.wonders.frame.console.dao.RoleDao;
import com.wonders.frame.console.dao.UserDao;
import com.wonders.frame.console.model.bo.Role;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.model.vo.Ticket;
import com.wonders.frame.console.service.PermissionService;
import com.wonders.frame.console.service.PrivilegeService;
import com.wonders.frame.console.service.ResourceService;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.service.RuleTypeService;
import com.wonders.frame.core.utils.JacksonMapper;


@Service("userService")
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private RoleDao roleDao;

	@Resource
	private RelationDao relationDao;
	
	@Resource
	private RelationService relationService;
	
	@Resource
	private RuleTypeService ruleTypeService;
	
	@Resource
	private PermissionService permissionService;

	@Resource
	private PrivilegeService privilegeService;

	@Resource
	private ResourceService resourceService;


	public List<User> findByLoginName(String loginName){
		return userDao.findByLoginName(loginName);
	}
	
	public void changeInfomations(User user) {
		if (user == null) {
			throw new IllegalArgumentException("user为null");
		}
		
		if (user.getId() == null) {
			logger.debug("保存实体");
			userDao.save(user);
			return;
		}
		
		User mergedUser = userDao.findOne(user.getId());
		if (mergedUser == null) {
			logger.warn("没有找到要更新的实体");
			return;
		}
		
		logger.debug("更新实体");
		mergedUser.setGender(user.getGender());
		mergedUser.setEmail(user.getEmail());
		userDao.save(mergedUser);
	}

	@Override
	public User findUserById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public boolean exists(Integer id, String password) {
		User user = findUserById(id);
		
		if (user != null) {
			String md5Pwd = DigestUtils.md5Hex(password);
			if (md5Pwd.equals(user.getPassword())) {
				return true;
			} else {
				logger.debug("密码不正确");
			}
		} else {
			logger.debug("没有找到实体");
		}
		return false;
	}

	@Override
	public void changePassword(Integer id, String password) {
		User user = userDao.findOne(id);
		if (user == null) {
			logger.debug("没有找到实体");
		} else {
			user.setPassword(DigestUtils.md5Hex(password));
			userDao.save(user);
		}
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	@Override
	public boolean hasRole(int userId, String roleName) {
		List<Role> tmpLists = new ArrayList();
		User user = userDao.findOne(userId);
		String pType = "U";
		if (user == null) return false;
		HashMap<String,String> qp=new HashMap<String,String>();
		qp.put("pid", String.valueOf(userId));
		qp.put("ptype", pType);
		List<Relation> relations = relationDao.findAll(qp,null);
		for(int i=0; i<relations.size(); i++){
			Role role = roleDao.findById(relations.get(i).getNid());
			tmpLists.add(role);
		}
		for (Role role : tmpLists) {
			if (role.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<User> findAll(){
		return userDao.findAll();
	}
	

	public User findByName(String username){
		return userDao.findByName(username);
	}

	@Override
	public void lock(Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock(Integer userId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.wonders.frame.core.service.UserService#findAllCached()
	 */
	@Override
	public List<User> findAllCached() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByNameAndPassword(String username, String password) {
		return userDao.findUserByNameAndPassword(username, password);
	}
	
	@Override
	public String getLoginInfo(String loginName, String password, String ruleType) {
		return JacksonMapper.toJson(login(loginName, password, ruleType));
	}

	@Override
	public ReturnObj<LoginInfo> login(String loginName, String password, String ruleType) {
		//查找对应规则类型
		RuleType rt=ruleTypeService.findByName(ruleType);
		if(rt==null){			
			return new ReturnObj<LoginInfo>("Has no matched ruleType with name:"+ruleType);
		}
		
		//根据登陆名查找用户
		List<Integer> matchedId=new ArrayList<Integer>();
		
		List<User> users=findByLoginName(loginName);
		
		if(users==null || users.size()==0){
			return new ReturnObj<LoginInfo>("Has no matched user with loginName:"+loginName);
		}
		
		//验证密码，获取用户ID
		for(User user:users){
			if(user.getPassword().equals(password)){
				matchedId.add(user.getId());
			}				
		}
		
		if(matchedId.size()==0){
			return new ReturnObj<LoginInfo>("Has no matched user with loginName:"+loginName);
		}
		//效验通过验证用户在制定规则类型下是否有关联对象存在
		List<Relation> userRelations=relationService.findAllRelation(rt.getId(), "user", matchedId);
		if(userRelations==null||userRelations.size()==0){
			return new ReturnObj<LoginInfo>("the user with loginName:"+loginName+" has no related objs in ruleType:"+ruleType);
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
		
		permissions=permissionService.findAllChildPermission(rt.getId(), "user", loginUserId,0,permissions,null);	
		
		List<String> objFilter=new ArrayList<String>();
		objFilter.add("privilege");
		objFilter.add("resource");		
		permissions=permissionService.findAllParentPermission(rt.getId(), "user", loginUserId,0,permissions,objFilter);
		
		permissions=privilegeService.bindPrivilegeType(permissions);
		
		permissions=resourceService.bindResourceInfo2Permission(permissions);
		

		LoginInfo loginUser=new LoginInfo();
		
		loginUser.setPermission(permissions);
		loginUser.setUser(findUserById(loginUserId));
		loginUser.setTicket(new Ticket(rt.getId(),loginUserId).getTicketNo());
  
		return new ReturnObj<LoginInfo>(loginUser);
	}
}
