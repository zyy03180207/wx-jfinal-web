<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>角色管理</title>
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="css/global.css" media="all">
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
		<link rel="stylesheet" href="css/table.css" />
	</head>

	<body>
		<div class="admin-main">

			<blockquote class="layui-elem-quote">
				<a href="javascript:;" class="layui-btn layui-btn-small" id="add">
					<i class="layui-icon">&#xe608;</i> 添加角色
				</a>
				<a href="#" class="layui-btn layui-btn-small" id="getDel">
					<i class="fa fa-shopping-cart" aria-hidden="true"></i> 批量删除
				</a>
				<!-- <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a> -->
			</blockquote>
			<fieldset class="layui-elem-field">
				<legend>数据列表</legend>
				<div class="layui-field-box layui-form">
					<table class="layui-table admin-table">
						<thead>
							<tr>
								<th style="width: 30px;"><input type="checkbox" lay-filter="allselector" lay-skin="primary"></th>
								<th>ID</th>
								<th>角色名</th>
								<th>描述</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="content">
						</tbody>
					</table>
				</div>
			</fieldset>
			</br></br>
			<div class="admin-table-page">
				<div id="paged" class="page">
				</div>
			</div>
		</div>
		<!--模板-->
		<script type="text/html" id="tpl">
			{{# layui.each(d.list, function(index, item){ }}
			<tr>
				<td><input type="checkbox" lay-skin="primary"></td>
				<td>{{ item.id }}</td>
				<td>{{ item.name }}</td>
				<td>{{ item.introduce }}</td>
				<td>
					<a href="javascript:;" data-id="{{ item.id }}" target="_blank" data-opt="fen" class="layui-btn layui-btn-normal layui-btn-mini">分配权限</a>
					<a href="javascript:;" data-id="{{ item.id }}" data-opt="edit" class="layui-btn layui-btn-mini">编辑</a>
					<a href="javascript:;" data-id="{{item.id}}" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
				</td>
			</tr>
			{{# }); }}
		</script>
		<script type="text/javascript" src="plugins/layui/layui.js"></script>
		<script>
			layui.config({
				base: 'js/'
			});

			layui.use(['paging', 'form'], function() {
					var $ = layui.jquery,
					paging = layui.paging(),
					layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
					layer = layui.layer, //获取当前窗口的layer对象
					form = layui.form();
					
				paging.init({
					url: 'role/roleList', //地址
					elem: '#content', //内容容器
					params: { //发送到服务端的参数
					},
					type: 'POST',
					tempElem: '#tpl', //模块容器
					pageConfig: { //分页参数配置
						elem: '#paged', //分页容器
						pageSize: 20 //分页大小
					},
					success: function() { //渲染成功的回调
						//alert('渲染成功');
					},
					fail: function(msg) { //获取数据失败的回调
						//alert('获取数据失败')
					},
					complate: function() { //完成的回调
						//alert('处理完成');
						//重新渲染复选框
						form.render('checkbox');
						form.on('checkbox(allselector)', function(data) {
							var elem = data.elem;

							$('#content').children('tr').each(function() {
								var $that = $(this);
								//全选或反选
								$that.children('td').eq(0).children('input[type=checkbox]')[0].checked = elem.checked;
								form.render('checkbox');
							});
						});

						//绑定所有编辑按钮事件						
						$('#content').children('tr').each(function() {
							var $that = $(this);
							$that.children('td:last-child').children('a[data-opt=edit]').on('click', function() {
								//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
								$.get('role/roleEdit?id=' + $(this).data('id'), null, function(form) {
									addBoxIndex = layer.open({
										type: 1,
										title: '编辑角色',
										content: form,
										btn: ['修改', '取消'],
										shade: false,
										offset: ['20px', '20%'],
										area: ['600px', '400px'],
										zIndex: 19950924,
										maxmin: true,
										yes: function(index) {
											//触发表单的提交事件
											$('form.layui-form').find('button[lay-filter=edit]').click();
										},
										full: function(elem) {
											var win = window.top === window.self ? window : parent.window;
											$(win).on('resize', function() {
												var $this = $(this);
												elem.width($this.width()).height($this.height()).css({
													top: 0,
													left: 0
												});
												elem.children('div.layui-layer-content').height($this.height() - 95);
											});
										},
										success: function(layero, index) {
											//弹出窗口成功后渲染表单
											var form = layui.form();
											form.render();
											form.on('submit(edit)', function(data) {
												$.ajax({
													type:"POST",
													url:"role/roleEdit",
													dataType:"json",
													data: data.field,
													success:function(data) {
														if(data.succ) {
															layerTips.msg(data.mesg, {icon: 6});
															layerTips.close(index);
															location.reload(); //刷新
														} else {
															layerTips.msg(data.mesg, {icon: 5});
														}
													},
													error:function(){
														layerTips.msg(data.mesg, {icon: 5});
													}
												});
												//这里可以写ajax方法提交表单
												return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。									
											});
											//console.log(layero, index);
										},
										end: function() {
											addBoxIndex = -1;
										}
									});
								});
							});

							$that.children('td:last-child').children('a[data-opt=fen]').on('click', function() {
								/* layer.msg($(this).data('name')); */
								//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
								$.get('role/roleToAuthor?id=' + $(this).data('id'), null, function(form) {
									addBoxIndex = layer.open({
										type: 1,
										title: '分配权限',
										content: form,
										btn: ['分配', '取消'],
										shade: false,
										offset: ['20px', '20%'],
										area: ['600px', '400px'],
										zIndex: 19950924,
										maxmin: true,
										yes: function(index) {
											//触发表单的提交事件
											$('form.layui-form').find('button[lay-filter=edit]').click();
										},
										full: function(elem) {
											var win = window.top === window.self ? window : parent.window;
											$(win).on('resize', function() {
												var $this = $(this);
												elem.width($this.width()).height($this.height()).css({
													top: 0,
													left: 0
												});
												elem.children('div.layui-layer-content').height($this.height() - 95);
											});
										},
										success: function(layero, index) {
											//弹出窗口成功后渲染表单
											var form = layui.form();
											form.render();
											form.on('submit(edit)', function(data) {
												console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
												console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
												console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
												//调用父窗口的layer对象
												$.ajax({
													type:"POST",
													url:"role/roleToAuthor",
													dataType:"json",
													data: data.field,
													success:function(data) {
														if(data.succ) {
															layerTips.msg(data.mesg, {icon: 6});
															layerTips.close(index);
															location.reload(); //刷新
														} else {
															layerTips.msg(data.mesg, {icon: 5});
														}
													},
													error:function(){
														layerTips.msg(data.mesg, {icon: 5});
													}
												});
												//这里可以写ajax方法提交表单
												return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。									
											});
											//console.log(layero, index);
										},
										end: function() {
											addBoxIndex = -1;
										}
									});
								});
							});
							
							$that.children('td:last-child').children('a[data-opt=del]').on('click', function() {
								var id = $(this).data('id');
								layer.confirm('确认删除吗，此操作是不可逆的？', {
									  btn: ['确认','取消'] //按钮
									}, function(){
										$.ajax({
											type:"POST",
											url:"role/roleDel",
											dataType:"json",
											data: {"id":id},
											success:function(data) {
												if(data.succ) {
													layerTips.msg(data.mesg, {icon: 6});
													location.reload(); //刷新
												} else {
													layerTips.msg(data.mesg, {icon: 5});
												}
											},
											error:function(data){
												layerTips.msg(data.mesg, {icon: 5});
											}
										});
									}, function(){
									  layer.msg('的确很重要', {icon: 1});
								});
							});
						});

					},
				});
				//删除批量
				$('#getDel').on('click', function() {
					var names = '';
					$('#content').children('tr').each(function() {
						var $that = $(this);
						var $cbx = $that.children('td').eq(0).children('input[type=checkbox]')[0].checked;
						if($cbx) {
							var n = $that.children('td:last-child').children('a[data-opt=edit]').data('id');
							names += n + ',';
						}
					});
					layer.confirm('确认要删除所选择的权限吗，此操作是不可逆的？', {
						  btn: ['确认','取消'] //按钮
						}, function(){
							$.ajax({
								type:"POST",
								url:"role/roleDel",
								dataType:"json",
								data: {"id":names},
								success:function(data) {
									if(data.succ) {
										layerTips.msg(data.mesg, {icon: 6});
										location.reload(); //刷新
									} else {
										layerTips.msg(data.mesg, {icon: 5});
									}
								},
								error:function(){
									layerTips.msg(data.mesg, {icon: 5});
								}
							});
						}, function(){
							layer.msg('的确很重要', {icon: 1});
					});
				});

				$('#search').on('click', function() {
					parent.layer.alert('你点击了搜索按钮')
				});

				var addBoxIndex = -1;
				$('#add').on('click', function() {
					if(addBoxIndex !== -1)
						return;
					//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
					$.get('role/roleAdd', null, function(form) {
						addBoxIndex = layer.open({
							type: 1,
							title: '添加角色',
							content: form,
							btn: ['保存', '取消'],
							shade: false,
							offset: ['20px', '20%'],
							area: ['600px', '400px'],
							zIndex: 19950924,
							maxmin: true,
							yes: function(index) {
								//触发表单的提交事件
								$('form.layui-form').find('button[lay-filter=edit]').click();
							},
							full: function(elem) {
								var win = window.top === window.self ? window : parent.window;
								$(win).on('resize', function() {
									var $this = $(this);
									elem.width($this.width()).height($this.height()).css({
										top: 0,
										left: 0
									});
									elem.children('div.layui-layer-content').height($this.height() - 95);
								});
							},
							success: function(layero, index) {
								//弹出窗口成功后渲染表单
								var form = layui.form();
								form.render();
								form.on('submit(edit)', function(data) {
									$.ajax({
										type:"POST",
										url:"role/roleAdd",
										dataType:"json",
										data: data.field,
										success:function(data) {
											if(data.succ) {
												layerTips.msg(data.mesg, {icon: 6});
												layerTips.close(index);
												location.reload(); //刷新
											} else {
												layerTips.msg(data.mesg, {icon: 5});
											}
										},
										error:function(){
											layerTips.msg(data.mesg, {icon: 5});
										}
									});
									//这里可以写ajax方法提交表单
									return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。									
								});
								//console.log(layero, index);
							},
							end: function() {
								addBoxIndex = -1;
							}
						});
					});
				});
				
				/* $('#import').on('click', function() {
					var that = this;
					var index = layer.tips('只想提示地精准些', that, { tips: [1, 'white'] });
					$('#layui-layer' + index).children('div.layui-layer-content').css('color', '#000000');
				}); */
			});
		</script>
	</body>
</html>