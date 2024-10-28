package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Article;
import com.entity.Bbs;
import com.entity.Cate;
import com.entity.Complains;
import com.entity.Fav;
import com.entity.Hist;
import com.entity.Music;
import com.entity.Rebbs;
import com.entity.Topic;
import com.entity.Users;
import com.service.ArticleService;
import com.service.BbsService;
import com.service.CateService;
import com.service.ComplainsService;
import com.service.FavService;
import com.service.HistService;
import com.service.MusicService;
import com.service.RebbsService;
import com.service.RecommendService;
import com.service.TopicService;
import com.service.UsersService;
import com.util.PageHelper;
import com.util.VeDate;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/index")
public class IndexController extends BaseController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CateService cateService;
	@Autowired
	private MusicService musicService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private FavService favService;
	@Autowired
	private HistService histService;
	@Autowired
	private BbsService bbsService;
	@Autowired
	private RebbsService rebbsService;
	@Autowired
	private ComplainsService complainsService;
	@Autowired
	private RecommendService recommendService;

	// 公共方法 提供公共查询数据
	private void front() {
		this.getRequest().setAttribute("title", "音乐网站");
		List<Cate> cateList = this.cateService.getAllCate();
		this.getRequest().setAttribute("cateList", cateList);
		List<Music> hotList = this.musicService.getMusicByHot();
		this.getRequest().setAttribute("hotList", hotList);
	}

	// 公共方法 提供协同过滤数推荐据
	private void recommend() {
		// 推荐音乐的算法 1 用户未登录 直接调用musicService的getMusicByHot方法
		// 2 用户登录 但是没有浏览音乐 则调用1
		// 3 用户登录 且有浏览的音乐 调用recommendService 通过协同过滤算法 自动推荐音乐
		if (this.getSession().getAttribute("userid") == null) {
			List<Music> recommendList = this.musicService.getMusicByHot();
			this.getRequest().setAttribute("recommendList", recommendList);
		} else {
			String userid = (String) this.getSession().getAttribute("userid");
			Hist hist = new Hist();
			hist.setUsersid(userid);
			List<Hist> histList = this.histService.getHistByCond(hist);
			if (histList.size() == 0) {
				List<Music> recommendList = this.musicService.getMusicByHot();
				this.getRequest().setAttribute("recommendList", recommendList);
			} else {
				List<Music> recommendList = this.recommendService.getRecommend(userid);
				this.getRequest().setAttribute("recommendList", recommendList);
			}
		}
	}

	// 首页显示
	@RequestMapping("index.action")
	public String index() {
		this.front();
		this.recommend();
		List<Cate> cateList = this.cateService.getCateFront();
		List<Cate> frontList = new ArrayList<Cate>();
		for (Cate cate : cateList) {
			List<Music> musicList = this.musicService.getMusicByCate(cate.getCateid());
			cate.setMusicList(musicList);
			frontList.add(cate);
		}
		this.getRequest().setAttribute("frontList", frontList);
		return "users/index";
	}

	// 公告
	@RequestMapping("article.action")
	public String article(String number) {
		this.front();
		this.recommend();
		List<Article> tempList = this.articleService.getAllArticle();
		PageHelper.getIndexPage(tempList, "article", "article", null, 10, number, this.getRequest());
		return "users/article";
	}

	// 阅读公告
	@RequestMapping("read.action")
	public String read(String id) {
		this.front();
		Article article = this.articleService.getArticleById(id);
		article.setHits("" + (Integer.parseInt(article.getHits()) + 1));
		this.articleService.updateArticle(article);
		this.getRequest().setAttribute("article", article);
		return "users/read";
	}

	@RequestMapping("music.action")
	public String music(String number) {
		this.front();
		this.recommend();
		List<Music> tempList = this.musicService.getAllMusic();
		PageHelper.getIndexPage(tempList, "music", "music", null, 12, number, this.getRequest());
		return "users/list";
	}

	@RequestMapping("cate.action")
	public String cate(String number, String id) {
		this.front();
		this.recommend();
		Music music = new Music();
		music.setCateid(id);
		List<Music> tempList = this.musicService.getMusicByCond(music);
		PageHelper.getIndexPage(tempList, "music", "music", null, 12, number, this.getRequest());
		return "users/list";
	}

	// 阅读公告
	@RequestMapping("detail.action")
	public String detail(String id) {
		this.front();
		this.recommend();
		Music music = this.musicService.getMusicById(id);
		music.setHits("" + (Integer.parseInt(music.getHits()) + 1));
		this.musicService.updateMusic(music);
		this.getRequest().setAttribute("music", music);
		Topic topic = new Topic();
		topic.setMusicid(id);
		List<Topic> topicList = this.topicService.getTopicByCond(topic);
		this.getRequest().setAttribute("topicList", topicList);
		this.getRequest().setAttribute("tnum", topicList.size());
		if (this.getSession().getAttribute("userid") != null) {
			String userid = (String) this.getSession().getAttribute("userid");
			Hist hist = new Hist();
			hist.setUsersid(userid);
			hist.setMusicid(id);
			List<Hist> histList = this.histService.getHistByCond(hist);
			if (histList.size() == 0) {
				hist.setNum("1");
				this.histService.insertHist(hist);
			} else {
				Hist h = histList.get(0);
				h.setNum("" + (Integer.parseInt(h.getNum()) + 1));
				this.histService.updateHist(h);
			}
		}
		return "users/detail";
	}

	@RequestMapping("play.action")
	public String play(String id) {
		this.front();
		Music music = this.musicService.getMusicById(id);
		music.setNum("" + (Integer.parseInt(music.getNum()) + 1));
		this.musicService.updateMusic(music);
		this.getRequest().setAttribute("music", music);
		return "users/play";
	}

	@SuppressWarnings("resource")
	@RequestMapping("download.action")
	public void download(String id, HttpServletResponse response) throws Exception {
		this.front();
		Music music = this.musicService.getMusicById(id);
		String fileName = this.getRequest().getSession().getServletContext().getRealPath("/") + music.getFileurl();
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
		String filename = music.getMusicname();
		filename = URLEncoder.encode(filename, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
		while ((len = bis.read()) != -1) {
			out.write(len);
			out.flush();
		}
		out.close();
	}

	@RequestMapping("query.action")
	public String query(String name) {
		this.front();
		Music music = new Music();
		music.setMusicname(name);
		List<Music> musicList = this.musicService.getMusicByLike(music);
		this.getRequest().setAttribute("musicList", musicList);
		return "users/list";
	}

	// 准备登录
	@RequestMapping("preLogin.action")
	public String prelogin() {
		this.front();
		return "users/login";
	}

	// 用户登录
	@RequestMapping("login.action")
	public String login() {
		this.front();
		String username = this.getRequest().getParameter("username");
		String password = this.getRequest().getParameter("password");
		Users u = new Users();
		u.setUsername(username);
		List<Users> usersList = this.usersService.getUsersByCond(u);
		if (usersList.size() == 0) {
			this.getSession().setAttribute("message", "用户名不存在");
			return "redirect:/index/preLogin.action";
		} else {
			Users users = usersList.get(0);
			if ("锁定".equals(users.getStatus())) {
				this.getSession().setAttribute("message", "账户被锁定");
				return "redirect:/index/preLogin.action";
			}
			if (password.equals(users.getPassword())) {
				this.getSession().setAttribute("userid", users.getUsersid());
				this.getSession().setAttribute("username", users.getUsername());
				this.getSession().setAttribute("users", users);
				return "redirect:/index/index.action";
			} else {
				this.getSession().setAttribute("message", "密码错误");
				return "redirect:/index/preLogin.action";
			}
		}
	}

	// 准备注册
	@RequestMapping("preReg.action")
	public String preReg() {
		this.front();
		return "users/register";
	}

	// 用户注册
	@RequestMapping("register.action")
	public String register(Users users) {
		this.front();
		Users u = new Users();
		u.setUsername(users.getUsername());
		List<Users> usersList = this.usersService.getUsersByCond(u);
		if (usersList.size() == 0) {
			users.setStatus("正常");
			users.setRegdate(VeDate.getStringDateShort());
			this.usersService.insertUsers(users);
		} else {
			this.getSession().setAttribute("message", "用户名已存在");
			return "redirect:/index/preReg.action";
		}

		return "redirect:/index/preLogin.action";
	}

	// 退出登录
	@RequestMapping("exit.action")
	public String exit() {
		this.front();
		this.getSession().removeAttribute("userid");
		this.getSession().removeAttribute("username");
		this.getSession().removeAttribute("users");
		return "redirect:/index/index.action";
	}

	// 准备修改密码
	@RequestMapping("prePwd.action")
	public String prePwd() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/editpwd";
	}

	// 修改密码
	@RequestMapping("editpwd.action")
	public String editpwd() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		String password = this.getRequest().getParameter("password");
		String repassword = this.getRequest().getParameter("repassword");
		Users users = this.usersService.getUsersById(userid);
		if (password.equals(users.getPassword())) {
			users.setPassword(repassword);
			this.usersService.updateUsers(users);
		} else {
			this.getSession().setAttribute("message", "旧密码错误");
			return "redirect:/index/prePwd.action";
		}
		return "redirect:/index/prePwd.action";
	}

	@RequestMapping("usercenter.action")
	public String usercenter() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/usercenter";
	}

	@RequestMapping("userinfo.action")
	public String userinfo() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		this.getSession().setAttribute("users", this.usersService.getUsersById(userid));
		return "users/userinfo";
	}

	@RequestMapping("personal.action")
	public String personal(Users users) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.usersService.updateUsers(users);
		return "redirect:/index/userinfo.action";
	}

	// 留言板
	@RequestMapping("bbs.action")
	public String bbs() {
		this.front();
		List<Bbs> bbsList = this.bbsService.getAllBbs();
		this.getRequest().setAttribute("bbsList", bbsList);
		return "users/bbs";
	}

	// 发布留言
	@RequestMapping("addbbs.action")
	public String addbbs() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Bbs bbs = new Bbs();
		bbs.setAddtime(VeDate.getStringDate());
		bbs.setContents(getRequest().getParameter("contents"));
		bbs.setHits("0");
		bbs.setRepnum("0");
		bbs.setTitle(getRequest().getParameter("title"));
		bbs.setUsersid(userid);
		this.bbsService.insertBbs(bbs);
		return "redirect:/index/bbs.action";
	}

	// 查看留言
	@RequestMapping("readbbs.action")
	public String readbbs() {
		this.front();
		Bbs bbs = this.bbsService.getBbsById(getRequest().getParameter("id"));
		bbs.setHits("" + (Integer.parseInt(bbs.getHits()) + 1));
		this.bbsService.updateBbs(bbs);
		this.getRequest().setAttribute("bbs", bbs);
		Rebbs rebbs = new Rebbs();
		rebbs.setBbsid(bbs.getBbsid());
		List<Rebbs> rebbsList = this.rebbsService.getRebbsByCond(rebbs);
		this.getRequest().setAttribute("rebbsList", rebbsList);
		return "users/readbbs";
	}

	// 回复留言
	@RequestMapping("rebbs.action")
	public String rebbs() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Rebbs rebbs = new Rebbs();
		rebbs.setAddtime(VeDate.getStringDate());
		rebbs.setContents(getRequest().getParameter("contents"));
		rebbs.setBbsid(getRequest().getParameter("bbsid"));
		rebbs.setUsersid(userid);
		this.rebbsService.insertRebbs(rebbs);
		Bbs bbs = this.bbsService.getBbsById(rebbs.getBbsid());
		bbs.setRepnum("" + (Integer.parseInt(bbs.getRepnum()) + 1));
		this.bbsService.updateBbs(bbs);
		String path = "redirect:/index/readbbs.action?id=" + bbs.getBbsid();
		return path;
	}

	// 添加收藏
	@RequestMapping("addfav.action")
	public String addfav(Fav fav) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		fav.setAddtime(VeDate.getStringDateShort());
		fav.setMusicid(getRequest().getParameter("id"));
		fav.setUsersid(userid);
		this.favService.insertFav(fav);
		return "redirect:/index/myfav.action";
	}

	// 我的收藏
	@RequestMapping("myfav.action")
	public String myfav(String number) {
		this.front();
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Fav fav = new Fav();
		fav.setUsersid(userid);
		List<Fav> favList = this.favService.getFavByCond(fav);
		PageHelper.getIndexPage(favList, "fav", "myfav", null, 10, number, this.getRequest());
		return "users/myfav";
	}

	// 删除收藏
	@RequestMapping("deletefav.action")
	public String deletefav() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.favService.deleteFav(this.getRequest().getParameter("id"));
		return "redirect:/index/myfav.action";
	}

	@RequestMapping("preComplains.action")
	public String preComplains() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/addComplains";
	}

	@RequestMapping("addComplains.action")
	public String addComplains(Complains complains) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		complains.setAddtime(VeDate.getStringDateShort());
		complains.setStatus("未回复");
		complains.setUsersid(userid);
		this.complainsService.insertComplains(complains);
		return "redirect:/index/preComplains.action";
	}

	@RequestMapping("myComplains.action")
	public String myComplains(String number) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Complains complains = new Complains();
		complains.setUsersid(userid);
		List<Complains> complainsList = this.complainsService.getComplainsByCond(complains);
		PageHelper.getIndexPage(complainsList, "complains", "myComplains", null, 10, number, this.getRequest());
		return "users/myComplains";
	}

	@RequestMapping("addTopic.action")
	public String addTopic(Topic topic) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		topic.setAddtime(VeDate.getStringDate());
		topic.setUsersid(userid);
		this.topicService.insertTopic(topic);
		String path = "redirect:/index/detail.action?id=" + topic.getMusicid();
		return path;
	}
}
