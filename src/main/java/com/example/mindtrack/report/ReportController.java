package com.example.mindtrack.report;

import com.example.mindtrack.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/teams/{teamId}")
    @PreAuthorize("hasAnyRole('RH','ADMIN')")
    public ResponseEntity<ApiResponse<ReportSummaryDTO>> teamReport(
            @PathVariable("teamId") Long teamId,
            @RequestParam(name = "start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(name = "end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        Period period = new Period(start, end);
        ReportSummaryDTO dto = reportService.getTeamReport(teamId, period);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
