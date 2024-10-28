$(function() {

$("#musicname").blur(
		function() {
			$("#musicname_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#musicname").after("<span id='musicname_msg' style='color: red'>音乐名称不能为空</span>");
			}
	});

$("#image").blur(
		function() {
			$("#image_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#image").after("<span id='image_msg' style='color: red'>音乐封面不能为空</span>");
			}
	});

$("#cateid").blur(
		function() {
			$("#cateid_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#cateid").after("<span id='cateid_msg' style='color: red'>音乐类型不能为空</span>");
			}
	});

$("#fileurl").blur(
		function() {
			$("#fileurl_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#fileurl").after("<span id='fileurl_msg' style='color: red'>音乐地址不能为空</span>");
			}
	});

$("#singer").blur(
		function() {
			$("#singer_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#singer").after("<span id='singer_msg' style='color: red'>歌手不能为空</span>");
			}
	});

$("#album").blur(
		function() {
			$("#album_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#album").after("<span id='album_msg' style='color: red'>专辑不能为空</span>");
			}
	});

$("#contents").blur(
		function() {
			$("#contents_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#contents").after("<span id='contents_msg' style='color: red'>音乐详情不能为空</span>");
			}
	});







$('#sub').click(function(){
var musicname = $("#musicname").val();
var image = $("#image").val();
var cateid = $("#cateid").val();
var fileurl = $("#fileurl").val();
var singer = $("#singer").val();
var album = $("#album").val();
var contents = $("#contents").val();
$("#musicname_msg").empty();
$("#image_msg").empty();
$("#cateid_msg").empty();
$("#fileurl_msg").empty();
$("#singer_msg").empty();
$("#album_msg").empty();
$("#contents_msg").empty();
if (musicname == "" || musicname == null) {
	$("#musicname").after("<span id='musicname_msg' style='color: red'>音乐名称不能为空</span>");
	return false;
}
if (image == "" || image == null) {
	$("#image").after("<span id='image_msg' style='color: red'>音乐封面不能为空</span>");
	return false;
}
if (cateid == "" || cateid == null) {
	$("#cateid").after("<span id='cateid_msg' style='color: red'>音乐类型不能为空</span>");
	return false;
}
if (fileurl == "" || fileurl == null) {
	$("#fileurl").after("<span id='fileurl_msg' style='color: red'>音乐地址不能为空</span>");
	return false;
}
if (singer == "" || singer == null) {
	$("#singer").after("<span id='singer_msg' style='color: red'>歌手不能为空</span>");
	return false;
}
if (album == "" || album == null) {
	$("#album").after("<span id='album_msg' style='color: red'>专辑不能为空</span>");
	return false;
}
if (contents == "" || contents == null) {
	$("#contents").after("<span id='contents_msg' style='color: red'>音乐详情不能为空</span>");
	return false;
}
});
$('#res').click(function() {
$("#musicname_msg").empty();
$("#image_msg").empty();
$("#cateid_msg").empty();
$("#fileurl_msg").empty();
$("#singer_msg").empty();
$("#album_msg").empty();
$("#contents_msg").empty();
});

});
