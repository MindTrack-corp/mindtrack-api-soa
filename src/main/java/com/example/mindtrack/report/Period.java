package com.example.mindtrack.report;

import java.time.LocalDate;

public record Period(LocalDate start, LocalDate end) {

    public boolean isValid() {
        return start != null && end != null && !end.isBefore(start);
    }
}
