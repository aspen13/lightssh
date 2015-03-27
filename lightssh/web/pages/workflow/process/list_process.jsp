<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>

	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<s:url value="/pages/workflow/process/popup_proc_def.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	<title>流程列表</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".calendar").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
		});
		
		/**
		 * 结束流程
		 */
		function terminate(procInstId,name){
			var url = '<s:url value="terminate.do"/>?process.processInstanceId=' + procInstId ;
			if( confirm('确认结束流程[' + name + ']'))
				location.href=url;
		}
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param ){
			$("input[name='process.owner']").val( param.text);
			
			$( popup_login_account ).dialog('destroy').html('');
		}
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>工作流</li>
		<li>流程查询</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
		
	<s:form name="list" method="post">
		<table class="profile">
			<colgroup>
				<col width="10%"/>
				<col width="23%"/>
				<col width="10%"/>
				<col width="23%"/>
				<col width="10%"/>
				<col />
			</colgroup>
			<tbody>
				<tr>
					<th><label for="proid">流程编号</label></th>
					<td><s:textfield id="proid" name="process.processInstanceId" size="20" maxlength="100"/></td>
					
					<th><label for="defid">流程名称</label></th>
					<td><s:textfield id="defid" name="process.processAttributeName" size="30" maxlength="100"/></td>
					
					<th><label for="proc_def_key">流程类型</label></th>
					<td>
						<span class="popup" onclick="popupProcDef('<s:url value="/workflow/process/popupdef.do"/>');">&nbsp;</span>
						<s:hidden id="proc_def_key" name="process.processDefinitionKey"/>
						<s:hidden id="proc_def_name" name="process.processDefinitionName"/>
						<span id ="span_procdef_name"><s:property value="process.processDefinitionName"/></span>
					</td>
				</tr>
				<tr>
					<th><label for="access_start_date">开始时间</label></th>
					<td>
						<s:textfield name="process.startPeriod.start" cssClass="calendar" id="access_start_date" size="10" /> -
						<s:textfield name="process.startPeriod.end" cssClass="calendar" id="access_end_date" size="10"/>
					</td>
					
					<th><label for="owner">创建者</label></th>
					<td>
						<s:textfield name="process.owner" id="owner" size="20" maxlength="100"/>
						<span class="popup user" onclick="popupLoginAccount('<s:url value="/security/account/popup.do"/>',{});">&nbsp;</span>
					</td>
					
					<th><label for="status">流程状态</label></th>
					<td>
						<s:select name="process.finish" list="#{false:'活动',true:'结束' }" headerKey="" headerValue="" />
						
						<input type="submit" class="action search right" value="查询"/>
					</td>
					
				</tr>
			</tbody>
		</table>
	</s:form>
	
	<mys:table cssClass="list" value="hp_page" status="loop" pageParamPrefix="hp_page">
		<mys:column title="序号" width="30px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<%-- 
		<mys:column title="ID" value="getId()" width="80px"/>
		<mys:column title="流程ID" value="processDefinitionId" sortable="true" width="160px"/>
		<mys:column title="业务关键字" value="businessKey" sortable="true"/>
		<mys:column title="结束任务ID" value="endActivityId" width="100px"/>
		<mys:column title="结束时间" sortable="true" sortKey="endTime" width="150px">
			<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(endTime,'yyyy-MM-dd HH:mm:ss')"/>
		</mys:column>
		<mys:column title="执行时间" width="50px" sortable="true" sortKey="duration">
			<s:property value="@com.google.code.lightssh.common.util.TextFormater@timeFormat(durationInMillis)"/>
		</mys:column>
		--%>
		<mys:column title="流程编号" value="processInstanceId" sortable="true" width="80px"/>
		<mys:column title="流程名称">
			<s:set name="procAttr" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcAttr(processInstanceId)"/>
			<s:property value="#procAttr.bizName"/>
		</mys:column>
		<mys:column title="流程节点">
			<s:if test="endTime==null">
				<s:set name="task" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@listTaskByProcessId(getId())"/>
				<s:iterator value="#task" status="loop">
					<s:property value="#loop.first?'':','"/><s:property value="name"/>
				</s:iterator>
			</s:if>
			<s:else>
				流程已结束
			</s:else>
		</mys:column>
		<mys:column title="状态" sortable="false" width="50px">
			<font color="<s:property value="endTime==null?'green':'black'"/>"><s:property value="endTime==null?'活动':'结束'"/></font>
		</mys:column>
		<mys:column title="流程类型" width="180px">
			<s:set name="proDef" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcessDefinition(processDefinitionId)"/>
			<s:property value="#proDef.name"/>
		</mys:column>
		<mys:column title="创建者" value="startUserId" sortable="false" width="100px"/>
		<mys:column title="开始时间" sortable="true" sortKey="startTime" width="150px">
			<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(startTime,'yyyy-MM-dd HH:mm:ss')"/>
		</mys:column>
		
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li class="view">
						<a href="<s:url value="view.do?process.processInstanceId=%{processInstanceId}"/>">流程详情</a>
					</li>
					
					
					<s:if test="endTime==null">
						<li class="section"/>
						<li class="remove">
							<a href="#" onclick="terminate('<s:property value="%{processInstanceId}"/>','<s:property value="#procAttr.bizName"/>')">结束流程</a>
						</li>
					</s:if>
				</ul>
			</div>
		</mys:column>
	</mys:table>

	<mys:pagination value="hp_page" pageParamPrefix="hp_page"/>
</body>
