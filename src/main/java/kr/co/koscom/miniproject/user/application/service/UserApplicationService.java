package kr.co.koscom.miniproject.user.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserOrdersResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserStocksResponse;
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
import org.springframework.http.ProblemDetail;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class UserApplicationService {

    private final UserQueryService userQueryService;
    private final UserService userService;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        log.info("UserApplicationService : createUser(): createUserRequest={}", createUserRequest);
        Long savedId = userQueryService.save(UserEntity.initial(createUserRequest.name()));

        return CreateUserResponse.from(savedId);
    }

    public RetrieveUserAssetResponse retrieveUserAsset(final Long userId) {
        log.info("UserApplicationService : retrieveUserAsset(): userId={}", userId);
        UserEntity user = userQueryService.findById(userId);

        log.info("UserApplicationService : retrieveUserAsset(): user.orders={}",
            user.getOrders().size());
        return RetrieveUserAssetResponse.of(
            user.calculateTotalAssets(),
            user.getBalance()
        );
    }

    public RetrieveUserOrdersResponse retrieveUserOrders(Long userId) {
        log.info("UserApplicationService : retrieveUserOrders(): userId={}", userId);
        List<OrderEntity> orders = userQueryService.findOrdersByUserIdExecutionTimeDesc(userId);

        return RetrieveUserOrdersResponse.from(orders);
    }

    public RetrieveUserStocksResponse retrieveUserStocks(Long userId) {
        log.info("UserApplicationService : retrieveUserStocks(): userId={}", userId);

        Map<String, Integer> stockQuantities = userQueryService.findOrdersByUserIdExecutionTimeDesc(userId)
            .stream()
            .collect(Collectors.groupingBy(
                OrderEntity::getStockName,
	Collectors.summingInt(OrderEntity::getQuantity)
            ));

        List<RetrieveUserStocksResponse.StockInfo> stocks = stockQuantities.entrySet().stream()
            .map(
	entry -> new RetrieveUserStocksResponse.StockInfo(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        return new RetrieveUserStocksResponse(userId, stocks);
    }

    @Transactional
    public WithdrawUserResponse withdraw(Long userId, WithdrawUserRequest withdrawUserResponse) {
        log.info("UserApplicationService : withdraw(): userId={}, withdrawUserResponse={}", userId,
            withdrawUserResponse);
        UserEntity user = userQueryService.findById(userId);

        return new WithdrawUserResponse(
            userId,
            userService.withdraw(user, withdrawUserResponse.amount()
            )
        );
    }

    @Transactional
    public DepositUserResponse deposit(Long userId, DepositUserRequest depositUserResponse) {
        log.info("UserApplicationService : deposit(): userId={}, depositUserResponse={}", userId,
            depositUserResponse);
        UserEntity user = userQueryService.findById(userId);

        return new DepositUserResponse(
            userId,
            userService.deposit(user, depositUserResponse.amount())
        );
    }
}
