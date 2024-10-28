<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html lang="zh_CN">
<head>
<base href="<%=basePath%>" />
<title>${title }</title>
<style type="text/css">
/*实现背景图片透明，内容不透明*/
#music {
	width: 500px;
	height: 500px;
	border-radius: 10px;
	margin: 20px auto;
	position: relative;
	background: url(${music.image}) no-repeat;
	background-size: cover;
	text-align: center;
}

#container {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	width: 500px;
	height: 500px;
	text-align: center;
	background: rgba(255, 255, 255, 0.6);
}
/*实现背景图片透明，内容不透明*/
#musicImg {
	width: 280px;
	height: 280px;
	border-radius: 50%;
}

audio {
	display: block;
	margin: 20px auto;
}

#musicName {
	padding-top: 10px;
	line-height: 30px;
	color: #cc1b1b;
}

.btn>button {
	width: 60px;
	height: 30px;
	border-radius: 10px;
	background: skyblue;
}
</style>

</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="music" style="background-image: url(${music.musicname});">
		<div id="container">
			<h3 id="musicName">${music.musicname}</h3>
			<img src="${music.image}" id="musicImg">
			<audio src="${music.fileurl}" controls="" id="audio"></audio>
			<div class="btn">
				<button id="play">play</button>
				<button id="pause">pause</button>
			</div>
		</div>
	</div>
	<script>
		var play = document.getElementById('play'), pause = document
				.getElementById('pause') ;

		// 播放
		play.onclick = function() {
			if (audio.paused) {
				audio.play();
			}
		}

		// 暂停
		pause.onclick = function() {
			if (audio.played) {
				audio.pause();
			}
		}
	</script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
