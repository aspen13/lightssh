<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<title>工作流</title>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery-ui-timepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("input[type='text'][class='date']").datetimepicker(
				{dateFormat: 'yy-mm-dd',changeYear:true
					,showSecond: true,timeFormat:'hh:mm:ss'});
		});
	</script>
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>工作流</li>
		<li>提交流程</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	
	<s:set name="history_tfd" value="#request.history_task_form_data"/>
	<s:if test="#history_tfd != null">
		<s:iterator value="#history_tfd">
			<s:property value="propertyId"/>:
			<s:property value="propertyValue"/><br/>
		</s:iterator>
	</s:if>
	
	<s:set name="task" value="#request.task_form_data.task"/>
	<s:if test="#task != null ">
		当前任务：<s:property value="#task.name"/>
	</s:if>
	
	<s:form action="submit">
		<s:hidden name="taskId" value="%{#task.id}"/>
		<table class="profile">
		<s:iterator value="#request.task_form_data.formProperties">
			<tr>
				<th>
					<s:set name="form_element_id" value="'task'+#task.id+'_'+id"/>
					<label class="<s:property value="required?'required':''"/>" for="<s:property value="#form_element_id"/>">
						<s:property value="name"/>
					</label>
				</th>
				<td>
					<s:if test="type.name =='boolean'">
						<s:select name="%{id}" list="#{true:'是',false:'否'}" value="%{value}"/>
					</s:if>
					<s:elseif test="type.name =='enum'">
						<s:select name="%{id}" list="type.getInformation('values')" headerKey="" headerValue=""/>
					</s:elseif>
					<s:else>
						<input type="text" name="<s:property value="id"/>" id="<s:property value="#form_element_id"/>"
							value="<s:property value="value"/>" class="<s:property value="type.name"/>"/>
					</s:else>
				</td>
			</tr>
		</s:iterator>
		</table>
		
		<input type="submit" class="action save" value="提交流程"/>
	</s:form>
</body>