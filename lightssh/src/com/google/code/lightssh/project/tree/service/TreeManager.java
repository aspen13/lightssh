package com.google.code.lightssh.project.tree.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.tree.entity.Node;
import com.google.code.lightssh.project.tree.entity.Tree;

/**
 * tree manager
 * @author YangXiaojin
 *
 */
public interface TreeManager extends BaseManager<Tree>{
	
	/**
	 * 查询Node
	 */
	public Node getNode( Node node );
	
	/**
	 * save node
	 */
	public void saveNode( Tree tree,Node node );

}
