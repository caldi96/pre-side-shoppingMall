package com.group.pre_side_shoppingMall.common;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TrackingNumberGenerator {

    private static final Random random = new Random();

    public String generateTrackingNumber() {
        return String.format("%04d-%04d-%04d",
                random.nextInt(10000), // 0 ~ 9999
                random.nextInt(10000),
                random.nextInt(10000));
    }
}
