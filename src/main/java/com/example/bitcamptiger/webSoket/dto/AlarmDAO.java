package com.example.bitcamptiger.webSoket.dto;

import com.example.bitcamptiger.webSoket.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlarmDAO {

    @Autowired
    private AlarmRepository alarmRepository;

    public int selectAlarmUncheckCount(String memberId){
        return alarmRepository.countByMemberIDAndUncheckIsFalse(memberId);
    }


}
