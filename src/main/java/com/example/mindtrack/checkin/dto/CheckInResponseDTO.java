package com.example.mindtrack.checkin.dto;

import com.example.mindtrack.checkin.Mood;

import java.time.LocalDate;

public record CheckInResponseDTO(
        Long id,
        LocalDate date,
        Mood mood,
        String note
) {}
