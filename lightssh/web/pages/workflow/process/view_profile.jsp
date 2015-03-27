<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

	
	<s:set name="proc" value="#request.process"/>
	<table class="profile">
		<caption>流程信息</caption>
		<colgroup>
			<col width="100px"/>
			<col width="30%"/>
			<col width="100px"/>
			<col />
		</colgroup>
		<tbody>
			<tr>
				<th><label>流程名称</label></th>
				<td colspan="3">
					<s:set name="procAttr" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcAttr(#proc.processInstanceId)"/>
					<s:property value="#procAttr.bizName"/>
				</td>
			</tr>
			
			<tr>
				<th><label>流程编号</label></th>
				<td>
					<s:property value="#proc.processInstanceId"/>
				</td>
				<th><label>流程类型</label></th>
				<td>
					<s:set name="proDef" value="@com.google.code.lightssh.project.workflow.util.WorkflowHelper@getProcessDefinition(#proc.processDefinitionId)"/>
					<s:property value="#proDef.name"/>
				</td>
			</tr>
			<tr>
				<th><label >创建者</label></th>
				<td>
					<s:property value="#proc.startUserId"/>
				</td>
				<th><label >创建时间</label></th>
				<td>
					<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(#proc.startTime,'yyyy-MM-dd HH:mm:ss')"/>
				</td>
			</tr>
		</tbody>
	</table>

