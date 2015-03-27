<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>角色审核列表</title>
		
		<script type="text/javascript">
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>角色管理</li>
			<li>角色审核列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" namespace="/security/role" method="post">
			<table class="profile">
				<tbody>
					<tr>
						<th><label for="name">名称</label></th>
						<td><s:textfield id="name" name="roleAudit.roleChange.role.name" size="40" maxlength="100"/></td>
						<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="raPage" status="loop" pageParamPrefix="raPage">
			<mys:column title="序号" width="50px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="角色名称" value="roleChange.role.name" sortable="true" width="220px"/>
			<mys:column title="操作类型" value="roleChange.type" width="60px"/>
			<mys:column title="审核人" value="user.loginName" sortable="true" width="140px"/>
			<mys:column title="审核结果" value="result" sortable="true" width="80px"/>
			<mys:column title="审核时间" value="createdTime" sortable="true" width="160px"/>
			<mys:column title="审核备注" value="description"/>
			<%-- 
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
					</ul>
				</div>
			</mys:column>
			--%>
		</mys:table>
	
		<mys:pagination value="raPage" pageParamPrefix="raPage"/>
	</body>
</html>