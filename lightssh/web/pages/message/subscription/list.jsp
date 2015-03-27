<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		
		<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
		
		<title>消息订阅</title>
		
		<script type="text/javascript">
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>消息管理</li>
			<li>消息订阅</li>
		</ul>
		
		<input type="button" class="action new" value="消息订阅" onclick="location.href='<s:url value="edit.do"/>'"/>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<mys:table cssClass="list" value="page" dynamic="false" status="loop">
			<mys:column title="序号" width="40px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="类型编号" value="catalog.id" width="80px"/>
			<mys:column title="消息类型" value="catalog.type" width="40px"/>
			<mys:column title="订阅类型" value="recType" sortable="true" width="60px"/>
			<mys:column title="订阅值" sortable="false" width="160px">
				<s:if test="@com.google.code.lightssh.project.message.entity.ReceiveType@USER == recType ">
					<s:property value="recValue"/>
				</s:if>
				<s:elseif test="(@com.google.code.lightssh.project.message.entity.ReceiveType@DEPARTMENT == recType)
					 || (@com.google.code.lightssh.project.message.entity.ReceiveType@PERSON == recType)">
					<s:property value="@com.google.code.lightssh.project.party.util.PartyHelper@getParty(recValue).name"/>
				</s:elseif>
			</mys:column>
			<mys:column title="有效期(起)" value="period.start" width="90px"/>
			<mys:column title="有效期(止)" value="period.end" width="90px"/>
			<mys:column title="创建人" value="creator" width="80px"/>
			<mys:column title="创建时间" value="createdTime" width="150px"/>
			<mys:column title="描述" value="description" />
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="section"/>
						
						<li class="edit">
							<a href="<s:url value="edit.do?subscription.id=%{id}"/>">编辑信息</a>
						</li>
					</ul>
				</div>
			</mys:column>
		</mys:table>
		
		<mys:pagination value="page"/>
	</body>
</html>