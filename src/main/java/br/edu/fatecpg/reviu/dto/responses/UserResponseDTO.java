package br.edu.fatecpg.reviu.dto.responses;

import br.edu.fatecpg.reviu.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "UserResponseDTO",
        description = "DTO de resposta com os dados públicos do usuário autenticado"
)
public record UserResponseDTO(

        @Schema(description = "Identificador do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "Thiago Almeida")
        String name,

        @Schema(description = "Nome de usuário", example = "thiago.almeida")
        String username,

        @Schema(description = "E-mail do usuário", example = "thiago@example.com")
        String email,

        @Schema(description = "URL da imagem de perfil", example = "https://conta.blob.core.windows.net/reviu-app-images/profile.jpg")
        String profileImageUrl,

        @Schema(description = "Indica se o e-mail do usuário foi verificado", example = "true")
        Boolean verified,

        @Schema(description = "Quantidade de dias consecutivos com pelo menos uma revisão", example = "7")
        int currentStreak,

        @Schema(description = "Data da última revisão que contou para a ofensiva", example = "2026-11-15")
        java.time.LocalDate lastReviewDate,

        @Schema(description = "Fuso horário IANA usado para calcular o dia da ofensiva", example = "America/Sao_Paulo")
        String timezone

) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getVerified(),
                user.getCurrentStreak(),
                user.getLastReviewDate(),
                user.getTimezone()
        );
    }
}
