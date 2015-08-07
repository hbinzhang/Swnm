package com.dao.videomonitor;

import java.util.List;
import java.util.Map;
import com.entity.videomonitor.TVmRound;

public interface TVmRoundMapper {
	int insertRound(TVmRound round);	
	int updateRound(TVmRound round);
	int deleteRoundById(int roundid);
	List<TVmRound> selectRound(Map<String,Object> cond);
}
