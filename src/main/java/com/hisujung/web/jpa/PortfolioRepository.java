package com.hisujung.web.jpa;


import com.hisujung.web.entity.Member;
import com.hisujung.web.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("SELECT p FROM Portfolio p WHERE p.member.id = :mid ORDER BY p.id DESC ")
    List<Portfolio> findAllDesc(@Param("mid") Long mid);

    List<Portfolio> findByMember(Member member);
}
