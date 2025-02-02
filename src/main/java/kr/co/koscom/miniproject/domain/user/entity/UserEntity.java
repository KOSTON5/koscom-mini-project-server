package kr.co.koscom.miniproject.domain.user.entity;

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
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_users")
public class UserEntity {

    private final int ZERO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_balance")
    private Integer balance;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderEntity> orders = new ArrayList<>();

    public boolean hasNotEnoughBalance(final Integer totalPrice) {
        return balance.compareTo(totalPrice) < ZERO;
    }

    public void decreaseBalance(final Integer totalPrice) {
        balance = balance - totalPrice;
    }

    public void increaseBalance(final Integer totalPrice) {
        balance = balance + totalPrice;
    }
}
