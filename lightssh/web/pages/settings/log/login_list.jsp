<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
		<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	
		<title>登录日志</title>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$("#access_start_date").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
				$("#access_end_date").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
			});

			/**
			 * 查看会员详情
			 */
			function popupView( name ){
				var url = '<s:url value="/security/account/view.do"/>?account.loginName=' + name;
				//<div id="account_popup" title="登录账户信息" style="display: none;">&nbsp;</div>
				var popup = $("<div title='登录账户信息'>&nbsp;<div>");
				//$( popup ).html( '<div><img id=\'loading\' src=\'<%= request.getContextPath() %>/images/loading.gif\'/>' );
				$( popup ).dialog({
					resizable:false,modal: true,height:400,width: 700,
					close: function(event, ui) {$(this).dialog('destroy').html(''); }
				});
				
				$.post(url,{},function(data){$( popup ).html( data );});
			}
			
			/**
			 * USER-弹出框回调
			 */
			function callbackSelectLoginAccount( param ){
				$("input[name='loginLog.operator']").val( param.text);
				
				$( popup_login_account ).dialog('destroy').html('');
			}
		</script>
		
	</head>
	
	<body>
	<ul class="path">
		<li>系统管理</li>
		<li>系统日志</li>
		<li>登录日志</li>
	</ul>
 		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="loginlist" namespace="/settings/log" method="post">
			<table class="profile">
				<tbody>
					<tr>
						<td><label for="access_start_date">时间</label></td>
						<td>
							<s:textfield name="loginLog._period.start" id="access_start_date" size="10" /> -
							<s:textfield name="loginLog._period.end" id="access_end_date" size="10"/>
						</td>
						
						<td><label for="ip">IP</label></td>
						<td><s:textfield id="ip" name="loginLog.ip"  size="16"/></td>
						
						<td><label for="operator">登录用户</label></td>
						<td>
							<s:textfield id="operator" name="loginLog.operator"  size="16"/>
							<span class="popup user" onclick="popupLoginAccount('<s:url value="/security/account/popup.do"/>',{});">&nbsp;</span>
						</td>
						
						<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="page" status="loop">
			<mys:column title="序号" width="50px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="时间" value="createdTime" sortable="true" sortKey="createdTime" width="200px"/>
			<mys:column title="IP" value="ip" sortable="true" width="150px"/>
			<mys:column title="操作者" value="operator" sortable="true" width="200px"/>
			<mys:column title="描述" value="description"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li>
							<a href="#" onclick="javascript:popupView('<s:property value="%{operator}"/>')">
								查看操作员[<s:property value="%{operator}"/>]
							</a>
						</li>
					</ul>
				</div>
			</mys:column>
		</mys:table>
	
		<mys:pagination value="page" />
	
	</body>
</html>