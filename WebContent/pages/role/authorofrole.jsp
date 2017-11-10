<%@page import="com.program.wx.model.RoleSecqurity"%>
<%@page import="java.util.List"%>
<%@page import="com.program.wx.utils.MenuUtil"%>
<%@page import="com.program.wx.utils.Menu"%>
<%@page import="com.program.wx.config.Global"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	LinkedList<Menu> menus = (LinkedList<Menu>)request.getSession().getAttribute(Global.SECQURITYMENU);
	String id = (String)request.getAttribute("id");
	List<RoleSecqurity> list = (List<RoleSecqurity>)request.getAttribute("list");
	String[] strings = (String[])request.getAttribute("strs");
%>
<div style="margin: 15px;">
	<form class="layui-form">
 		<fieldset id="field" class="layui-elem-field rule_check">
			<legend>
				<%if(strings.length > 0){ %>
				<input value="0" class="rules_all" type="checkbox" lay-filter="allChoose" lay-skin="primary" checked="checked">至高无上
				<%}else{ %>
				<input value="0" class="rules_all" type="checkbox" lay-filter="allChoose" lay-skin="primary">至高无上
				<%} %>
			</legend>
			<div class="layui-field-box">
				<%for(Menu menu : menus) { 
					if(menu.getPid() == 0){%>
						<fieldset class="layui-elem-field rule_check1">
							<legend>
								<%if(list.contains(String.valueOf(menu.getId()))){ %>
								<input value="<%=menu.getId() %>" class="auth_rules rules_all" type="checkbox" lay-filter="allChoose1" lay-skin="primary" checked="checked"><span><%=menu.getTitle()%></span>
								<%}else{ %>
								<input value="<%=menu.getId() %>" class="auth_rules rules_all" type="checkbox" lay-filter="allChoose1" lay-skin="primary"><span><%=menu.getTitle()%></span>
								<%} %>
							</legend>
							<div class="layui-field-box">
								<%for(Menu menu1 : menus) {
									if(menu1.getPid() == menu.getId()){%>
										<fieldset class="layui-elem-field rule_check2">
											<legend>
											<%if(list.contains(String.valueOf(menu1.getId()))){ %>
												<input value="<%=menu1.getId() %>" class="auth_rules rules_row" type="checkbox" lay-filter="allChoose2" lay-skin="primary" checked="checked"><span><%=menu1.getTitle()%></span>
											<%}else{ %>
												<input value="<%=menu1.getId() %>" class="auth_rules rules_row" type="checkbox" lay-filter="allChoose2" lay-skin="primary"><span><%=menu1.getTitle()%></span>
											<%} %>
											</legend>
											<div class="layui-field-box child_row">
												<%for(Menu menu2 : menus){ 
													if(menu2.getPid() == menu1.getId()){%>
														<%if(list.contains(String.valueOf(menu2.getId()))){ %>
														<input value="<%=menu2.getId() %>" class="auth_rules" type="checkbox" lay-filter="allChoose3" lay-skin="primary" checked="checked"><span><%=menu2.getTitle()%></span>
														<%}else{ %>
														<input value="<%=menu2.getId() %>" class="auth_rules" type="checkbox" lay-filter="allChoose3" lay-skin="primary"><span><%=menu2.getTitle()%></span>
														<%} %>
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
		<input type="text" id="id" name="id" style="display: none;" value="<%=id%>"/>
		<input type="text" id="sec" name="sec" style="display: none;"/>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>
<script type="text/javascript" src="plugins/layui/lay/dest/layui.all.js"></script>  
<script type="text/javascript">
	var $ = layui.jquery,  
	form = layui.form();  
	var sec = new Array();
	<%for(int i = 0; i<strings.length; i++){%>
		sec.push('<%=strings[i]%>');
	<%}%>
	$("#sec").val(sec);
	//全选  
	form.on('checkbox(allChoose)', function(data){  
	//先清空数组，重新选择则，防止重复选择问题
	sec.splice(0,sec.length);
	var child = $(data.elem).parents('.rule_check').find('input[type="checkbox"]'); 
		child.each(function(index, item){  
		  item.checked = data.elem.checked;
		  checkSec(item.value, data.elem.checked);
		});
		$("#sec").val(sec);
		form.render('checkbox');  
	});  
	
	form.on('checkbox(allChoose1)', function(data){  
		var child = $(data.elem).parents('.rule_check1').find('input[type="checkbox"]');  
		child.each(function(index, item){  
		  item.checked = data.elem.checked; 
		  checkSec(item.value, data.elem.checked);
		});  
		$("#sec").val(sec);
		form.render('checkbox');  
	}); 
	
	form.on('checkbox(allChoose2)', function(data){  
		var child = $(data.elem).parents('.rule_check2').find('input[type="checkbox"]');  
		child.each(function(index, item){  
		  item.checked = data.elem.checked;  
		  checkSec(item.value, data.elem.checked);
		});  
		$("#sec").val(sec);
		form.render('checkbox');  
	}); 
	
	form.on('checkbox(allChoose3)', function(data){  
		checkSec(data.elem.value, data.elem.checked);
		$("#sec").val(sec);
		form.render('checkbox');  
	}); 
	
	function checkSec(val, falg) {
		if(falg) {
			sec.push(val);
		} else {
			sec.splice($.inArray(val, sec), 1)
		}
	}
</script>
