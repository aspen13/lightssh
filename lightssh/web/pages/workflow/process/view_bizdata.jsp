<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

	<%@ include file="/pages/common/util/messages.jsp" %>

	<s:set name="bizView" value="%{#request.bizView}"/>
	<s:property value="%{#bizView.namespace}"/>|NP|
	<s:if test="#bizView == null">
		未设置业务数据展示参数！
	</s:if>
	<s:else>
		<%-- 
		<s:action name="%{#bizView.action}" namespace="%{#bizView.namespace}" executeResult="true">
			<s:param name="%{#bizView.paramName}" value="%{#bizView.actProcDefKey}"/>
		</s:action>
		--%>
	</s:else>
	
