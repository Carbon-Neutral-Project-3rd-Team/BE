package carbon.carbon_be.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA entity들이 상속받아 사용할 공통 매핑 정보
@EntityListeners(AuditingEntityListener.class) // JPA Auditing을 사용하기 위해 필요
public abstract class BaseTimeEntity {

   @CreatedDate
   @Column(updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updateAt;
}
