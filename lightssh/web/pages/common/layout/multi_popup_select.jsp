<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			var MENU_WIDTH = 30;
			var select = $("#popup_multi_select");
			var menu = $("#popup_multi_menu");
			
			$(select).width( $(select).width() - MENU_WIDTH );
			$(menu).width(MENU_WIDTH).css("display","block");
			$(menu).css("margin-top",parseInt(($(select).height() - $(menu).height())/2));//垂直居中
			
		});
		
		/**
		 * 获取窗口值
		 */
		function getMultiPopupData(selected){
			var mainBody = null;
			var dataBody = null;
			if( top.frames[ "main_frame" ] == null ){
				dataBody = $(top.frames[ 'popup_multi_data_iframe' ].window.document.body);
			}else{
				mainBody = $(top.frames[ "main_frame" ].window.document.body);
				dataBody = $(mainBody).find('#popup_multi_data_iframe').contents().find("body");
			}
			
			//数据<tr class="popupdata" ...>
			if( selected == null || !selected )
				return $(dataBody).find("tr.popupdata"); 
			else
				return $(dataBody).find("tr.popupdata.selected"); 
			
		}
		
		/**
		 * 移除选择的所有
		 */
		function decSelectedValue( ele ){
			if( ele != null && $(ele).hasClass("disabled") )
				return;
			
			$("#popup_multi_select").find("option:selected").remove();
		}
		
		/**
		 * 移除全部
		 */
		function decAllValue( ele ){
			if( $(ele).hasClass("disabled") )
				return;
				
			$("#popup_multi_select").find("option").remove();
		}
		
		/**
		 * 添加全部
		 */
		function incAllValue( ele ,selected){
			if( $(ele).hasClass("disabled") )
				return;
				
			var data = getMultiPopupData(selected);
			if( data.size() ==0 )
				return;
				
			var select = $("#popup_multi_select");
			
			for(var i=0;i<data.size();i++){
				var key = $(data[i]).attr("key");
				var text = $(data[i]).attr("text");
				if( $(select).find("option[value='"+key+"']").length == 0 )
					$(select).append("<option value='"+key+"'>"+ text+"</option>");
			}
				
		}
		
		/**
		 * 添加选择的所有
		 */
		function incSelectedValue( ele ){
			if( ele != null && $(ele).hasClass("disabled") )
				return;
			
			incAllValue(ele,true);
		}
		
	</script>
	
	<style type="text/css">
		body{
			margin: 2px;
		}
		select{
			width:100%;
			height:100%;
			margin:0;
			display: block;
			float: left;
		}
		
		div#popup_multi_menu{
			width:20px;
			float:left;
			font-size:12px;
		}
		
		div#popup_multi_menu a{
			display: block;
			margin: 2em 0 2em 0;
			height:16px;
			line-height: 16px;
			
			cursor:pointer;
			background-repeat: no-repeat;
			background-position: center;
		}
		
		div#popup_multi_menu a.inc_one{
			background-image: url("<s:url value="/images/background/icon_popup_+one.png"/>");
		}
		
		div#popup_multi_menu a.inc_all{
			background-image: url("<s:url value="/images/background/icon_popup_+all.png"/>");
		}
		
		div#popup_multi_menu a.dec_one{
			background-image: url("<s:url value="/images/background/icon_popup_-one.png"/>");
		}
		
		div#popup_multi_menu a.dec_all{
			background-image: url("<s:url value="/images/background/icon_popup_-all.png"/>");
		}
		
		div#popup_multi_menu a.disabled:hover{
			cursor:auto;
			background-image: url("<s:url value="/images/background/icon_disabled.png"/>");
		}
		
	</style>
</head>

<body>
	<div id="popup_multi_menu" style="display: none">
		<a class="inc_one" title="添加所选" onclick="incSelectedValue(this)"></a>
		<a class="inc_all" title="添加全部" onclick="incAllValue(this)"></a>
		<a class="dec_all" title="移除全部" onclick="decAllValue(this)"></a>
		<a class="dec_one" title="移除所选" onclick="decSelectedValue(this)"></a>
	</div>
	
	<select id="popup_multi_select" multiple="multiple" onchange="selectChange()" ondblclick="decSelectedValue( )">
	</select>
</body>
	
