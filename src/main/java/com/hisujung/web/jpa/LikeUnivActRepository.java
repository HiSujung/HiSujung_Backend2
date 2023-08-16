package com.hisujung.web.jpa;

import com.hisujung.web.entity.LikeUnivAct;
import com.hisujung.web.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeUnivActRepository extends JpaRepository<LikeUnivAct, Long> {

    List<LikeUnivAct> findByMember(Member member);
}
