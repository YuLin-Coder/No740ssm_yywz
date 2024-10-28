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
.slider {
	position: absolute;
	width: 100%;
	height: 425px;
}

.slider li, .slider li a {
	list-style: none;
	float: left;
	width: 100%;
	height: 425px;
}

.slider img {
	width: 100%;
	height: 425px;
	display: block;
}

.slider2 {
	width: 2000px;
}

.slider2 li {
	float: left;
}

.num {
	position: absolute;
	right: 400px;
	bottom: 20px;
}

.num li {
	float: left;
	color: #669934;
	text-align: center;
	line-height: 16px;
	width: 16px;
	height: 16px;
	font-family: Arial;
	font-size: 12px;
	cursor: pointer;
	overflow: hidden;
	margin: 3px 1px;
	border: 1px solid #669934;
	background-color: #fff;
}

.num li.on {
	color: #fff;
	line-height: 21px;
	width: 21px;
	height: 21px;
	font-size: 16px;
	margin: 0 1px;
	border: 0;
	background-color: #669934;
	font-weight: bold;
}
</style>
<script type="text/javascript">
	var $s = function(id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	};
	var Class = {
		create : function() {
			return function() {
				this.initialize.apply(this, arguments);
			}
		}
	}
	Object.extend = function(destination, source) {
		for ( var property in source) {
			destination[property] = source[property];
		}
		return destination;
	}
	var TransformView = Class.create();
	TransformView.prototype = {
		//容器对象,滑动对象,切换参数,切换数量
		initialize : function(container, slider, parameter, count, options) {
			if (parameter <= 0 || count <= 0)
				return;
			var oContainer = $s(container), oSlider = $s(slider), oThis = this;
			this.Index = 0;//当前索引

			this._timer = null;//定时器
			this._slider = oSlider;//滑动对象
			this._parameter = parameter;//切换参数
			this._count = count || 0;//切换数量
			this._target = 0;//目标参数

			this.SetOptions(options);

			this.Up = !!this.options.Up;
			this.Step = Math.abs(this.options.Step);
			this.Time = Math.abs(this.options.Time);
			this.Auto = !!this.options.Auto;
			this.Pause = Math.abs(this.options.Pause);
			this.onStart = this.options.onStart;
			this.onFinish = this.options.onFinish;

			oContainer.style.overflow = "hidden";
			oContainer.style.position = "relative";

			oSlider.style.position = "absolute";
			oSlider.style.top = oSlider.style.left = 0;
		},
		//设置默认属性
		SetOptions : function(options) {
			this.options = {//默认值
				Up : true,//是否向上(否则向左)
				Step : 5,//滑动变化率
				Time : 10,//滑动延时
				Auto : true,//是否自动转换
				Pause : 2000,//停顿时间(Auto为true时有效)
				onStart : function() {
				},//开始转换时执行
				onFinish : function() {
				}//完成转换时执行
			};
			Object.extend(this.options, options || {});
		},
		//开始切换设置
		Start : function() {
			if (this.Index < 0) {
				this.Index = this._count - 1;
			} else if (this.Index >= this._count) {
				this.Index = 0;
			}

			this._target = -1 * this._parameter * this.Index;
			this.onStart();
			this.Move();
		},
		//移动
		Move : function() {
			clearTimeout(this._timer);
			var oThis = this, style = this.Up ? "top" : "left", iNow = parseInt(this._slider.style[style]) || 0, iStep = this
					.GetStep(this._target, iNow);

			if (iStep != 0) {
				this._slider.style[style] = (iNow + iStep) + "px";
				this._timer = setTimeout(function() {
					oThis.Move();
				}, this.Time);
			} else {
				this._slider.style[style] = this._target + "px";
				this.onFinish();
				if (this.Auto) {
					this._timer = setTimeout(function() {
						oThis.Index++;
						oThis.Start();
					}, this.Pause);
				}
			}
		},
		//获取步长
		GetStep : function(iTarget, iNow) {
			var iStep = (iTarget - iNow) / this.Step;
			if (iStep == 0)
				return 0;
			if (Math.abs(iStep) < 1)
				return (iStep > 0 ? 1 : -1);
			return iStep;
		},
		//停止
		Stop : function(iTarget, iNow) {
			clearTimeout(this._timer);
			this._slider.style[this.Up ? "top" : "left"] = this._target + "px";
		}
	};
	window.onload = function() {
		function Each(list, fun) {
			for (var i = 0, len = list.length; i < len; i++) {
				fun(list[i], i);
			}
		}
		;

		var objs = $s("idNum").getElementsByTagName("li");
		var obj_len = objs.length;
		var tv = new TransformView("idTransformView", "idSlider", 425, obj_len,
				{
					onStart : function() {
						Each(objs, function(o, i) {
							o.className = tv.Index == i ? "on" : "";
						})
					}//按钮样式
				});

		tv.Start();

		Each(objs, function(o, i) {
			o.onmouseover = function() {
				o.className = "on";
				tv.Auto = false;
				tv.Index = i;
				tv.Start();
			}
			o.onmouseout = function() {
				o.className = "";
				tv.Auto = true;
				tv.Start();
			}
		})

	}
