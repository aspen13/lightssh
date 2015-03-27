<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<script type="text/javascript">

	/**
	 * 隐藏或显示元素
	 */
	function displayElement( val ){
		if( val == null || val == '')
			val = $("#contact_type").val();
			
		var tr_array = $("#contact_prfile_table tr");
		$.each( tr_array,function(index,tr){
			if( index > 0 && index != tr_array.length-1)
				$(tr).remove();
		});
			
		var clazz_val = "contactmechanism";
		if( 'POSTAL_ADDRESS' == val ){ //邮政地址
			$("#contact_prfile_table tr:first").after( newTableTr(
				{'id':'contact_address','label':'地址','name':'contact.address','size':80}) );
				
			$("#contact_prfile_table tr:first").after( newTableTr(
				{'id':'contact_postalcode','label':'邮编','name':'contact.postalCode'}) );
				
			$("#contact_prfile_table tr:first").after( newTableTr(
				{'id':'contact_consignee','label':'收件人','name':'contact.consignee'}) );
				
			clazz_val = "postaladdress";
		}else if( 'TELEPHONE' == val || 'FAX' == val ){ //电话
			var name = ('TELEPHONE' == val)?"电话":"传真";
			var dom_tel_html = "<tr><th>"+ name +"</th><td>"
				+"<input type='text'  param='ajax' size='2' name='contact.countryCode' value='86'/>"
				+" - <input type='text'  param='ajax' size='2' name='contact.areaCode'/>"
				+" - <input type='text'  param='ajax' size='10' name='contact.contactNumber'/>"
				+" - <input type='text'  param='ajax' size='2' name='contact.extCode'/>"
				+"&nbsp;[ 国家代码 - 区号 - 号码 - 分机号 ]</td></tr>";
			$("#contact_prfile_table tr:first").after( dom_tel_html );
			clazz_val = "telephonenumber";
		}else if('MOBILE' == val){//手机
			var dom_tel_html = "<tr><th><label class='required'>手机</label></th><td>"
				+"<input type='text'  param='ajax' size='2' name='contact.countryCode' value='+86'/>"
				+" - <input type='text'  param='ajax' size='20' name='contact.contactNumber'/>"
				+"&nbsp;[ 国家代码 - 号码  ]</td></tr>";
			$("#contact_prfile_table tr:first").after( dom_tel_html );
			clazz_val = "telephonenumber";
		}else if('OTHER' == val || val == null ){ //其它
			$("#contact_prfile_table tr:first").after( newTableTr(
				{id:'contact_othertypevalue',label:'联系信息',name:'contact.otherTypeValue','size':40}) );
			$("#contact_prfile_table tr:first").after( newTableTr(
				{id:'contact_othertypename',label:'其它类型',name:'contact.otherTypeName'}) );
		}else{
			var label = '联系信息'
			if('EMAIL'==val){
				label = '邮箱地址'
			}else{
				label = $("#contact_type>option:selected").get(0).text;
			}
			$("#contact_prfile_table tr:first").after( newTableTr(
				{'id':'contact_othertypevalue','label':label,'name':'contact.otherTypeValue','size':40}) );
		}
		$("#clazz_contact").val( clazz_val );
	}
	
	function newTableTr( params ){
		var size = ( params.size != null )?params.size:20;
		var html = "<tr>"
					+"<th><label class='required' for='"+params.id+"'>"+params.label+"</label></th>"
					+"<td><input type='text' param='ajax' id='"+params.id+"' size='"+size+"' name='"+params.name+"'/></td>"
				  +"</tr>";
		return html;
	}
	
	function showContactForm( val ){
		showMessage('');
		displayElement( val );
	}
	
	/**
	 * 提交数据
	 */
	function dosubmit( ){
		showMessage('');
		$("#submit").attr('disabled', 'true');//防重复提交
		
		var url = "<s:url value="/party/contact/save.do"/>";
		var other_param = '';
		$.each( $("input[param='ajax']"),function(index,ele){
			other_param += ',"'+$(ele).attr("name")+'":"' +$(ele).val()+'"' ;
		});
		//alert( $("#contact_type").val() );
		var params = '{"contact":"' + $("#clazz_contact").val() + '"'
			+',"party":"organization","party.id":"'+ $("input[name='party.id']").val() + '"'
			+',"contact.type":"'+ $("#contact_type").val() + '"'
			+',"contact.description":"'+ $("#contact_description").val() + '"'
			
			+ other_param
			
			+',"struts.enableJSONValidation":"true","struts.validateOnly":"false"'
			+'}';
		$.post( url,$.parseJSON(params),function(data){ajaxResult(data);},"text");
	}
	
	function ajaxResult( data ){
		var json = $.parseJSON(data);
		if( json.result != null && json.result ){
			showMessage('插入成功!');
			displayElement( $("#contact_type").val() );
		}
		if(json.fieldErrors != null ){
			$("label[name='error']").remove();
			var errors = json.fieldErrors;
			if( errors['contact.type'] != null ){ //类型
				$.lightssh.showFieldError( $("select[name='contact.type']"),errors['contact.type'][0] );
			}
			if( errors['contact.description'] != null ){ //描述
				$.lightssh.showFieldError( $("textarea[name='contact.description']"),errors['contact.description'][0] );
			}
			if( errors['contact.otherTypeName'] != null ){ //其它类型
				$.lightssh.showFieldError( $("input[name='contact.otherTypeName']"),errors['contact.otherTypeName'][0] );
			}

			if( errors['contact.consignee'] != null ){ //邮政地址-收件人
				$.lightssh.showFieldError( $("input[name='contact.consignee']"),errors['contact.consignee'][0] );
			}
			
			if( errors['contact.postalCode'] != null ){ //邮政地址-邮编
				$.lightssh.showFieldError( $("input[name='contact.postalCode']"),errors['contact.postalCode'][0] );
			}
			
			if( errors['contact.address'] != null ){ //邮政地址-地址
				$.lightssh.showFieldError( $("input[name='contact.address']"),errors['contact.address'][0] );
			}
			
			if( errors['contact.contactNumber'] != null ){ //号码
				$.lightssh.showFieldError( $("input[name='contact.contactNumber']"),errors['contact.contactNumber'][0] );
			}
			
			if( errors['contact.otherTypeValue'] != null ){ //号码
				$.lightssh.showFieldError( $("input[name='contact.otherTypeValue']"),errors['contact.otherTypeValue'][0] );
			}
		}

		$("#submit").removeAttr('disabled');//防重复提交
	}
	
	/**
	 * 显示提示信息
	 */
	function showMessage( msg ){
		if( $('.success').html() == null ){
			$("<div class='success'>"+msg+"</div>").appendTo($("#action_message_hint"));
		}else{
			$('.success').text( msg );
		}
	}
	
	$(document).ready(function(){
		displayElement( '' );
	});
</script>

<div id="action_message_hint" class="messages">
</div>

<table class="profile" id="contact_prfile_table">
	<tbody>
		<tr>
			<th><label for="name" class="required">类型</label></th>
			<td>
				<input type="hidden" id="clazz_contact" name="contact" value="contactmechanism"/>
				<s:select list="@com.google.code.lightssh.project.contact.entity.ContactMechanism$ContactMechanismType@values()"
					id="contact_type" listKey="name()" name="contact.type" value="contact.type.name()" 
					onchange="showContactForm(this.value)"/>
			</td>
		</tr>
		<tr>
			<th><label for="contact_description">描述</label></th>
			<td><s:textarea id="contact_description" name="contact.description" cols="60" rows="5"/></td>
		</tr>
	</tbody>
</table>

	<p class="submit">
		<input id="submit" type="button" class="action save" name="save" value="添加" onclick="dosubmit()"/>
	</p>