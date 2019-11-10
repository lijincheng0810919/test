<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
</head>
<body>
	<form id="myForm">
	  <table>
		  <tr>
			<td>用户名</td>
			<td>
				<input type="text" name="userName">
			</td>
		  </tr>
		  <tr>
			<td>密码</td>
			<td>
				<input type="text" name="userPwd">
			</td>
		  </tr>
		  <tr>
			<td>验证码</td>
			<td>
				<input type="text" name="imgcode">
				<img src="<%=request.getContextPath()%>/user/imgCode.do" id="imgCode">
			</td>
		  </tr>
		  <tr>
			<td><input type="button" value="注册"></td>
			<td><input type="button" value="登录" id="loginBtn"></td>
		  </tr>
	  </table>	
	</form>
</body>
<script type="text/javascript">
	$("#imgCode").click(function(){
		$(this).attr("src","<%=request.getContextPath()%>/user/imgCode.do?t="+new Date().getTime());
	})
	//登录
	$("#loginBtn").click(function(){
		$.ajax({
			url:"<%=request.getContextPath()%>/user/login.do",
			type:"post",
			data:$("#myForm").serialize(),
			success:function(data){
				alert(data);
				//登录成功跳转页面
				if(data=="登录成功"){
					location.href="<%=request.getContextPath()%>/user/toUserList.do";
				}
			},
			error:function(){
				alert("请求失败");
			}
		})
	})
</script>
</html>