<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<title>index page</title>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
	    <link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/scripts/jquery/styles/theme.css" />
	        
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/jquery-ui.custom.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/my/layout/my_menu_tabs.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/timers/jquery.timers-1.2.js"></script>
	
		<script type="text/javascript">
			var myMenuTabs = null;
			
			$(document).ready(function(){
				myMenuTabs = $("#multi_window ul.tabs").mymenutabs();
				
				//myMenuTabs.addTab();
		    	
				$(document).everyTime(2*60*1000, function() {
			    	if( $.cookie( "REFRESH-HEADER" ) == 'TRUE' ){
			    		$.cookie( "REFRESH-HEADER",'',{path:'<%= request.getContextPath() %>'});
			    		//location.reload();
			    	}
			    	
			    	initCounter();
				});
				
				initCounter();
				
				$(".counter").bind("mouseover",function(){
					$(this).siblings("a").addClass("hourglass");
				});
				
				$(".counter").bind("mouseout",function(){
					var clazz = $(this).siblings("a").attr("class");
					
					$(this).siblings("a").removeClass("hourglass");
					
					if( clazz.indexOf("message") > 0 )
						refreshMessageCounter();
					else if( clazz.indexOf("task") > 0 )
						refreshTaskCounter();
					else if( clazz.indexOf("shoppingcart") > 0 )
						refreshShoppingcartCounter();
				});
				
			});
			
			function initCounter( ){
				if( $("a.message").length > 0)
					refreshMessageCounter();
				if( $("a.task").length > 0)
					refreshTaskCounter();
				if( $("a.shoppingcart").length > 0)
					refreshShoppingcartCounter();
			}
			
			/**
			 * 样式
			 */
			function theme( link ){
				var select = $("<select></select>");
				$("<option value=''></option>").appendTo(select);
				$("<option value='default'>经典</option>").appendTo(select);
				$("<option value='vigor'>热血</option>").appendTo(select);
				
				$(link).hide();
				select.insertAfter(link);
				select.focus();
				
				$(select).blur(function(){
					$(this).hide();
					$(link).show();
				});
				
				$(select).change(function(){
					var date = new Date();
					date.setTime(date.getTime() + (365 * 24 * 60 * 60 * 1000)); //cookie 超期时间
					$.cookie( "theme", this.value ,{expires: date});
					
					var url = "<s:url value="/welcome.do?theme="/>" + this.value;
					top.location.href=url;
					//$(this).hide()
					//$(link).show()
				});
			}
			
			/**
			 * 语言
			 */
			function locale( link ){
				var select = $("<select></select>");
				$("<option value=''></option>").appendTo(select);
				$("<option value='en'>ENGLISH</option>").appendTo(select);
				$("<option value='zh_CN'>中文</option>").appendTo(select);
				
				$(link).hide();
				select.insertAfter(link);
				select.focus();
				
				$(select).blur(function(){
					$(this).hide()
					$(link).show()
				});
				
				$(select).change(function(){
					var url = "<s:url value="/welcome.do?locale="/>" + this.value;
					top.location.href=url;
				});
			}
			
			/**
			 * 未读消息数
			 */
			function refreshMessageCounter(ele,url){
				refreshCounter( $("li > a.message"),'<s:url value="/message/publish/myunreadcount.do"/>' );
			}
			
			/**
			 * 待办任务数
			 */
			function refreshTaskCounter(ele,url){
				refreshCounter( $("li > a.task"),'<s:url value="/workflow/task/mytodocount.do"/>' );
			}
			
			/**
			 * 购物车商品数
			 */
			function refreshShoppingcartCounter(ele,url){
				refreshCounter( $("li > a.shoppingcart"),'' );
			}
			
			/**
			 * 更新数字
			 */
			function refreshCounter(ele,url){
				if( ele == null )
					return;
				
				var msg = '( ? )';
				var counter = $(ele).siblings("span");
				$(ele).addClass("hourglass");
				$.ajax({
					url:url,dataType: "json",type:"post",async:false,data: {}
			        ,error: function(){ 
			        	$(counter).html( 'ERR' );
			        	$(ele).removeClass("hourglass");
			        }
			        ,success: function(json){
			        	if( json.type == 'login' ){
			        		//Session失效
			        		$(counter).html( 'E00' );
				        	$(ele).removeClass("hourglass");
			        	}else{
				        	if( json.count < 10 ){
				        		msg = '' + json.count + '';
				        	}else if( json.count > 99 ){
				        		msg = '99+';
				        	}else{
				        		msg = '' + json.count + '';
				        	}
				        	$(counter).html( msg );
				        	$(ele).removeClass("hourglass");
			        	}
			        }
				});
			}
			
			/**
			 * 搜索
			 */
			function search(){
				return false;
			}
		</script>
	</head>
	
	<body id="header">
		<div id="header">
			<div class="logo">
				<%-- 
				<img alt="logo" src="<s:url value="/images/logo.gif"/>" />
				<a href="#" >采购管理系统</a>
				--%>
				<img alt="logo" src="<s:url value="/images/logo.png"/>" />
				<a href="http://code.google.com/p/lightssh/" target="_blank">Light SSH</a>
			</div>
			
			<div class="status" id="div_status">
				<div class="right">
					<ul>
						<li class="first">
							<s:set name="principal" value="#session[@com.google.code.lightssh.common.web.SessionKey@LOGIN_ACCOUNT].loginName"/>
							<%--<s:text name="project.header.welcome"/>--%>
							<a href="<s:url value="/security/account/my.do"/>" class="icon user" target="main_frame"><s:property value="#principal"/></a>
						</li>
						<li>
							<a href="<s:url value="/welcome.do"/>" class="icon home" target="_top"><s:text name="project.header.home"/></a>
						</li>
						<li>
							<a href="<s:url value="/security/account/edit.do?password=update"/>" class="icon password" target="main_frame"><s:text name="project.header.changepassword"/></a>
						</li>
						<li>
							<a href="<s:url value="/message/publish/mylist.do"/>?publish.read=false" class="icon message" target="main_frame" >未读消息</a>
							<span class="counter badge">?</span>
						</li>
						<li>
							<a href="<s:url value="/workflow/task/mytodolist.do"/>" class="icon task" target="main_frame" >待办事宜</a>
							<span class="counter badge">?</span>
						</li>
						<%--
						<li>
							<a href="#" class="icon shoppingcart" target="main_frame" >购物车</a>
							<span class="counter">( 0 )</span>
						</li>
						--%>
						<li>
							<a href="#" class="icon theme" onclick="theme(this)"><s:text name="project.header.theme"/></a>
						</li>
						<li>
							<a href="#" class="icon language" onclick="locale(this)"><s:text name="project.header.language"/></a>
						</li>
						<li class="last">
							<a href="<s:url value="/logout.do"/>" class="icon exit" target="_top"><s:text name="project.header.exit"/></a>
						</li>
					</ul>
				</div>
				
				<div class="search">
					<form method="POST" action="<s:url value=""/>" target="main_frame" onsubmit="return search()">
			            <input type="text" name="keywords">
			            <input type="submit" value="">
			        </form>
				</div>
			</div>
			
			<%-- 
			<div id="multi_window">
				<ul class="tabs">
					<li class="tab icon home active" id="<s:url value="/welcome.do"/>" href="<s:url value="welcome.do"/>">
						<a href="#">首页</a>
						<span class="">&times;<span>
					</li>
				</ul>
			</div>
			--%>
		</div>
		
		<div class="line">
			<div class="left"></div>
			<div class="right"></div>
		</div>
		<%-- 
		--%>
	</body>
</html>