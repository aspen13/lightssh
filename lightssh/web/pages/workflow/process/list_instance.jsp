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
		<li>流程实例</li>
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
	
	<mys:table cssClass="list" value="pi_page" status="loop" pageParamPrefix="pi_page">
		<mys:column title="序号" width="50px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<mys:column title="流程定义ID" value="processDefinitionId" sortable="true" width="100px"/>
		<mys:column title="流程实例ID" value="processInstanceId" sortable="true" width="100px"/>
		<mys:column title="当前任务节点" value="activityId" sortable="true" width="200px"/>
		<mys:column title="活动" sortable="false" width="50px">
			<s:property value="active?'是':'否'"/>
		</mys:column>
		<mys:column title="描述" />
		<%-- 
		<mys:column title="文件名" value="resourceName" sortable="false" width="80px"/>
		<mys:column title="DeploymentId" value="deploymentId" sortable="false" width="80px"/>
		--%>
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li class="disabled">
						<a href="#">详情</a>
					</li>
					
					<li class="section"/>
					
					<li class="disabled">
						<a href="#">流程图</a>
					</li>
				</ul>
			</div>
		</mys:column>
	</mys:table>

	<mys:pagination value="pi_page"  pageParamPrefix="pi_page"/>
</body>
