
/**
 * 选择Party弹出框
 */		
var popup_party = $("<div id='popup_party' title='人员/部门' style='display: none;'></div>");

//$(popup).html('') fixed IE bug
function popupParty( url ,param){
	var popup = $( popup_party );
	//$( popup ).html( '<div><img id=\'loading\' src=\'<%= request.getContextPath() %>/images/loading.gif\'/>' );
	$( popup ).dialog({
		resizable: true,modal: true,height:500,width: 700,
		close: function(event, ui) {$(this).dialog('destroy'); },				
		buttons: {				
			"关闭": function() {$(this).dialog('destroy');}
		}
	});
	
	$.post(url,param,function(data){$( popup ).html( data );});
}

/**
 * 弹出框回调
 */
function callbackSelectParty( party ){
	$("#span_party_name").html( party.name );
	$("#party_name").val( party.name );
	$("#party_id").val( party.id );
	$("#party_clazz").val( party.clazz );
	$("#popup_party").dialog('destroy').html('');
	$("label[for='party_id']").remove(); //移除样式
}