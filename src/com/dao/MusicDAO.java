package com.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.entity.Music;

@Repository("musicDAO") // Repository标签定义数据库连接的访问 Spring中直接
public interface MusicDAO {

	/**
	 * MusicDAO 接口 可以按名称直接调用music.xml配置文件的SQL语句
	 */

	// 插入数据 调用entity包music.xml里的insertMusic配置 返回值0(失败),1(成功)
	public int insertMusic(Music music);

	// 更新数据 调用entity包music.xml里的updateMusic配置 返回值0(失败),1(成功)
	public int updateMusic(Music music);

	// 删除数据 调用entity包music.xml里的deleteMusic配置 返回值0(失败),1(成功)
	public int deleteMusic(String musicid);

	// 查询全部数据 调用entity包music.xml里的getAllMusic配置 返回List类型的数据
	public List<Music> getAllMusic();

	public List<Music> getMusicByNews();

	public List<Music> getMusicByHot();

	public List<Music> getMusicByCate(String cateid);

	// 按照Music类里面的值精确查询 调用entity包music.xml里的getMusicByCond配置 返回List类型的数据
	public List<Music> getMusicByCond(Music music);

	// 按照Music类里面的值模糊查询 调用entity包music.xml里的getMusicByLike配置 返回List类型的数据
	public List<Music> getMusicByLike(Music music);

	// 按主键查询表返回单一的Music实例 调用entity包music.xml里的getMusicById配置
	public Music getMusicById(String musicid);

}
