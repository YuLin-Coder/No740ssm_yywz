<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!doctype html>
<html lang="zh_CN">
<head>
<base href="<%=basePath%>" />
<title>${title }</title>

</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="block box">
		<div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href=".">首页</a>
			<code>&gt;</code>
			<a href="index/music.action">全部音乐</a>
			<code>&gt;</code>
			<a href="index/cate.action?id=${music.cateid }">${music.catename }</a>
			<code>&gt;</code>
			${music.musicname }
		</div>
	</div>
	<div class="blank"></div>
	<div class="block clearfix">
		<div class="AreaL">
			<div id="category_tree" class="category_tree">
				<div class="title">所有音乐分类</div>
				<dl class="clearfix" style="background: #fafafa; width: 216px; padding: 0;">
					<div class="box1 cate" id="cate">
						<c:forEach items="${cateList}" var="cate">
							<h1>
								<a href="index/cate.action?id=${cate.cateid }" class="  f_l">${cate.catename }</a>
							</h1>
							<div style="clear: both"></div>
						</c:forEach>
					</div>
					<div style="clear: both"></div>
				</dl>
			</div>
			<div class="blank"></div>
			<div class="box" id='history_div'>
				<div class="box_1">
					<h3>
						<span>热门音乐</span>
					</h3>
					<div class="boxCenterList clearfix" id='history_list'>
						<c:forEach items="${hotList}" var="music">
							<ul class="clearfix">
								<li class="goodsimg"><a href="index/detail.action?id=${music.musicid }" target="_blank"><img
										src="${music.image }" alt="" class="B_blue" /> </a></li>
								<li><a href="index/detail.action?id=${music.musicid }" target="_blank" title="${music.musicname }">${music.musicname }</a>
								</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="blank5"></div>
		</div>


		<div class="AreaR">

			<div id="goodsInfo" class="clearfix">


				<div class="imgInfo">
					<img src="${music.image }" alt="${music.musicname }" width="360px;" height="360px" />
					<div class="blank5"></div>
					<div class="blank"></div>
				</div>

				<div class="textInfo">
					<h1 class="clearfix">${music.musicname }</h1>
					<ul class="ul2 clearfix">
						<li class="clearfix">
							<dd>
								<strong>歌手：</strong>${music.singer }
							</dd>
						</li>
						<li class="clearfix">
							<dd>
								<strong>专辑：</strong>${music.album }
							</dd>
						</li>
						<li class="clearfix">
							<dd>
								<strong>音乐类型：</strong><a href="index/cate.action?id=${music.cateid }">${music.catename }</a>
							</dd>
						</li>
						<li class="clearfix">
							<dd>
								<strong>发布日期：</strong>${music.addtime }
							</dd>
						</li>
						<li class="clearfix">
							<dd>
								<strong>点击数：</strong>${music.hits }
							</dd>
						</li>
						<li class="clearfix">
							<dd>
								<strong>播放次数：</strong>${music.num }
							</dd>
						</li>
						<li class="clearfix">
							<dd>
								<strong>收藏音乐：</strong><a href="index/addfav.action?id=${music.musicid }">加入收藏</a>
							</dd>
						</li>
					</ul>
					<ul class="bnt_ul">
						<li class="padd"><a href="index/play.action?id=${music.musicid }"><img
								src="themes/ecmoban_dangdang/images/play.jpg" /></a></li>
					</ul>
				</div>
			</div>
			<div class="blank"></div>


			<div class="box">

				<div style="padding: 0 0px;">
					<div id="com_b" class="history clearfix">
						<h2>音乐描述</h2>
					</div>
				</div>
				<div class="box_1">
					<div id="com_v" class="  " style="padding: 6px;"></div>
					<div id="com_h">
						<blockquote>${music.contents }</blockquote>
					</div>
				</div>
			</div>
			<div class="blank"></div>
			<c:if test="${sessionScope.userid != null }">
				<div class="box">
					<div class="box_1">
						<h3>
							<span class="text">猜你喜欢</span>
						</h3>
						<div class="boxCenterList clearfix ie6" style="padding: 2px;">
							<c:forEach items="${recommendList}" var="music">
								<div class="goodsItem">
									<a href="index/detail.action?id=${music.musicid }"><img src="${music.image }" alt="" class="goodsimg" /></a><br />
									<p>
										<a href="index/detail.action?id=${music.musicid }" title="">${music.musicname }</a>
									</p>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="blank5"></div>
			</c:if>
			<div id="ECS_COMMENT">
				<div class="box">
					<div class="box_1">
						<h3>
							<span class="text">用户评论</span>(共<font class="f1">${tnum }</font>条评论)
						</h3>
						<div class="boxCenterList clearfix" style="height: 1%;">
							<ul class="comments">
								<c:forEach items="${topicList}" var="topic">
									<li class="word"><font class="f2">${topic.username } </font> <font class="f3">(${topic.addtime }) </font>
										<br /> <img src="themes/ecmoban_dangdang/images/stars${topic.num }.gif" />
										<p>${topic.contents }</p></li>
								</c:forEach>
							</ul>
							<div class="blank5"></div>
							<div class="commentsList">
								<c:if test="${sessionScope.userid != null }">
									<form action="index/addTopic.action" method="post" name="commentForm" id="commentForm">
										<table width="710" border="0" cellspacing="5" cellpadding="0">
											<tr>
												<td align="right">评价等级：</td>
												<td><input name="num" type="radio" value="1" id="comment_rank1" /> <img
													src="themes/ecmoban_dangdang/images/stars1.gif" /> <input name="num" type="radio" value="2"
													id="comment_rank2" /> <img src="themes/ecmoban_dangdang/images/stars2.gif" /> <input name="num"
													type="radio" value="3" id="comment_rank3" /> <img src="themes/ecmoban_dangdang/images/stars3.gif" /> <input
													name="num" type="radio" value="4" id="comment_rank4" /> <img
													src="themes/ecmoban_dangdang/images/stars4.gif" /> <input name="num" type="radio" value="5"
													checked="checked" id="comment_rank5" /> <img src="themes/ecmoban_dangdang/images/stars5.gif" /></td>
											</tr>
											<tr>
												<td align="right" valign="top">评论内容：</td>
												<td><textarea name="contents" class="inputBorder" style="height: 50px; width: 620px;"></textarea> <input
													type="hidden" name="musicid" value="${music.musicid }" /></td>
											</tr>
											<tr>
												<td colspan="2"><input type="submit" value="评论" class="f_r bnt_blue_1" style="margin-right: 8px;" /></td>
											</tr>
										</table>
									</form>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<div class="blank5"></div>

			</div>

		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
