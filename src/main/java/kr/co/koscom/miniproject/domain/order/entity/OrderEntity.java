package kr.co.koscom.miniproject.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import kr.co.koscom.miniproject.domain.order.vo.OrderCondition;
import kr.co.koscom.miniproject.domain.order.vo.OrderStatus;
import kr.co.koscom.miniproject.domain.order.vo.OrderType;
import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String ticker;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_condition")
    private OrderCondition orderCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_price")
    private Integer price;

    @Column(name = "order_quantity")
    private Integer quantity;

    @Column(name = "order_expiration_time")
    private LocalDate expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void cancel() {
        this.orderStatus = OrderStatus.FAILED;
    }

    public void updatePrice(final Integer realtimePrice) {
        this.price = realtimePrice;
    }

    public void changeOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
