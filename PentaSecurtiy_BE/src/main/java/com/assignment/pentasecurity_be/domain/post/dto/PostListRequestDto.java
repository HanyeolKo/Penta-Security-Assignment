package com.assignment.pentasecurity_be.domain.post.dto;

import com.assignment.pentasecurity_be.domain.post.ListType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PostListRequestDto (
        @Pattern(regexp = ListType.PAGING + "|" + ListType.INFINITE, message = "type은 " + ListType.PAGING + " 또는 " + ListType.INFINITE + "값만을 지원합니다.")
        @NotBlank
        String type,
        @Min(0)
        int page,
        @Min(1) @Max(50)
        int size
){}
