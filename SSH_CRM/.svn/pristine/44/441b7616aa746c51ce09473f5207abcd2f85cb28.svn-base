//参数一:type_Code----->数据字典类型 (dict_type_code)
     //参数二:positionId---->将下拉选项框放入的标签id
     //参数三:selectName---->生成下拉选项框时,select元素的name属性
     //参数四:selectedId---->需要回显时,选中哪个option
     function loadSelect(type_Code,positionId,selectName,selectedId){
    	//1.创建select对象,制定name属性   	
	    var $select = $("<select name="+selectName+"></select>");
		//2.添加提示选项	
		$select.append($("<option value=''>----请选择----</option>"));
		//3.使用Ajax,访问后台
		/*$.post(
			"${pageContext.request.contextPath}/BaseDictAction", //要访问的URL
			{"dict_type_code":type_Code},//需要传递过去的参数
			function(data){
				//4.返回json数组,并对其进行遍历
				$.each(data, function(i, json){
					//每次遍历创建一个option对象										
					var $option = $("<option value='"+json['dict_id']+"'>"+json['dict_item_name']+"</option>");
					//判断是否要回显
					if(json['dict_id']==selectedId){
						$option.attr("selected","selected");
					}
					//并将其添加到$select
					$select.append($option);
					  
				});
            },
            "json");*/
		$.ajax({
			url:"${pageContext.request.contextPath}/BaseDictAction",
			async:false,
			type:"POST",
			data:{"dict_type_code":type_Code},
			success:function(data){
				$.each(data, function(i, json){
					//每次遍历创建一个option对象										
					var $option = $("<option value='"+json['dict_id']+"'>"+json['dict_item_name']+"</option>");
					//判断是否要回显
					if(json['dict_id']==selectedId){
						$option.attr("selected","selected");
					}
					//并将其添加到$select
					$select.append($option);
					  
				});
			},
			dataType:"json"
		});
		
		//5.将组装好的select对象放入页面指定位置
		$("#"+positionId).append($select);

	}

