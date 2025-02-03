package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.user.adapter.out.jpa.UserJpaRepository;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.user.infrastructure.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class UserQueryService {

    private final UserJpaRepository userJpaRepository;

    public UserEntity findById(final Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException());
    }
}
