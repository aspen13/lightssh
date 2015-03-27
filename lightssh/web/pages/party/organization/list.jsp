<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>用户列表</title>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treetable/jquery.treetable.css" />
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treetable/jquery.treetable.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/livefilter/jquery.livefilter.js"></script>
		
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">
		
		<script type="text/javascript">
			function doRemove( id,name ){
				var url = '<s:url value="/party/organization/remove.do?party.id="/>' + id ;
				if( confirm('确认删除组织机构[' + name + ']'))
					location.href=url;
			}
			
			/**
			 * init treeTable
			 */
			function initTreeTable( ){
				$("#orgnization_list").treeTable(
				  	 {expandable:true,initialState:"expanded"}
				);
			}
			
			function select( span ){
				//var id = $( span ).attr("id");
				//var name = $( span ).attr("name");
			}
			
			$(document).ready(function(){
				$( "#tabs" ).tabs();
				initTreeTable();
				$('#live_filter').liveFilter('table');
				
				$("#organization_tree").treeview({
					persist: "false",
					animated: "fast",
					collapsed: true,
					cookieId: "treeview-organization"
				});
			});
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>组织机构</li>
			<li>组织列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<div id="tabs"> 
			<ul> 	
				<li><a href="#tabs-1">列表</a></li> 	
				<li><a href="#tabs-2">关系树</a></li> 	
			</ul> 
		
			<div id="tabs-1">
				<table class="profile">
					<tr>
						<th><label for="name">名称/描述</label></th>
						<td>
							<input id="name" class="filter" name="livefilter" 
								type="text" value="" size="40" onchange="if(this.value=='')initTreeTable();"/>
						</td>
					</tr>
				</table>
				
				<div id="live_filter">
					<table id="orgnization_list" class="list">
						<colgroup>
							<col class="element" width="50px"/>
							<col class="element" width="100px"/>
							<col class="element" width="200px"/>
							<col class="element" width="200px"/>
							<col class="element" width="50px"/>
							<col class="element" />
							<col class="element" width="100px"/>
						</colgroup>
						<thead>
							<tr>
								<th>&nbsp;</th>
								<th>编号</th>
								<th>名称</th>
								<th>上级组织</th>
								<th>有效</th>
								<th>描述</th>
								<th>操作</th>
							</tr>
						</thead>
						
						<s:if test="party != null">
						<s:iterator value="new java.lang.Object[]{party}" status="loop1">
							<s:set name="parent" value="null"/>
							<s:set name="loop" value="#loop1"/>
							<%@include file="table_tr.jsp" %>
						
							<s:iterator value="%{sortedChildren}" status="loop2">
								<s:set name="parent" value="parent.id"/>
								<s:set name="loop" value="#loop2"/>
								<%@include file="table_tr.jsp" %>
								
								<s:iterator value="%{sortedChildren}" status="loop3">
									<s:set name="parent" value="parent.id"/>
									<s:set name="loop" value="#loop3"/>
									<%@include file="table_tr.jsp" %>
									
									<s:iterator value="%{sortedChildren}" status="loop4">
										<s:set name="parent" value="parent.id"/>
										<s:set name="loop" value="#loop4"/>
										<%@include file="table_tr.jsp" %>
										
										<s:iterator value="%{sortedChildren}" status="loop5">
											<s:set name="parent" value="parent.id"/>
											<s:set name="loop" value="#loop5"/>
											<%@include file="table_tr.jsp" %>
										</s:iterator>
									</s:iterator>
								</s:iterator>
							</s:iterator>
						</s:iterator>
						
						</s:if>
					</table>
				</div>
			</div>
			
			<div id="tabs-2" style="padding:5px;">
				<%@ include file="tree.jsp" %>
			</div>
		</div>
		
		
	</body>
</html>