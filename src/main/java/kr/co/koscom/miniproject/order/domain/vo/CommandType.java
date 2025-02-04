package kr.co.koscom.miniproject.order.domain.vo;

import java.util.Arrays;

public enum CommandType {
    BUY("BUY"),
    SELL("SELL"),
    BALANCE("BALANCE"),
    STOCK("STOCK"),
    ;

    private String value;

    CommandType(String value) {
        this.value = value;
    }

    public static CommandType from(String value) {
        return Arrays.stream(CommandType.values())
            .filter(v -> v.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("CommandType not found."));
    }

    public boolean isTrading() {
        return this == BUY || this == SELL;
    }
}
