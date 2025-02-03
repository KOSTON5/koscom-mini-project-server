package kr.co.koscom.miniproject.user.domain.service;

import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import kr.co.koscom.miniproject.common.infrastructure.annotation.DomainService;
import kr.co.koscom.miniproject.user.infrastructure.exception.UserBalanceNotEnoughException;
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

    public Integer withdraw(UserEntity user, Integer amount) {
        decreaseBalance(user, amount);
        return user.getBalance();
    }

    public Integer deposit(UserEntity user, Integer amount) {
        increaseBalance(user, amount);
        return user.getBalance();
    }
}
