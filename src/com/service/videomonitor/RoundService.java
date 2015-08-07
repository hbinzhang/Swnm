package com.service.videomonitor;

import java.util.List;

import com.entity.videomonitor.TVmRound;

public interface RoundService {
	int saveRound(String roundSetData) throws Exception;
	List<TVmRound> loadRounds() throws Exception;
	int deleteRoundById(String roundSetData);

}
