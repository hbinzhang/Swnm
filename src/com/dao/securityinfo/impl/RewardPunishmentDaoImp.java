package com.dao.securityinfo.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.securityinfo.IRewardPunishmentDao;
import com.entity.securityinfo.RewardPunishment;
import com.entity.securityinfo.RewardPunishmentCondition;

public class RewardPunishmentDaoImp implements IRewardPunishmentDao{

	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public List queryRewardPunishment(
			RewardPunishmentCondition rewardPunishmentCondition) {
		return sqlmapclienttemplate.queryForList("queryRewardPunishment",rewardPunishmentCondition);
	}

	@Override
	public void createRewardPunishment(RewardPunishment rp) {
		sqlmapclienttemplate.insert("createRewardPunishment",rp);
		
	}

	@Override
	public Object queryRewardPunishByName(RewardPunishment rewardPunishment) {
		return sqlmapclienttemplate.queryForObject("queryRewardPunishByName",rewardPunishment);
	}

	@Override
	public void updateRewardPunishment(RewardPunishment rewardPunishment) {
		sqlmapclienttemplate.update("updateRewardPunishment",rewardPunishment);
	}

	@Override
	public void deleteRewardPunishment(RewardPunishment rewardPunishment) {
		sqlmapclienttemplate.delete("deleteRewardPunishment",rewardPunishment);
		
	}
}
