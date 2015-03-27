package com.google.code.lightssh.project.mail.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.mail.entity.EmailContent;

/** 
 * @author YangXiaojin
 * @date 2012-11-13
 * @description：TODO
 */
@Repository("emailContentDao")
public class EmailContentDaoJpa extends JpaAnnotationDao<EmailContent> implements EmailContentDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3758429064655543513L;

}
