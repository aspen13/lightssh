<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>帐户帮助</title>
	
	<script type="text/javascript">
	</script>
</head>	

<body class="public">
	<h1>您试图使用的网址不正确或已失效。</h1>
	<p>
		<div class="secondary">
			如果您要重试，请从头开始执行<a href="<s:url value="/security/recovery/account.do"/>">恢复过程</a>。
			<br/>
		</div>
	</p>
	
</body>