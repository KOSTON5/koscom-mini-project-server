package kr.co.koscom.miniproject.domain.user.service;

import kr.co.koscom.miniproject.adapter.out.jpa.UserJpaRepository;
import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.DomainService;
import kr.co.koscom.miniproject.infrastructure.exception.UserBalanceNotEnoughException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class UserService {

    public void decreaseBalance(UserEntity user, Integer totalPrice) {
        if (user.hasNotEnoughBalance(totalPrice)) {
            throw new UserBalanceNotEnoughException();
        }

        user.decreaseBalance(totalPrice);
    }

    public void increaseBalance(UserEntity user, Integer totalPrice) {
        user.increaseBalance(totalPrice);
    }
}
