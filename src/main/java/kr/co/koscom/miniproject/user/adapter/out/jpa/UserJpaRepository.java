package kr.co.koscom.miniproject.user.adapter.out.jpa;

import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}
