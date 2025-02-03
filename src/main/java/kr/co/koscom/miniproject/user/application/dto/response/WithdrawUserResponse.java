package kr.co.koscom.miniproject.user.application.dto.response;

public record WithdrawUserResponse(
    Long userId,
    Integer currentBalance
) {

}
