package com.service.securityinfo.impl;

import java.util.List;

import com.dao.securityinfo.IRewardPunishmentDao;
import com.entity.securityinfo.RewardPunishment;
import com.entity.securityinfo.RewardPunishmentCondition;
import com.service.securityinfo.IRewardPunishmentService;

public class RewardPunishmentServiceImp implements IRewardPunishmentService{

	private IRewardPunishmentDao rewardPunishmentDao;

	public IRewardPunishmentDao getRewardPunishmentDao() {
		return rewardPunishmentDao;
	}

	public void setRewardPunishmentDao(IRewardPunishmentDao rewardPunishmentDao) {
		this.rewardPunishmentDao = rewardPunishmentDao;
	}

	@Override
	public List queryRewardPunishment(
			RewardPunishmentCondition rewardPunishmentCondition) {
		return rewardPunishmentDao.queryRewardPunishment(rewardPunishmentCondition);
	}

	@Override
	public void createRewardPunishment(RewardPunishment rp) {
		rewardPunishmentDao.createRewardPunishment(rp);
		
	}

	@Override
	public Object queryRewardPunishByName(RewardPunishment rewardPunishment) {
		return rewardPunishmentDao.queryRewardPunishByName(rewardPunishment);
	}

	@Override
	public void updateRewardPunishment(RewardPunishment rewardPunishment) {
		rewardPunishmentDao.updateRewardPunishment(rewardPunishment);
		
	}

	@Override
	public void deleteRewardPunishment(RewardPunishment rewardPunishment) {
		rewardPunishmentDao.deleteRewardPunishment(rewardPunishment);
		
	}
	
	

}
