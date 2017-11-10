package com.program.wx.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class RoleSecqurity extends Model<RoleSecqurity> {

	private static final long serialVersionUID = 645783219672644165L;
	
	public static final RoleSecqurity dao = new RoleSecqurity();

	public boolean addSecqurityByRole(int sid, int rid) {
		RoleSecqurity secqurity = new RoleSecqurity();
		return secqurity.set("sid", sid).set("rid", rid).save();
	}
		
	public void delSecqurityByRoleId(int rid) {
		String sql = "DELETE FROM tb_role_secqurity where rid = ?";
		Db.update(sql, rid); 
	}
	
	public void delSecqurityBySId(int sid) {
		String sql = "DELETE FROM tb_role_secqurity where sid = ?";
		Db.update(sql, sid); 
	}
}	
