package kr.co.koscom.miniproject.order.domain.vo;

import java.util.Arrays;

public enum OrderType {
    BUY("BUY"),
    SELL("SELL");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    public static OrderType from(String value) {
        return Arrays.stream(OrderType.values())
            .filter(v -> v.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("OrderType not found."));
    }
}
