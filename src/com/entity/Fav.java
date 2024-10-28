package com.entity;

import com.util.VeDate;

public class Fav {
	private String favid = "F" + VeDate.getStringId();// 生成主键编号
	private String usersid;// 用户
	private String musicid;// 音乐
	private String addtime;// 收藏日期
	private String username;// 映射数据
	private String musicname;// 映射数据
	private String image;// 映射数据
	private String num;// 映射数据

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getFavid() {
		return favid;
	}

	public void setFavid(String favid) {
		this.favid = favid;
	}

	public String getUsersid() {
		return this.usersid;
	}

	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}

	public String getMusicid() {
		return this.musicid;
	}

	public void setMusicid(String musicid) {
		this.musicid = musicid;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMusicname() {
		return this.musicname;
	}

	public void setMusicname(String musicname) {
		this.musicname = musicname;
	}

	// 重载方法 生成JSON类型字符串
	@Override
	public String toString() {
		return "Fav [favid=" + this.favid + ", usersid=" + this.usersid + ", musicid=" + this.musicid + ", addtime="
				+ this.addtime + ", username=" + this.username + ", musicname=" + this.musicname + "]";
	}

}

/**
 * 
 */
