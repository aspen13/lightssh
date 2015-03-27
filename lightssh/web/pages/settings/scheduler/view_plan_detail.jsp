<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<head>
		<meta name="decorator" content="background"/>
		
		<title>执行计划明细详细</title>
		
	
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
			
		</script>
	</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>计划任务</li>
		<li>执行计划明细详细</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<table class="profile">
		<caption>执行计划明细详细</caption>
		<tbody>
			<tr>
				<th><label>编号</label></th>
				<td>
					<s:property value="planDetail.id"/>
				</td>
			</tr>
			<tr>
				<th><label>类型</label></th>
				<td>
					<s:property value="planDetail.type.name"/>
				</td>
			</tr>
			
			<tr>
				<th><label>组名</label></th>
				<td>
					<s:property value="planDetail.group"/>
				</td>
			</tr>
			
			<tr>
				<th><label>前置任务编号</label></th>
				<td>
					<s:property value="planDetail.precondition.id"/>
				</td>
			</tr>
			
			<tr>
				<th><label>前置任务类型</label></th>
				<td>
					<s:property value="planDetail.precondition.type.name"/>
				</td>
			</tr>
			
			<tr>
				<th><label>实际执行时间</label></th>
				<td>
					<s:property value="planDetail.fireTime"/>
				</td>
			</tr>
			
			<tr>
				<th><label>执行完成时间</label></th>
				<td>
					<s:property value="planDetail.finishTime"/>
				</td>
			</tr>
			
			<tr>
				<th><label>执行方式</label></th>
				<td>
					<s:property value="planDetail.synTask?'异步':'同步'"/>
				</td>
			</tr>
			
			<tr>
				<th><label>状态</label></th>
				<td>
					<s:property value="planDetail.status"/>
				</td>
			</tr>
			
			<tr>
				<th><label>失败次数</label></th>
				<td>
					<s:property value="planDetail.failureCount"/>
				</td>
			</tr>
			
			<tr>
				<th><label>错误消息</label></th>
				<td>
					<s:property value="planDetail.errMsg"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="desc">创建时间</label></th>
				<td>
					<s:property value="planDetail.createdTime"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="desc">描述</label></th>
				<td>
					<s:property value="planDetail.description"/>
					<br/>
				</td>
			</tr>
			
		</tbody>
	</table>

	<%-- 
	<p class="submit">
		<input type="button" class="action list" name="Submit" value="明细列表"
			 onclick="location.href='<s:url value="listdetail.do?planDetail.billId=%{planDetail.billId}"/>'"/>
	</p>
	--%>
</body>