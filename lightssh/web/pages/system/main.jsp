<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>
    <script type="text/javascript">
	    $(document).ready(function(){
	    })
    </script>
</head>

<body style="margin:0;width: 100%;height: 100%;">
	<ul class="path">
		<li>系统首页</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	System Panel!
	<br/>
	Hello, <shiro:principal/>, how are you today?
	
</body>