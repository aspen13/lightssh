<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/util/taglibs.jsp"%>
<%@ page pageEncoding="utf-8" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <link rel="stylesheet" type="text/css" media="all" href="<s:url value="/"/>styles/<mys:theme />/theme.css" />
		<script language="javascript" src="<s:url value="/scripts/jquery/jquery.min.js"/>"></script>

        <decorator:head/>
        <title><decorator:title/></title>
    </head>
    
	<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
		<div id="container">			
				    			
	        <div id="main">      
	        	<div id="system">
		        	
		            <jsp:include page="/pages/common/util/messages.jsp"/>
		        	
		            <decorator:body/>
		       	</div>
			</div>
			
			<div id="footer">
				<jsp:include page="/pages/common/layout/footer.jsp"/>			
			</div>
		</div>
		
	</body>
</html>
