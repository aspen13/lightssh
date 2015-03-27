package com.google.code.lightssh.project.tree.action;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.web.action.CrudAction;
import com.google.code.lightssh.project.tree.entity.Node;
import com.google.code.lightssh.project.tree.entity.Tree;
import com.google.code.lightssh.project.tree.service.TreeManager;

/**
 * tree action
 * @author YangXiaojin
 *
 */
@Component("treeAction")
@Scope("prototype")
public class TreeAction extends CrudAction<Tree>{

	private static final long serialVersionUID = 1L;
	
	private Tree tree;
	
	private Node node;
	
	@JSON(name="unique")
	public boolean isUnique() {
		return unique;
	}
	
	@Resource(name="treeManager")
	public void setTreeManager( TreeManager treeManager ){
		super.manager = treeManager;
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public TreeManager getManager( ){
		return (TreeManager)super.manager;
	}

	public Tree getTree() {
		this.tree = super.model;
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
		super.model = tree;
	}
	
	/**
	 * node popup
	 * @return
	 */
	public String popup( ){
		if( tree != null && tree.getIdentity() != null ){
			tree = getManager().get(tree);
			if( tree != null )
				request.setAttribute("tree_root", tree.getRoot() );
		}
		
		return SUCCESS;
	}
	
	public String editnode( ){
		if( node != null && node.getIdentity() != null )
			node = this.getManager().getNode(node);
		
		return SUCCESS;
	}
	
	/**
	 * 保存结点
	 */
	public String savenode( ){
		if( node == null)
			return INPUT;
		
		this.getManager().saveNode(tree,node);
		
		String hint =  "保存结点("+ node.getName() +")成功！";
        saveSuccessMessage( hint );
        String saveAndNext = request.getParameter("saveAndNext");
        if( saveAndNext != null && !"".equals( saveAndNext.trim() ) ){
        	return NEXT;
        }else{        	
        	return SUCCESS;
        }
	}

}
