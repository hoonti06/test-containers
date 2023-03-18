package me.hoonti06.testcontainers.repository;

import java.util.Optional;
import me.hoonti06.testcontainers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

  @Query(nativeQuery = true, value = "select * from member where id in ()")
  Optional<Member> wrongSql();

}
