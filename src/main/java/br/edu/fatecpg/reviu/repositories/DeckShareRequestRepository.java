package br.edu.fatecpg.reviu.repositories;

import br.edu.fatecpg.reviu.domain.deck.DeckShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeckShareRequestRepository extends JpaRepository<DeckShareRequest, Long> {
    List<DeckShareRequest> findByRecipientIdAndStatusOrderByCreatedAtDesc(Long recipientId, String status);

    Optional<DeckShareRequest> findByIdAndRecipientIdAndStatus(
            Long id,
            Long recipientId,
            String status
    );

    boolean existsByDeckIdAndRecipientIdAndStatus(Long deckId, Long recipientId, String status);

    @Query("""
            SELECT COUNT(r) > 0
            FROM DeckShareRequest r
            WHERE r.status = :status
              AND ((r.sender.id = :firstUserId AND r.recipient.id = :secondUserId)
                OR (r.sender.id = :secondUserId AND r.recipient.id = :firstUserId))
            """)
    boolean existsPendingBetween(
            @Param("firstUserId") Long firstUserId,
            @Param("secondUserId") Long secondUserId,
            @Param("status") String status
    );

    @Query("""
            SELECT r
            FROM DeckShareRequest r
            WHERE r.status = :status
              AND ((r.sender.id = :firstUserId AND r.recipient.id = :secondUserId)
                OR (r.sender.id = :secondUserId AND r.recipient.id = :firstUserId))
            """)
    List<DeckShareRequest> findBetween(
            @Param("firstUserId") Long firstUserId,
            @Param("secondUserId") Long secondUserId,
            @Param("status") String status
    );
}
