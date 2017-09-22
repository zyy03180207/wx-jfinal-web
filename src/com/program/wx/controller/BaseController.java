package com.program.wx.controller;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class BaseController extends Controller {

	public boolean reqGet() {
		if (getRequest().getMethod().equals("GET"))
			return true;
		return false;
	}

	public void setMesg(String mesg) {
		setMesg(false, mesg, true);
	}

	public void setMesg(boolean succ, String mesg, boolean t) {
		this.setAttr("succ", succ);
		this.setAttr("mesg", mesg);
		if (t)
			renderJson();
	}

	public void setPageJson(JSONArray array, int count, String msg) {
		this.setAttr("code", 0);
		this.setAttr("list", array);
		this.setAttr("count", count);
		this.setAttr("msg", msg);
	}

	public JSONArray paresList(List<Record> list) {
		JSONArray array = new JSONArray();
		for (Record record : list) {
			Map<String, Object> map = record.getColumns();
			JSONObject object = new JSONObject();
			for (String key : map.keySet()) {
				object.put(key, map.get(key));
			}
			array.add(object);
		}
		return array;
	}

	public static String md5(String str) {
		try {
			byte[] data = str.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			return toHex(md.digest(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

}
