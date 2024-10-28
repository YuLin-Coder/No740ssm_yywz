<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<title>${title }</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.SuperSlide.min.js"></script>
<style type="text/css">
/* css 重置 */
* {
	margin: 0;
	padding: 0;
	list-style: none
}

body {
	background: #fff;
	font: normal 12px/22px 宋体
}

img {
	border: 0
}

a {
	text-decoration: none;
	color: #333
}
/* 本例子css */
.picScroll-left {
	overflow: hidden;
	position: relative;
	border: 1px solid #ccc
}

.picScroll-left .hd {
	overflow: hidden;
	height: 30px;
	background: #f4f4f4;
	padding: 0 10px
}

.picScroll-left .hd .prev, .picScroll-left .hd .next {
	display: block;
	width: 5px;
	height: 9px;
	float: right;
	margin-right: 5px;
	margin-top: 10px;
	overflow: hidden;
	cursor: pointer;
	background: url("themes/ecmoban_dangdang/images/arrow.png") 0 0
		no-repeat
}

.picScroll-left .hd .next {
	background-position: 0 -50px
}

.picScroll-left .hd .prevStop {
	background-position: -60px 0
}

.picScroll-left .hd .nextStop {
	background-position: -60px -50px
}

.picScroll-left .hd ul {
	float: right;
	overflow: hidden;
	zoom: 1;
	margin-top: 10px;
	zoom: 1
}

.picScroll-left .hd ul li {
	float: left;
	width: 9px;
	height: 9px;
	overflow: hidden;
	margin-right: 5px;
	text-indent: -999px;
	cursor: pointer;
	background: url("themes/ecmoban_dangdang/images/icoCircle.gif") 0 -9px
		no-repeat
}

.picScroll-left .hd ul li.on {
	background-position: 0 0
}

.picScroll-left .bd {
	padding: 10px
}

.picScroll-left .bd ul {
	overflow: hidden;
	zoom: 1
}

.picScroll-left .bd ul li {
	margin: 0 8px;
	float: left;
	_display: inline;
	overflow: hidden;
	text-align: center
}

.picScroll-left .bd ul li .pic {
	width: 230px;
	height: 299px;
	text-align: center
}

.picScroll-left .bd ul li .pic img {
	width: 230px;
	height: 299px;
	display: block;
	padding: 2px;
	border: 1px solid #ccc
}

.picScroll-left .bd ul li .pic a:hover img {
	border-color: #999
}

.picScroll-left .bd ul li .title {
	line-height: 24px
}
</style>
</head>

<body>
	<div class="picScroll-left">
		<div class="hd">
			<a class="prev" href="javascript:void(0)"><img src="themes/ecmoban_dangdang/images/left.jpg" /></a> <a class="next"
				href="javascript:void(0)"><img src="themes/ecmoban_dangdang/images/right.jpg" /></a>
		</div>
		<div class="bd">
			<ul class="picList">
				<c:forEach items="${hotList}" var="goods">
					<li>
						<div class="pic">
							<a href="index/detail.action?id=${goods.goodsid }"><img src="${goods.image }" /></a>
						</div>
						<div class="title">
							<a href="index/detail.action?id=${goods.goodsid }">${fn:substring(goods.goodsname, 0, 8)}</a>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(".picScroll-left").slide({
			titCell : ".hd ul",
			mainCell : ".bd ul",
			autoPage : true,
			effect : "left",
			autoPlay : true,
			vis : 3,
			trigger : "click"
		});
	</script>

</body>
</html>
