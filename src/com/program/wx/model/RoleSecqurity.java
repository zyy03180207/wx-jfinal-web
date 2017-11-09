package com.program.wx.model;

import com.jfinal.plugin.activerecord.Model;

public class RoleSecqurity extends Model<RoleSecqurity> {

	public static final RoleSecqurity dao = new RoleSecqurity();

	public boolean addSecqurityByRole(int sid, int rid) {
		RoleSecqurity secqurity = new RoleSecqurity();
		return secqurity.set("sid", sid).set("rid", rid).save();
	}
	
}
