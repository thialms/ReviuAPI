package br.edu.fatecpg.reviu.repositories;

import br.edu.fatecpg.reviu.domain.user.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

    List<UserBlock> findByBlockerIdOrderByCreatedAtDesc(Long blockerId);

    Optional<UserBlock> findByBlockerIdAndBlockedId(Long blockerId, Long blockedId);

    boolean existsByBlockerIdAndBlockedId(Long blockerId, Long blockedId);

}
