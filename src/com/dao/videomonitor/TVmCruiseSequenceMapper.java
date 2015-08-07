package com.dao.videomonitor;

import com.entity.videomonitor.TVmCruiseSequence;

public interface TVmCruiseSequenceMapper {
	int insertCruiseSequence(TVmCruiseSequence cruiseSequence);
	int deleteCruiseSequenceByCruise(int cruiseid);
}
