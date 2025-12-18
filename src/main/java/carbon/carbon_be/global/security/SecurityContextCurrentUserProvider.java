package carbon.carbon_be.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextCurrentUserProvider implements CurrentUserProvider {

    @Override
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }

        Object principal = auth.getPrincipal();

        // 정상 케이스: CustomUserDetails 사용
        if (principal instanceof CustomUserDetails cud) {
            return cud.getId();
        }

        // 예외 케이스 처리(익명 사용자 등)
        // principal이 "anonymousUser" 문자열로 오는 경우가 있으니 명확히 방어
        throw new IllegalStateException("지원하지 않는 Principal 타입: " + principal);
    }
}
