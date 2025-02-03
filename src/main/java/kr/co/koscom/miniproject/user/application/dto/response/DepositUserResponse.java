package kr.co.koscom.miniproject.user.application.dto.response;

public record DepositUserResponse(
    Long userId,
    Integer currentBalance
) {

}
