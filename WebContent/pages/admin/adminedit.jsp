<%@page import="com.jfinal.plugin.activerecord.Record"%>
<%@page import="com.program.wx.model.AdminUser"%>
<%@page import="com.program.wx.model.Role"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Role> roles = (List<Role>)request.getAttribute("roles");
	Record adminUser = (Record)request.getAttribute("adminUser");
%>
<div style="margin: 15px;">
	<form class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">用户ID</label>
			<div class="layui-input-inline">
				<input value="<%=adminUser.getInt("id") %>" type="text" name="id" autocomplete="off" class="layui-input" readonly>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">登录名</label>
			<div class="layui-input-inline">
				<input value="<%=adminUser.getStr("username") %>" type="text" name="username" placeholder="请输入登录名" required lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号</label>
			<div class="layui-input-inline">
				<input value="<%=adminUser.getStr("phone") %>" type="text" name="phone" required lay-verify="phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">手机号不会公开</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">邮箱</label>
			<div class="layui-input-inline">
				<input value="<%=adminUser.getStr("email") %>" type="text" name="email" required lay-verify="email" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">邮箱不会公开</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">选择角色</label>
			<div class="layui-input-block">
				<select name="role" lay-verify="required">
					<%for(Role role : roles) { 
						if(role.getInt("id") == adminUser.getInt("rid")){%>
						<option value="<%=role.getInt("id")%>" selected><%=role.getStr("name")%></option>
						<%}else{ %>
						<option value="<%=role.getInt("id")%>"><%=role.getStr("name")%></option>
					<%}} %>
				</select>
			</div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>