<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>角色审核</title>
		
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">
		
		<script type="text/javascript">
			
			$(document).ready(function(){
				//$("#mytree").treeview();
				$("#navigation_tree").treeview({
					persist: "cookie",
					collapsed: false,
					cookieId: "treeview-navigation-permission"
				});

				/**
				 * 数据校验
				 */
				$("#addForm").validate({
					rules:{
						"roleAudit.description":{required:true,maxlength:200}
					}
					,submitHandler: function(form) {
						if( confirm('确认进行审核操作?') )
							form.submit();
					}
				});
			});
			
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>角色管理</li>
			<li>审核角色</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<%@ include file="/pages/security/role/compare.jsp" %>
		
		<table class="profile">
			<caption>操作信息</caption>
			<tbody>
				<tr>
					<th><label for="desc">操作人</label></th>
					<td><s:property value="roleChange.operator.loginName"/></td>
				</tr>
				<tr>
					<th><label>操作类型</label></th>
					<td><s:property value="roleChange.type"/></td>
				</tr>
				<tr>
					<th><label>操作时间</label></th>
					<td><s:property value="roleChange.createdTime"/></td>
				</tr>
				<tr>
					<th><label>操作备注</label></th>
					<td><s:property value="roleChange.description"/></td>
				</tr>
			</tbody>
		</table>
		
		<s:form id="addForm" action="audit" namespace="/security/role" method="post">
			<table class="profile">
				<caption>审核信息</caption>
				<tbody>
					<tr>
						<th><label class="required" for="desc">审核备注</label></th>
						<td><s:textarea id="desc" name="roleAudit.description" cols="60" rows="5"/></td>
					</tr>
				</tbody>
			</table>
			
			<p class="submit">
				<s:hidden name="roleChange.id"/>
				<s:hidden name="roleChange.role.id"/>
				<input type="submit" class="action audit" name="passed" value="审核通过"/>
				<input type="submit" class="action audit reject" name="reject" value="审核拒绝"/>
			</p>
		</s:form>
	</body>
</html>