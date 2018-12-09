<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

</head>
<body>
    <!-- 方式一：将静态HTML渲染为datagrid样式 -->
    <table class="easyui-datagrid">
        <thead>
            <tr>
                <th data-options="field:'id'">编号</th>
                <th data-options="field:'name'">姓名</th>
                <th data-options="field:'age'">年龄</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>001</td>
                <td>小黄</td>
                <td>20</td>
            </tr>
            <tr>
                <td>002</td>
                <td>大黑</td>
                <td>80</td>
            </tr>
            <tr>
                <td>003</td>
                <td>阿龙</td>
                <td>30</td>
            </tr>
        </tbody>
    </table>
    
    <!-- 方式二：发送ajax请求获取json数据创建datagrid -->
    <table data-options="url:'${pageContext.request.contextPath }/json/datagrid.json'" class="easyui-datagrid">
        <thead>
            <tr>
                <th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
            </tr>
        </thead>
    </table>
    
    
    <!-- 方式三：使用easyUI提供的API创建datagrid -->
    <table id="myTables"></table>
    <script type="text/javascript">
         $(function(){
        	$("#myTables").datagrid({
        		//定义标题行所有的列
        		columns:[[
                           {title:'编号',field:'id',checkbox:true},  
                           {title:'姓名',field:'name'},  
                           {title:'年龄',field:'age'},  
        		        ]],
        		//指定数据表格发送ajax请求的地址
        		url:'${pageContext.request.contextPath }/json/datagrid.json',
        		//列数
        		rownumbers:true,
        		//值允许选择一行
        		singleSelect:true,
        		//工具栏
        		toolbar:[
        		      {text:'添加',iconCls:'icon-add',handler:function(){//为按钮添加单击事件
        		    	                                         alert('添加');
        		      }},
        		      {text:'修改',iconCls:'icon-edit'},
        		      {text:'查找',iconCls:'icon-search'},
        		      {text:'删除',iconCls:'icon-remove'}
        		      
        		      ],
        		 //显示分页条
      			pagination:true,
      			pageList:[3,5,7,10]
        	}); 
         });
    </script>
</body>
</html>