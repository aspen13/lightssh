<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

	
<s:set name="proc" value="#request.processHistory"/>

<p>
	<s:if test="#proc.endTime!=null">
		<img src="<s:url value="/workflow/process/procdefimage.do?process.processDefinitionId=%{#proc.processDefinitionId}"/>" alt="流程图"/>
	</s:if>
	<s:else>
		<img src="<s:url value="/workflow/process/procactiveimage.do?process.processInstanceId=%{#proc.processInstanceId}"/>" alt="流程图(活动)"/>
	</s:else>
</p>
