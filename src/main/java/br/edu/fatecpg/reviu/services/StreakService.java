package br.edu.fatecpg.reviu.services;

import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.repositories.CardReviewRepository;
import br.edu.fatecpg.reviu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreakService {
    private final UserRepository userRepository;
    private final CardReviewRepository cardReviewRepository;

    @Scheduled(cron = "0 */15 * * * *", zone = "UTC")
    @Transactional
    public void resetExpiredStreaks() {
        for (User user : userRepository.findAll()) {
            if (user.getCurrentStreak() <= 0) {
                continue;
            }

            LocalDate yesterday = localToday(user).minusDays(1);
            if (user.getLastReviewDate() != null
                    && !user.getLastReviewDate().isBefore(yesterday)) {
                continue;
            }

            boolean hadCardsDue = !cardReviewRepository
                    .findByUserIdAndNextReviewLessThanEqual(user.getId(), yesterday)
                    .isEmpty();
            if (hadCardsDue) {
                user.setCurrentStreak(0);
                userRepository.save(user);
            }
        }
    }

    private LocalDate localToday(User user) {
        String timezone = user.getTimezone() == null || user.getTimezone().isBlank()
                ? "UTC"
                : user.getTimezone();
        try {
            return LocalDate.now(ZoneId.of(timezone));
        } catch (DateTimeException exception) {
            log.warn("Invalid timezone '{}' for user {}; using UTC", timezone, user.getId());
            return LocalDate.now(ZoneId.of("UTC"));
        }
    }
}
