package kr.co.koscom.miniproject.user.adapter.in.rest;

import kr.co.koscom.miniproject.user.application.dto.request.CreateUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.CreateUserResponse;
import kr.co.koscom.miniproject.user.application.dto.request.DepositUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.DepositUserResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserAssetResponse;
import kr.co.koscom.miniproject.user.application.dto.request.WithdrawUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserOrdersResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserStocksResponse;
import kr.co.koscom.miniproject.user.application.dto.response.WithdrawUserResponse;
import kr.co.koscom.miniproject.user.application.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerDocs {

    private final UserApplicationService userApplicationService;

    @Override
    public ResponseEntity<RetrieveUserAssetResponse> retrieveUserAsset(
        final Long userId
    ) {
        return ResponseEntity.ok(userApplicationService.retrieveUserAsset(userId));
    }

    @Override
    public ResponseEntity<RetrieveUserOrdersResponse> retrieveUserOrders(
        final Long userId
    ) {
        return ResponseEntity.ok(userApplicationService.retrieveUserOrders(userId));
    }

    @Override
    public ResponseEntity<RetrieveUserStocksResponse> retrieveUserStocks(
        final Long userId
    ) {
        return ResponseEntity.ok(userApplicationService.retrieveUserStocks(userId));
    }

    @Override
    public ResponseEntity<CreateUserResponse> createUser(
        final CreateUserRequest createUserRequest
    ) {
        log.info("UserController : createUser request: {}", createUserRequest);
        return ResponseEntity.ok(userApplicationService.createUser(createUserRequest));
    }

    @Override
    public ResponseEntity<WithdrawUserResponse> withdraw(
        final Long userId,
        final WithdrawUserRequest withdrawUserRequest
    ) {
        log.info("UserController : withdraw request: {} by userId : {}", withdrawUserRequest, userId);
        return ResponseEntity.ok(userApplicationService.withdraw(userId, withdrawUserRequest));
    }


    @Override
    public ResponseEntity<DepositUserResponse> deposit(
        final Long userId,
        final DepositUserRequest depositUserRequest
    ) {
        return ResponseEntity.ok(userApplicationService.deposit(userId, depositUserRequest));
    }
}
