package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.HistDAO;
import com.dao.MusicDAO;
import com.dao.UsersDAO;
import com.entity.Hist;
import com.entity.Music;
import com.entity.Users;
import com.service.RecommendService;

@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {
	@Autowired
	private HistDAO histDAO;
	// 注入DAO对象
	@Autowired
	private MusicDAO musicDAO;
	@Autowired
	private UsersDAO usersDAO;
	private String userid = "";
	// 相似用户集合
	private List<List<Object>> similarityUsers = null;
	// 推荐所有音乐集合
	private List<String> targetRecommendMusic = null;
	// 浏览过音乐集合
	private List<String> commentedMusic = null;
	// 用户在音乐浏览集合中的位置
	private int targetUserIndex = 0;
	// 目标用户浏览过的音乐
	private List<String> targetUserCommentedMusic = null;

	private String[] music = null;

	@Override
	public List<Music> getRecommend(String userid) {
		this.userid = userid;
		// 建立用户数组 除了当前用户外 随机抽取9个用户
		String[] users = new String[10];
		users[0] = this.userid;
		List<Users> usersList = this.usersDAO.getUsers(this.userid);
		System.out.println("users == > " + usersList.size());
		for (int i = 0; i < 9; i++) {
			int j = i + 1;
			int tbNum = usersList.size();
			if (i < tbNum) {
				users[j] = usersList.get(i).getUsersid();
			} else {
				users[j] = "0";
			}
		}
		List<Music> musicList = this.musicDAO.getAllMusic();
		this.music = new String[musicList.size()];
		for (int j = 0; j < musicList.size(); j++) {
			this.music[j] = musicList.get(j).getMusicid();
		}
		// 建立浏览二维数组 用户浏览了音乐 1 未浏览 0 之后计算用户的相似度
		int[][] allUserMusichist = new int[10][musicList.size()];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < musicList.size(); j++) {
				String musicid = this.music[j];
				Hist hist = new Hist();
				hist.setUsersid(users[i]);
				hist.setMusicid(musicid);
				List<Hist> histList = this.histDAO.getHistByCond(hist);
				if (histList.size() == 0) {
					allUserMusichist[i][j] = 0;
				} else {
					Hist h = histList.get(0);
					allUserMusichist[i][j] = Integer.parseInt(h.getNum());
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < musicList.size(); j++) {
				if (j == musicList.size() - 1) {
					System.out.print(allUserMusichist[i][j]);
				} else {
					System.out.print(allUserMusichist[i][j] + " ,");
				}
			}
			System.out.println("");
		}
		this.targetUserCommentedMusic = new ArrayList<String>();
		Hist hist = new Hist();
		hist.setUsersid(this.userid);
		List<Hist> histList = this.histDAO.getHistByCond(hist);
		for (int i = 0; i < histList.size(); i++) {
			// 转换目标用户音乐浏览列表
			this.targetUserCommentedMusic.add(histList.get(i).getHistid());
		}

		// 计算用户相似度，排序
		this.calcUserSimilarity(allUserMusichist, musicList.size());
		// 计算音乐推荐度，排序
		this.calcRecommendMusic(allUserMusichist, musicList.size());
		// 处理推荐音乐列表
		this.handleRecommendMusic(allUserMusichist, musicList.size());
		String rommendId = "";
		for (int i = 0; i < this.targetRecommendMusic.size(); i++) {
			String item = this.targetRecommendMusic.get(i);
			if (!commentedMusic.contains(item)) {
				if (i == this.targetRecommendMusic.size() - 1) {
					rommendId += item;
				} else {
					rommendId += item + ",";
				}
			}
		}
		String[] str = rommendId.split(",");
		List<Music> mList = new ArrayList<Music>();
		List<Music> musList = new ArrayList<Music>();
		int musicize = 0;
		if (!"".equals(rommendId)) {
			for (String x : str) {
				Music g = this.musicDAO.getMusicById(x);
				mList.add(g);
			}
			if (mList.size() < 10) {
				musicize = 10 - musList.size();
				List<Music> list = this.musicDAO.getMusicByHot();
				for (int i = 0; i < musicize; i++) {
					Music x = list.get(i);
					mList.add(x);
				}
			} else if (musList.size() > 10) {
				mList = new ArrayList<Music>();
				for (int i = 0; i < 10; i++) {
					Music x = musList.get(i);
					mList.add(x);
				}
			}
		} else {
			mList = this.musicDAO.getMusicByHot();
		}
		// 返回推荐音乐
		return mList;
	}

	private void calcRecommendMusic(int[][] allUserMusichist, int musicNum) {
		this.targetRecommendMusic = new ArrayList<String>();
		List<List<Object>> recommendMusic = new ArrayList<List<Object>>();
		List<Object> recommendMus = null;
		double recommdRate = 0, sumRate = 0;
		for (int i = 0; i < musicNum; i++) {
			recommendMus = new ArrayList<Object>();
			recommendMus.add(i);
			recommdRate = allUserMusichist[Integer.parseInt(similarityUsers.get(0).get(0).toString())][i]
					* Double.parseDouble(similarityUsers.get(0).get(1).toString())
					+ allUserMusichist[Integer.parseInt(similarityUsers.get(1).get(0).toString())][i]
							* Double.parseDouble(similarityUsers.get(1).get(1).toString());
			recommendMus.add(recommdRate);
			recommendMusic.add(recommendMus);
			sumRate += recommdRate;
		}
		System.out.println("sumRate  == > " + sumRate / musicNum);
		recommendMusic = compare(recommendMusic);
		for (List<Object> tList : recommendMusic) {
			System.out.println(tList.get(1));
		}
		// 大于平均推荐度的音乐才有可能被推荐
		for (int i = 0; i < recommendMusic.size(); i++) {
			List<Object> item = recommendMusic.get(i);
			if (Double.parseDouble(item.get(1).toString()) > sumRate / musicNum) { // 大于平均推荐度的音乐才有可能被推荐
				System.out.println("music= = >" + music[Integer.parseInt(item.get(0).toString())]);
				this.targetRecommendMusic.add(music[Integer.parseInt(item.get(0).toString())]);
			}
		}
		for (String x : this.targetRecommendMusic) {
			System.out.println("x= = >" + x);
		}
	}

	/**
	 * 把推荐列表中用户已经浏览过的音乐剔除
	 */
	private void handleRecommendMusic(int[][] allUserMusichist, int musicNum) {
		int[] user2hist = new int[musicNum];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < musicNum; j++) {
				user2hist[j] = allUserMusichist[i][j];
			}
		}
		commentedMusic = new ArrayList<String>();
		for (int i = 0; i < user2hist.length; i++) {
			if (allUserMusichist[0][i] != 0) {
				commentedMusic.add(music[i]);
			}
		}
	}

	/**
	 * 获取两个最相似的用户
	 */
	private void calcUserSimilarity(int[][] allUserMusichist, int musicNum) {
		int[] user2hist = new int[musicNum];
		List<List<Object>> tmpList = new ArrayList<List<Object>>();

		for (int i = 0; i < 10; i++) {
			if (i == targetUserIndex) {
				for (int j = 0; j < musicNum; j++) {
					user2hist[j] = allUserMusichist[i][j];
				}
				continue;
			}
			List<Object> userSimilarity = new ArrayList<Object>();
			int[] user1hist = new int[musicNum];
			for (int j = 0; j < musicNum; j++) {
				user1hist[j] = allUserMusichist[i][j];
			}
			userSimilarity.add(i);
			userSimilarity.add(calcTwoUserSimilarity(user1hist, user2hist, musicNum));
			tmpList.add(userSimilarity);
		}
		this.similarityUsers = compare(tmpList);
	}

	/**
	 * 根据用户数据，计算用户相似度
	 * 
	 * @param user1hist
	 * @param user2hist
	 * @return
	 */
	private static double calcTwoUserSimilarity(int[] user1hist, int[] user2hist, int musicNum) {
		double sum = 0;
		for (int i = 0; i < musicNum; i++) {
			sum += Math.pow(user1hist[i] - user2hist[i], 2);
		}
		return Math.sqrt(sum);
	}

	/**
	 * 集合排序
	 */
	private static List<List<Object>> compare(List<List<Object>> tmpList) {
		for (int i = 0; i < tmpList.size(); i++) {
			for (int j = 0; j < tmpList.size() - i; j++) {
				List<Object> t1 = tmpList.get(i);
				List<Object> t2 = tmpList.get(j);
				if (Double.parseDouble("" + t1.get(1)) > Double.parseDouble("" + t2.get(1))) {
					List<Object> tmp = new ArrayList<Object>();
					tmp = t1;
					tmpList.set(i, t2);
					tmpList.set(j, tmp);
				}
			}
		}
		return tmpList;
	}

}
