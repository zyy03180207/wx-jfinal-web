package com.program.wx.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.program.wx.model.Fans;
import com.program.wx.model.Role;

public class FansController extends BaseController {

	public void index() {

	}

	public void fansList() {
		String pageIndex = getPara("pageIndex");
		String pageSize = getPara("pageSize");
		Page<Record> page = Fans.dao.findFansByPage(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
		if (page != null) {
			List<Record> list = page.getList();
			JSONArray array = paresList(list);
			this.setPageJson(array, page.getTotalRow(), "查询成功");
		} else {
			JSONArray array = new JSONArray();
			this.setPageJson(array, 0, "查询失败");
		}
		renderJson();
	}

}
