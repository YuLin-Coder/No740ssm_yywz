package com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.MusicDAO;
import com.entity.Music;
import com.service.MusicService;

@Service("musicService")
public class MusicServiceImpl implements MusicService {
	@Autowired
	private MusicDAO musicDAO;

	@Override // 继承接口的新增 返回值0(失败),1(成功)
	public int insertMusic(Music music) {
		return this.musicDAO.insertMusic(music);
	}

	@Override // 继承接口的更新 返回值0(失败),1(成功)
	public int updateMusic(Music music) {
		return this.musicDAO.updateMusic(music);
	}

	@Override // 继承接口的删除 返回值0(失败),1(成功)
	public int deleteMusic(String musicid) {
		return this.musicDAO.deleteMusic(musicid);
	}

	@Override // 继承接口的查询全部
	public List<Music> getAllMusic() {
		return this.musicDAO.getAllMusic();
	}

	@Override // 继承接口的查询全部
	public List<Music> getMusicByNews() {
		return this.musicDAO.getMusicByNews();
	}

	@Override // 继承接口的查询全部
	public List<Music> getMusicByHot() {
		return this.musicDAO.getMusicByHot();
	}

	@Override // 继承接口的查询全部
	public List<Music> getMusicByCate(String cateid) {
		return this.musicDAO.getMusicByCate(cateid);
	}

	@Override // 继承接口的按条件精确查询
	public List<Music> getMusicByCond(Music music) {
		return this.musicDAO.getMusicByCond(music);
	}

	@Override // 继承接口的按条件模糊查询
	public List<Music> getMusicByLike(Music music) {
		return this.musicDAO.getMusicByLike(music);
	}

	@Override // 继承接口的按主键查询 返回Entity实例
	public Music getMusicById(String musicid) {
		return this.musicDAO.getMusicById(musicid);
	}

}

// 
