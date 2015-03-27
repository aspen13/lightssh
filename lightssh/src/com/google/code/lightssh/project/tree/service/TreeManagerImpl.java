package com.google.code.lightssh.project.tree.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.tree.entity.Node;
import com.google.code.lightssh.project.tree.entity.Tree;

/**
 * tree manager 
 * @author YangXiaojin
 *
 */
@Component("treeManager")
public class TreeManagerImpl extends BaseManagerImpl<Tree> implements TreeManager{
	
	private static final long serialVersionUID = 377108948620867982L;
	
	@Resource(name="nodeDao")
	private Dao<Node> nodeDao;
	
	@Resource(name="treeDao")
	public void setDao(Dao<Tree> dao) {
		this.dao = dao;
	}
	
	public Dao<Tree> getDao( ){
		return super.dao;
	}

	@Override
	public Node getNode(Node node) {
		return nodeDao.read(node);
	}
	
	public void save(Tree t) {
		if( t.isInsert() ){
			Node root = new Node("root","tree root node!");
			t.setRoot(root);
			dao.create(t);
		}else{
			Tree db_tree = dao.read(t);
			if( db_tree != null){
				db_tree.setName(t.getName());
				db_tree.setDescription(t.getDescription());
				db_tree.setMaxChildren(t.getMaxChildren());
				db_tree.setMaxLayer(t.getMaxLayer());
				dao.update(db_tree);		
			}
		}
	}

	@Override
	public void saveNode(Tree tree,Node node) {
		if( node == null || tree == null )
			throw new ApplicationException("树和结点都不能为空！");
		
		Tree db_tree = this.get(tree);
		if( db_tree == null )
			throw new ApplicationException("树已不存在！");
		
		if(node.getParent()==null || 
				StringUtil.clean(node.getParent().getIdentity())==null ){
			node.setParent( db_tree.getRoot() );
		}else{
			Node parent = nodeDao.read(node.getParent());
			if( parent == null )
				throw new ApplicationException("父结点(id="
						+node.getParent().getIdentity()+")已不存在！");
		}
		
		if( node.isInsert() )
			nodeDao.create(node);
		else 
			nodeDao.update(node);
	}

}
