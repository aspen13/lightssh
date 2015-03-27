<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>

	<title>流程列表</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
		});
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>工作流</li>
		<li>流程类型列表</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<%-- 
	<s:form name="list" namespace="/workflow" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="type">类型</label></th>
					<td>
						<s:select id="type" list="@com.google.code.lightssh.project.uom.entity.UnitOfMeasure$UomType@values()"
						 listKey="name()" headerKey="" headerValue="" value="uom.type.name()" name="uom.type"/>
					</td>
					
					<th><label for="isocode">ISO编码</label></th>
					<td>
						<s:textfield name="uom.isoCode" id="isocode"/>
					</td>
					
					<th><label for="active">状态</label></th>
					<td>
						<s:select headerKey="" headerValue="" list="#{true:'活动的',false:'已冻结'}" 
							name="uom.active" value="uom.getActive()" id="active"/>
					</td>
					
					<td colspan="2"><input type="submit" class="action search" value="查询"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	--%>
	
	<mys:table cssClass="list" value="pd_page" status="loop" pageParamPrefix="pd_page">
		<mys:column title="序号" width="50px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<mys:column title="ID" value="getId()" sortable="false" width="200px"/>
		<mys:column title="编号" value="key" sortable="true" width="200px"/>
		<mys:column title="版本" value="version" sortable="true" width="60px"/>
		<mys:column title="部署编号" value="deploymentId" sortable="true" width="80px"/>
		<mys:column title="名称" value="name" sortable="true"/>
		<%-- 
		<mys:column title="文件名" value="resourceName" sortable="false" width="80px"/>
		--%>
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li><a href="start.do?processDefinitionKey=<s:property value="%{key}"/>">启动流程</a></li>
					
					<li class="section"/>
					
					<li class="disabled">
						<a href="#">详情</a>
					</li>
					<li>
						<a href="<s:url value="procdefimage.do?process.processDefinitionId=%{getId()}"/>">流程图</a>
					</li>
					
					<li class="section"/>
					
					<li class="disabled">
						<a href="#">历史版本</a>
					</li>
				</ul>
			</div>
		</mys:column>
	</mys:table>

	<mys:pagination value="pd_page" pageParamPrefix="pd_page"/>
</body>
