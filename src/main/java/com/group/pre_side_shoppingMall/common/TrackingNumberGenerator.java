package com.group.pre_side_shoppingMall.common;

import java.util.Random;

public class TrackingNumberGenerator {

    private static final Random random = new Random();

    public String generateTrackingNumber() {
        return String.format("%04-%04-%04",
                random.nextInt(10000), // 0 ~ 9999
                random.nextInt(10000),
                random.nextInt(10000));
    }
}
