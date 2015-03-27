<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>


		
<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">

<script type="text/javascript">
	$(document).ready(function(){
		//$("#mytree").treeview();
		$("#navigation_tree").treeview({
			persist: "cookie",
			collapsed: false,
			cookieId: "treeview-navigation-view"
		});
	});
</script>

<%@ include file="/pages/security/role/compare.jsp" %>
		
