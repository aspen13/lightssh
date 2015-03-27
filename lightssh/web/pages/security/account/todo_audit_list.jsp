<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>待审核列表</title>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>登录账号</li>
			<li>待审核列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="todoauditlist" namespace="/system/account" method="post">
			<table class="profile">
				<tbody>
					<tr>
						<th><label for="name">名称</label></th>
						<td><s:textfield id="name" name="accountChange.loginAccount.loginName" size="40" maxlength="100"/></td>
						<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="lacPage" status="loop">
			<mys:column title="序号" width="50px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="登录帐号名称" value="loginAccount.loginName" sortable="false" width="260px"/>
			<mys:column title="登录帐号状态" value="loginAccount.status" sortable="false" width="60px"/>
			<mys:column title="创建日期" value="createdTime" sortable="false" width="160px"/>
			<mys:column title="操作备注" value="description"/>
			<mys:column title="操作类型" value="type" sortable="false" width="60px"/>
			<mys:column title="操作人" value="operator.loginName" sortable="false" width="120px"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="audit"><a href="<s:url value="todoaudit.do?accountChange.id=%{id}&accountChange.loginAccount.id=%{loginAccount.id}"/>">审核登录帐号</a></li>
					</ul>
				</div>
			</mys:column>
		</mys:table>
	
		<mys:pagination value="lacPage" pageParamPrefix="lacPage" />
	</body>
</html>