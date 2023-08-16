package com.hisujung.web.jpa;

import com.hisujung.web.entity.ChatingJoinInfo;
import com.hisujung.web.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatingJoinInfoRepository extends JpaRepository<ChatingJoinInfo,Long> {
    List<ChatingJoinInfo> findByMember(Member member);
}
