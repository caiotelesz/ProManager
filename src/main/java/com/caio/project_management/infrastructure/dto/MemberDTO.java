package com.caio.project_management.infrastructure.dto;

import com.caio.project_management.domain.entity.Member;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MemberDTO {
    private final String id;
    private final String secret;
    private final String name;
    private final String email;

    public static MemberDTO create(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getSecret(),
                member.getName(),
                member.getEmail()
        );
    }
}
