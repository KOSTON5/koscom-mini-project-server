package kr.co.koscom.miniproject.common.adapter.out.client.naver.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverStockResponse(
    @JsonProperty("resultCode") String resultCode,
    @JsonProperty("result") Result result
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(
        @JsonProperty("pollingInterval") long pollingInterval,
        @JsonProperty("areas") List<Area> areas
    ) {

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Area(
        @JsonProperty("name") String name,
        @JsonProperty("datas") List<Data> datas
    ) {

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
        @JsonProperty("cd") String ticker,   // 종목 코드 (005930 - 삼성전자)
        @JsonProperty("nm") String name,     // 종목명
        @JsonProperty("sv") int previousClose, // 전일 종가
        @JsonProperty("nv") int nowPrice,    // 현재가
        @JsonProperty("cv") int changeValue, // 변동 금액
        @JsonProperty("cr") double changeRate, // 변동률 (%)
        @JsonProperty("rf") String rf,      // 등락 상태 (상승/하락/보합)
        @JsonProperty("mt") String marketType, // 시장 구분
        @JsonProperty("ms") String marketStatus, // 시장 상태 (장중/마감 등)
        @JsonProperty("pcv") int previousClosingPrice, // 전일 종가
        @JsonProperty("ov") int openingPrice, // 시가
        @JsonProperty("hv") int highPrice, // 고가
        @JsonProperty("lv") int lowPrice, // 저가
        @JsonProperty("ul") int upperLimit, // 상한가
        @JsonProperty("ll") int lowerLimit, // 하한가
        @JsonProperty("aq") long tradingVolume, // 거래량
        @JsonProperty("aa") long tradingAmount, // 거래대금
        @JsonProperty("eps") double eps, // EPS (주당순이익)
        @JsonProperty("bps") double bps, // BPS (주당순자산)
        @JsonProperty("dv") double dividend // 배당금
    ) {

    }

    public Data getFirstStockData() {
        if (result != null && result.areas() != null && !result.areas().isEmpty() &&
            result.areas().get(0).datas() != null && !result.areas().get(0).datas().isEmpty()) {
            return result.areas().get(0).datas().get(0);
        }
        return null;
    }
}
