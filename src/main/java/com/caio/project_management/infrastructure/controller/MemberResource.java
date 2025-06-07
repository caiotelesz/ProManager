package com.caio.project_management.infrastructure.controller;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.service.MemberService;
import com.caio.project_management.infrastructure.dto.MemberDTO;
import com.caio.project_management.infrastructure.dto.SaveMemberDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static com.caio.project_management.infrastructure.controller.RestResource.PATH_MEMBERS;

@Controller
@RequestMapping(PATH_MEMBERS)
@RequiredArgsConstructor
public class MemberResource {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDTO> create(@RequestBody @Valid SaveMemberDataDTO saveMemberData) {

        Member member = memberService.createMember(saveMemberData);

        return ResponseEntity
                .created(URI.create(PATH_MEMBERS + "/" + member.getId()))
                .body(MemberDTO.create(member));
    }
}
