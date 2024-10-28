package com.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entity.Music;
import com.service.MusicService;
import com.entity.Cate;
import com.service.CateService;
import com.util.PageHelper;
import com.util.VeDate;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/music" , produces = "text/plain;charset=utf-8")
public class MusicController extends BaseController {
	// 注入Service 由于标签的存在 所以不需要getter setter
	@Autowired
	private MusicService musicService;
	@Autowired
	private CateService cateService;

	// 准备添加数据
	@RequestMapping("createMusic.action")
	public String createMusic() {
		List<Cate> cateList = this.cateService.getAllCate();
		this.getRequest().setAttribute("cateList", cateList);
		return "admin/addmusic";
	}
	// 添加数据
	@RequestMapping("addMusic.action")
	public String addMusic(Music music) {
		music.setAddtime(VeDate.getStringDateShort());
		music.setHits("0");
		music.setNum("0");
		this.musicService.insertMusic(music);
		return "redirect:/music/createMusic.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteMusic.action")
	public String deleteMusic(String id) {
		this.musicService.deleteMusic(id);
		return "redirect:/music/getAllMusic.action";
	}

	// 批量删除数据
	@RequestMapping("deleteMusicByIds.action")
	public String deleteMusicByIds() {
		String[] ids = this.getRequest().getParameterValues("musicid");
		for (String musicid : ids) {
			this.musicService.deleteMusic(musicid);
		}
		return "redirect:/music/getAllMusic.action";
	}

	// 更新数据
	@RequestMapping("updateMusic.action")
	public String updateMusic(Music music) {
		this.musicService.updateMusic(music);
		return "redirect:/music/getAllMusic.action";
	}

	// 显示全部数据
	@RequestMapping("getAllMusic.action")
	public String getAllMusic(String number) {
		List<Music> musicList = this.musicService.getAllMusic();
		PageHelper.getPage(musicList, "music", null, null, 10, number, this.getRequest(), null);
		return "admin/listmusic";
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryMusicByCond.action")
	public String queryMusicByCond(String cond, String name, String number) {
		Music music = new Music();
		if(cond != null){
			if ("musicname".equals(cond)) {
				music.setMusicname(name);
			}
			if ("image".equals(cond)) {
				music.setImage(name);
			}
			if ("cateid".equals(cond)) {
				music.setCateid(name);
			}
			if ("fileurl".equals(cond)) {
				music.setFileurl(name);
			}
			if ("singer".equals(cond)) {
				music.setSinger(name);
			}
			if ("album".equals(cond)) {
				music.setAlbum(name);
			}
			if ("addtime".equals(cond)) {
				music.setAddtime(name);
			}
			if ("hits".equals(cond)) {
				music.setHits(name);
			}
			if ("num".equals(cond)) {
				music.setNum(name);
			}
			if ("contents".equals(cond)) {
				music.setContents(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.musicService.getMusicByLike(music), "music", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/querymusic";
	}

	// 按主键查询数据
	@RequestMapping("getMusicById.action")
	public String getMusicById(String id) {
		Music music = this.musicService.getMusicById(id);
		this.getRequest().setAttribute("music", music);
		List<Cate> cateList = this.cateService.getAllCate();
		this.getRequest().setAttribute("cateList", cateList);
		return "admin/editmusic";
	}

	public MusicService getMusicService() { return musicService; }

	public void setMusicService(MusicService musicService) { this.musicService = musicService; }

}
// 
