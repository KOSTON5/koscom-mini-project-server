package kr.co.koscom.miniproject.user.adapter.in.rest;

import kr.co.koscom.miniproject.user.application.dto.response.RetrieveUserAssetResponse;
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
    public ResponseEntity<RetrieveUserAssetResponse> retrieveUserAsset(Long userId) {
        return ResponseEntity.ok(userApplicationService.retrieveUserAsset(userId));
    }
}
