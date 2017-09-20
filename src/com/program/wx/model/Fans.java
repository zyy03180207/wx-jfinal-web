package com.program.wx.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Fans extends Model<Fans> {

	public static final Fans dao = new Fans();

	public List<Fans> findFansByAll() {
		return dao.find("SELECT * FROM tb_fans");
	}
	
	public Page<Record> findFansByPage(int pageIndex, int pageSize) {
		String sql = "FROM tb_fans";
		return Db.paginate(pageIndex, pageSize, "SELECT * ", sql);
	}
}
