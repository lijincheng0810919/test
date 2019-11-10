<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../jquery-easyui-1.5/jquery.min.js"></script>
<script src="../jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script src="../jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="../jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" href="../jquery-easyui-1.5/themes/icon.css">
<title>Insert title here</title>
</head>
<body>
	<div class="easyui-dialog" data-options="title:'登录',modal:true,buttons:[{
		text:'登录',
		iconCls:'icon-man',
		plain:true,
		handler:function(){
			login();
		}
	}]" style="width: 300px;height: 200px">
		<form id="loginForm" method="post">
			<table>
				<tr>
					<td>手机号：</td>
					<td> <input type="text" class="easyui-textbox" id="account" name="account"/> </td>
				</tr>
				<tr>
					<td>验证码：</td>
					<td> 
						<input type="text" size="5" class="easyui-textbox" name="checkCode"/>
						 <input type="button" value="获取验证码" onclick="sendSmsCode(this);"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
var wait=60;
function sendSmsCode(obj){
	var account = $("#account").textbox('getValue');
	var obj = $(obj);
    obj.attr("disabled","disabled");
    var time = 60;
    var set=setInterval(function(){
    obj.val(--time);
    }, 1000);
    setTimeout(function(){
    obj.attr("disabled",false).val("获取验证码");
    clearInterval(set);
    }, 60000);
    
	$.post('../book/sendSms.do',{account:account},function(data){
		if (data.code != 0) {
			$.messager.alert('提示',data.msg,'error')
		}else{
			$.messager.alert('提示','获取成功，请注意查收','info')
		}
	})
}
function login(){
		$("#loginForm").form('submit',{
			url:'../book/kjlogin.do',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 0) {
					location.href="../page/touserList.do";
				}else{
					$.messager.alert('提示',data.msg,'error');
				}
			}
		})
}
</script>
</html>