<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>
	<title>账号列表</title>
	
	<script type="text/javascript">
		/**
		 * 添加元素
		 */
		function addItem( tr ){
			var param = {
					"key":$( tr ).attr("key")
					,"text":$( tr ).attr("text")
				};
			
			var select = $.lightssh.getMultiPopupSelect();
			
			if( $(select).find("option[value='"+param.key+"']").length == 0 )
				$(select).append("<option value='"+param.key+"'>"+param.text+"</option>");
		}
		
		/**
		 * 选中元素
		 */
		function select( tr ){
			var CLAZZ = "selected";
			if( $(tr).hasClass( CLAZZ ) )
				$(tr).removeClass( CLAZZ );
			else
				$(tr).addClass( CLAZZ );
		}
	</script>
</head>

<body>
	<s:form name="list" namespace="/system/account" method="post">
		<table class="profile">
			<colgroup>
				<col width="10%"/>
				<col width="20%"/>
				<col width="10%"/>
				<col width="20%"/>
				<col/>
			</colgroup>
			<tbody>
				<tr>
					<th><label for="name">账号名称</label></th>
					<td><s:textfield id="name" name="account.loginName" value="%{cachedParams['account.loginName']}" size="20" maxlength="100"/></td>
					<th><label for="status">状态</label></th>
					<td>
						<s:select  id="status" name="account.status" value="%{cachedParams['account.status']}"
							list="@com.google.code.lightssh.project.util.constant.AuditStatus@values()"
							listKey="name()" listValue="getValue()" headerKey="" headerValue=""/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="submit" class="action search right" value="查询"/>
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
		
	<table class="list">
		<colgroup>
			<col class="element" width="30px"/>
			<col class="element" width="100px"/>
			<col class="element" width="120px"/>
			<col class="element" width="50px"/>
			<col class="element" width="90px"/>
			<col class="element" />
		</colgroup>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>帐号名称</th>
				<th>人员名称</th>
				<th>状态</th>
				<th>创建日期</th>
				<th>描述</th>
			</tr>
		</thead>
		
		<s:iterator value="page.list" status="loop">
		<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/> popupdata"
			key="<s:property value="id"/>" 
			text="<s:property value="loginName"/>" 
			style="cursor: pointer;" ondblclick="addItem(this)" onclick="select(this)">
			<td><s:property value="#loop.index +1"/></td>
			<td><s:property value="loginName"/></td>
			<td>
				<s:property value="@com.google.code.lightssh.project.party.util.PartyHelper@getParty(partyId).name"/>
			</td>
			<td>
				<s:property value="status"/>
			</td>
			<td>
				<s:property value="createDate"/>
			</td>
			<td>
				<s:property value="description"/>
			</td>
		</tr>
		</s:iterator>
	</table>
	
	<mys:pagination value="page" />
</body>