<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

</head>
<body class="easyui-layout">
	<!-- 使用div元素描述每个区域 -->
	<div title="XXX管理系统" style="height: 100px" data-options="region:'north'">北部区域</div>
	
	<!-- 西部区域 -->
	<div title="系统菜单" style="width: 200px" data-options="region:'west'">
		<!-- 制作accordion折叠面板 
			fit:true----自适应(填充父容器)
		-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div data-options="iconCls:'icon-cut'" title="面板一">
			      <a id="addButton" class="easyui-linkbutton">添加一个选项卡</a>
			      <script type="text/javascript">
			          //页面加载事件完毕
			          $(function(){
			        	  $("#addButton").click(function(){
			        		  //判断该选项卡是否存在
			        		  var e = $("#mytabs").tabs("exists","系统管理");
			        		  if(e){
			        			  //已经存在
			        			  $("#mytabs").tabs("select","系统管理");
			        		  }else{
			        		      $("#mytabs").tabs("add",{
									  title:'系统管理',
									  iconCls:'icon-edit',
									  closable:true,
									  content:'<iframe frameborder="0" height="100%" width="100%" src="https://www.baidu.com"></iframe>'
								   });
			        		  }
			        	  });
			          });
			      </script>
			</div>
			<div title="面板二">
			    <!-- 展示ztree效果 :使用标准json数据构造ztree-->
			    <ul id="ztree1" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，执行这段代码----动态创建ztree
						var setting = {};
						//构造节点数据
						var zNodes = [
						              {"name":"节点一","children":[
						                                        	{"name":"节点一_1"},
						                                        	{"name":"节点一_2"}
						                                        ]},//每个json对象表示一个节点数据
						              {"name":"节点二"},
						              {"name":"节点三"}
						              ];
						//调用API初始化ztree
						$.fn.zTree.init($("#ztree1"), setting, zNodes);
					});
				</script>
			</div>
			<div title="面板三">
			    <!-- 展示ztree效果 :使用简单json数据构造ztree-->
			    <ul id="ztree2" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，执行这段代码----动态创建ztree
						var setting2 = {
								data: {
									simpleData: {
										enable: true//使用简单json数据构造ztree节点
									}
								}
						};
						//构造节点数据
						var zNodes2 = [
						              {"id":"1","pId":"o","name":"节点一"},
						              {"id":"2","pId":"1","name":"节点一"},
						              {"id":"3","pId":"2","name":"节点一"}
						              
						              ];
						//调用API初始化ztree
						$.fn.zTree.init($("#ztree2"), setting2, zNodes2);
					});
				</script>
			</div>
			
			<div title="面板4">
			    <!-- 展示ztree效果 :发送ajax请求获取简单json数据构造ztree-->
				<ul id="ztree3" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，执行这段代码----动态创建ztree
						var setting3 = {
								data: {
									simpleData: {
										enable: true//使用简单json数据构造ztree节点
									}
								}/* ,
								callback: {
									//为ztree节点绑定单击事件
									onClick: function(event, treeId, treeNode){
										if(treeNode.page != undefined){
											//判断选项卡是否已经存在
											var e = $("#mytabs").tabs("exists",treeNode.name);
											if(e){
												//已经存在，选中
												$("#mytabs").tabs("select",treeNode.name);
											}else{
												//动态添加一个选项卡
												$("#mytabs").tabs("add",{
													title:treeNode.name,
													closable:true,
													content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
												});
											}
										}
									}
								} */
						};
						
						//发送ajax请求，获取json数据
						//jQuery提供 的ajax方法：ajax、post、get、load、getJSON、getScript
						var url = "${pageContext.request.contextPath}/json/menu.json";
						$.post(url,{},function(data){
							//调用API初始化ztree
							$.fn.zTree.init($("#ztree3"), setting3, data);
						},'json');
					});
				</script>
			</div>
		</div>
	</div>
	
	
	<!-- 中部区域 -->
	<div data-options="region:'center'">
		<!-- 制作一个tabs选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div data-options="iconCls:'icon-cut'" title="面板一">1111</div>
			<div data-options="closable:true" title="面板二">2222</div>
			<div title="面板三">3333</div>
		</div>
	</div>
	<div style="width: 100px" data-options="region:'east'">
	   <select id="cc" class="easyui-combotree" style="width:200px;" data-options="url:'get_data.php',required:true"></select>
	   <input id="cc" value="01"> 
<script type="text/javascript">
  $(function(){
	  $('#cc').combotree({ 

			url: '${pageContext.request.contextPath}/json/xxxxxxx.json', 

			required: true 

			}); 
  })

</script>



	   
	</div>
	<div style="height: 50px" data-options="region:'south'">南部区域</div>
</body>
</html>