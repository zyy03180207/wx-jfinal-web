package com.program.wx.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.CaptchaRender;
import com.program.wx.config.Global;
import com.program.wx.model.AdminUser;
import com.program.wx.model.Fans;
import com.program.wx.utils.LoggerUtil;
import com.program.wx.utils.StringUtil;

public class IndexController extends BaseController {

	Logger logger = LoggerUtil.getLogger(IndexController.class);

	public void index() {

	}

	@Clear
	public void login() {
		this.getSession().removeAttribute(Global.USER_INFO);
		this.getSession().removeAttribute(Global.MENUS);
		this.getSession().removeAttribute(Global.SECQURITIES);
		this.getSession().removeAttribute(Global.KEY);
		this.getSession().removeAttribute(Global.SECQURITYMENU);
		renderJsp("login.jsp");
	}

	public void outLogin() {
		redirect("/login");
	}

	@Clear
	public void toLogin() {
		try {
			HttpSession session = this.getSession();
			String user = getPara("username");
			String pass = getPara("password");
			String vcode = getPara("vcode");
			if (StringUtil.isEmpty(user)) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "请输入用户名");
				renderJson();
				return;
			}
			if (StringUtil.isEmpty(pass)) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "请输入密码");
				renderJson();
				return;
			}
			if (StringUtil.isEmpty(vcode)) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "请输入验证码");
				renderJson();
				return;
			}
			if (!CaptchaRender.validate(this, vcode)) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "验证码不正确");
				renderJson();
				return;
			}
			AdminUser adminUser = AdminUser.dao.findUserByName(user);
			if (adminUser == null) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "用户不存在");
				renderJson();
				return;
			}
			if (!adminUser.get("password").equals(md5(pass))) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "用户名或密码不正确");
				renderJson();
				return;
			}
			if (adminUser.get("state").equals("0")) {
				this.setAttr("succ", false);
				this.setAttr("mesg", "该用户未启用，请联系管理员");
				renderJson();
				return;
			}
			this.setAttr("succ", true);
			this.setAttr("mesg", "登陆成功");
			session.setAttribute(Global.USER_INFO, adminUser);
			renderJson();
			return;
		} catch (Exception e) {
			this.setAttr("succ", false);
			this.setAttr("mesg", e.getCause().getMessage());
			renderJson();
		}
	}

	public void main() {
		renderJsp("main.jsp");
	}

	public void home() {
		AdminUser adminUser = (AdminUser) this.getSession().getAttribute(Global.USER_INFO);
		this.setAttr("adminUser", adminUser);
		renderJsp("index.jsp");
	}

	public void adminList() {
		renderJsp("admin/adminlist.jsp");
	}

	public void authorList() {
		renderJsp("secqurity/authorlist.jsp");
	}

	public void roleList() {
		renderJsp("role/rolelist.jsp");
	}

	@Clear
	public void randCode() {
		CaptchaRender img = new CaptchaRender();
		render(img);
	}

}