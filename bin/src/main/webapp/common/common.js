/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 * 
 * @author lanyuan 2014-12-12
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
;
(function() {
	ly = {};
	ly.ajax = (function(params) {
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '95%', '95%' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$.ajax(pp);
	});
	ly.ajaxSubmit = (function(form, params) {// form 表单ID. params ajax参数
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '95%', '95%' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$(form).ajaxSubmit(pp);
	});
	CommnUtil = {
		/**
		 * ajax同步请求 返回一个html内容 dataType=html. 默认为html格式 如果想返回json.
		 * CommnUtil.ajax(url, data,"json")
		 * 
		 */
		ajax : function(url, data, dataType) {
			if (!CommnUtil.notNull(dataType)) {
				dataType = "html";
			}
			var html = 'null';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date();
			} else {
				url = url + "?_t=" + new Date();
			}
			ly.ajax({
				type : "post",
				data : data,
				url : url,
				dataType : dataType,// 这里的dataType就是返回回来的数据格式了html,xml,json
				async : false,
				cache : false,// 设置是否缓存，默认设置成为true，当需要每次刷新都需要执行数据库操作的话，需要设置成为false
				success : function(data) {
					html = data;
				}
			});
			return html;
		},
		
		normalAjax : function (url, data, dataType){
			if (!CommnUtil.notNull(dataType)) {
				dataType = "html";
			}
			var html = 'null';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date();
			} else {
				url = url + "?_t=" + new Date();
			}
			$.ajax({
				type : "post", //使用get方法访问后台
			    dataType : dataType, //json格式的数据
			    async : false, //同步   不写的情况下 默认为true
			    url : rootPath+url,
			    data : data,//方式二：$("#formid").serialize();
			    success : function(data){
			    	html =  data;
				}
			});
			return html;
		},
		
		formAjax : function (url, formid, dataType){
			if (!CommnUtil.notNull(dataType)) {
				dataType = "html";
			}
			var html = 'null';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date();
			} else {
				url = url + "?_t=" + new Date();
			}
			$.ajax({
				type : "post", //使用get方法访问后台
			    dataType : dataType, //json格式的数据
			    async : false, //同步   不写的情况下 默认为true
			    url : rootPath+url,
			    data : $("#"+formid).serialize(),
			    success : function(data){
			    	html =  data;
				}
			});
			return html;
		},
		
		cleanInputValue : function(intputIds){
			if(""!=intputIds && "undefined"!=intputIds){
				var ids = intputIds.split(",");
				for(var i=0;i<ids.length;i++)
				{
					$("#"+ids[i]).val("");
				}
			}
		},
		
		getInputValue : function(intputIds){
			var rtnvalue = "";
			if(""!=intputIds && "undefined"!=intputIds){
				var ids = intputIds.split(",");
				for(var i=0;i<ids.length;i++)
				{
					alert(ids[i]);
					if(i==0){
						rtnvalue = rtnvalue+$("#"+ids[i]).val();
					}
					else
					{
					    rtnvalue += "@_@"+$("#"+ids[i]).val();
					}
				}
			}
			return rtnvalue;
		},
		UrlOfInputValue : function(intputIds){
			var rtnvalue = "";
			if(""!=intputIds && "undefined"!=intputIds){
				var ids = intputIds.split(",");
				for(var i=0;i<ids.length;i++)
				{
					if(i==0){
						rtnvalue = ids[i] + "=" + rtnvalue+$("#"+ids[i]).val();
					}
					else
					{
						rtnvalue += "&"+ids[i] + "=" + $("#"+ids[i]).val();
					}
				}
			}
			return rtnvalue;
		},
		
		isHaveSelectMoreCheckbox : function (checkboxname){
			return $("input[name='"+checkboxname+"']").length >= 1;
		},
		
		getAllCheckBoxesValue : function (checkboxname){
			var rtn = "";
			var arrChk=$("input[name='"+checkboxname+"']:checked");
		    $(arrChk).each(function(index){
		    	if(0==index){
		    		rtn = rtn + this.value;
		    	}
		    	else{
		    		rtn = rtn + "@_@" + this.value;                        
		    	}
		    }); 
		    return rtn;
		},
		
		isHaveSelectOneCheckbox : function (checkboxname){
			return $("input[name='"+checkboxname+"']:checked").length==1;
		},
		
		//先使用isHaveSelectOneCheckbox判断在使用此方法，即：只有一个复选框选中的前提下使用
		getCheckBoxValueOfSelect : function (checkboxname){
			var rtn = "";
			var arrChk=$("input[name='"+checkboxname+"']:checked");
		    $(arrChk).each(function(){
		    	rtn = this.value;                        
		    }); 
			return rtn;
		},
		
		disableOrEnabledInputValue : function (intputIds,flag){
			if(""!=intputIds && "undefined"!=intputIds){
				var ids = intputIds.split(",");
				for(var i=0;i<ids.length;i++)
				{
					$("#"+ids[i]).attr("disabled",flag);
				}
			}
		},
		
		haveOneTagIsNull : function(intputIds){
			if(""!=intputIds && "undefined"!=intputIds){
				var ids = intputIds.split(",");
				for(var i=0;i<ids.length;i++)
				{
					if($("#"+ids[i]).length > 0){
						var incontent = $("#"+ids[i]).val();
						if(((incontent === null)||(incontent === undefined)||(incontent === "undefined")||(incontent === ""))){
							$("#"+ids[i]).attr("style","border-color: red");
							return true;
						}else{
							$("#"+ids[i]).attr("style","");
						}
				    }
				}
			}
			return false
		},
		
		isnumber : function(obj){
			if(obj && ""!=obj && "undefined"!=obj){
				var items  = obj.split(",");
				for(var i=0;i<items.length;i++){
					var item = $("#"+items[i]).val();
					if(item != null && item != undefined && item != "undefined" && item != ""){
						if(!isNaN(item)){
							$("#"+items[i]).attr("style","");
						}else{
							$("#"+items[i]).attr("style","border-color: red");
							return false
						}
					}
				}
		   }
			return true;
		},
		
		/**
		 * 判断某对象不为空..返回true 否则 false
		 */
		notNull : function(obj) {
			if (obj === null) {
				return false;
			} else if (obj === undefined) {
				return false;
			} else if (obj === "undefined") {
				return false;
			} else if (obj === "") {
				return false;
			} else if (obj === "[]") {
				return false;
			} else if (obj === "{}") {
				return false;
			} else {
				return true;
			}

		},

		/**
		 * 判断某对象不为空..返回obj 否则 ""
		 */
		notEmpty : function(obj) {
			if (obj === null) {
				return "";
			} else if (obj === undefined) {
				return "";
			} else if (obj === "undefined") {
				return "";
			} else if (obj === "") {
				return "";
			} else if (obj === "[]") {
				return "";
			} else if (obj === "{}") {
				return "";
			} else {
				return obj;
			}

		},
		loadingImg : function() {
			var html = '<div class="alert alert-warning">'
					+ '<button type="button" class="close" data-dismiss="alert">'
					+ '<i class="ace-icon fa fa-times"></i></button><div style="text-align:center">'
					+ '<img src="' + rootPath + '/images/loading.gif"/><div>'
					+ '</div>';
			return html;
		},
		
		validatetime : function(intime){
			var rex = /^(\d{4})\-(\d{2})\-(\d{2})$/;
			if(CommnUtil.notNull(intime)&&rex.test(intime)){
				return true;
			}
			return false;
		},
		
		/**
		 * html标签转义
		 */
		htmlspecialchars : function(str) {
			var s = "";
			if (str.length == 0)
				return "";
			for (var i = 0; i < str.length; i++) {
				switch (str.substr(i, 1)) {
				case "<":
					s += "&lt;";
					break;
				case ">":
					s += "&gt;";
					break;
				case "&":
					s += "&amp;";
					break;
				case " ":
					if (str.substr(i + 1, 1) == " ") {
						s += " &nbsp;";
						i++;
					} else
						s += " ";
					break;
				case "\"":
					s += "&quot;";
					break;
				case "\n":
					s += "";
					break;
				default:
					s += str.substr(i, 1);
					break;
				}
			}
		}
	};
})();
// 表单json格式化方法……不使用&拼接
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()// millisecond
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
})(jQuery);