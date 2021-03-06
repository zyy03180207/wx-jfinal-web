<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.program.wx.config.Global"%>
<%@page import="com.program.wx.model.AdminUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	AdminUser adminUser = (AdminUser)request.getSession().getAttribute(Global.USER_INFO);
	JSONArray array = (JSONArray)request.getSession().getAttribute(Global.MENUS);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>后台管理系统 v1.0</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no">
		<link rel="shortcut icon" href="images/12333.ico" />
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="css/global.css" media="all">
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
		
	</head>

	<body>
		<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
			<div class="layui-header header header-demo">
				<div class="layui-main">
					<div class="admin-login-box">
						<a class="logo" style="left: 0;" href="home">
							<span style="font-size: 20px;">Somates管理系统</span>
						</a>
						<div class="admin-side-toggle">
							<i class="fa fa-bars" aria-hidden="true" title="隐藏/显示菜单"></i>
						</div>
						<div class="admin-side-full">
							<i class="fa fa-life-bouy" aria-hidden="true" title="全屏显示"></i>
						</div>
					</div>
					
					<ul class="layui-nav admin-header-item">
						<li class="layui-nav-item layui-this">
                 	   	   <a href="home"><i class="iconfont icon-wangzhanguanli"></i>Somate平台</a>
                 	   	</li>
                 	   	<li class="layui-nav-item">
                 	   	   <a href="blog"><i class="iconfont icon-weixin3"></i>博客平台</a>
                 	   	</li>
                 	   	<li class="layui-nav-item">
                 	   	   <a href="wx"><i class="iconfont icon-ht_expand"></i>微信平台</a>
                 	   	</li>
                 	   	<li class="layui-nav-item">
                 	   	   <a href="system"><i class="iconfont icon-ht_expand"></i>系统平台</a>
                 	   	</li>
						<li class="layui-nav-item">
							<a href="javascript:;" class="admin-header-user">
								<img src="images/0.jpg" />
								<span><%=adminUser.getStr("username") %></span>
							</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:;"><i class="fa fa-user-circle" aria-hidden="true"></i> 个人信息</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="fa fa-gear" aria-hidden="true"></i> 设置</a>
								</dd>
								<dd id="lock">
									<a href="javascript:;">
										<i class="fa fa-lock" aria-hidden="true" style="padding-right: 3px;padding-left: 1px;"></i> 锁屏 (Alt+L)
									</a>
								</dd>
								<dd>
									<a href="outLogin"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
								</dd>
							</dl>
						</li>
					</ul>
					<ul class="layui-nav admin-header-item-mobile">
						<li class="layui-nav-item">
							<a href="outLogin"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="layui-side layui-bg-black" id="admin-side">
				<div class="layui-side-scroll" id="admin-navbar-side" lay-filter="side"></div>
			</div>
			<div class="layui-body" style="bottom: 0;border-left: solid 2px #1AA094;" id="admin-body">
				<div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab">
					<ul class="layui-tab-title">
						<li class="layui-this">
							<i class="fa fa-dashboard" aria-hidden="true"></i>
							<cite>控制面板</cite>
						</li>
					</ul>
					<div class="layui-tab-content" style="min-height: 150px; padding: 5px 0 0 0;">
						<div class="layui-tab-item layui-show">
							<iframe src="main"></iframe>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-footer footer footer-demo" id="admin-footer">
				<div class="layui-main">
					<p>2017 &copy;
						<a href="http://m.zhengjinfan.cn/">programafter.com</a> 十五度工作室
					</p>
				</div>
			</div>
			<div class="site-tree-mobile layui-hide">
				<i class="layui-icon">&#xe602;</i>
			</div>
			<div class="site-mobile-shade"></div>
			
			<!--锁屏模板 start-->
			<script type="text/template" id="lock-temp">
				<div class="admin-header-lock" id="lock-box">
					<div class="admin-header-lock-img">
						<img src="images/0.jpg"/>
					</div>
					<div class="admin-header-lock-name" id="lockUserName">beginner</div>
					<input type="text" class="admin-header-lock-input" value="输入密码解锁.." name="lockPwd" id="lockPwd" />
					<button class="layui-btn layui-btn-small" id="unlock">解锁</button>
				</div>
			</script>
			<!--锁屏模板 end -->
			<script type="text/javascript" src="plugins/layui/layui.js"></script>
			<script type="text/javascript" src="datas/nav.js"></script>
			<script src="js/index.js"></script>
			<script>
				layui.use('layer', function() {
					var $ = layui.jquery,
						layer = layui.layer;

					$('#video1').on('click', function() {
						layer.open({
							title: 'YouTube',
							maxmin: true,
							type: 2,
							content: 'video.html',
							area: ['800px', '500px']
						});
					});

				});
				navs = <%=array%>;
			</script>
		</div>
	</body>

</html>