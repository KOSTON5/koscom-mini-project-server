package kr.co.koscom.miniproject.user.adapter.in.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.infrastructure.annotation.CurrentUserId;
import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserAssetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User API", description = "사용자 정보를 제공하는 API 명세서입니다.")
@RequestMapping("/api/users")
public interface UserControllerDocs {

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회하는 API 입니다.")
    @GetMapping("/information")
    ResponseEntity<RetrieveUserAssetResponse> retrieveUserAsset(
        @CurrentUserId Long userId
    );
}
