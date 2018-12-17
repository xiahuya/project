<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
	// 工具栏
	var toolbar = [ 
    <shiro:hasPermission name="staff-assssadadad">                
	{
		id : 'button-view',	
		text : '查看',
		iconCls : 'icon-search',
		handler : doView
	}, 
	</shiro:hasPermission>
	{
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	},
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}
	];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
		rowspan : 2
	}, {
		field : 'username',
		title : '名称',
		width : 80,
		rowspan : 2
	} ] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'gender',
		title : '性别',
		width : 60,
		rowspan : 2,
		align : 'center'
	}, {
		field : 'birthdayString',
		title : '生日',
		width : 120,
		rowspan : 2,
		align : 'center'
	}, {
		title : '其他信息',
		colspan : 2
	}, {
		field : 'roleNames',
		title : '对应角色',
		width : 800,
		rowspan : 2
	} ], [ {
		field : 'station',
		title : '单位',
		width : 80,
		align : 'center'
	}, {
		field : 'salary',
		title : '工资',
		width : 80,
		align : 'right'
	} ] ];
	$(function(){
		// 修改取派员窗口
		$('#editUserWindow').window({
			title : '修改用户',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 600,
			resizable : false
		});
		
		
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "UserAction_pageQuery.action",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		$("body").css({visibility:"visible"});
		
	});
	// 双击
	function doDblClickRow(rowIndex, rowData) {
		$('#editUserWindow').window("open");
		//回显
		$('#editUserWindow').form("load", rowData);
	}
	
	function doAdd() {
		location.href="${pageContext.request.contextPath}/page_admin_userinfo.action";
	}

	function doView() {
		alert("编辑用户");
		var item = $('#grid').datagrid('getSelected');
		console.info(item);
	}

	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		 if(rows.length==0){			
			//没有选择记录
			$.messager.alert("提示信息","你还没有选择要删除的User","warning");
		}else{
			//选中了取派员,弹出确认框
			$.messager.confirm("删除确认","你 确定删除选中的User吗？",function(r){
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
					location.href="${pageContext.request.contextPath}/UserAction_deleteBatch.action?ids="+ids;
				}
			});

		}
	}
	
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>

	<!-- 修改用户 -->
	<div class="easyui-window" title="对用户进行修改" id="editUserWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;"
			split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="edit-save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				<script type="text/javascript">
			        $(function(){
			        	$("#edit-save").click(function(){							    		
			        	    var v = $("#editUserFrom").form("validate");
			        	    if(v){
				    		   $("#editUserFrom").submit();
			        	    }
			        	})						    		
			        })
			    </script>
			</div>
		</div>

		<div region="center" style="overflow: auto; padding: 5px;"
			border="false">
			<form id="editUserFrom" action="UserAction_editUser.action"
				method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="4">基本信息</td>
					</tr>
					<tr>
						<td>用户名:</td>
						<td><input type="text" name="username" id="username"
							class="easyui-validatebox" required="true" /></td>
						<td>口令:</td>
						<td><input type="password" name="password" id="password"
							class="easyui-validatebox" required="true"
							validType="minLength[5]"/></td>
					</tr>
					<tr class="title">
						<td colspan="4">其他信息</td>
					</tr>
					<tr>
						<td>工资:</td>
						<td><input type="text" name="salary" id="salary"
							class="easyui-numberbox" /></td>
						<td>生日:</td>
						<td><input type="text" name="birthday" id="birthday"
							class="easyui-datebox" /></td>
					</tr>
					<tr>
						<td>性别:</td>
						<td><select name="gender" id="gender" class="easyui-combobox"
							style="width: 150px;">
								<option value="">请选择</option>
								<option value="男">男</option>
								<option value="女">女</option>
						</select></td>
						<td>单位:</td>
						<td><select name="station" id="station"
							class="easyui-combobox" style="width: 150px;">
								<option value="">请选择</option>
								<option value="总公司">总公司</option>
								<option value="分公司">分公司</option>
								<option value="厅点">厅点</option>
								<option value="基地运转中心">基地运转中心</option>
								<option value="营业所">营业所</option>
						</select></td>
					</tr>
					<tr>
						<td>联系电话</td>
						<script type="text/javascript">					     						    	 							    	 
						//校验手机号码的正则表达格式
						var res = /^1[3|4|5|7|8][0-9]{9}$/;

						//添加一个手机号码校验
						$.extend($.fn.validatebox.defaults.rules, {
							telephone : {
								validator : function(value, param) {
									return res.test(value);
								},
								message : '手机号码输入有误'
							}
						});
					</script>
						<td colspan="3"><input type="text" validType="telephone"
							name="telephone" id="telephone" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>备注:</td>
						<td colspan="3"><textarea style="width: 80%"></textarea></td>
					</tr>
					<tr>
						<td>角色:</td>
						<td colspan="3" id="roleTds"><script type="text/javascript">
	           	           $(function(){
	           	        	   //加载全部角色对象
	           	        	   $.post("RoleAction_ajaxList.action",function(data){
	           	        		   for(var i = 0 ;i<data.length ; i++){
	           	        			   var id = data[i].id;
	           	        			   var name = data[i].name;	           	        			   
	           	        			   $("#roleTds").append('<input id="'+id+'" type="checkbox" name="roleIds" value="'+id+'"><label for="'+id+'">'+name+'</label>');
	           	        		   }
	           	        	   });
	           	           })	           	        
	           	       </script></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>