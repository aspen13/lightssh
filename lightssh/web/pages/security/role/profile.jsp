<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>编辑角色</title>
		
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">
		
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
				var ele = $("input[id='"+id+"'][type='checkbox']");
	
				var children = $("input[parent='"+id+"'][type='checkbox']");
				var selected_len =0;
				$.each(children,function( index,c ){
					if( $(c).attr("checked") )
						selected_len++;
				});
				if( selected_len==0 )
					$("input[id='"+id+"'][type='checkbox']").attr("checked",false)
				
				if( selected_len > 0 )
					$(ele).attr("checked",true); //选中子节点大于0，父节点选中
				
				var parent = $("input[id='"+id+"'][type='checkbox']").attr("parent");
				if( parent != 'empty' ){
					 selectParent ( parent );
				}
			}
			
			function selectChildren( id , selectedOrNot ){
				var allResources = $("input[parent='"+id+"'][type='checkbox']");
					
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
					collapsed: false,
					cookieId: "treeview-navigation-permission"
				});

				/**
				 * 数据校验
				 */
				$("#addForm").validate({
					rules:{
						"role.name":{required:true,maxlength:100}
						,"role.description":{maxlength:200}
					}
					,submitHandler: function(form) {
						doSave();
						form.submit();
					}
				});
			});
			
			/**
			 * 提交表单
			 */
			function doSave( ){
				$.each( $("input:checked"), function( index, checkbox ){
					var val = $(checkbox).attr('value');
					if( val != 'null'){
						var checked_per = "<input type='hidden' name='p_list["
							+index+"].id' value='"+ val +"' />";
						$("#addForm").append( checked_per );
					}
				});
				
				return true;
			}
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>角色管理</li>
			<li>编辑角色</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:set name="roles_perm_set" value="role.permissions.{token}"/>
		
		<s:form id="addForm" action="addPermission" namespace="/security/role" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name" class="required">名称</label></th>
					<td>
						<s:hidden name="role.id"/>
						<s:textfield name="role.name" id="name" size="60"/>
					</td>
				</tr>
				<tr>
					<th><label for="desc">描述</label></th>
					<td><s:textarea name="role.description" id="desc" cols="60" rows="3"/></td>
				</tr>
				<tr>
					<th><label for="permission">权限集</label></th>
					<td>
					
						<ul id="navigation_tree">
						<li>
							<s:set name="inRole" value="%{(#roles_perm_set.size>0)?true:false}"/>
							<input type="checkbox" value="null" id="null" parent="empty" onclick="selectResource(this);" <s:property value="%{#inRole?\"checked\":\"\"}"/>/>
							<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>">系统权限</span>
							<ul>
							<s:set name="sortedList" value="@com.google.code.lightssh.project.security.entity.Navigation@sort(navigation)"/>
							<s:iterator value="#sortedList" status="loop" >
								<li>
									<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
									<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" parent="null" onclick="selectResource(this);" <s:property value="%{#inRole?\"checked\":\"\"}"/>/>
									<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
									<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop2">
										<s:if test="#loop2.first"><ul></s:if>
										<li>
											<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
											<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" 
												parent="<s:property value="%{parent.id}"/>" onclick="selectResource(this);" <s:property value="%{#inRole?\"checked\":\"\"}"/>/>
											<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
											<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop3">
												<s:if test="#loop3.first"><ul></s:if>
												<li>
													<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
													<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" 
														parent="<s:property value="%{parent.id}"/>" onclick="selectResource(this);" <s:property value="%{#inRole?\"checked\":\"\"}"/>/>
													<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
													
													<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop4">
														<s:if test="#loop4.first"><ul></s:if>
														<li>
															<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
															<input type="checkbox" value="<s:property value="%{id}"/>" id="<s:property value="%{id}"/>" 
																parent="<s:property value="%{parent.id}"/>" onclick="selectResource(this);" <s:property value="%{#inRole?\"checked\":\"\"}"/>/>
															<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
														</li>
														<s:if test="#loop4.last"></ul></s:if>
													</s:iterator>
												</li>
												<s:if test="#loop3.last"></ul></s:if>
											</s:iterator>
										</li>
										<s:if test="#loop2.last"></ul></s:if>
									</s:iterator>
								</li>
							</s:iterator>
							</ul>
						
						</ul>
						
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<p class="submit">
			<input type="submit" class="action save" name="Submit" value="<s:property value="role!=null&&role.effective?'变更角色':'保存角色'"/>"/>
		</p>
		</s:form>
	</body>
</html>