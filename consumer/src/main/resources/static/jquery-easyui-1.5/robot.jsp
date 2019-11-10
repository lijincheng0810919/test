<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="../jquery-easyui-1.5/jquery.min.js"></script>
<script src="../jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script  src="../jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="../jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" href="../jquery-easyui-1.5/themes/icon.css">
<script src="../jquery-easyui-1.5/util-js.js"></script>
<style type="text/css"> 
	.sendP{
		text-align: right;
	}
	.sendCen{
		background-color: #33CCFF;
		color: white;
	    font-size: 18px;
	    padding: 2px 5px;
	    border-radius: 8px;
	}
	.sendIcon{
		color: pink;
	    margin-left: -3px;
	}
	.reciveCen{
		background-color: #CC00FF;
	    color: white;
	    font-size: 18px;
	    padding: 2px 5px;
	    border-radius: 8px;
	}
	.reciveIcon{
		color: black;
    	margin-right: -3px;
	}
</style>
</head>
<body>
	
	<div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%" id="content" >

		</div>
		<div data-options="region:'center'" style="text-align:center;">
			<input class="easyui-textbox" id="message" style="width: 820px;height: 94px;" data-options="multiline:true" >
			<a href="javascript:sendMsg()" class="easyui-linkbutton" style="width: 180px;height: 94px;" data-options="iconCls:'icon-ok',plain:true">发送</a>
		</div>
	</div>
<script type="text/javascript">
function sendMsg(){
	var message = $("#message").textbox("getValue");
	var send = '<p class="sendP"><span class="sendCen">'+message+'</span><span class="sendIcon">▶乔</span></p>';
	$("#content").append(send);
	//清楚输入框
	$("#message").textbox("setValue","");
	//设置滚动条
	$("#content").scrollTop($("#content")[0].scrollHeight);
	$.ajax({
		url:'<%=request.getContextPath()%>/http/queryRobot.do',
		type:'post',
		data:{
			message:message
		},
		success:function(data){
			var revice = '<p><span class="reciveIcon">小美人贝贝◀</span><span class="reciveCen">'+data+'</span></p>'
			$("#content").append(revice);
			//设置滚动条高度
			$("#content").scrollTop($("#content")[0].scrollHeight);
		}
	})
}
</script>


</body>
</html>