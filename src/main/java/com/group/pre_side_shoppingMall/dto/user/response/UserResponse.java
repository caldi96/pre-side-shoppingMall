package com.group.pre_side_shoppingMall.dto.user.response;

import com.group.pre_side_shoppingMall.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String userName;
    private String name;
    private String token;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.name = user.getName();
    }

    public UserResponse(String token) {
        this.token = token;
    }
}
