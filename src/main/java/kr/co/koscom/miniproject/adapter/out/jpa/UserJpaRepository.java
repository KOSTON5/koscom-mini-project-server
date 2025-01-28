package kr.co.koscom.miniproject.adapter.out.jpa;

import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}
