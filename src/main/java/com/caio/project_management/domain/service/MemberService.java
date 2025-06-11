package com.caio.project_management.domain.service;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.exception.DuplicateProjectException;
import com.caio.project_management.domain.exception.MemberNotFoundException;
import com.caio.project_management.domain.repository.MemberRepository;
import com.caio.project_management.infrastructure.dto.MemberDTO;
import com.caio.project_management.infrastructure.dto.SaveMemberDataDTO;
import com.caio.project_management.infrastructure.dto.SaveProjectDataDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            throw new DuplicateProjectException(saveMemberData.getEmail());
        }

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

    public Member loadMember(String memberId) {
        return memberRepository
                .findByIdAndDeleted(memberId, false)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional
    public void deleteMember(String memberId) {
        Member member = loadMember(memberId);

        member.setDeleted(true);
    }

    @Transactional
    public Member updateMember(String memberId, SaveMemberDataDTO saveMemberData) {
        if(existsMemberWithEmail(saveMemberData.getEmail(), memberId)) {
            throw new DuplicateProjectException(saveMemberData.getEmail());
        }

        Member member = loadMember(memberId);

        member.setName(saveMemberData.getName());
        member.setEmail(saveMemberData.getEmail());

        return member;
    }

    private boolean existsMemberWithEmail(String email, String idToExclude) {
        return memberRepository
                .findByEmailAndDeleted(email, false)
                .filter(m -> !Objects.equals(m.getId(), idToExclude))
                .isPresent();
    }
}
