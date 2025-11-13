package com.example.mindtrack.report;

public record ReportSummaryDTO(
        Long teamId,
        String teamName,
        Double avgMood,
        Long checkinsCount
) {}
