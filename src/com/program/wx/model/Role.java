package com.program.wx.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Role extends Model<Role> {

	public static final Role dao = new Role();

	public Page<Record> findRoleByPage(int pageIndex, int pageSize) {
		String sql = "FROM tb_role";
		return Db.paginate(pageIndex, pageSize, "SELECT * ", sql);
	}

	public List<Role> findRoleByAll() {
		return dao.find("SELECT * FROM tb_role");
	}
	
	public Role findRoleByName(String name) {
		String sql = "SELECT * FROM tb_role WHERE name = ?";
		return dao.findFirst(sql, name);
	}
}