</script>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>


	<div style="clear: both"></div>

	<div id="idTransformView" style="height: 425px; overflow: hidden; position: relative;">
		<ul class="slider" id="idSlider">
			<li><img src="pic/001.jpg" /></li>
			<li><img src="pic/002.jpg" /></li>
			<li><img src="pic/003.jpg" /></li>
			<li><img src="pic/004.jpg" /></li>
		</ul>
		<ul class="num" id="idNum">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>

	<div class="blank5"></div>
	<div class="block clearfix">
		<div class="blank"></div>
		<c:if test="${sessionScope.userid != null }">
			<div class="box">
				<div class="box_1">
					<h3>
						<span class="text">猜你喜欢</span>
					</h3>
					<div class="picScroll-left">
						<div class="bd">
							<ul class="picList">
								<c:forEach items="${recommendList}" var="music">
									<li>
										<div class="pic">
											<a href="index/detail.action?id=${music.musicid }"><img src="${music.image }" /></a>
										</div>
										<div class="title">
											<a href="index/detail.action?id=${music.musicid }">${fn:substring(music.musicname, 0, 8)}</a>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="blank"></div>
		</c:if>
		<div class="goodsBox_1">
			<!-- 循环开始  -->
			<c:forEach items="${frontList}" var="cate">
				<div class="xm-box">
					<h4 class="title">
						<span>${cate.catename }</span> <a class="more" href="index/cate.action?id=${cate.cateid }">更多</a>
					</h4>
					<div id="show_new_area" class="clearfix">
						<!-- 循环开始 -->
						<c:forEach items="${cate.musicList}" var="music">
							<div class="goodsItem">
								<a href="index/detail.action?id=${music.musicid }"><img src="${music.image }" alt="${music.musicname }"
									class="goodsimg" /></a><br />
								<p class="f1">
									<a href="index/detail.action?id=${music.musicid }" title="${music.musicname }">${music.musicname }</a>
								</p>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="blank"></div>
			</c:forEach>
			<!-- 循环结束  -->
			<div class="blank"></div>
		</div>
	</div>
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
	background: url("themes/ecmoban_benlai/images/arrow.png") 0 0 no-repeat
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
	background: url("themes/ecmoban_benlai/images/icoCircle.gif") 0 -9px no-repeat
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
	text-align: center
}

.picScroll-left .bd ul li .pic img {
	width: 220px;
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
	<script type="text/javascript">
		var ary = location.href.split("&");
		jQuery(".picScroll-left").slide({
			titCell : ".hd ul",
			mainCell : ".bd ul",
			autoPage : true,
			effect : "left",
			autoPlay : true,
			scroll : 6,
			vis : 5,
			delayTime : 1500,
			pnLoop : false
		});
	</script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
