package org.iptime.yoon.blog.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iptime.yoon.blog.post.dto.PostValidationConstants;

/**
 * @author rival
 * @since 2023-10-03
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {

    @Schema(description = "Category", example = "/backend/spring_boot/mvc")
    @NotBlank(message = "Category cannot be empty value")
    @Pattern(regexp = PostValidationConstants.CATEGORY_DEPTH_REGEX, message = PostValidationConstants.CATEGORY_DEPTH_MESSAGE)
    private String category;
}
