package com.entity;
import com.util.VeDate;
public class Topic {
	private String topicid = "T"+VeDate.getStringId();//生成主键编号
	private String usersid;//用户
	private String musicid;//音乐
	private String num;//评分
	private String contents;//内容
	private String addtime;//日期
	private String username;// 映射数据
	private String musicname;// 映射数据
	public String getTopicid() { return topicid; }
	public void setTopicid(String topicid) { this.topicid = topicid; }
	public String getUsersid() { return this.usersid; }
	public void setUsersid(String usersid) { this.usersid = usersid; }
	public String getMusicid() { return this.musicid; }
	public void setMusicid(String musicid) { this.musicid = musicid; }
	public String getNum() { return this.num; }
	public void setNum(String num) { this.num = num; }
	public String getContents() { return this.contents; }
	public void setContents(String contents) { this.contents = contents; }
	public String getAddtime() { return this.addtime; }
	public void setAddtime(String addtime) { this.addtime = addtime; }
	public String getUsername() { return this.username; }
	public void setUsername(String username) { this.username = username; }
	public String getMusicname() { return this.musicname; }
	public void setMusicname(String musicname) { this.musicname = musicname; }

// 重载方法 生成JSON类型字符串 
@Override
public String toString() {
return "Topic [topicid="+this.topicid+", usersid="+this.usersid+", musicid="+this.musicid+", num="+this.num+", contents="+this.contents+", addtime="+this.addtime+", username="+this.username+", musicname="+this.musicname+"]";
}



}




/**
* 
*/
