<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<head>
		<meta name="decorator" content="background"/>
		
		<title>系统参数</title>
		
		<script type="text/javascript">
		</script>
	</head>
	
<body>
	<ul class="path">
		<li>基础数据</li>
		<li>系统参数</li>
		<li>查看参数</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<table class="profile">
		<tbody>
			<tr>
				<th><label>所属子系统</label></th>
				<td>
					<s:property value="param.system==null?\"管理平台\":param.system"/>
				</td>
			</tr>
			<tr>
				<th><label>组名</label></th>
				<td>
					<s:property value="param.group"/>
				</td>
			</tr>
			<tr>
				<th><label>名称</label></th>
				<td>
					<s:property value="param.name"/>
				</td>
			</tr>
			
			<tr>
				<th><label>参数值</label></th>
				<td>
					<s:property value="param.value"/>
				</td>
			</tr>
			
			<tr>
				<th><label>是否有效</label></th>
				<td>
					<s:property value="%{param.enabled?'是':'否'}"/>
				</td>
			</tr>
			
			<tr>
				<th><label>是否只读</label></th>
				<td>
					<s:property value="%{param.readonly?'是':'否'}"/>
				</td>
			</tr>
			
			<tr>
				<th><label>描述</label></th>
				<td><s:property value="param.description"/></td>
			</tr>
		</tbody>
	</table>

</body>