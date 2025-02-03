package kr.co.koscom.miniproject.user.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_users")
public class UserEntity {

    private static final int ZERO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_balance")
    private Integer balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderEntity> orders = new ArrayList<>();

    public static UserEntity initial(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.name = name;
        userEntity.balance = ZERO;
        return userEntity;
    }

    public boolean hasNotEnoughBalance(final Integer totalPrice) {
        return balance.compareTo(totalPrice) < ZERO;
    }

    public void decreaseBalance(final Integer totalPrice) {
        balance = balance - totalPrice;
    }

    public void increaseBalance(final Integer totalPrice) {
        balance = balance + totalPrice;
    }

    public Integer calculateTotalAssets() {
        int stockValue = orders.stream()
            .filter(order -> order.getOrderStatus() == OrderStatus.MATCHED)
            .mapToInt(order -> order.getPrice() * order.getQuantity())
            .sum();

        return stockValue + balance;
    }
}
