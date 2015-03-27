<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<head>
		<meta name="decorator" content="background"/>
		
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">
		
		<title>编辑分类树</title>
		
		<script type="text/javascript">
			/**
			 * 显示树结点
			 */
			function popup( id ){
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
				$.post(req_url,{"view":"false","level":4,"tree.id":id},function(data){$( popup ).html( data );});
			}
			
			function callbackSelectTreeNode( node ){
				$("#span_parent_name").html( node.name );
				$("#parent_name").val( node.name );
				$("#parent_id").val( node.id );
				$("#popup").dialog('destroy');
			}
		
			$(document).ready(function(){
				//$("#mytree").treeview();
				
				/**
				 * 数据校验
				 */
				$("#profile_form").validate({
					rules:{
						"node.name":{required:true,maxlength:100}
						,"node.description":{maxlength:200}
					}
				});
				
			});
		</script>
	</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>分类树</li>
		<li>编辑树结点</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form id="profile_form" action="savenode" namespace="/settings/tree" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name" class="required">名称</label></th>
					<td>
						<s:set name="isInsert" value="%{(node==null||node.id==null)}"/>
						<s:hidden name="tree.id"/>
						<s:hidden name="node.id"/>
						
						<s:textfield id="name" name="node.name" size="40" maxlength="100"/>
					</td>
				</tr>
				
				<tr>
					<th><label for="parent">父结点</label></th>
					<td>
						<span class="popup" onclick="popup('<s:property value="%{tree.id}"/>');">&nbsp;</span>
						<s:hidden name="node.parent.id" id="parent_id"/>
						<s:hidden name="node.parent.name" id="parent_name"/>
						<span id="span_parent_name"><s:property value="node.parent.name"/></span>
					</td>
				</tr>
				
				<tr>
					<th><label for="desc">描述</label></th>
					<td><s:textarea id="desc" name="node.description" cols="60" rows="5"/></td>
				</tr>
			</tbody>
		</table>
	
		<s:token/>
	
		<p class="submit">
			<input type="submit" class="action save" name="Submit" 
				value="<s:property value="%{#isInsert?\"新增树结点\":\"修改树结点\"}"/>"/>
				
			<input type="submit" class="action save" name="saveAndNext" value="保存&新增下一个"/>
		</p>
	</s:form>

	<div id="popup" title="树结点" style="display: none;">&nbsp;</div>
</body>