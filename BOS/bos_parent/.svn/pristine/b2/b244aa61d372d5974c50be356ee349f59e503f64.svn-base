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
    <script type="text/javascript">
    var myIndex = -1;//全局变量，值为正在编辑行的索引
    
         $(function(){
        	$("#myTables").datagrid({
        		//定义标题行所有的列
        		columns:[[
                           {width:100,title:'编号',field:'id',checkbox:true},  
                           {width:100,title:'姓名',field:'name',editor:{
                        	                                type:"validatebox",//numberbox,validatebox,datebox 
                        	                                options:{}
                           }},  
                           {width:100,title:'年龄',field:'age',editor:{
                        	                               type:"numberbox",
                        	                               options:{}
                           }},  
                           {width:150,title:'日期',field:'address',editor:{
								                           type:'datebox',
 								                           options:{}
						   }}
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
        		    	  $("#myTables").datagrid("insertRow",{index:0,//在第一行插入数据
        		    		                                   row:{}//空行
			        		 });
			        	  $("#myTables").datagrid("beginEdit",0);
			        		                            
        		      }},
        		      
        		      {text:'修改',iconCls:'icon-edit',handler:function(){
        		    	  //获取当前选择的行
        		    	  var rows = $("#myTables").datagrid("getSelections");
        		    	  if(rows.length==1){
        		    		  var row = rows[0];
        		    		 //获得指定行对象的索引
        		    		  myIndex = $("#myTables").datagrid("getRowIndex",row);
        		    	  }
        		    	  
        		    	  
        		    	  $("#myTables").datagrid("beginEdit",myIndex);                      
        		      }},
        		              		      
        		      {text:'删除',iconCls:'icon-remove',handler:function(){
        		    	  var rows = $("#myTables").datagrid("getSelections");
        		    	  if(rows.length==1){
        		    		  var row = rows[0];
        		    		 //获得指定行对象的索引
        		    		  myIndex = $("#myTables").datagrid("getRowIndex",row);
        		    	  }
        		    	  $("#myTables").datagrid("deleteRow",myIndex);
        		      }},
        		      {text:'保存',iconCls:'icon-save',handler:function(){
				          $("#myTables").datagrid("endEdit",myIndex);
				      }}       		      
        		      ],
        		 //显示分页条
      			pagination:true,
      			pageList:[3,5,7,10],
        	//数据表格提供的用于监听结束编辑事件
			onAfterEdit:function(index,data,changes){
				console.info(data);
				$.post();
			}
        	}); 
         });
    </script>
    
    <table id="myTables"></table>
</body>
</html>