package com.hisujung.web.jpa;

import com.hisujung.web.entity.LikeExternalAct;
import com.hisujung.web.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeExternalActRepository extends JpaRepository<LikeExternalAct, Long> {

    List<LikeExternalAct> findByMember(Member member);
}
