package com.entity;

import com.util.VeDate;

public class Music {
	private String musicid = "M" + VeDate.getStringId();// 生成主键编号
	private String musicname;// 音乐名称
	private String image;// 音乐封面
	private String cateid;// 音乐类型
	private String fileurl;// 音乐地址
	private String singer;// 歌手
	private String album;// 专辑
	private String addtime;// 发布日期
	private String hits;// 点击数
	private String num;// 播放次数
	private String contents;// 音乐详情
	private String catename;// 映射数据

	public String getMusicid() {
		return musicid;
	}

	public void setMusicid(String musicid) {
		this.musicid = musicid;
	}

	public String getMusicname() {
		return this.musicname;
	}

	public void setMusicname(String musicname) {
		this.musicname = musicname;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCateid() {
		return this.cateid;
	}

	public void setCateid(String cateid) {
		this.cateid = cateid;
	}

	public String getFileurl() {
		return this.fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getSinger() {
		return this.singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getAlbum() {
		return this.album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getHits() {
		return this.hits;
	}

	public void setHits(String hits) {
		this.hits = hits;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCatename() {
		return this.catename;
	}

	public void setCatename(String catename) {
		this.catename = catename;
	}

	// 重载方法 生成JSON类型字符串
	@Override
	public String toString() {
		return "Music [musicid=" + this.musicid + ", musicname=" + this.musicname + ", image=" + this.image
				+ ", cateid=" + this.cateid + ", fileurl=" + this.fileurl + ", singer=" + this.singer + ", album="
				+ this.album + ", addtime=" + this.addtime + ", hits=" + this.hits + ", num=" + this.num + ", contents="
				+ this.contents + ", catename=" + this.catename + "]";
	}

}

/**
 * 
 */
