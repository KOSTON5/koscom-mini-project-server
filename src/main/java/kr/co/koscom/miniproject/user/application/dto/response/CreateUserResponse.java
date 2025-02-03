package kr.co.koscom.miniproject.user.application.dto.response;

public record CreateUserResponse(
    Long userId
) {

    public static CreateUserResponse from(final Long userId) {
        return new CreateUserResponse(userId);
    }
}
