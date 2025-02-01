package kr.co.koscom.miniproject.domain.user.service;

import java.math.BigDecimal;
import kr.co.koscom.miniproject.adapter.out.jpa.UserJpaRepository;
import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@DomainService
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public UserEntity findUserById(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void decreaseBalance(UserEntity user, BigDecimal totalPrice) {
        if (user.isNotEnoughBalance(totalPrice)) {
            // todo : Exception 변경 할 예정
            throw new IllegalArgumentException("Not enough balance");
        }

        user.decreaseBalance(totalPrice);
    }

    public void increaseBalance(UserEntity user, BigDecimal totalPrice) {
        user.increaseBalance(totalPrice);
    }
}
