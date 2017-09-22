package com.program.wx.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.program.wx.model.Secqurity;
import com.program.wx.utils.StringUtil;
/**
 * 权限功能
 * @author yangyang.zhang
 * @Package com.program.wx.controller 
 * @Date 2017年9月20日 下午5:27:51 
 * @Description TODO(用一句话描述该文件做什么)
 * @version V1.0
 */
public class AuthorController extends BaseController {

	public void index() {

	}

	public void authorList() {
		String pageIndex = getPara("pageIndex");
		String pageSize = getPara("pageSize");
		Page<Record> page = Secqurity.dao.findSecqurityByPage(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
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

	public void authorAdd() {
		try {
			if (this.reqGet()) {
				String c = this.getPara("child");
				if (!StringUtil.isEmpty(c)) {
					this.setAttr("child", Integer.valueOf(c));
				} else {
					this.setAttr("child", -2);
				}
				renderJsp("authoradd.jsp");
			} else {
				String title = getPara("name");
				String pid = getPara("author");
				String des = getPara("des");
				String icon = getPara("icon");
				String action = getPara("uri");
				String ismenu = getPara("switch");
				if (StringUtil.isEmpty(title)) {
					this.setMesg("请输入名称");
					return;
				}
				if (StringUtil.isEmpty(des)) {
					this.setMesg("请输入权限描述");
					return;
				}
				if (StringUtil.isEmpty(action)) {
					this.setMesg("请输入权限连接");
					return;
				}
				if (!StringUtil.isEmpty(ismenu) && ismenu.equals("on")) {
					ismenu = "1";
				} else {
					ismenu = "0";
				}
				Secqurity secqurity = new Secqurity();
				boolean s = secqurity.set("action", action).set("des", des).set("menu_name", title).set("pid", pid)
						.set("ismenu", ismenu).set("icon", icon).save();
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

	public void authorEdit() {
		try {
			if (this.reqGet()) {
				String id = this.getPara("id");
				Secqurity secqurity = Secqurity.dao.findById(Integer.valueOf(id));
				this.setAttr("secqurity", secqurity);
				renderJsp("authoredit.jsp");
			}else{
				
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}
	
	public void authorDel() {
		try {
			String id = this.getPara("id");
			boolean s = Secqurity.dao.deleteById(Integer.valueOf(id));
			if (s) {
				this.setMesg(true, "删除成功", true);
			} else {
				this.setMesg("删除失败");
			}
		} catch (Exception e) {
			this.setMesg(e.getCause().getMessage());
		}
	}
}
