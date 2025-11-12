package carbon.carbon_be.domain.point.service;

import carbon.carbon_be.domain.point.dto.PointBalanceResponseDto;
import carbon.carbon_be.domain.point.dto.PointHistoryResponseDto;
import carbon.carbon_be.domain.point.dto.TodayPointResponseDto;
import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.point.repository.PointHistoryRepository;
import carbon.carbon_be.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointQueryService {

    private final PointHistoryRepository pointHistoryRepository;

//     사용자 포인트 적립 이력 전체 조회 (최신순)
//     @param user 로그인된 사용자
//     @return 날짜별 포인트 적립 내역 리스트

    public List<PointHistoryResponseDto> getUserPointHistory(User user) {
        // DB에서 해당 유저의 포인트 이력 가져오기
        List<PointHistory> histories =
                pointHistoryRepository.findByUserOrderByRecordDateDesc(user);

        // Entity → DTO 변환
        return histories.stream()
                .map(PointHistoryResponseDto::fromEntity)
                .toList();
    }

    // 오늘 적립된 포인트 조회
    @Transactional(readOnly = true)
    public TodayPointResponseDto getTodayPoints(User user) {
        LocalDate today = LocalDate.now();
        int points = pointHistoryRepository
                .findByUserAndRecordDate(user, today)
                .map(PointHistory::getEarnedPoints)
                .orElse(0);

        return TodayPointResponseDto.of(points);
    }


    //누적 포인트 조회
    public PointBalanceResponseDto getTotalPoints(User user) {
        int total = pointHistoryRepository.findByUserOrderByRecordDateDesc(user)
                .stream()
                .mapToInt(PointHistory::getEarnedPoints)
                .sum();

        return PointBalanceResponseDto.of(total);
    }
}
