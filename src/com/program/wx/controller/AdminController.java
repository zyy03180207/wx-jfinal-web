package com.program.wx.controller;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.program.wx.model.AdminUser;
import com.program.wx.model.AdminUserRole;
import com.program.wx.model.Role;
import com.program.wx.utils.StringUtil;

public class AdminController extends BaseController {

	public void index() {

	}

	public void adminList() {
		String pageIndex = getPara("pageIndex");
		String pageSize = getPara("pageSize");
		Page<Record> page = AdminUser.dao.findUserByPage(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
		if (page != null) {
			List<Record> list = page.getList();
			JSONArray array = paresList(list);
			this.setPageJson(array, page.getTotalRow(), "查询成功");
		} else {
			JSONArray array = new JSONArray();
			this.setPageJson(array, 0, "查询失败");
		}
		renderJson();
	}

	@Before(Tx.class)
	public void adminAdd() {
		try {
			if (this.reqGet()) {
				List<Role> roles = Role.dao.findRoleByAll();
				this.setAttr("roles", roles);
				renderJsp("adminadd.jsp");
			} else {
				String username = getPara("username");
				String password = getPara("password");
				String phone = getPara("phone");
				String email = getPara("email");
				String roleId = getPara("role");
				String state = getPara("switch");
				if (StringUtil.isEmpty(username)) {
					this.setMesg("请输入登录名");
					return;
				}
				if (StringUtil.isEmpty(password)) {
					this.setMesg("请输入登录密码");
					return;
				}
				if (StringUtil.isEmpty(phone)) {
					this.setMesg("请输入手机号");
					return;
				}
				if (StringUtil.isEmpty(email)) {
					this.setMesg("请输入邮箱");
					return;
				}
				if (StringUtil.isEmpty(roleId) && roleId.equals("0")) {
					this.setMesg("请选择用户角色");
					return;
				}
				if (StringUtil.isEmpty(state)) {
					state = "0";
				} else {
					state = "1";
				}
				if (AdminUser.dao.findUserByName(username) != null) {
					this.setMesg("登录名已存在");
					return;
				}
				AdminUser adminUser = new AdminUser();
				boolean s = adminUser.set("username", username).set("password", md5(password)).set("phone", phone)
						.set("email", email).set("state", state).set("crtime", System.currentTimeMillis() + "").save();
				if (s) {
					AdminUser user = AdminUser.dao.findUserByName(username);
					AdminUserRole userRole = new AdminUserRole();
					boolean r = userRole.set("uid", user.getInt("id")).set("rid", roleId).save();
					if (r) {
						this.setMesg(true, "添加成功", true);
						return;
					} else {
						this.setMesg("添加失败");
					}
				} else {
					this.setMesg("添加失败");
				}
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}

	public void adminOS() {
		try {
			String idStr = getPara("ids");
			String state = getPara("state");
			if (StringUtil.isEmpty(idStr)) {
				if ("0".equals(state)) {
					this.setMesg("请选择要停用的用户");
					return;
				} else {
					this.setMesg("请选择要启用的用户");
					return;
				}
			}
			String[] ids = null;
			if (idStr.contains(",")) {
				idStr = idStr.substring(0, idStr.length() - 1);
				ids = idStr.split(",");
			} else {
				ids = new String[] { idStr };
			}
			for (int i = 0; i < ids.length; i++) {
				AdminUser adminUser = new AdminUser();
				adminUser.set("id", ids[i]).set("state", state).update();
			}
			this.setMesg(true, "修改成功", true);
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}

	public void adminDel() {
		try {
			String idStr = getPara("delId");
			if (StringUtil.isEmpty(idStr)) {
				this.setMesg("请选择要删除的用户");
				return;
			}
			String[] ids = null;
			if (idStr.contains(",")) {
				idStr = idStr.substring(0, idStr.length() - 1);
				ids = idStr.split(",");
			} else {
				ids = new String[] { idStr };
			}
			for (int i = 0; i < ids.length; i++) {
				AdminUser adminUser = new AdminUser();
				adminUser.set("id", ids[i]).delete();
			}
			this.setMesg(true, "操作成功", true);
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}

	public void adminEdit() {
		try {
			if (this.reqGet()) {
				String id = this.getPara("id");
				Record adminUser = AdminUser.dao.findUserById(Integer.valueOf(id));
				this.setAttr("adminUser", adminUser);
				List<Role> roles = Role.dao.findRoleByAll();
				this.setAttr("roles", roles);
				renderJsp("adminedit.jsp");
			} else {
				String username = getPara("username");
				String id = getPara("id");
				String phone = getPara("phone");
				String email = getPara("email");
				String roleId = getPara("role");
				if (StringUtil.isEmpty(username)) {
					this.setMesg("请输入登录名");
					return;
				}
				if (StringUtil.isEmpty(phone)) {
					this.setMesg("请输入手机号");
					return;
				}
				if (StringUtil.isEmpty(email)) {
					this.setMesg("请输入邮箱");
					return;
				}
				if (StringUtil.isEmpty(roleId) && roleId.equals("0")) {
					this.setMesg("请选择用户角色");
					return;
				}
				AdminUser adminUser = AdminUser.dao.findUserByName(username);
				if (adminUser != null && adminUser.getInt("id") != Integer.valueOf(id)) {
					this.setMesg("该登陆名已被使用");
					return;
				}
				AdminUser user = new AdminUser();
				boolean s = user.set("id", id).set("username", username).set("phone", phone).set("email", email)
						.update();
				if (s) {
					AdminUserRole.dao.updUserRoleByUid(Integer.valueOf(id), Integer.valueOf(roleId));
					this.setMesg(true, "修改成功", true);
				} else {
					this.setMesg("修改失败");
				}
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}

	public void adminUpPass() {
		try {
			String id = this.getPara("id");
			AdminUser adminUser = new AdminUser();
			boolean s = adminUser.set("password", md5("12345678")).set("id", Integer.valueOf(id)).update();
			if (s) {
				this.setMesg(true, "初始化成功", true);
			} else {
				this.setMesg("初始化失败");
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}
}
