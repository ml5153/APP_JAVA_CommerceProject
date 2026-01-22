package com.sparta.commerce.model.type;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum UserLevel {
    BRONZE(0), SILVER(5), GOLD(10), PLATINUM(15);

    private final int discountRate;

    public int getDiscountRate() {
        return discountRate;
    }

    UserLevel(final int discountRate) {
        this.discountRate = discountRate;
    }

    public static Optional<UserLevel> fromDiscountRate(final int discountRate) {
        return Arrays.stream(UserLevel.values())
                .filter(userLevel -> Objects.equals(userLevel.discountRate, discountRate))
                .findFirst();
    }

}
