package com.service.securityinfo;

import java.util.List;

import com.entity.securityinfo.RewardPunishment;
import com.entity.securityinfo.RewardPunishmentCondition;

public interface IRewardPunishmentService {

	List queryRewardPunishment(
			RewardPunishmentCondition rewardPunishmentCondition);

	void createRewardPunishment(RewardPunishment rp);

	Object queryRewardPunishByName(RewardPunishment rewardPunishment);

	void updateRewardPunishment(RewardPunishment rewardPunishment);
	
	void deleteRewardPunishment(RewardPunishment rewardPunishment);

}
