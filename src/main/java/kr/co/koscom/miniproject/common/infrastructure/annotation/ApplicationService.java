package kr.co.koscom.miniproject.common.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Validated
@Service
@Transactional(readOnly = true)
public @interface ApplicationService {

}
