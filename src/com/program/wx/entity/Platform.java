package com.program.wx.entity;
/**
 * 平台
 * @author yangyang.zhang
 * @Package com.program.wx.entity 
 * @Date 2017年11月9日 下午3:54:08 
 * @Description TODO(用一句话描述该文件做什么)
 * @version V1.0
 */
public class Platform {
	
	private String platform;
	private String name;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Platform(String platform, String name) {
		super();
		this.platform = platform;
		this.name = name;
	}
	public Platform() {
		super();
		// TODO Auto-generated constructor stub
	}
}
