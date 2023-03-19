package me.hoonti06.testcontainers.repository;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import me.hoonti06.testcontainers.DatabaseCleanup;
import me.hoonti06.testcontainers.config.TestDatabaseConfig;
import me.hoonti06.testcontainers.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@DataJpaTest
@Import({TestDatabaseConfig.class, DatabaseCleanup.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

  @Autowired private MemberRepository memberRepository;
  @Autowired private DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp() {
    databaseCleanup.execute();
  }

  @Test
  void save() {
    // given
    Member member = new Member();
    member.setName("hoon");
    member.setAddress("Seoul");

    // when
    memberRepository.save(member);

    // then
    final Member findMember = memberRepository.findById(member.getId()).get();
    assertThat(findMember.getId()).isEqualTo(member.getId());
  }

  @Test
  void save2() {
    // given
    Member member = new Member();
    member.setName("kim");
    member.setAddress("Jeju");

    // when
    memberRepository.save(member);

    // then
    final Member findMember = memberRepository.findById(member.getId()).get();
    assertThat(findMember.getId()).isEqualTo(member.getId());
  }

}