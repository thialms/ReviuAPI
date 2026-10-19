package br.edu.fatecpg.reviu.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.domain.user.VerificationCodes;

public interface VerificationCodesRepository extends JpaRepository<VerificationCodes, Long> {

    Optional<VerificationCodes> findByUser(User user);
    Optional<VerificationCodes> findByCode(String code);
}