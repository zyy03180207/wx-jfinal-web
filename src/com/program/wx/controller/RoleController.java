package com.program.wx.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.program.wx.model.AdminUserRole;
import com.program.wx.model.Role;
import com.program.wx.model.RoleSecqurity;
import com.program.wx.model.Secqurity;
import com.program.wx.utils.StringUtil;
/**
 * 角色管理
 * @author yangyang.zhang
 * @Package com.program.wx.controller 
 * @Date 2017年9月20日 下午5:27:11 
 * @Description TODO(用一句话描述该文件做什么)
 * @version V1.0
 */
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
				Role role1 = Role.dao.findRoleByName(name);
				if (role1 != null) {
					this.setMesg("角色名已存在");
					return;
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

	public void roleEdit() {
		try {
			if (this.reqGet()) {
				String id = this.getPara("id");
				Role role = Role.dao.findById(Integer.valueOf(id));
				this.setAttr("role", role);
				renderJsp("roleedit.jsp");
			} else {
				String id = getPara("id");
				String name = getPara("name");
				String introduce = getPara("introduce");
				if (StringUtil.isEmpty(name)) {
					this.setMesg("请输入角色名称");
					return;
				}
				if (StringUtil.isEmpty(introduce)) {
					this.setMesg("请输入角色描述");
					return;
				}
				Role role = Role.dao.findRoleByName(name);
				if (role != null && role.getInt("id") != Integer.valueOf(id)) {
					this.setMesg("角色名已存在");
					return;
				}
				Role role2 = new Role();
				boolean s = role2.set("id", id).set("name", name).set("introduce", introduce).update();
				if (s) {
					this.setMesg(true, "修改成功", true);
				} else {
					this.setMesg("修改失败");
				}
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}
	@Before(Tx.class)
	public void roleDel() {
		try {
			String idStr = this.getPara("id");
			if (StringUtil.isEmpty(idStr)) {
				this.setMesg("请选择要删除的角色");
				return;
			}
			String[] ids = null;
			if (idStr.contains(",")) {
				idStr = idStr.substring(0, idStr.length() - 1);
				ids = idStr.split(",");
			} else {
				ids = new String[] { idStr };
			}
			for(int i = 0 ; i < ids.length; i++) {
				Role.dao.deleteById(Integer.valueOf(ids[i]));
				AdminUserRole.dao.delRoleByRId(Integer.valueOf(ids[i]));
				RoleSecqurity.dao.delSecqurityByRoleId(Integer.valueOf(ids[i]));
			}
			this.setMesg(true, "删除成功", true);
		} catch (Exception e) {
			this.setMesg("删除失败"+e.getCause().getMessage());
		}
	}
	@Before(Tx.class)
	public void roleToAuthor() {
		if (this.reqGet()) {
			String id = getPara("id");
			List<RoleSecqurity> roleSecqurities = RoleSecqurity.dao.find("SELECT * FROM tb_role_secqurity WHERE rid = ?", id);
			List<String> list = new ArrayList<>();
			for(RoleSecqurity rSecqurity : roleSecqurities) {
				list.add(String.valueOf(rSecqurity.getInt("sid")));
			}
			String[] strings = (String[]) list.toArray(new String[list.size()]);
			this.setAttr("id", id);
			this.setAttr("list", list);
			this.setAttr("strs", strings);
			renderJsp("authorofrole.jsp");
		} else {
			try{
				int id = getParaToInt("id");
				String sec = getPara("sec");
				if(StringUtil.isEmpty(sec)) {
					this.setMesg("请选择要分配的权限");
					return;
				}
				RoleSecqurity.dao.delSecqurityByRoleId(id);
				String[] strs = sec.split(",");
				List<RoleSecqurity> modelList = new ArrayList<>();
				for(int i = 0; i < strs.length; i++) {
					RoleSecqurity secqurity = new RoleSecqurity().set("rid", id).set("sid", strs[i]);
					modelList.add(secqurity);
				}
				Db.batchSave(modelList, modelList.size()); 
				this.setMesg(true, "分配成功", true);
			}catch(Exception e){
				this.setMesg("分配失敗"+ e.getMessage());
			}
		}
	}
}
