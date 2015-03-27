<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title><s:text name="project.name" /></title>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
		<script type="text/javascript">
			$(document).ready(function(){
				if( top != self ){//防止多嵌套
					top.location.href=location.href;
				}
				$("#js_warning").remove();
				$("#password").attr("value",'');
				paint();
			});
		</script>
	</head>
	
	<frameset rows="62px,*,20px" frameborder="0" framespacing="0" border="0" >
		<frame id="head_frame" scrolling="no" src="<s:url value="/header.do"/>"></frame>
		<frameset id="main_frameset" frameborder="0" framespacing="0" border="0" cols="200px,10px,*" width="100%">
			<frame id="navigation_frame" name="navigation_frame" scrolling="no" src="<s:url value="/navigation.do"/>"></frame>
			<frame id="switch_frame" name="switch_frame" scrolling="no" noResize="true" src="<s:url value="/switch.do"/>" />
			<s:if test="#parameters['url'][0] != null">
				<frame id="main_frame" name="main_frame" style="margin-left:-5px" scrolling="yes" src="<s:url value="%{#parameters['url'][0]}"/>"></frame>
			</s:if>
			<s:else>
				<frame id="main_frame" name="main_frame" style="margin-left:-15px;" scrolling="yes" src="<s:url value="/panel.do"/>"></frame>
			</s:else>
		</frameset>		
		<frame id="bottom_frame" scrolling="no" src="<s:url value="/footer.do"/>"></frame>
		<noframes>
			<body>该浏览器不支持FRAMESET!</body>
		</noframes>
	</frameset>	
</html>