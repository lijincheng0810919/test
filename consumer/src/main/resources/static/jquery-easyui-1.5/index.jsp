<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery-easyui-1.5/themes/icon.css">
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:150,title:'金科教育'"></div>
		<div data-options="region:'west',width:200,title:'功能区',iconCls:'icon-large-shapes',split:true">
			<div class="easyui-accordion" data-options="fit:true,multiple:true">
				<div data-options="title:'菜单',iconCls:'icon-large-smartart'">
					<ul id="tbtree"></ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center',title:'详情'">
			<div class="easyui-tabs" id="myTabs" data-options="fit:true">
				<div class="easyui-tabs" data-options="fit:true,title:'首页'"></div>
			</div>
		</div>

		<div data-options="region:'east',width:200,title:'展示'"></div>
		<div data-options="region:'south',height:150,title:'说明'"></div>
	</div>
	<script type="text/javascript">
			<%-- url:"<%=request.getContextPath()%>/tree/findTree2.do?pid=0",
			onBeforeExpand:function(node){
				$("#tree").tree("options").url="<%=request.getContextPath()%>/tree/findTree2.do?pid="+node.id;
			}, --%>

		$("#tbtree").tree({
			url:"",
			onClick:function(node){
				if (node.url !=null && node.url !="") {
					var flag = $("#myTabs").tabs("exists",node.text);
					if (flag) {
						$("#myTabs").tabs("select",node.text);
					}else{
						$("#myTabs").tabs("add",{
						title:node.text,
						closable:true,
						content:createJsp(node.url),					    
						tools:[{    
					        iconCls:'icon-mini-refresh',    
					        handler:function(){    
					        	var tab = $('#myTabs').tabs('getSelected');
					        	$('#myTabs').tabs('update', {
					        		tab: tab,
					        		options: {
					        			content:createJsp(node.url)
					        		}
					        	});
					        }    
					    }]
						})
					}
				}
			}
		})
	function createJsp(url){  
	        return '<iframe scrolling="auto" frameborder="0"  src="<%=request.getContextPath() %>'+ url + '" style="width:100%;height:100%;"></iframe>';  
	} 
	
	
	</script>
	
	
	
</body>
</html>