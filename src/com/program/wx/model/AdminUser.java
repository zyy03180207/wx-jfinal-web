package com.program.wx.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdminUser extends Model<AdminUser> {

	public static final AdminUser dao = new AdminUser();

	public AdminUser findUserByName(String username) {
		String sql = "SELECT * FROM tb_admin_user WHERE username = ?";
		return dao.findFirst(sql, username);
	}

	public Page<Record> findUserByPage(int pageIndex, int pageSize) {
		String sql = "FROM tb_admin_user a LEFT JOIN tb_user_role ur on a.id = ur.uid LEFT JOIN tb_role r on r.id = ur.rid";
		return Db.paginate(pageIndex, pageSize,
				"SELECT a.id as 'id',a.username as 'username',a.phone as 'phone',a.email as 'email',a.crtime as 'crtime',a.state as 'state',ur.rid as 'rid',ur.uid as 'uid',r.name as 'rname' ",
				sql);
	}
	
	public Record findUserById(int id) {
		String sql = "SELECT * FROM tb_admin_user t LEFT JOIN tb_user_role r ON t.id = r.uid WHERE t.id = ?";
		return Db.findFirst(sql, id);
	}
}
