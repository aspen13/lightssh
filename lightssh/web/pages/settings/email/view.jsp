<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<title>邮件内容</title>
</head>
	
<body>

	<ul class="path">
		<li>基础数据</li>
		<li>邮件服务</li>
		<li>邮件内容情况</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<table class="profile">
		<tbody>
			<tr>
				<th><label>类型</label></th>
				<td>
					<s:property value="emailContent.type"/>
				</td>
			</tr>
			<tr>
				<th><label>状态</label></th>
				<td>
					<s:property value="emailContent.status"/>
				</td>
			</tr>
			
			<tr>
				<th><label>发件人</label></th>
				<td>
					<s:property value="emailContent.sender"/>
				</td>
			</tr>
			
			<tr>
				<th><label>收件人</label></th>
				<td>
					<s:iterator value="emailContent.addressees">
						<s:property /><br/>
					</s:iterator>
				</td>
			</tr>
			
			<tr>
				<th><label>抄送人</label></th>
				<td>
					<s:iterator value="emailContent.ccs">
						<s:property /><br/>
					</s:iterator>
				</td>
			</tr>
			
			<tr>
				<th><label>邮件标题</label></th>
				<td>
					<s:property value="emailContent.subject"/>
				</td>
			</tr>
			
			<tr>
				<th><label>邮件内容</label></th>
				<td>
					<s:set name="isHtml" value="@com.google.code.lightssh.project.mail.entity.EmailContent$Type@HTML.equals(emailContent.type)"/>
					<s:iterator value="emailContent.contentLines">
						<s:if test="#isHtml">
							<s:property escape="false"/><br/>
						</s:if>
						<s:else>
							<s:property escape="true"/><br/>
						</s:else>
					</s:iterator>
					
				</td>
			</tr>
			
			<tr>
				<th><label>发送失败次数</label></th>
				<td>
					<s:property value="emailContent.failureCount"/>
				</td>
			</tr>
			
			<tr>
				<th><label>异常消息</label></th>
				<td>
					<s:property value="emailContent.errMsg"/>
				</td>
			</tr>
			
			<tr>
				<th><label>创建时间</label></th>
				<td>
					<s:property value="emailContent.createdTime"/>
				</td>
			</tr>
			
			<tr>
				<th><label>发送完成时间</label></th>
				<td>
					<s:property value="emailContent.finishedTime"/>
				</td>
			</tr>
		</tbody>
	</table>
</body>