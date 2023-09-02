package com.example.bitcamptiger.webSoket.repository;

import com.example.bitcamptiger.webSoket.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query("SELECT COUNT(a) FROM Alarm a WHERE a.memberId = :memberId AND a.uncheck = false")
    int countByMemberIDAndUncheckIsFalse(@Param("memberId") String memberId);
}
