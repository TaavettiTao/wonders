package com.wonders.frame.security.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wonders.frame.security.dao.LoginDao;



@Repository("loginDao")
public class LoginDaoImpl implements LoginDao{
	
	@PersistenceContext
	protected EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findUserInfoByUsername(String username) {
		String jpql="select u.name,u.password,p.type"
                +" from User u,Role r,Relation re,Privilege p,Relation re1"
                +" where u.id=re.pid and re.nid=r.id and re.nid=re1.pid and re1.nid=p.id and u.name=:username";
	    Query query = em.createQuery(jpql).setParameter("username", username);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllPrililegeType() {
		String sql="select type from af_privilege";
		Query query=em.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findUrlByPrililege(String privilege) {
		String sql="select res.path " 
		           +"from af_privilege p,af_relation rel,af_resource res " 
			       +"where p.id=rel.p_id and rel.n_id=res.id and p.type=?";
		Query query=em.createNativeQuery(sql).setParameter(1, privilege);
		return query.getResultList();
	}	
}
		
