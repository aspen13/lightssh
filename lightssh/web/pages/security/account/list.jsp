<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>账号列表</title>
		
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_<s:property value="locale"/>.js"></script>
		<script type="text/javascript">
			function doRemove( id,name ){
				var url = '<s:url value="/security/account/remove.do?account.id="/>' + id ;
				if( confirm('确认删除用户账号[' + name + ']'))
					location.href=url;
			}
			
			
			$(document).ready(function(){
				$(".calendar").datepicker( );
			});
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>登录账号</li>
			<li>账号列表</li>
		</ul>
		
		<input type="button" class="action new" value="新增账号" onclick="location.href='<s:url value="edit.do"/>'"/>
		<input type="button" class="action pdf" value="PDF展示" onclick="location.href='<s:url value="report.do?type=PDF"/>'"/>
	
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" namespace="/system/account" method="post">
			<table class="profile">
				<colgroup>
					<col width="10%"/>
					<col width="20%"/>
					<col width="10%"/>
					<col width="20%"/>
					<col width="10%"/>
					<col width="20%"/>
					<col/>
				</colgroup>
				<tbody>
					<tr>
						<th><label for="name">登录账号</label></th>
						<td><s:textfield id="name" name="account.loginName" value="%{cachedParams['account.loginName']}" size="30" maxlength="100"/></td>
						<th><label for="createDate">创建日期</label></th>
						<td>
							<s:textfield name="account._createDatePeriod.start" cssClass="calendar" size="10" /> -
							<s:textfield name="account._createDatePeriod.end" cssClass="calendar" size="10"/>
						</td>
						<th><label for="status">状态</label></th>
						<td>
							<s:select  id="status" name="account.status" value="%{cachedParams['account.status']}"
								list="@com.google.code.lightssh.project.util.constant.AuditStatus@values()"
								listKey="name()" listValue="getValue()" headerKey="" headerValue=""/>
							<input type="submit" class="action search right" value="查询"/>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="page" status="loop" dynamic="false">
			<mys:column title="序号" value="#loop.index + 1" width="30px"/>
			<mys:column title="登录账号" value="loginName" sortable="true" width="150px"/>
			<mys:column title="人员信息" sortKey="partyId" sortable="true" width="150px"
				value="@com.google.code.lightssh.project.party.util.PartyHelper@getParty(partyId).name"/>
			<mys:column title="有效期(起)" value="period.start" sortable="true" width="90px" />
			<mys:column title="有效期(止)" value="period.end" sortable="true" width="90px"/>
			<mys:column title="状态" value="status" sortable="true" width="50px"/>
			<mys:column title="角色" width="200px">
				<s:iterator value="roles" status="loop">
					<font style="color:<s:property value="effective?'green':''"/>;">
						<s:property value="#loop.first?'':' , '"/><s:property value="%{name}"/>
					</font>
				</s:iterator>
			</mys:column>
			<mys:column title="描述" value="description"/>
			<mys:column title="创建日期" value="createDate" sortable="true" width="90px"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="view">
							<a href="<s:url value="view.do?account.id=%{id}"/>">帐号详情</a>
						</li>
						
						<li class="section"/>
						<li class="edit">
							<a href="<s:url value="/security/account/edit.do?account.id=%{id}"/>">编辑帐号</a>
						</li>
						
						<s:if test="loginName != @com.google.code.lightssh.project.security.service.LoginAccountManagerImpl@ROOT_LOGIN_NAME">
							<s:if test="removed">
								<li class="disabled">
									<a href="#">删除帐号</a>
								</li>
							</s:if>
							<s:else>
								<li class="remove">
									<a href="#" onclick="javascript:doRemove('<s:property value="%{id}"/>','<s:property value="%{loginName}"/>')">删除帐号</a>
								</li>
							</s:else>
						</s:if>
						
						<li class="section"></li>
						<li class="password">
							<a href="<s:url value="/security/account/prereset.do?account.loginName=%{loginName}"/>">重设密码</a>
						</li>
						
						<li class="disabled">
							<a href="#">禁用帐号</a>
						</li>
						
						<li class="unlock">
							<a href="<s:url value="releaselock.do?account.id=%{id}"/>">释放登录锁</a>
						</li>
						
					</ul>
				</div>
			</mys:column>
		</mys:table>
	
		<mys:pagination value="page" />
	
	</body>
</html>