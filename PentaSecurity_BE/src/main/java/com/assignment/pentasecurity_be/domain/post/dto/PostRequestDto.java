package com.assignment.pentasecurity_be.domain.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostRequestDto(
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotBlank(message = "작성자는 필수입니다.")
        String author
) {
}
