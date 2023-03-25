package me.hoonti06.testcontainers.repository;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import me.hoonti06.testcontainers.TestContainerDataJpaTest;
import me.hoonti06.testcontainers.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@TestContainerDataJpaTest
class MemberRepositoryTest {

  @Autowired private MemberRepository memberRepository;

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