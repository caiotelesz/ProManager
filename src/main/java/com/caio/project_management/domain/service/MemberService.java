package com.caio.project_management.domain.service;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.exception.DuplicateMemberException;
import com.caio.project_management.domain.exception.MemberNotFoundException;
import com.caio.project_management.domain.repository.MemberRepository;
import com.caio.project_management.infrastructure.dto.SaveMemberDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(SaveMemberDataDTO saveMemberData) {
        if(existsMemberWithEmail(saveMemberData.getEmail(), null)) {
            throw new DuplicateMemberException(saveMemberData.getEmail());
        }

        Member member = Member
                .builder()
                .name(saveMemberData.getName())
                .email(saveMemberData.getEmail())
                .secret(UUID.randomUUID().toString())
                .deleted(false)
                .build();

        memberRepository.save(member);

        log.info("Saved member: {}", member);

        return member;
    }

    public Member loadMember(String memberId) {
        Member member = memberRepository
                .findByIdAndDeleted(memberId, false)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        log.info("Loaded member: {}", member);

        return member;
    }

    @Transactional
    public void deleteMember(String memberId) {
        Member member = loadMember(memberId);

        member.setDeleted(true);

        log.info("Deleted member: {}", member);
    }

    @Transactional
    public Member updateMember(String memberId, SaveMemberDataDTO saveMemberData) {
        if(existsMemberWithEmail(saveMemberData.getEmail(), memberId)) {
            throw new DuplicateMemberException(saveMemberData.getEmail());
        }

        Member member = loadMember(memberId);

        member.setName(saveMemberData.getName());
        member.setEmail(saveMemberData.getEmail());

        log.info("Updated member: {}", member);

        return member;
    }

    public List<Member> findMembers(String email) {
        List<Member> members;

        if(Objects.isNull(email)) {
            members = memberRepository.findAllAndNotDeleted();
        } else {
            members = memberRepository
                    .findByEmailAndDeleted(email, false)
                    .map(List::of)
                    .orElse(List.of());
        }

        log.info("Found {} members", members.size());

        return members;
    }

    private boolean existsMemberWithEmail(String email, String idToExclude) {
        return memberRepository
                .findByEmailAndDeleted(email, false)
                .filter(m -> !Objects.equals(m.getId(), idToExclude))
                .isPresent();
    }
}
