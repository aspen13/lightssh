<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<head>
		<meta name="decorator" content="background"/>
		
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/my/geo.js"></script>
	
		<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
		
		<title>编辑人员</title>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$( "#tabs" ).tabs({ ajaxOptions: { async: false },cache: true });
				$( "#tabs" ).bind( "tabsload", function(event, ui) {
					showDatepicker();
				});
				
				/**
				 * 数据校验
				 */
				$("#profile_form").validate({
					rules:{
						"party.name":{required:true}
					}
				});
				
				showDatepicker();

				initGeo({'geo_parent_url':'<s:url value="/settings/geo/listcountry.do"/>',
					'geo_children_url':'<s:url value="/settings/geo/listchildren.do"/>',
					'geo_active':'true',
					'geo_selectors':[
						{'name':'party.country.code','value':'<s:property value="%{party.country.code}"/>'},
						//{'name':'party.secondaryGeo.code','value':'<s:property value="%{party.secondaryGeo.code}"/>','types':['PROVINCE','MUNICIPALITY','AUTONOMOUS_REGION','SPECIAL_ADMINISTRATIVE_REGION']},
						{'name':'party.secondaryGeo.code','value':'<s:property value="%{party.secondaryGeo.code}"/>'},
						{'name':'party.thirdGeo.code','value':'<s:property value="%{party.thirdGeo.code}"/>'},
						{'name':'party.fourthGeo.code','value':'<s:property value="%{party.fourthGeo.code}"/>'}]
				});
			});

			/**
			 * 显示日历
			 */
			function showDatepicker( ){
				$("input[type='text'][name='party.birthday']").datepicker(
					{dateFormat: 'yy-mm-dd',changeYear:true,yearRange:'-60,60'});
			}
			
			/**
			 * 自动填值
			 */
			function autofill( ele ){
				var p_identity = $("input[name='party.identityCardNumber']");
				if( $(ele).attr("name") == "party.identityCardNumber" 
					|| $(ele).attr("name") == "party.credentialsType"){
					if( $(p_identity).val().length == 18 
						&& 'P01' == $("select[name='party.credentialsType']").val() ){
						var ymd = $(p_identity).val().substring(6,14);
						ymd = ymd.substring(0,4)+'-'+ymd.substring(4,6)+'-'+ymd.substring(6,8)
						$("input[name='party.birthday']").val( ymd )
					}
				}
			}

		</script>
	</head>
	
<body>
	<ul class="path">
		<li>系统管理</li>
		<li>人员管理</li>
		<li>编辑人员</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<div id="tabs"> 
			<ul> 	
				<li><a href="#tabs-1">基本信息</a></li>
				
				<s:if test="party != null && !party.insert ">
				<li><a href="<s:url value="edit.do?party=person&party.id=%{party.id}&profile=employee"/>">人事信息</a></li>
				</s:if>
				<%-- 
				<li><a href="<s:url value="?profile=contact"/>">联系方式</a></li>
				<li><a href="<s:url value="?profile=family"/>">工作经历</a></li>
				<li><a href="<s:url value="?profile=family"/>">家庭成员</a></li>
				--%>
			</ul> 
		
			<div id="tabs-1">
				<%@ include file="profile_base.jsp" %>
			</div>
	</div>
	
	<div id="popup" title="所属组织机构" style="display: none;">&nbsp;</div>
</body>