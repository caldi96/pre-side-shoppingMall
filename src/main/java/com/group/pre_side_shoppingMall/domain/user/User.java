package com.group.pre_side_shoppingMall.domain.user;

import com.group.pre_side_shoppingMall.domain.order.Order;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId = null;

    @Column(nullable = false, unique = true)
    private String userName; // 아이디 (로그인용)

    @Column(nullable = false)
    private String password; // 비밀번호 (BCrypt 해시 저장)

    @Column(nullable = false)
    private String name; // 사용자 이름

    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    protected User() {}

    public User(String userName, String password, String name, int age) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
