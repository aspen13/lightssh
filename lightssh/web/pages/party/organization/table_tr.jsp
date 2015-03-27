<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<tr id="<s:property value="id"/>" class="<s:property value="#loop.odd?\"odd\":\"even\""/> <s:property value="#parent==null?\"\":(\"child-of-\"+#parent)"/>">
	<td>&nbsp;</td>
	<td><s:property value="%{id}"/></td>
	<td><a href="<s:url value="/party/organization/edit.do?party.id=%{id}"/>"><s:property value="%{name}"/></a></td>
	<td><s:property value="%{parent.name}"/></td>
	<td><s:property value="%{enabled?'是':'否'}"/></td>
	<td><s:property value="%{description}"/></td>
	<td>
		<a href="<s:url value="/party/organization/edit.do?party.id=%{id}"/>">编辑</a>
		<a href="#" onclick="javascript:doRemove('<s:property value="%{id}"/>','<s:property value="%{name}"/>')">删除</a>
	</td>
</tr>