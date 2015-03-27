<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>

	<title>任务列表</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
		});
		
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>工作流</li>
		<li>部署列表</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<input type="button" class="action new" onclick="location.href='<s:url value="/workflow/deployment/deploy.do"/>'" value="部署流程"/>
	
	<mys:table cssClass="list" value="deployment_page" status="loop" pageParamPrefix="deployment_page">
		<mys:column title="序号" width="50px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<mys:column title="编号" value="getId()" sortKey="id" sortable="true" width="60px"/>
		<mys:column title="名称" value="name" sortable="true" width="220px"/>
		<mys:column title="创建时间" sortKey="deploymentTime" sortable="true" width="150px">
			<s:property  value="@com.google.code.lightssh.common.util.TextFormater@format(deploymentTime,'yyyy-MM-dd HH:hh:ss')" />
		</mys:column>
		<mys:column title="描述" value="description" />
		<%-- 
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li class="remove"><a href="undeploy.do?deploymentId=<s:property value="id"/>">删除部署</a></li>
				</ul>
			</div>
		</mys:column>
		--%>
	</mys:table>

	<mys:pagination value="deployment_page" pageParamPrefix="deployment_page"/>
	
</body>
