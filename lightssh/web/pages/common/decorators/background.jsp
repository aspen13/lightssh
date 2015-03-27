<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/util/taglibs.jsp"%>
<%@ page pageEncoding="utf-8" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
        <link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/scripts/jquery/styles/theme.css" />
        
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/jquery-ui.custom.min.js"></script>
		
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/validate/jquery.validate.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/validate/i18n/messages_<s:property value="locale"/>.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/cryptography/jquery.md5.js"></script>
		
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/my/util.js"></script>
		<script type="text/javascript">
			var path = '<%=request.getContextPath() %>';
			$(document).ready(function(){
				//hide #back-top 
				//$("#back-top").hide();
				$.lightssh.scrollTop( );
				
				$(document).bind('click',function(event){
					$.lightssh.popupMenu( event );
				});
			});
		</script>
		
        <decorator:head/>
        <meta name="uri" content="<s:url />"/>
        <title><decorator:title/></title>
    </head>
    
	<body <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
		<decorator:body/>
		
		<div id="background"></div>
		<p id="back-top" style="display: none;">
			<a href="#top"><span></span>TOP</a>
		</p>
	</body>
</html>
