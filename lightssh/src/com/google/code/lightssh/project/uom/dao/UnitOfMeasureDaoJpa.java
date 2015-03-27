package com.google.code.lightssh.project.uom.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.uom.entity.Currency;
import com.google.code.lightssh.project.uom.entity.UnitOfMeasure;
import com.google.code.lightssh.project.uom.entity.UnitOfMeasure.UomType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("uomDao")
public class UnitOfMeasureDaoJpa extends JpaAnnotationDao<UnitOfMeasure>{
	
	private static final long serialVersionUID = 3401883955850546215L;

	public ListPage<UnitOfMeasure> list(ListPage<UnitOfMeasure> page,UnitOfMeasure t,UomType...types){
		if( t == null && types == null ){
			return list( page );
		}
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder( );
		
		hql.append( " FROM " + entityClass.getName() + " AS m " );
		hql.append( " WHERE 1=1 ");
		
		if( t != null ){
			 if( t.getType() != null ){
				hql.append( " AND m.type = ? " );
				params.add( t.getType() );
			 }
		
		if( t.getActive() != null ){
			hql.append( " AND ( m.active = ? "
					+ (Boolean.FALSE.equals(t.getActive())?"OR m.active IS NULL":"")+")" );
			params.add( t.getActive() );
		}
		
			if( StringUtil.clean( t.getAbbreviation() ) != null ){
				hql.append( " AND m.abbreviation like ? " );
				params.add( "%" + t.getAbbreviation().trim() + "%");
			}
			
			if( StringUtil.clean( t.getIsoCode() ) != null ){
				hql.append( " AND m.isoCode like ? " );
				params.add( "%" + t.getIsoCode().trim() + "%");
			}
		} 
		
		if( types != null ){
			for( int i=0;i<types.length;i++ ){
				if( i == 0 )
					hql.append( " AND ( m.type = ? " );
				else
					hql.append( "OR m.type = ? ");
				if( i== types.length-1 )
					hql.append( " ) " );
				params.add( types[i]);
			}
		}
		
		return super.query(page, hql.toString(), params.toArray( ) );
	}
	
	public ListPage<UnitOfMeasure> list(ListPage<UnitOfMeasure> page,UnitOfMeasure t ){
		if( t == null ){
			return list( page );
		}
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder( );
		
		hql.append( " FROM " + entityClass.getName() + " AS m " );
		hql.append( " WHERE 1=1 ");
		
		if (t.getType() != null) {
			hql.append(" AND m.type = ? ");
			params.add(t.getType());
		}

		if (t.getActive() != null) {
			hql.append(" AND ( m.active = ? "
							+ (Boolean.FALSE.equals(t.getActive()) ? "OR m.active IS NULL" : "") + ")");
			params.add(t.getActive());
		}

		if (StringUtil.clean(t.getIsoCode()) != null) {
			hql.append(" AND m.isoCode like ? ");
			params.add("%" + t.getIsoCode().trim() + "%");
		}
		
		return super.query(page, hql.toString(), params.toArray( ) );
		
	}
	
	@SuppressWarnings("unchecked")
	public Currency getCurrByCode(String code){
		if( code == null || code.trim().equals("") )
			return null;
		
		// hql.append( " FROM " + Currency.class.getName(); + " AS m " );
		
		StringBuilder hql = new StringBuilder( "FROM " );
		hql.append( Currency.class.getName() );
		hql.append( " AS m WHERE m.code = ? ");
		
		//List<Currency> result = super.getJpaTemplate().find( 
		//		hql.toString(), new Object[]{code} );
		
		Query query = this.getEntityManager().createQuery(hql.toString());
		this.addQueryParams(query,code);
		List<Currency> result = query.getResultList();
		
		return (result==null||result.isEmpty())?null:result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean isExsit(UnitOfMeasure unitOfMeasure){
		Boolean exsit = false;
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder( "FROM " );
		hql.append( UnitOfMeasure.class.getName() );
		hql.append( " AS m WHERE m.abbreviation = ? ");
		params.add(unitOfMeasure.getAbbreviation());
		
		hql.append( " AND m.type = ? ");
		params.add(unitOfMeasure.getType());
		
		if (unitOfMeasure.getCode() != null && !unitOfMeasure.getCode().equals("") ) {
			hql.append(" AND m.code <> ? ");
			params.add(unitOfMeasure.getCode());
		}
		
		Query query = this.getEntityManager().createQuery(hql.toString());
		this.addQueryParams(query,params);
		List<Currency> result = query.getResultList();
		
		if(result!=null && !result.isEmpty()){
			exsit = true;
		}
		
		return exsit;
	}

}
