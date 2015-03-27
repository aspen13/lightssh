<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>邮件内容列表</title>
		
		<script src="<%= request.getContextPath() %>/scripts/jquery/plugins/cluetip/jquery.cluetip.all.js"></script>		
		<link href="<%=request.getContextPath() %>/scripts/jquery/plugins/cluetip/jquery.cluetip.css"	rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
		
			$(document).ready(function(){
				$('a.title').cluetip({splitTitle: '|'});
			});

			function doResend(id,subject){
				var url = '<s:url value="/settings/email/resend.do"/>?emailContent.id='+id;
				if( confirm('确认重发邮件[' + subject + ']'))
					location.href=url;
			}
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础数据</li>
			<li>邮件服务</li>
			<li>邮件内容</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" namespace="/settings/email" method="post">
			<table class="profile">
				<tbody>
					<tr>
						<th><label for="type">邮件类型</label></th>
						<td>
							<s:select id="type" name="emailContent.type" value="emailContent.type.name()"
								headerKey="" headerValue=""
								listKey="name()" listValue="getValue()"
								list="@com.google.code.lightssh.project.mail.entity.EmailContent$Type@values()"/>
						</td>
						<th><label for="status">状态</label></th>
						<td>
							<s:select id="status" name="emailContent.status" value="emailContent.status.name()"
								headerKey="" headerValue=""
								listKey="name()" listValue="getValue()"
								list="@com.google.code.lightssh.project.mail.entity.EmailContent$Status@values()"/>
						</td>
						<th><label for="addressee">收件人</label></th>
						<td>
							<s:textfield name="emailContent.addressee" id="addressee" size="40"/>
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
			<mys:column title="标题" value="subject" sortable="true"/>
			<mys:column title="发件人" value="sender" sortable="false" width="200px"/>
			<mys:column title="收件人" sortable="false" width="100px">
				<s:if test="addressees.length == 1">
					<s:property value="%{addressee}"/>
				</s:if>
				<s:else>
					<a class="title" href="#" title="<s:property value="%{addressees}"/>">
						<s:property value="%{addressees[0]}"/>
					</a>
				</s:else>
			</mys:column>
			<mys:column title="创建时间" value="createdTime" width="160px"/>
			<mys:column title="类型" value="type" sortable="true" width="60px"/> 
			<mys:column title="状态" value="status" sortable="true" width="60px"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li><a href="<s:url value="view.do?emailContent.id=%{id}"/>">邮件内容详情</a></li>
						<s:set name="resend" value="@com.google.code.lightssh.project.mail.entity.EmailContent$Status@FAILURE.equals(status)"/>
						<s:if test="#resend">
							<li class="section"></li>
							<li>
								<a href="#" onclick="doResend('<s:property value='id'/>','<s:property value='subject'/>')">重发邮件</a>
							</li>
						</s:if>
					</ul>
				</div>
			</mys:column>
		</mys:table>
	
		<mys:pagination value="page" />
	</body>
</html>