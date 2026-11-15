package br.edu.fatecpg.reviu.repositories;

import br.edu.fatecpg.reviu.domain.user.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

    List<UserBlock> findByBlockerIdOrderByCreatedAtDesc(Long blockerId);

    Optional<UserBlock> findByBlockerIdAndBlockedId(Long blockerId, Long blockedId);

    boolean existsByBlockerIdAndBlockedId(Long blockerId, Long blockedId);

    @Query("""
            SELECT COUNT(b) > 0
            FROM UserBlock b
            WHERE (b.blocker.id = :firstUserId AND b.blocked.id = :secondUserId)
               OR (b.blocker.id = :secondUserId AND b.blocked.id = :firstUserId)
            """)
    boolean existsBetween(
            @Param("firstUserId") Long firstUserId,
            @Param("secondUserId") Long secondUserId
    );

}
