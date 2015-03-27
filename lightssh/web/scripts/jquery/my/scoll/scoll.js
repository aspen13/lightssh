	var all_index = 0;

	/**
	 *添加行
	 */
	function addRow( data, table_id ,index){
		var table = $('#'+table_id);
		var tr = "<tr class=\"" + ((index%2==0)?"odd":"even") + "\">";
		tr += "<td>"+ data.number+"</td>";
		tr += "<td>"+ data.name+"</td>";
		tr += "<td>"+ data.industry+"</td>";
		tr += "<td>"+ data.price+"</td>";
		tr += "<td>"+ data.date+"</td>";
		tr += "</tr>";
		//$(tr).fadeIn("slow",function(){$(tr).appendTo( table );});
		$(tr).appendTo( table );
	}

	/**
	 * 删除行
	 */
	function removeRow( table_id ){
		var row = $('#'+table_id + ' tr:eq(1)');
		//$(row).fadeOut("slow",function(){$(row).remove();});
		$(row).remove();
	}

	/**
	 * 自动滚动
	 */
	function autoScoll(list, table_id ,open){
		var table = $('#'+table_id);
		if( open ){
			$(table).everyTime(1000, function() {
			    scoll(list,table_id);
			});
		}else{
			$(table).stopTime();
		}
	}

	function scoll( list, table_id ){
		var index = all_index%list.length;
		++all_index;
		addRow(list[index],table_id,all_index );
		removeRow( table_id);
	}
	
	/**
	 * 显示数据
	 */
	function display(json,table_id){
		var data_count = json.list.length; //数据条数
		var MAX_DISPLY_COUNT = 5; //最大显示数
		
		$.each(json.list,function(index){
			//alert(json.list[index].name);
			++all_index;
			addRow( json.list[index],table_id,all_index);
			if( index + 1 == MAX_DISPLY_COUNT ){
				return false;
			}
		});
		
		//鼠标移上表格暂停
		$('#'+table_id).hover(
			function(){autoScoll( json.list,table_id,false)}
			,function(){autoScoll( json.list,table_id,true)}
		);
		
		if( data_count > MAX_DISPLY_COUNT )
			autoScoll( json.list,table_id,true); //滚动
	}