package kr.co.koscom.miniproject.user.application.dto.response;

import kr.co.koscom.miniproject.user.domain.entity.UserEntity;

public record RetrieveUserAssetResponse(
    Integer totalAssets,
    Integer availableBalance,
    Double profitRate
) {

    public static RetrieveUserAssetResponse from(final UserEntity userEntity) {
        return new RetrieveUserAssetResponse(
            userEntity.calculateTotalAssets(),
            userEntity.getBalance(),
            2.45
        );
    }
}
