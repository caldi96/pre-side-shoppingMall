package com.group.pre_side_shoppingMall.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "사용자명은 필수입니다")
    private String userName;

    @NotBlank(message = "패스워드는 필수입니다")
    private String password;

}
