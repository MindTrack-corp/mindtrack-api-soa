package com.example.mindtrack.report;

import com.example.mindtrack.checkin.CheckIn;
import com.example.mindtrack.checkin.CheckInRepository;
import com.example.mindtrack.common.exception.BusinessException;
import com.example.mindtrack.team.Team;
import com.example.mindtrack.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CheckInRepository checkInRepository;
    private final TeamService teamService;

    public ReportSummaryDTO getTeamReport(Long teamId, Period period) {
        if (!period.isValid()) {
            throw new BusinessException("Período inválido");
        }
        Team team = teamService.findById(teamId);
        List<CheckIn> checkIns = checkInRepository.findByTeamAndPeriod(
                teamId, period.start(), period.end());

        if (checkIns.isEmpty()) {
            return new ReportSummaryDTO(team.getId(), team.getName(), null, 0L);
        }

        double avg = checkIns.stream()
                .mapToInt(c -> c.getMood().ordinal() + 1)
                .average()
                .orElse(0.0);

        return new ReportSummaryDTO(team.getId(), team.getName(), avg, (long) checkIns.size());
    }
}
