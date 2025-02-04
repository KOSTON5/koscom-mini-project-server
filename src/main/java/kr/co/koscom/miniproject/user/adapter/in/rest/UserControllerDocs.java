package kr.co.koscom.miniproject.user.adapter.in.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.user.application.dto.request.CreateUserRequest;
import kr.co.koscom.miniproject.user.application.dto.request.DepositUserRequest;
import kr.co.koscom.miniproject.user.application.dto.request.WithdrawUserRequest;
import kr.co.koscom.miniproject.user.application.dto.response.CreateUserResponse;
import kr.co.koscom.miniproject.user.application.dto.response.DepositUserResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserAssetResponse;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserOrdersResponse;
import kr.co.koscom.miniproject.user.application.dto.response.WithdrawUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User API", description = "사용자 정보를 제공하는 API 명세서입니다.")
@RequestMapping("/api/users")
public interface UserControllerDocs {

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회하는 API 입니다.")
    @GetMapping("/information")
    ResponseEntity<RetrieveUserAssetResponse> retrieveUserAsset(
        @RequestHeader(name = "X-USER-ID") Long userId
    );

    @Operation(summary = "사용자 주문 정보 조회", description = "사용자 주문 정보를 조회하는 API 입니다.")
    @GetMapping("/orders")
    ResponseEntity<RetrieveUserOrdersResponse> retrieveUserOrders(
        @RequestHeader(name = "X-USER-ID") Long userId
    );

    @Operation(summary = "사용자 생성", description = "사용자를 생성하는 API 입니다.")
    @PostMapping
    ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest createUserRequest
    );

    @Operation(summary = "사용자 출금", description = "사용자 출금 API 입니다.")
    @PostMapping("/withdraw")
    ResponseEntity<WithdrawUserResponse> withdraw(
        @RequestHeader(name = "X-USER-ID") Long userId,
        @RequestBody WithdrawUserRequest withdrawUserResponse
    );

    @Operation(summary = "사용자 입금", description = "사용자 입금 API 입니다.")
    @PostMapping("/deposit")
    ResponseEntity<DepositUserResponse> deposit(
        @RequestHeader(name = "X-USER-ID") Long userId,
        @RequestBody DepositUserRequest depositUserResponse
    );
}
