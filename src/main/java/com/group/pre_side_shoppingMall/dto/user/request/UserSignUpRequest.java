package com.group.pre_side_shoppingMall.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

    @NotBlank(message = "사용자명은 필수입니다")
    private String userName;

    @NotBlank(message = "패스워드는 필수입니다")
    private String password;

    @NotBlank(message = "상품명은 필수입니다")
    private String name;

    @PositiveOrZero(message = "나이는 0살 이상이어야 합니다")
    private int age;

}
