<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
		
		<script>
			function toggle() {
				var isHidden = false;
				if (parent.document.getElementById('main_frameset').cols == "200px,10px,*") {
					isHidden = true;
					parent.document.getElementById('main_frameset').cols = "0,10px,*";
				}else{
					parent.document.getElementById('main_frameset').cols = "200px,10px,*";
				}

				$("div#toggle > a").html( !isHidden?'&laquo;':'&raquo;');
			}
		</script>
		
		<style type="text/css">
			div#top_half,div#bottom_half{
				width:1px;
				height:30%;
				line-height:30%;
			}
			
			div#bottom_half{
				height:60%;
				line-height:60%;
			}
			
			div#toggle{
				height:10%;
				line-height:10%;
				font-size:14px;
				vertical-align:middle;
				text-align:center;
				margin-top: -4px; /** fixed ie bug*/
			}
			
			div#toggle a{
				height:50px;
				line-height:50px;
				text-align:center; 
				font-size:14px;
				color:#00B266;
				display: block;
			}
			
			div#toggle a:hover{
				z-index:1;
				color:red;
				-webkit-transform: scale(1.6);  /* Chrome, Safari 3.1+ */
				-moz-transform: scale(1.6);  /* Firefox 3.5+ */
				-ms-transform: scale(1.6);  /* IE 9 */
				-o-transform: scale(1.6);  /* Opera 10.50-12.00 */
				transform: scale(1.6);  /* Firefox 16+, IE 10+, Opera 12.10+ */
			}
			
			div#top_half,div#bottom_half{
				/* Mozilla: */
			    background: -moz-linear-gradient(top, #FFFFFF, #4C66B5);
			    
				/* Chrome, Safari:*/
			    background: -webkit-gradient(linear,
			                left top, left bottom, from(#FFFFFF), to(#4C66B5));
			                
			    /* Opera */
				background: -o-linear-gradient(top, #FFFFFF, #4C66B5);
				
			    /* MSIE */
			    filter: progid:DXImageTransform.Microsoft.Gradient(
			                StartColorStr='#FFFFFF', EndColorStr='#4C66B5', GradientType=0);
			}
			
			div#toggle{
				border-left-width:1px;
				border-left-style:solid;
				border-left-color:#4C66B5;
			}
			
			div#bottom_half{
				/* Mozilla: */
			    background: -moz-linear-gradient(top, #4C66B5, #FFFFFF);
			    
				/* Chrome, Safari:*/
			    background: -webkit-gradient(linear,
			                left top, left bottom, from(#4C66B5), to(#FFFFFF));
			                
			    /* Opera */
				background: -o-linear-gradient(top, #4C66B5, #FFFFFF);
				
			    /* MSIE */
			    filter: progid:DXImageTransform.Microsoft.Gradient(
			                StartColorStr='#4C66B5', EndColorStr='#FFFFFF', GradientType=0);
			}
		</style>
	</head>
	
	
	<body style="margin:0">
		<div style="margin:0;height: 100%;line-height: 100%;">
			<div id="top_half" style=""></div>
			<div id="toggle" style="">
				<a onclick="toggle()" href="javascript:void(0);"><span>&laquo;</span></a>
			</div>
			<div id="bottom_half" style=""></div>
		</div>
		
		<%-- 
		<table height="100%" cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody>
				<tr>
					<td bgcolor="#198bc9" width="1">
						<img height="1" width="1" src="<%=request.getContextPath()%>/images/ccc.gif" />
					</td>
					
					<td id="leftbar" style="display: none;">
						<a onclick="toggle()" href="javascript:void(0);">&raquo;</a>
					</td>
					
					<td id="rightbar" >
						<a onclick="toggle()" href="javascript:void(0);">&laquo;</a>
					</td>
				</tr>
			</tbody>
		</table>
		--%>
	</body>
</html>