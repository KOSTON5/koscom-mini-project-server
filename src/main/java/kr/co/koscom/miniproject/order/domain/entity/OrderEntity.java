package kr.co.koscom.miniproject.order.domain.entity;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.koscom.miniproject.order.domain.vo.OrderCondition;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.order.domain.vo.CommandType;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
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

    @Column(name = "order_stock_ticker")
    private String ticker;

    @Column(name = "order_stock_name")
    private String stockName;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private CommandType orderType;

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

    @Column(name = "execution_time")
    private LocalDateTime executionTime;

    @Column(name = "expiration_time")
    private LocalDate expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void updatePrice(final Integer realtimePrice) {
        this.price = realtimePrice;
    }

    public void changeOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void match() {
        this.orderStatus = OrderStatus.MATCHED;
        this.executionTime = LocalDateTime.now();
    }

    public void cancel() {
        this.orderStatus = OrderStatus.FAILED;
    }
}
