package com.caio.project_management.domain.service;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.repository.MemberRepository;
import com.caio.project_management.infrastructure.dto.SaveMemberDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(SaveMemberDataDTO saveMemberData) {

        Member member = Member
                .builder()
                .name(saveMemberData.getName())
                .email(saveMemberData.getEmail())
                .secret(UUID.randomUUID().toString())
                .deleted(false)
                .build();

        memberRepository.save(member);

        log.info("Member created: {}", member);

        return member;
    }
}
