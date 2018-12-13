<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			toolbar : [
				{
					id : 'add',
					text : '添加权限',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_function_add.action';
					}
				},{
					id:'delete',
					text:'删除权限',
					iconCls : 'icon-cancel',
					handler : function(){
						//获取数据表格中所有选中的行，返回数组对象
						var rows = $("#grid").datagrid("getSelections");
						 if(rows.length==0){			
							//没有选择记录
							$.messager.alert("提示信息","你还没有选择要删除的该权限?","warning");
						}else{
							//选中了取派员,弹出确认框
							$.messager.confirm("删除确认","你 确定删除选中的权限吗？",function(r){
								if(r){
									var array= new Array();
									
									//确定删除					
					                for (var i = 0; i < rows.length; i++) {						
					                    var staff = rows[i];//json对象
					                    var id = staff.id;
					                    array.push(id);
									}
									var ids =array.join(",");
									//将数据发送给服务端
									location.href="${pageContext.request.contextPath}/FunctionAction_deleteBatch.action?ids="+ids;
								}
							});

						}
					}
				}          
			],
			url : 'FunctionAction_pageQuery.action',
			pagination : true,
			fit:true,
			columns : [[
			  {
				  field : 'id',
				  title : '编号',
				  width : 200,
				  checkbox:true
			  },
			  {
				  field : 'name',
				  title : '名称',
				  width : 200
			  },  
			  {
				  field : 'description',
				  title : '描述',
				  width : 200
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 150,
				  formatter : function(data,row, index){
						if(data=="1"){
							return "是";
						}else{
							return "否";
						}
					}
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 200
			  },  
			  {
				  field : 'page',
				  title : '路径',
				  width : 200
			  }
			]],
			onDblClickRow : doDblClickRow
		});
		
		// 修改取派员窗口
		$('#editFunctionWindow').window({
			title : '修改权限',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

	});
	
	//双击选中行,编辑
	function doDblClickRow(rowIndex, rowData) {
	    $('#editFunctionWindow').window("open");
		//回显
		$('#editFunctionWindow').form("load", rowData);
	}
</script>	
</head>
<body class="easyui-layout">
<div data-options="region:'center'">
	<table id="grid"></table>
	<!-- 修改权限 -->
	<div class="easyui-window" title="对权限进行修改" id="editFunctionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit-save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >确定修改</a>
				<script type="text/javascript">
				$(function(){
			    	//为保存按钮绑定事件
			    	$("#edit-save").click(function(){
			    		var v = $("#editFunctionFrom").form("validate");
			    	    if(v){
			    	    	$("#editFunctionFrom").submit();
			    	    }
			    	})
			    });
				</script>
				
			</div>
			
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editFunctionFrom" action="FunctionAction_editFunction.action" method="post">
			    <input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">功能权限信息</td>
					</tr>
					<tr>
						<td width="200">关键字</td>
						<td>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" />						
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>访问路径</td>
						<td><input type="text" name="page"  /></td>
					</tr>
					<tr>
						<td>是否生成菜单</td>
						<td>
							<select name="generatemenu" class="easyui-combobox">
								<option value="0">不生成</option>
								<option value="1">生成</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>优先级</td>
						<td>
							<input type="text" name="zindex" class="easyui-numberbox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>父功能点</td>
						<td>												 
						    <input class="easyui-combotree" name="parentFunction.id"
							 	data-options="url:'FunctionAction_ajaxList.action'" 
							 	style="width:170px;">
						
						</td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>