<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>

	<title>任务列表</title>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<s:url value="/pages/workflow/process/popup_proc_def.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".calendar").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
		});
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param ){
			$("input[name='task.procInstStartUser']").val( param.text);
			
			$( popup_login_account ).dialog('destroy').html('');
		}
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>消息中心</li>
		<li>待办事宜</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form name="mytodolist" method="post">
		<input type="submit" class="action search" value="查询"/>
		
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
					<th><label for="proc_inst_id">流程编号</label></th>
					<td><s:textfield id="proc_inst_id" name="task.processInstanceId" size="20" maxlength="100"/></td>
					
					<th><label for="proc_attr_name">流程名称</label></th>
					<td><s:textfield id="proc_attr_name" name="task.processAttributeName" size="30" maxlength="100"/></td>
					
					<th><label for="proc_def_key">流程类型</label></th>
					<td>
						<span class="popup" onclick="popupProcDef('<s:url value="/workflow/process/popupdef.do"/>');">&nbsp;</span>
						<s:hidden id="proc_def_key" name="task.processDefinitionKey"/>
						<s:hidden id="proc_def_name" name="task.processDefinitionName"/>
						<span id ="span_procdef_name"><s:property value="task.processDefinitionName"/></span>
					</td>
				</tr>
				
				<tr>
					<th><label for="startTime">流程开始时间</label></th>
					<td>
						<s:textfield name="task.procStartPeriod.start" id="startTime" cssClass="calendar" size="10" /> -
						<s:textfield name="task.procStartPeriod.end" cssClass="calendar"  size="10"/>
					</td>
					
					<th><label for="arrviedTime">任务到达时间</label></th>
					<td>
						<s:textfield name="task.startPeriod.start" id="arrviedTime" cssClass="calendar" size="10" /> -
						<s:textfield name="task.startPeriod.end" cssClass="calendar" size="10"/>
					</td>
					
					<th><label for="owner">流程创建者</label></th>
					<td>
						<s:textfield name="task.procInstStartUser" id="owner" size="20" />
						<span class="popup user" onclick="popupLoginAccount('<s:url value="/security/account/popup.do"/>',{});">&nbsp;</span>
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
	<mys:table cssClass="list" value="taskPage" status="loop" pageParamPrefix="task_page">
		<mys:column title="序号" width="30px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<mys:column title="流程编号" value="processInstanceId" width="80px"/>
		<mys:column title="流程名称">
			<s:set name="procAttr" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcAttr(processInstanceId)"/>
			<s:property value="#procAttr.bizName"/>
		</mys:column>
		<mys:column title="流程节点" value="name" sortable="false" width="160px"/>
		<mys:column title="流程类型" width="100px">
			<s:set name="procInst" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcessInstance(processInstanceId)"/>
			<s:set name="procDef" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcessDefinition(processDefinitionId)"/>
			<s:property value="#procDef.name"/>
		</mys:column>
		<mys:column title="流程创建者" value="#procInst.startUserId" sortable="false" width="80px"/>
		<mys:column title="流程开始时间" value="@com.google.code.lightssh.common.util.TextFormater@format(#procInst.startTime,'yyyy-MM-dd HH:hh:ss')" 
			sortable="false" width="150px"/>
		<mys:column title="任务到达时间" value="@com.google.code.lightssh.common.util.TextFormater@format(createTime,'yyyy-MM-dd HH:hh:ss')" 
			sortKey="createTime" sortable="false" width="150px"/>
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li><a href="prepare.do?taskId=<s:property value="id"/>">处理流程</a></li>
					<li class="section"/>
					<li class="disabled"><a href="#" onclick="popup('<s:property value="id"/>');">签转代办</a></li>
				</ul>
			</div>
		</mys:column>
	</mys:table>

	<mys:pagination value="taskPage"  pageParamPrefix="taskPage"/>
</body>
