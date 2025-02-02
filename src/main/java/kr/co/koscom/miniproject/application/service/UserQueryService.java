package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.adapter.out.jpa.UserJpaRepository;
import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class UserQueryService {

    private final UserJpaRepository userJpaRepository;

    public UserEntity findById(final Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
    }
}
