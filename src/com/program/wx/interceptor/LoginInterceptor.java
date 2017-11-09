package com.program.wx.interceptor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.Record;
import com.program.wx.config.Global;
import com.program.wx.controller.BaseController;
import com.program.wx.model.AdminUser;
import com.program.wx.model.Secqurity;
import com.program.wx.utils.Menu;
import com.program.wx.utils.MenuUtil;

public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		String url = inv.getActionKey();
		BaseController controller = (BaseController) inv.getController();
		HttpSession session = controller.getSession();
		AdminUser adminUser = (AdminUser) session.getAttribute(Global.USER_INFO);
		JSONArray secqs = (JSONArray) session.getAttribute(Global.SECQURITIES);
		LinkedList<Menu> menus = (LinkedList<Menu>) session.getAttribute(Global.SECQURITYMENU);
		// 是否登录验证,先判断是不是login请求，然后在判断是否登录过
		if (adminUser == null) {
			controller.redirect("/login");
			return;
		}
		if (secqs == null) {
			List<Record> list = Secqurity.dao.findSecqurityByUser(adminUser.getStr("username"));
			JSONArray array = new JSONArray();
			for (Record record : list) {
				Map<String, Object> map = record.getColumns();
				JSONObject object = new JSONObject();
				for (String key : map.keySet()) {
					object.put(key, map.get(key));
				}
				array.add(object);
			}
			session.setAttribute(Global.SECQURITIES, array);
			session.setAttribute(Global.MENUS, MenuUtil.paresMenu(array, 0));
		}
		if(menus == null) {
			List<Record> list = Secqurity.dao.findSecqurityByAll();
			JSONArray array = new JSONArray();
			for (Record record : list) {
				Map<String, Object> map = record.getColumns();
				JSONObject object = new JSONObject();
				for (String key : map.keySet()) {
					object.put(key, map.get(key));
				}
				array.add(object);
			}
			session.setAttribute(Global.SECQURITYMENU, MenuUtil.paresMenuList(array, 0, 0));
		}
		inv.invoke();
	}

}
