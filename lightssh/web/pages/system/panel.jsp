<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
    <link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/scripts/jquery/styles/theme.css" />
    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
    
    <script type="text/javascript">
	    $(document).ready(function(){
	    	var mainBody = $(top.frames['main_frame'].window.document.body);
	    	var homeUrl = '<s:url value="/welcome.do"/>';
	    	var mainUrl = '<s:url value="/main.do"/>';
	    	var homeIframe = $(mainBody).find("iframe.tab[id='"+homeUrl+"']");
	    	if( $(homeIframe).length == 0){ //不存在
	    		homeIframe = $("<iframe class='tab' id='"+homeUrl +"'"
						+" src='"+mainUrl+"' "
						+" width='100%' height='100%' "
						+" marginwidth='0' marginheight='0' frameborder='0' border='0' />");
				$(mainBody).append( homeIframe );
	    	}
	    })
    </script>
</head>

<body style="margin:0;width: 100%;height: 100%;">
	<%-- 
	<ul class="path">
		<li>系统首页</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	System Panel!
	<br/>
	Hello, <shiro:principal/>, how are you today?
	--%>
	
</body>