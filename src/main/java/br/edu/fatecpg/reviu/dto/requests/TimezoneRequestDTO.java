package br.edu.fatecpg.reviu.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record TimezoneRequestDTO(
        @NotBlank(message = "O fuso horário é obrigatório")
        String timezone
) {
}
