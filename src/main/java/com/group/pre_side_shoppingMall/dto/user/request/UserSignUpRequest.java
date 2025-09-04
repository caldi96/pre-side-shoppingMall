package com.group.pre_side_shoppingMall.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

    private String userName;
    private String password;
    private String name;
    private int age;

}
