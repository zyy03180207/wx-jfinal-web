<%@page import="com.program.wx.model.Secqurity"%>
<%@page import="com.program.wx.utils.Menu"%>
<%@page import="com.program.wx.config.Global"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	LinkedList<Menu> menus = (LinkedList<Menu>)request.getSession().getAttribute(Global.SECQURITYMENU);
	Secqurity secqurity = (Secqurity)request.getAttribute("secqurity"); 
%>
<div style="margin: 15px;">
	<form class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">上级菜单</label>
			<div class="layui-input-block">
				<select name="author" lay-verify="required">
					<option value="-1">作为功能权限</option>
					<option value="0">作为一级菜单</option>
					<%for(Menu menu : menus) { 
						if(menu.getId() == secqurity.getInt("pid")) {
					%>
						<option value="<%=menu.getId()%>" selected="selected"><%for(int i = 0; i < menu.getLevel(); i++){ %>&nbsp;&nbsp;<% }if(menu.getLevel()!=0){%>└─<%} %><%=menu.getTitle() %></option>
					<%} else { %>
						<option value="<%=menu.getId()%>"><%for(int i = 0; i < menu.getLevel(); i++){ %>&nbsp;&nbsp;<% }if(menu.getLevel()!=0){%>└─<%} %><%=menu.getTitle() %></option>
					<%}} %>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限ID</label>
			<div class="layui-input-inline">
				<input value="<%=secqurity.getInt("id") %>"  type="text" name="id" placeholder="" required lay-verify="required" autocomplete="off" class="layui-input" readonly>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">名称</label>
			<div class="layui-input-inline">
				<input value="<%=secqurity.getStr("menu_name") %>" type="text" name="name" placeholder="请输入菜单或权限名称" required lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">描述</label>
			<div class="layui-input-inline">
				<input value="<%=secqurity.getStr("des") %>" type="text" name="des" placeholder="请输入菜单或权限描述" required lay-verify="required" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">菜单或权限的描述</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">链接</label>
			<div class="layui-input-inline">
				<input value="<%=secqurity.getStr("action") %>" type="text" name="uri" placeholder="请输入链接地址" required lay-verify="required" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">只需要输入controller的路径</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">图标</label>
			<div class="layui-input-inline">
				<input value="<%=secqurity.getStr("icon") %>" type="text" name="icon" placeholder="请输入菜单图标" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">font-awesome图标名（无图标则不输入）</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否菜单</label>
			<div class="layui-input-block">
				<%if(secqurity.getStr("ismenu").equals("1")){ %>
					<input type="checkbox" name="switch" lay-skin="switch" lay-verify="required" checked="checked">
				<%}else{ %>
					<input type="checkbox" name="switch" lay-skin="switch" lay-verify="required">
				<%} %>
			</div>
			<div class="layui-form-mid layui-word-aux">若不是菜单则上级选项无效</div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>