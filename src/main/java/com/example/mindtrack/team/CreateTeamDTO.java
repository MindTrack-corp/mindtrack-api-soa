package com.example.mindtrack.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTeamDTO(
        @NotBlank(message = "Nome da equipe é obrigatório")
        @Size(max = 100, message = "Nome da equipe deve ter no máximo 100 caracteres")
        String name
) {}
