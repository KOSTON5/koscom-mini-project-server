package kr.co.koscom.miniproject.user.application.service;

import java.util.List;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.user.adapter.out.jpa.UserJpaRepository;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.user.infrastructure.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserJpaRepository userJpaRepository;

    public UserEntity findById(final Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public Long save(UserEntity user) {
        return userJpaRepository.save(user).getId();
    }

    public List<OrderEntity> findOrdersByUserIdExecutionTimeDesc(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException())
            .getOrders()
            .stream().sorted(
	(o1, o2) -> o2.getExecutionTime().compareTo(o1.getExecutionTime())
            )
            .toList();
    }
}
