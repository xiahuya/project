<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
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
<script src="${pageContext.request.contextPath }/js/highcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath }/js/highcharts/modules/exporting.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js" type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		$('#addSubareaWindow').window("open");
	}
	
	
	function doDelete(){
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		 if(rows.length==0){			
			//没有选择记录
			$.messager.alert("提示信息","你还没有选择要删除的分区","warning");
		}else{
			//选中了取派员,弹出确认框
			$.messager.confirm("删除确认","你 确定删除选中的分区吗？",function(r){
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
					location.href="${pageContext.request.contextPath}/SubareaAction_deleteBatch.action?ids="+ids;
				}
			});

		}
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	function doExport(){
		//向服务端发送请求
		window.location.href="${pageContext.request.contextPath }/SubareaAction_subareaExport.action";
	}
	
	function doImport(){
		//导入按钮添加事件
		$("#button-import").upload({
			    action:"${pageContext.request.contextPath}/SubareaAction_importFile.action",
			    name:"subareaFile"
		});
	}
		
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
		handler : doImport
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}
	,{
		id : 'button-showHighcharts',
		text : '显示区域分区分布图',
		iconCls : 'icon-search',
		handler : doShowHighcharts
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'showid',
		title : '分拣编号',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.id;
		}
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.province;
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.city;
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.district;
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center'
	}, {
		field : 'startnum',
		title : '起始号',
		width : 120,
		align : 'center'
	}, {
		field : 'endnum',
		title : '终止号',
		width : 120,
		align : 'center'
	} , {
		field : 'single',
		title : '单双号',
		width : 120,
		align : 'center'
	} , {
		field : 'position',
		title : '位置',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [3],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath }/SubareaAction_getSubareaList.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加分区
		$('#addSubareaWindow').window({
	        title: '添加修改分区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		//修改分区
		$('#editSubareaWindow').window({
	        title: '添加修改分区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		
		
		// 区域分区显示图
		$('#showSubareaWindow').window({	        
	        width: 700,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 500,
	        resizable:false
	    });
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		$("#btn").click(function(){
			//将指定的form表单中所有的输入项转为json数据{key:value,key:value}
        	var p = $("#searchForm").serializeJson();
        	////调用数据表格的load方法，重新发送一次ajax请求，并且提交参数
        	$("#grid").datagrid("load",p);
        	//关闭查询窗口
			$("#searchWindow").window("close");
		});
				
		
		//添加区域信息
		$("#save").click(function(){
			//校验表单
			var v = $("#addSubareaForm").form("validate");
			if(v){
				//校验成功,提交表单
				$("#addSubareaForm").submit();
				
			}
		});
		
		//更新
		//添加区域信息
		$("#edit").click(function(){
			//校验表单
			var v = $("#editSubareaForm").form("validate");
			if(v){
				//校验成功,提交表单
				$("#editSubareaForm").submit();
				
			}
		});
		
		
		//定义一个工具方法，用于将指定的form表单中所有的输入项转为json数据{key:value,key:value}
		$.fn.serializeJson=function(){  
            var serializeObj={};  
            var array=this.serializeArray();
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });  
            return serializeObj;  
        }; 
			
	});
	
	//显示区域分区显示图
	function doShowHighcharts(){
		$('#showSubareaWindow').window("open");
		$.post("SubareaAction_findSubareaGroupByProvince.action",function(data){
			//页面加载完成后，动态创建图表
			$("#test").highcharts({
				title: {
		            text: '各地区份额'
		        },
		        series: [{
		            type: 'pie',
		            name: '地区占比',
		            data: data
		            
		        }]
			});
		});
		
	}

	function doDblClickRow(rowIndex, rowData){
		$('#editSubareaWindow').window("open");
		//回显
		$('#editSubareaWindow').form("load", rowData);
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<!-- 添加分区 -->
	<div class="easyui-window" title="分区添加修改" id="addSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form method="post" id="addSubareaForm" action="${pageContext.request.contextPath }/SubareaAction_save.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="region.id"  
    							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/RegionAction_getAjaxList',mode:'remote'" style="width:180px;" />  
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" name="startnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" name="endnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" name="single" style="width:150px;">  
							    <option value="0">单双号</option>  
							    <option value="1">单号</option>  
							    <option value="2">双号</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 修改分区 -->
	<div class="easyui-window" title="分区添加修改" id="editSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form method="post" id="editSubareaForm" action="${pageContext.request.contextPath }/SubareaAction_edit.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="region.id"  
    							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/RegionAction_getAjaxList',mode:'remote'" style="width:180px;" />  
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" name="startnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" name="endnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" name="single" style="width:150px;">  
							    <option value="0">单双号</option>  
							    <option value="1">单号</option>  
							    <option value="2">双号</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="region.province"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="region.city"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="region.district"/></td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey"/></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	
	
	<!--显示区域分区管理 -->
	<div class="easyui-window" title="分区添加修改" id="showSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div id="test" split="false" border="false" >
		</div>				
	</div>
</body>
</html>