package com.program.wx.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Secqurity extends Model<Secqurity> {

	public static final Secqurity dao = new Secqurity();

	public List<Record> findSecqurityByUser(String username) {
		String sql = "SELECT s.id AS 'id',s.action AS 'action',s.des AS 'des',s.icon AS 'icon',s.ismenu AS 'ismenu',s.menu_name AS 'menu_name',s.pid AS 'pid' FROM tb_admin_user u LEFT JOIN tb_user_role ur ON u.id = ur.uid LEFT JOIN tb_role r ON ur.rid = r.id LEFT JOIN tb_role_secqurity rs ON r.id = rs.rid LEFT JOIN tb_secqurity s ON s.id = rs.sid WHERE u.username = ?";
		return Db.find(sql, username);
	}

	public Page<Record> findSecqurityByPage(int pageIndex, int pageSize) {
		String sql = "FROM tb_secqurity";
		return Db.paginate(pageIndex, pageSize, "SELECT * ", sql);
	}
	
	public List<Record> findSecqurityByAll() {
		String sql = "SELECT * FROM tb_secqurity";
		return Db.find(sql);
	}
	
	public Secqurity findSecByAction(String action) {
		String sql = "SELECT * FROM tb_secqurity s WHERE s.action = ?";
		return dao.findFirst(sql, action);
	}
}
