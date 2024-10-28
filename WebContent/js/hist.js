$(function() {

$("#usersid").blur(
		function() {
			$("#usersid_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#usersid").after("<span id='usersid_msg' style='color: red'>用户不能为空</span>");
			}
	});

$("#musicid").blur(
		function() {
			$("#musicid_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#musicid").after("<span id='musicid_msg' style='color: red'>音乐不能为空</span>");
			}
	});

$("#num").blur(
		function() {
			$("#num_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#num").after("<span id='num_msg' style='color: red'>次数不能为空</span>");
			}
	});







$('#sub').click(function(){
var usersid = $("#usersid").val();
var musicid = $("#musicid").val();
var num = $("#num").val();
$("#usersid_msg").empty();
$("#musicid_msg").empty();
$("#num_msg").empty();
if (usersid == "" || usersid == null) {
	$("#usersid").after("<span id='usersid_msg' style='color: red'>用户不能为空</span>");
	return false;
}
if (musicid == "" || musicid == null) {
	$("#musicid").after("<span id='musicid_msg' style='color: red'>音乐不能为空</span>");
	return false;
}
if (num == "" || num == null) {
	$("#num").after("<span id='num_msg' style='color: red'>次数不能为空</span>");
	return false;
}
});
$('#res').click(function() {
$("#usersid_msg").empty();
$("#musicid_msg").empty();
$("#num_msg").empty();
});

});
