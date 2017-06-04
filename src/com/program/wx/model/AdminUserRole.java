package com.program.wx.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class AdminUserRole extends Model<AdminUserRole> {

	public static final AdminUserRole dao = new AdminUserRole();

	public AdminUserRole findUserRoleByUid(int uid) {
		return dao.findFirst("SELECT * FROM tb_user_role WHERE uid = ?", uid);
	}
	
	public void updUserRoleByUid(int uid, int rid) {
		Db.update("UPDATE tb_user_role SET rid = ? WHERE uid = ?", rid, uid);
	}
}
