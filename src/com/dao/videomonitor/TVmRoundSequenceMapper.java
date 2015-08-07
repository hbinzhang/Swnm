package com.dao.videomonitor;

import com.entity.videomonitor.TVmRoundSequence;

public interface TVmRoundSequenceMapper {
	int insertRoundSequence(TVmRoundSequence roundSequence);
	int deleteRoundSequenceByRound(int roundid);
}
