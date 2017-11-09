<%@page import="com.program.wx.utils.MenuUtil"%>
<%@page import="com.program.wx.utils.Menu"%>
<%@page import="com.program.wx.config.Global"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	LinkedList<Menu> menus = (LinkedList<Menu>)request.getSession().getAttribute(Global.SECQURITYMENU);
%>
<div style="margin: 15px;">
	<form class="layui-form">
 		<fieldset id="field" class="layui-elem-field rule_check">
			<legend>
				<input class="rules_all" type="checkbox" lay-filter="allChoose" lay-skin="primary">至高无上
			</legend>
			<div class="layui-field-box">
				<%for(Menu menu : menus) { 
					if(menu.getPid() == 0){%>
						<fieldset class="layui-elem-field rule_check1">
							<legend>
								<input class="auth_rules rules_all" type="checkbox" lay-filter="allChoose1" lay-skin="primary"><span><%=menu.getTitle()%></span>
							</legend>
							<div class="layui-field-box">
								<%for(Menu menu1 : menus) {
									if(menu1.getPid() == menu.getId()){%>
										<fieldset class="layui-elem-field rule_check2">
											<legend>
												<input class="auth_rules rules_row" type="checkbox" lay-filter="allChoose2" lay-skin="primary"><span><%=menu1.getTitle()%></span>
											</legend>
											<div class="layui-field-box child_row">
												<%for(Menu menu2 : menus){ 
													if(menu2.getPid() == menu1.getId()){%>
														<input class="auth_rules" type="checkbox" lay-skin="primary"><span><%=menu2.getTitle()%></span>
												<%}}%>
											</div>
										</fieldset>
								<%}}%>
							</div>
						</fieldset>
					<%}%>
				<%} %>
			</div>
		</fieldset>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>
<script type="text/javascript" src="plugins/layui/lay/dest/layui.all.js"></script>  
<script type="text/javascript">
	var $ = layui.jquery,  
	form = layui.form();  
	//全选  
	form.on('checkbox(allChoose)', function(data){  
	var child = $(data.elem).parents('.rule_check').find('fieldset input[type="checkbox"]');  
	child.each(function(index, item){  
	  item.checked = data.elem.checked;  
	});  
	form.render('checkbox');  
	});  
	
	form.on('checkbox(allChoose1)', function(data){  
		var child = $(data.elem).parents('.rule_check1').find('fieldset input[type="checkbox"]');  
		child.each(function(index, item){  
		  item.checked = data.elem.checked;  
		});  
		form.render('checkbox');  
		}); 
	
	form.on('checkbox(allChoose2)', function(data){  
		var child = $(data.elem).parents('.rule_check2').find('div input[type="checkbox"]');  
		child.each(function(index, item){  
		  item.checked = data.elem.checked;  
		});  
		form.render('checkbox');  
		}); 
</script>
