package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entity.Music;

@Service("recommendService")
public interface RecommendService {
	public List<Music> getRecommend(String userid);
}
