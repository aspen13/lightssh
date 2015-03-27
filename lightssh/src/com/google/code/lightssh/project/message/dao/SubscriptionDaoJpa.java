package com.google.code.lightssh.project.message.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.message.entity.Subscription;

/**
 * 
 * @author Aspen
 * 
 */
@Repository("subscriptionDao")
public class SubscriptionDaoJpa extends JpaDao<Subscription> implements SubscriptionDao{

	private static final long serialVersionUID = -1621702801674079585L;
	
}
