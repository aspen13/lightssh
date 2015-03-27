<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<title>权限资源</title>
		
		<meta name="decorator" content="background"/>
		
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css"/>
		
		<script type="text/javascript">
			function selectResource( objElement ){	
				var arrayAllResources = $("input[type='checkbox']");			

				var parent = objElement.getAttribute('parent');	
				var identity = objElement.getAttribute('id');
				
				if( parent != 'empty' )
					selectParent( parent );	
				selectChildren( identity, objElement.checked );	
			}  
		
			/**
			 * 选择父结点
			 */
			function selectParent( id ){
				$("input[id='"+id+"']").attr("checked",true);
				
				var parent = $("input[id='"+id+"']").attr("parent");
				if( parent != 'empty' ){
					 selectParent ( parent );
				}
			}
			
			function selectChildren( id , selectedOrNot ){
				var allResources = $("input[parent='"+id+"']");
					
				for( var i=0;i<allResources.length ;i++){
					allResources[i].checked = selectedOrNot; 	
					//alert( oo );
					selectChildren( allResources[i].getAttribute('id') , selectedOrNot );
				}
			}
			
			$(document).ready(function(){
				//$("#mytree").treeview();
				$("#navigation_tree").treeview({
					persist: "cookie",
					collapsed: true,
					cookieId: "treeview-navigation"
				});
			});
		</script>
	</head>
	
	<body>
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<ul id="navigation_tree">
		<li><input type="checkbox" value="null" id="null" parent="empty" onclick="selectResource(this);"/>系统权限
			<ul>
			<s:set name="sortedList" value="@com.ylink.project.security.entity.Navigation@sort(#request.nav_list)"/>
			<s:iterator value="#sortedList" status="loop" >
				<li>
					<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" parent="null" onclick="selectResource(this);"/>
					<s:property value="%{name}"/>
					<s:iterator value="@com.ylink.project.security.entity.Navigation@sort(children)" status="loop2">
						<s:if test="#loop2.first"><ul></s:if>
						<li>
							<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" parent="<s:property value="%{parent.id}"/>" onclick="selectResource(this);"/>
							<s:property value="%{name}"/>
							<s:iterator value="@com.ylink.project.security.entity.Navigation@sort(children)" status="loop3">
								<s:if test="#loop3.first"><ul></s:if>
								<li>
									<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" parent="<s:property value="%{parent.id}"/>" onclick="selectResource(this);"/>
									<s:property value="%{name}"/>
								</li>
								<s:if test="#loop3.last"></ul></s:if>
							</s:iterator>
						</li>
						<s:if test="#loop2.last"></ul></s:if>
					</s:iterator>
				</li>
			</s:iterator>
			</ul>
		</li>
		</ul>
	
	</body>
</html>