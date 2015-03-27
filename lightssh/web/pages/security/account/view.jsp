<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<title>我的账号</title>
</head>
	
<body>

	<ul class="path">
		<li>系统管理</li>
		<li>登录账号</li>
		<li>我的账号</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<%-- 
	<input type="button" class="action back" value="返回" onclick="location.href='<s:url value="list.do"/>'"/>
	--%>
	
	<table class="profile">
		<caption>登录帐户信息</caption>
		<tbody>
			<tr>
				<th><label for="name" >登录账号</label></th>
				<td>
					<s:property value="account.loginName"/>
				</td>
			</tr>
			<tr>
				<th><label for="account_party">人员</label></th>
				<td>
					<s:property value="@com.google.code.lightssh.project.party.util.PartyHelper@getParty(account.partyId).name"/>
				</td>
			</tr>
			<tr>
				<th><label>状态</label></th>
				<td>
					<s:property value="account.status"/>
				</td>
			</tr>
			<tr>
				<th><label for="account_start_date">有效期</label></th>
				<td>
					<s:if test="account.period == null ">
						永不过期
					</s:if>
					<s:else>
						<s:property value="account.period.start"/> -
						<s:property value="account.period.end"/>
					</s:else>
				</td>
			</tr>
			<tr>
				<th><label>拥有的角色</label></th>
				<td>
					<s:iterator value="%{account.roles}" status="loop">
						<a href="<s:url value="/security/role/view.do?role.id=%{id}"/>"><s:property value="name"/></a><br/>
					</s:iterator>
				</td>
			</tr>
			
			<tr>
				<th><label >邮箱地址</label></th>
				<td>
					<s:property value="account.email"/>
				</td>
			</tr>
			
			<tr>
				<th><label>上次更新密码时间</label></th>
				<td>
					<s:property value="account.lastUpdatePasswordTime"/>
				</td>
			</tr>
			
			<tr>
				<th><label>上次登录锁定时间</label></th>
				<td>
					<s:property value="account.lastLoginLockTime"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="desc">创建时间</label></th>
				<td>
					<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(account.createDate,'yyyy-MM-dd HH:mm:ss')"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="desc">描述</label></th>
				<td>
					<s:property value="account.description"/>
					<br/>
				</td>
			</tr>
			
		</tbody>
	</table>
</body>