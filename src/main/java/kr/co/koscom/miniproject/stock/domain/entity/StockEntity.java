package kr.co.koscom.miniproject.stock.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_stock")
@Builder(toBuilder = true)
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "stock_ticker", unique = true)
    private String ticker;

    @Column(name = "stock_name")
    private String name;

    @Column(name = "stock_current_price")
    private Integer currentPrice;

    @Column(name = "stock_trading_volume")
    private Long tradingVolume;

    @Column(name = "stock_fluctuation_rate")
    private Double fluctuationRate;

    @Column(name = "stock_logo_image_url")
    private String logoImageUrl;

    public void updateCurrentPrice(final Integer realtimeMarketPrice) {
        this.currentPrice = realtimeMarketPrice;
    }
}
