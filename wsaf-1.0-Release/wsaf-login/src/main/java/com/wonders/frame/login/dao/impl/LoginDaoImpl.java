package com.wonders.frame.login.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.login.dao.LoginDao;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findUserInfoByUsername(String username) {
		String jpql = "select u.name,u.password,p.type"
				+ " from User u,Role r,Relation re,Privilege p,Relation re1"
				+ " where u.id=re.pid and re.nid=r.id and re.nid=re1.pid and re1.nid=p.id and u.name=:username";
		Query query = em.createQuery(jpql).setParameter("username", username);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllPrililegeType() {
		String sql = "select type  from af_privilege t where t.removed=0";
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findUrlByPrililege(String privilege) {
		String sql = "select res.path "
				+ "from af_privilege p,af_relation rel,af_resource res "
				+ "where p.id=rel.p_id and rel.n_id=res.id and p.type=?";
		// +"where p.id=rel.p_id and rel.n_id=res.id and p.removed=0 and rel.removed=0 and res.removed=0 and p.type=?";
		Query query = em.createNativeQuery(sql).setParameter(1, privilege);
		return query.getResultList();
	}

	public User getById(int id) {
		String jpql = "select u from User u  where u.id=:id";
		Query query = em.createQuery(jpql).setParameter("id", id);
		return (User) query.getResultList();
	}

	public User getByName(String name) {
		String jpql = "select u from User u  where u.name=:name";
		Query query = em.createQuery(jpql).setParameter("name", name);
		return (User) query.getResultList();
	}

	public boolean loginUsers(String loginName, String password) {
		if (loginName == null)
			loginName = "";
		if (password == null)
			password = "";
		String jpql = "select u from User u  where u.loginName=:loginName and u.password=:password";
		Query query = em.createQuery(jpql).setParameter("loginName", loginName).setParameter("password", password);
		List l = query.getResultList();
		boolean flag = true;
		if (l.size() == 0)
			flag = false;
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<User> searchUsers(String name) {
		String jpql = "select u from User u  where u.name=:name";
		Query query = em.createQuery(jpql).setParameter("name", name);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		String jpql = "select u from User u where u.removed=0";
		Query query = em.createQuery(jpql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRuleTypeFromUserId(Integer userId) {
//		String sql = "select distinct t.ruleTypeId from Relation t where (t.ptype='user' and t.pid==:pid) or  (t.ntype='user' and t.nid=:nid)";
		String jpql = "select distinct t.rule_type_id from af_relation t where (t.p_type='user' and t.p_id=:pid) or  (t.n_type='user' and t.n_id=:nid)";
		Query query = em.createNativeQuery(jpql).setParameter("pid", userId).setParameter("nid", userId);
		return query.getResultList();
	}

	public int save(User user) {
		return 1;

	}

	public void update(User user) {
	}

	public void delete(int id) {
		User c = getById(id);
//		sessionFactory.getCurrentSession().delete(c);
	}

	public int checkName(String loginName) {
		if (loginName == null)
			loginName = "";
		String jpql = "select u from User u  where u.loginName=:loginName";
		Query query = em.createQuery(jpql).setParameter("loginName", loginName);
		List l = query.getResultList();
		if (l.size() > 0){
			return 1;
		} else {
			return 0;
		}
		
	}
}
