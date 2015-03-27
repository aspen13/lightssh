<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<title>部署工作流</title>
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>工作流</li>
		<li>部署工作流</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form action="deploy" method="post" enctype="multipart/form-data">
	<table class="profile">
		<tr>
			<th><label for="deployment_file_name"  class="required">名称</label></th>
			<td><s:textfield name="deployment_file_name" id="deployment_file_name" size="40" /></td>
		</tr>
		<tr>
			<th><label class="required">文件</label></th>
			<td><s:file name="upload" size="40"/></td>
		</tr>
	</table>
	
	<p class="submit">
		<input type="submit" class="action save" value="保存" />
	</p>
	</s:form>
</body>