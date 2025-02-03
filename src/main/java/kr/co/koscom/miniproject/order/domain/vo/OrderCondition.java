package kr.co.koscom.miniproject.order.domain.vo;

import java.util.Arrays;

public enum OrderCondition {
    MARKET("MARKET"),
    LIMIT("LIMIT");

    private String value;

    OrderCondition(String value) {
        this.value = value;
    }

    public static OrderCondition from(String value) {
        return Arrays.stream(OrderCondition.values())
            .filter(v -> v.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("OrderCondition not found."));
    }
}
