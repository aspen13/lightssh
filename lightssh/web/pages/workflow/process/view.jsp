<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>
	
	<title>流程情况</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$( "#tabs" ).tabs({
				cache:false
			});
			
		});
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>工作流管理</li>
		<li>流程管理</li>
		<li>流程详情</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<%-- 
	<s:action name="viewproc" namespace="/workflow/process" executeResult="true">
		<s:param name="process.processInstanceId" value="#request.procInstId"></s:param>
	</s:action>
	--%>
	
	<div id="tabs"> 
		<ul> 	
			<s:if test="#request.task != null">
			<li><a href="#tabs-task-submit">处理流程</a></li>
			</s:if>
			<s:else>
			<li><a href="<s:url value="/workflow/process/viewproc.do?process.processInstanceId=%{#request.procInstId}"/>">流程信息</a></li>
			</s:else>
			
			<li><a href="<s:url value="/workflow/process/viewbizdata.do?process.processInstanceId=%{#request.procInstId}"/>">业务数据</a></li>
			<li><a href="<s:url value="/workflow/process/viewtasklog.do?process.processInstanceId=%{#request.procInstId}"/>">操作日志</a></li>
			<li><a href="<s:url value="/workflow/process/viewimage.do?process.processInstanceId=%{#request.procInstId}"/>">流程图</a></li>
		</ul>
		
		<s:if test="#request.task != null">
		<div id="tabs-task-submit">
			<%@ include file="/pages/workflow/task/profile.jsp" %>
		</div>
		</s:if>
	</div>
	
</body>
