package com.caio.project_management.infrastructure.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class SortProperties {

    private static final List<String> ALLOWED_PROPERTIES = List.of(
            "title",
            "status",
            "numberOfDays"
    );

    private final List<String> sortProperties;

    public SortProperties(String commaSeparatedString) {
        sortProperties = Arrays
                .stream(commaSeparatedString.split(","))
                .filter(ALLOWED_PROPERTIES::contains)
                .toList();
    }
}
