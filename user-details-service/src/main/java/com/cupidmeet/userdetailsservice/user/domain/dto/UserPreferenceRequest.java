package com.cupidmeet.userdetailsservice.user.domain.dto;

import com.cupidmeet.userdetailsservice.user.domain.types.PreferredGender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о предпочтениях пользователе")
public class UserPreferenceRequest {

    @Schema(description = "Предпочитаемый пол пользователя")
    private PreferredGender preferredGender;

    @Schema(description = "Минимальный предпочитаемый возвраст")
    private Integer minAge;

    @Schema(description = "Максимальный предпочитаемый возвраст")
    private Integer maxAge;
}
