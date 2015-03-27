<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>角色列表</title>
		
		<script type="text/javascript">
			function doRemove( id,name ){
				var url = '<s:url value="/security/role/remove.do?role.id="/>' + id ;
				if( confirm('确认删除角色[' + name + ']'))
					location.href=url;
			}
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>角色管理</li>
			<li>角色列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" namespace="/system/role" method="post">
			<table class="profile">
				<tbody>
					<tr>
						<th><label for="name">名称</label></th>
						<td><s:textfield id="name" name="role.name" size="40" maxlength="100"/></td>
						<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="page" status="loop">
			<mys:column title="序号" width="50px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="编号" value="getId()" sortable="true" sortKey="id" width="160px"/>
			<mys:column title="名称" value="name" sortable="true" width="260px"/>
			<mys:column title="状态" value="status" sortable="true" width="60px"/>
			<mys:column title="创建日期" value="createdTime" sortable="true" width="160px"/>
			<mys:column title="描述" value="description"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="view"><a href="<s:url value="/security/role/view.do?role.id=%{id}"/>">查看角色</a></li>
						<li class="section"/>
						<s:if test="removed">
							<li class="disabled"><a href="#">编辑角色</a></li>
						</s:if>
						<s:else>
						<li class="edit"><a href="<s:url value="/security/role/edit.do?role.id=%{id}"/>">编辑角色</a></li>
						</s:else>
						
						<li class="section"/>
						<s:if test="removed">
							<li class="disabled"><a href="#">删除角色</a></li>
						</s:if>
						<s:else>
							<li class="remove">
								<a href="#" onclick="javascript:doRemove('<s:property value="%{id}"/>','<s:property value="%{name}"/>')">删除角色</a>
							</li>
						</s:else>					
					</ul>
				</div>
			</mys:column>
		</mys:table>
	
		<mys:pagination value="page" />
	</body>
</html>