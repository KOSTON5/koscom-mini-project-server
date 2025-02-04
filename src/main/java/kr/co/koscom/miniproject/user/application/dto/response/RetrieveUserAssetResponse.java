package kr.co.koscom.miniproject.user.application.dto.response;

import kr.co.koscom.miniproject.user.domain.entity.UserEntity;

public record RetrieveUserAssetResponse(
    Integer totalAssets,
    Integer availableBalance,
    Double profitRate
) {

    public static RetrieveUserAssetResponse of(final Integer totalAssets,
        final Integer availableBalance) {
        return new RetrieveUserAssetResponse(
            totalAssets,
            availableBalance,
            2.45
        );
    }
}
