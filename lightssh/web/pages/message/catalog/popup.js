
/**
 * 选择Party弹出框
 */		
var popup_msg_catalog = $("<div id='popup_msg_catalog' title='消息类型' style='display: none;'></div>");

function popupMsgCatalog( url ,param){
	var popup = $( popup_msg_catalog );
	//$( popup ).html( '<div><img id=\'loading\' src=\'<%= request.getContextPath() %>/images/loading.gif\'/>' );
	$( popup ).dialog({
		resizable: true,modal: true,height:500,width: 700,
		close: function(event, ui) {$(this).dialog('destroy'); },				
		buttons: {				
			"关闭": function() {callbackSelectMsgCatalog({});$(this).dialog('destroy');}
		}
	});
	
	$.post(url,param,function(data){$( popup ).html( data );});
}

/**
 * 弹出框回调
 */
function callbackSelectMsgCatalog( param ){
	//$("#span_party_name").html( param.name );
	//$("#catalog_name").val( param.name );
	//$("#catalog_id").val( param.id );
	$( popup_msg_catalog ).dialog('destroy').html('');
	//$("label[for='party_id']").remove(); //移除样式
}