package com.program.wx.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.program.wx.controller.AdminController;
import com.program.wx.controller.AuthorController;
import com.program.wx.controller.FansController;
import com.program.wx.controller.IndexController;
import com.program.wx.controller.RoleController;
import com.program.wx.interceptor.LoginInterceptor;
import com.program.wx.model.AdminUser;
import com.program.wx.model.AdminUserRole;
import com.program.wx.model.Fans;
import com.program.wx.model.Role;
import com.program.wx.model.RoleSecqurity;
import com.program.wx.model.Secqurity;

public class WXConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		PropKit.use("little_config.txt");
		me.setViewType(ViewType.JSP);
		me.setDevMode(true);
		me.setError404View("404.jsp");
		me.setError500View("500.jsp");
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/", IndexController.class, "/pages");
		me.add("/admin", AdminController.class, "/pages/admin");
		me.add("/role", RoleController.class, "/pages/role");
		me.add("/author", AuthorController.class, "/pages/secqurity");
		me.add("/fans", FansController.class, "pages/fans");
	}

	public static C3p0Plugin createDruidPlugin() {
		String jdbcUrl = new String(PropKit.get("jdbcUrl"));
		String driver = PropKit.get("driverClass");
		String username = new String(PropKit.get("username"));
		String password = new String(PropKit.get("password"));
		return new C3p0Plugin(jdbcUrl, username, password, driver);
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		C3p0Plugin plugin = createDruidPlugin();
		me.add(plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(plugin);
		arp.setShowSql(true);
		arp.setDialect(new MysqlDialect());
		me.add(arp);
		arp.addMapping("tb_admin_user", "id", AdminUser.class);
		arp.addMapping("tb_user_role", AdminUserRole.class);
		arp.addMapping("tb_fans", "cid", Fans.class);
		arp.addMapping("tb_role", "id", Role.class);
		arp.addMapping("tb_secqurity", "id", Secqurity.class);
		arp.addMapping("tb_role_secqurity", RoleSecqurity.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		me.add(new LoginInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJFinalStart() {
		// TODO Auto-generated method stub
		super.afterJFinalStart();
	}

	@Override
	public void beforeJFinalStop() {
		// TODO Auto-generated method stub
		super.beforeJFinalStop();
	}
}
