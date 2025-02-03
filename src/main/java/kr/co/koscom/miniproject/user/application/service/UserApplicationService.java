package kr.co.koscom.miniproject.user.application.service;

import kr.co.koscom.miniproject.application.service.UserQueryService;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.user.application.dto.request.CreateUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.CreateUserResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserAssetResponse;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class UserApplicationService {

    private final UserQueryService userQueryService;

    public RetrieveUserAssetResponse retrieveUserAsset(final Long userId) {
        return RetrieveUserAssetResponse.from(
            userQueryService.findById(userId)
        );
    }

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        Long savedId = userQueryService.save(UserEntity.initial(createUserRequest.name()));
        return CreateUserResponse.from(savedId);
    }
}
