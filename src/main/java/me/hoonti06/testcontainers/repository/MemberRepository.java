package me.hoonti06.testcontainers.repository;

import me.hoonti06.testcontainers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
