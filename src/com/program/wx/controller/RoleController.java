package com.program.wx.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.program.wx.model.Role;
import com.program.wx.utils.StringUtil;

public class RoleController extends BaseController {

	public void index() {

	}

	public void roleList() {
		String pageIndex = getPara("pageIndex");
		String pageSize = getPara("pageSize");
		Page<Record> page = Role.dao.findRoleByPage(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
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

	public void roleAdd() {
		try {
			if (this.reqGet()) {
				renderJsp("roleadd.jsp");
			} else {
				String name = getPara("name");
				String introduce = getPara("introduce");
				String state = getPara("state");
				if (StringUtil.isEmpty(name)) {
					this.setMesg("请输入角色名称");
					return;
				}
				if (StringUtil.isEmpty(introduce)) {
					this.setMesg("请输入角色描述");
					return;
				}
				if (StringUtil.isEmpty(state)) {
					state = "0";
				} else {
					state = "1";
				}
				Role role = new Role();
				boolean s = role.set("name", name).set("introduce", introduce).set("state", state).save();
				if (s) {
					this.setMesg(true, "添加成功", true);
				} else {
					this.setMesg("添加失败");
				}
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}
}