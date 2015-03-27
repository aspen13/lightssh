<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>
	
	<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">
	
	<title>树列表</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
		});
		
		/**
		 * 显示树结点
		 */
		function viewnode( id ){
			var popup = $("#popup");
			$( popup ).html( '<div><img id=\'loading\' src=\'<%= request.getContextPath() %>/images/loading.gif\'/>' );
			$( popup ).dialog({
				resizable: true,modal: true,height:400,width: 700,
				close: function(event, ui) { $(this).dialog('destroy'); },				
				buttons: {				
					"关闭": function() {$(this).dialog('destroy');}
				}
			});
			
			var req_url = '<s:url value="/settings/tree/popup.do"/>';
			$.post(req_url,{"view":"true","level":4,"tree.id":id},function(data){$( popup ).html( data );});
		}
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>分类树</li>
		<li>搜索列表</li>
	</ul>
	
	<input type="button" class="action new" value="新增分类树" onclick="location.href='<s:url value="edit.do"/>'"/>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form name="list" namespace="/settings/tree" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name">名称</label></th>
					<td>
						<%-- 
						<s:select id="type" list="@com.google.code.lightssh.project.uom.entity.UnitOfMeasure$UomType@values()"
						 listKey="name()" headerKey="" headerValue="" value="uom.type.name()" name="uom.type"/>
						--%>
						<s:textfield name="tree.name" id="name"/>
					</td>
					
					<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
	<mys:table cssClass="list" value="page" status="loop">
		<mys:column title="序号" width="50px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<mys:column title="名称" value="name" sortable="true" width="300px"/>
		<mys:column title="创建时间" value="createdTime" sortable="true" width="160px"/>
		<mys:column title="描述" value="description" />
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li>
						<a href="#" onclick="viewnode('<s:property value="%{id}"/>')">查看结点</a>
					</li>
					<li class="section"/>
					<li>
						<a href="<s:url value="editnode.do?tree.id=%{id}"/>">添加结点</a>
					</li>
					<li>
						<a href="<s:url value="edit.do?tree.id=%{id}"/>">编辑树</a>
					</li>
				</ul>
			</div>
		</mys:column>
	</mys:table>
	
	<mys:pagination value="page" />

	<div id="popup" title="树结点" style="display: none;">&nbsp;</div>
</body>
