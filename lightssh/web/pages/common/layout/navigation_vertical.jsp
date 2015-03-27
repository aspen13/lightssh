<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<title>index page</title>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/my/layout/vertical_menu.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/my/layout/my_multi_window.js"></script>
		<style type="text/css">
			body{
				padding:0;
				background-color: #FCFFEF;
			}
			
			#navigation{
			}
			
			ul#vertical_navigation{
				margin:0;/* fixed IE bug*/
				padding:0;/* fixed IE bug*/
			}
			
			ul#vertical_navigation,ul#vertical_navigation li a{
				width:100%;
			}
			
		</style>
		
		<script>
			var myMultiWindows = null;
			
			$(document).ready(function(){
				initVerticalMenu( "vertical_navigation" );
				
				//myMultiWindows = $("a[target='main_frame']").mymultiwindows();
			})
		</script>
	</head>
	
	<body>
	<div id="navigation">
		
		<ul id="vertical_navigation" class="menu vertical-menu">
			<li>
				<a href="#">消息中心</a>
				<ul id="msgwf_mgr">
					<li>
						<a href="<s:url value="/message/publish/mylist.do"/>" target="main_frame">我的消息</a>
					</li>
					
					<li>
						<a href="#">工作流程</a>
						<ul id="msgwf_wf_mgr">
							<li><a href="<s:url value="/workflow/task/mytodolist.do"/>" target="main_frame">待办事宜</a></li>
							<li><a href="<s:url value="/workflow/task/myassignlist.do"/>" target="main_frame">待签流程</a></li>
							<li><a href="<s:url value="/workflow/process/my.do"/>" target="main_frame">流程查询</a></li>
						</ul>
					</li>
				</ul>
			</li>
			<li>
				<%-- 系统管理--%>
				<a href="#"><s:text name="project.nav.sysmgr"/></a>
				<ul id="system_mgr">
					<li><a href="<s:url value="/settings/organization/viewparent.do?ok=test"/>" target="main_frame">企业资料</a></li>
					
					<li>
						<%-- 系统管理--%>
						<a href="#"><s:text name="project.nav.org"/></a>
						<ul id="org_mgr">
							<li><a href="<s:url value="/party/organization/edit.do"/>" target="main_frame"><s:text name="project.nav.neworg"/></a></li>
							<%-- 组织机构列表 --%>
							<li><a href="<s:url value="/party/organization/list.do"/>" target="main_frame"><s:text name="project.nav.listorg"/></a></li>
						</ul>
					</li>
					
					<li>
						<%-- 人员管理--%>
						<a href="#"><s:text name="project.nav.person"/></a>
						<ul id="member_mgr">
							<%-- 新增人员--%>
							<li><a href="<s:url value="/party/person/edit.do"/>" target="main_frame"><s:text name="project.nav.newperson"/></a></li>
							<%-- 人员列表--%>
							<li><a href="<s:url value="/party/person/list.do"/>" target="main_frame"><s:text name="project.nav.listperson"/></a></li>
						</ul>
					</li>
					
					<li>
						<%-- 登录账号--%>
						<a href="#"><s:text name="project.nav.loginaccount"/></a>
						<ul id="login_account">
							<%-- 
							<li><a href="<s:url value="/security/account/edit.do?password=update"/>" target="main_frame"><s:text name="project.nav.changepassword"/></a></li>
							--%>
							<%-- 新增用户--%>
							<li><a href="<s:url value="/security/account/edit.do"/>" target="main_frame"><s:text name="project.nav.newaccount"/></a></li>
							<%-- 用户列表--%>
							<li><a href="<s:url value="/security/account/list.do"/>" target="main_frame"><s:text name="project.nav.listaccount"/></a></li>
							
							<%-- 
							<li><a href="<s:url value="/security/account/todoauditlist.do"/>" target="main_frame">待审核列表</a></li>
							<li><a href="<s:url value="/security/account/auditlist.do"/>" target="main_frame">审核结果</a></li>
							--%>
						</ul>
					</li>
					
					<li>
						<%-- 角色管理--%>
						<a href="#"><s:text name="project.nav.rolemgr"/></a>
						<ul id="security">
							<%-- 新增角色--%>
							<li><a href="<s:url value="/security/role/edit.do"/>" target="main_frame"><s:text name="project.nav.newrole"/></a></li>
							<%-- 角色列表--%>
							<li><a href="<s:url value="/security/role/list.do"/>" target="main_frame"><s:text name="project.nav.listrole"/></a></li>
							<%-- 
							<li><a href="<s:url value="/security/role/todoauditlist.do"/>" target="main_frame">待审核列表</a></li>
							<li><a href="<s:url value="/security/role/auditlist.do"/>" target="main_frame">审核结果</a></li>
							--%>
						</ul>
					</li>
					
				</ul>
			</li>
			
			<li>
				<%-- 基础管理--%>
				<a href="#"><s:text name="project.nav.settings"/></a>
				<ul id="settings_mgr">
					<%-- 系统日志--%>
					<li>
						<a href="#"><s:text name="project.nav.syslog"/></a>
						<ul id="settings_log">
							<li><a href="<s:url value="/settings/log/loginlist.do"/>" target="main_frame">登录日志</a></li>
							<li><a href="<s:url value="/settings/log/list.do"/>" target="main_frame"><s:text name="project.nav.syslog"/></a></li>
						</ul>
					</li>
					
					<%-- 计划任务 --%>
					<li>
						<a href="#">计划任务</a>
						<ul id="settings_scheduler">
							<li><a href="<s:url value="/settings/scheduler/list.do"/>" target="main_frame">时钟列表</a></li>
							<li><a href="<s:url value="/settings/plan/list.do"/>" target="main_frame">批处理计划</a></li>
						</ul>
					</li>
					
					<li>
						<a href="#">工作流</a>
						<ul id="workflow_mgr">
							<li><a href="<s:url value="/workflow/deployment/list.do"/>" target="main_frame">部署列表</a></li>
							<li><a href="<s:url value="/workflow/process/definitionlist.do"/>" target="main_frame">流程类型</a></li>
							<li><a href="<s:url value="/workflow/process/list.do"/>" target="main_frame">流程查询</a></li>
							<li><a href="<s:url value="/workflow/task/list.do"/>" target="main_frame">任务列表</a></li>
						</ul>
					</li>
					
					<li>
						<a href="#">消息管理</a>
						<ul id="message_mgr">
							<li><a href="<s:url value="/message/catalog/list.do"/>" target="main_frame">消息类型</a></li>
							<li><a href="<s:url value="/message/subscription/list.do"/>" target="main_frame">消息订阅</a></li>
							<li><a href="<s:url value="/message/message/list.do"/>" target="main_frame">消息列表</a></li>
						</ul>
					</li>
					
					<li>
						<a href="#">定制化列</a>
						<ul id="column_mgr">
							<li><a href="<s:url value="/settings/column/listtable.do"/>" target="main_frame">定制列表</a></li>
						</ul>
					</li>
					
					<%-- 邮件内容 --%>
					<li><a href="<s:url value="/settings/email/list.do"/>" target="main_frame">邮件内容</a></li>
							
					<%-- 系统参数 --%>
					<li><a href="<s:url value="/settings/param/list.do"/>" target="main_frame">系统参数</a></li>
					<%-- 计量单位 --%>
					<li><a href="<s:url value="/settings/uom/list.do"/>" target="main_frame">计量单位</a></li>
					<%-- 地理区域--%>
					<li><a href="<s:url value="/settings/geo/list.do"/>" target="main_frame">地理区域</a></li>
					<%-- 分类树--%>
					<li><a href="<s:url value="/settings/tree/list.do"/>" target="main_frame">分类树</a></li>
				</ul>
			</li>
			
		</ul>
		
		<div class="spliter">
		</div>
	</div>
	</body>
</html>