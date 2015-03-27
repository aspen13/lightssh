package com.google.code.lightssh.project.message.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.message.entity.Catalog;

/**
 * 
 * @author Aspen
 * 
 */
@Repository("catalogDao")
public class CatalogDaoJpa extends JpaDao<Catalog> implements CatalogDao{

	private static final long serialVersionUID = 4591184695004948881L;

}
