<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		
		<script type="text/javascript" src="<s:url value="/pages/message/catalog/popup.js" />"></script>
		<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
		
		<title>消息发件箱</title>
		
		<script type="text/javascript">
			/**
			 * 选择消息类型回调
			 */
			function callbackSelectMsgCatalog(param){
				$("input[name='message.catalog.id']").val(param.id);
				$("input[name='message.catalog.description']").val(param.description);
				$("#span_msg_catalog_name").text( param.id + '-' +param.description);
				$( popup_msg_catalog ).dialog('destroy').html('');
			}
			
			/**
			 * 删除消息
			 */
			function doRemove( id,name ){
				var url = '<s:url value="/message/message/myremove.do"/>?message.id=' + id ;
				if( confirm('确认删除消息[' + name + ']'))
					location.href=url;
			}
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>消息中心</li>
			<li>我的消息</li>
			<li>消息发件箱</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" method="post">
			<input type="submit" class="action outbox" value="查询"/>
			
			<input type="button" class="action back" value="返回"
				onclick="location.href='<s:url value="/message/publish/mylist.do"/>'"/>
				
			<input type="button" class="action new" value="创建消息" 
				onclick="location.href='<s:url value="/message/message/edit.do"/>'"/>
				
			<table class="profile">
				<colgroup>
					<col width="120px"/>
					<col width="200px"/>
					<col width="120px"/>
					<col width="200px"/>
					<col width="120px"/>
					<col width="200px"/>
					<col />
				</colgroup>
				<tbody>
					<tr>
						<th><label for="catalog">消息类型</label></th>
						<td>
							<span class="popup" onclick="popupMsgCatalog('<s:url value="/message/catalog/popup.do"/>');">&nbsp;</span>
							<s:hidden name="message.catalog.id"/>
							<s:hidden name="message.catalog.description"/>
							<span id="span_msg_catalog_name"><s:property value="message.catalog.id + '-' + message.catalog.description"/></span>
						</td>
						<th><label for="title">标题</label></th>
						<td>
							<s:textfield id="title" name="message.title" size="40" maxlength="100"/>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="page" dynamic="false" status="loop">
			<mys:column title="序号" width="40px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<%--<mys:column title="类型编号" value="catalog.id" width="80px"/>--%>
			<%--<mys:column title="消息类型" value="catalog.type" width="40px"/>--%>
			<mys:column title="接收人" width="80px">
				<s:property value="@com.google.code.lightssh.project.message.util.ReceiveTypeHelper@getReceiveTitle(recType,recValue)"/>
			</mys:column>
			<mys:column title="优先级" value="priority" sortable="true" width="60px"/>
			<mys:column title="标题">
				<s:property value="title"/>
			</mys:column>
			<mys:column title="状态" value="status" width="60px"/>
			<mys:column title="可链接" width="60px">
				<s:property value="linkable?'是':'否'"/>
			</mys:column>
			<mys:column title="可转发" width="60px">
				<s:property value="forward?'是':'否'"/>
			</mys:column>
			<mys:column title="发布条数" value="publishedCount" sortable="true"  width="60px"/>
			<%--<mys:column title="点击次数" value="hitCount" sortable="true" width="60px"/>--%>
			<%--<mys:column title="阅读者数" value="readerCount" sortable="true"  width="60px"/>--%>
			<mys:column title="创建人" value="creator" width="80px"/>
			<mys:column title="创建时间" value="createdTime" sortable="true"  width="150px"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="view">
							<a href="<s:url value="view.do?message.id=%{id}"/>">消息详情</a>
						</li>
						<li class="section"/>
						<li class="remove">
							<a href="#" onclick="doRemove('<s:property value="id"/>','<s:property value="title"/>')">删除消息</a>
						</li>
					</ul>
				</div>
			</mys:column>
		</mys:table>
		
		<mys:pagination value="page"/>
	</body>
</html>