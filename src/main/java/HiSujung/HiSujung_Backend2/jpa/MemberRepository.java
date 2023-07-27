package HiSujung.HiSujung_Backend2.jpa;

import HiSujung.HiSujung_Backend2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
