package kr.co.koscom.miniproject.user.application.service;

import kr.co.koscom.miniproject.user.domain.service.UserService;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.user.application.dto.request.CreateUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.CreateUserResponse;
import kr.co.koscom.miniproject.user.application.dto.request.DepositUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.DepositUserResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserAssetResponse;
import kr.co.koscom.miniproject.user.application.dto.request.WithdrawUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.WithdrawUserResponse;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class UserApplicationService {

    private final UserQueryService userQueryService;
    private final UserService userService;

    public RetrieveUserAssetResponse retrieveUserAsset(final Long userId) {
        return RetrieveUserAssetResponse.from(
            userQueryService.findById(userId)
        );
    }

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        Long savedId = userQueryService.save(UserEntity.initial(createUserRequest.name()));
        return CreateUserResponse.from(savedId);
    }

    @Transactional
    public WithdrawUserResponse withdraw(Long userId, WithdrawUserRequest withdrawUserResponse) {
        UserEntity user = userQueryService.findById(userId);

        return new WithdrawUserResponse(
            userId,
            userService.withdraw(user, withdrawUserResponse.amount()
        ));
    }

    @Transactional
    public DepositUserResponse deposit(Long userId, DepositUserRequest depositUserResponse) {
        UserEntity user = userQueryService.findById(userId);
        return new DepositUserResponse(
            userId,
            userService.deposit(user, depositUserResponse.amount())
        );
    }
}
