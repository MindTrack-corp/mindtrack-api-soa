package com.example.mindtrack.checkin.dto;

import com.example.mindtrack.checkin.Mood;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCheckInDTO(
        @NotNull(message = "O humor é obrigatório")
        Mood mood,
        @Size(max = 500, message = "A anotação deve ter no máximo 500 caracteres")
        String note
) {}
