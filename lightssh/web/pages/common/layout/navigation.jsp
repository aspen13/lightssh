<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<div class="navigation">
	<ul class="menu horizontal-menu">
		<li class="current">
			<a href="<s:url value="/"/>" title="home">首页</a>
		</li>
		
		<li>
			<a class="members" href="#" title="members">会员服务</a>
			<ul>
				<li>
					<a href="#">二级菜单</a>
					<ul>
						<li><a href="#">三级菜单</a></li>
					</ul>
				</li>
				<li><a href="#">会员注册</a></li>
			</ul>
		</li>
		
		<li><a href="<s:url value="/welcome.do"/>">系统管理</a></li>
	</ul>
</div>