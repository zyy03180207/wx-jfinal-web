package com.program.wx.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Fans extends Model<Fans> {

	public static final Fans dao = new Fans();

	public List<Fans> findFansByAll() {
		return dao.find("SELECT * FROM tb_fans");
	}
}
