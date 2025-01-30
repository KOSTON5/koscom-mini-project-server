package kr.co.koscom.miniproject.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import kr.co.koscom.miniproject.domain.order.vo.OrderCondition;
import kr.co.koscom.miniproject.domain.order.vo.OrderType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_order")
@Builder(toBuilder = true)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_ticker")
    @NotNull
    private String ticker;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    @NotNull
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_condition")
    @NotNull
    private OrderCondition orderCondition;

    @Column(name = "order_price")
    @NotNull
    private BigDecimal price;

    @Column(name = "order_quantity")
    @NotNull
    private BigDecimal quantity;

    @Column(name = "order_expiration_time")
    @Nullable
    private LocalDate expirationTime;

}
