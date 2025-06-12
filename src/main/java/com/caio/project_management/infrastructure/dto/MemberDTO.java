package com.caio.project_management.infrastructure.dto;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.entity.Project;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MemberDTO {
    private final String id;
    private final String secret;
    private final String name;
    private final String email;
    private final Set<String> projectIds;

    public static MemberDTO create(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getSecret(),
                member.getName(),
                member.getEmail(),
                Optional
                        .ofNullable(member.getProjects())
                        .orElse(List.of())
                        .stream()
                        .map(Project::getId)
                        .collect(Collectors.toSet())
        );
    }
}
