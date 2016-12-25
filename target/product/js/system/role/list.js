
$(function() {
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数

	});
	$("#addcargo").click("click", function() {
		addRole();
	});
	$("#editcargo").click("click", function() {
		editRole();
	});
	$("#deletecargo").click("click", function() {
		delRole();
	});
});
function editcargo() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "60%" ],
		content : rootPath + '/role/editUI.shtml?id=' + cbox
	});
}
function addcargo() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "60%" ],
		content : rootPath + '/role/addUI.shtml'
	});
}
function deletecargo() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/role/deleteEntity.shtml';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "json");
		if (s == "success") {
			layer.msg('删除成功');
			grid.loadData();
		} else {
			layer.msg('删除失败');
		}
	});
}
