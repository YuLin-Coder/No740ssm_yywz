package com.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Music;

@Service("musicService")
public interface MusicService {
	// 插入数据 调用musicDAO里的insertMusic配置
	public int insertMusic(Music music);

	// 更新数据 调用musicDAO里的updateMusic配置
	public int updateMusic(Music music);

	// 删除数据 调用musicDAO里的deleteMusic配置
	public int deleteMusic(String musicid);

	// 查询全部数据 调用musicDAO里的getAllMusic配置
	public List<Music> getAllMusic();

	public List<Music> getMusicByNews();

	public List<Music> getMusicByHot();

	public List<Music> getMusicByCate(String cateid);

	// 按照Music类里面的字段名称精确查询 调用musicDAO里的getMusicByCond配置
	public List<Music> getMusicByCond(Music music);

	// 按照Music类里面的字段名称模糊查询 调用musicDAO里的getMusicByLike配置
	public List<Music> getMusicByLike(Music music);

	// 按主键查询表返回单一的Music实例 调用musicDAO里的getMusicById配置
	public Music getMusicById(String musicid);

}
