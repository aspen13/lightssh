<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		
		<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
		
		<title>消息类型</title>
		
		<script type="text/javascript">
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>消息管理</li>
			<li>消息类型</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<mys:table cssClass="list" value="page" dynamic="false" status="loop">
			<mys:column title="序号" width="40px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="编号" value="identity" sortKey="id" sortable="false" width="80px"/>
			<mys:column title="上级" value="parent.id" width="80px"/>
			<mys:column title="类型" value="type" sortable="true" width="60px"/>
			<mys:column title="可订阅" sortable="true" sortKey="subscribe" width="60px">
				<s:property value="subscribe?'是':'否'"/>
			</mys:column>
			<mys:column title="可转发" sortable="true" sortKey="forward" width="60px">
				<s:property value="forward?'是':'否'"/>
			</mys:column>
			<mys:column title="只读" sortable="true" sortKey="readOnly" width="60px">
				<s:property value="readOnly?'是':'否'"/>
			</mys:column>
			<mys:column title="创建时间" value="createdTime" width="150px"/>
			<mys:column title="描述" value="description" />
			<%-- 
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="section"/>
						
						<li class="edit">
							<a href="#">编辑信息</a>
						</li>
					</ul>
				</div>
			</mys:column>
			--%>
		</mys:table>
		
		<mys:pagination value="page"/>
	</body>
</html>