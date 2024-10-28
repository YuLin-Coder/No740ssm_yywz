package com.entity;
import com.util.VeDate;
public class Hist {
	private String histid = "H"+VeDate.getStringId();//生成主键编号
	private String usersid;//用户
	private String musicid;//音乐
	private String num;//次数
	private String username;// 映射数据
	private String musicname;// 映射数据
	public String getHistid() { return histid; }
	public void setHistid(String histid) { this.histid = histid; }
	public String getUsersid() { return this.usersid; }
	public void setUsersid(String usersid) { this.usersid = usersid; }
	public String getMusicid() { return this.musicid; }
	public void setMusicid(String musicid) { this.musicid = musicid; }
	public String getNum() { return this.num; }
	public void setNum(String num) { this.num = num; }
	public String getUsername() { return this.username; }
	public void setUsername(String username) { this.username = username; }
	public String getMusicname() { return this.musicname; }
	public void setMusicname(String musicname) { this.musicname = musicname; }

// 重载方法 生成JSON类型字符串 
@Override
public String toString() {
return "Hist [histid="+this.histid+", usersid="+this.usersid+", musicid="+this.musicid+", num="+this.num+", username="+this.username+", musicname="+this.musicname+"]";
}



}




/**
* 
*/
