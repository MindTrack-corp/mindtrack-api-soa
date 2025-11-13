package com.example.mindtrack.checkin;

import com.example.mindtrack.checkin.dto.CheckInResponseDTO;
import com.example.mindtrack.checkin.dto.CreateCheckInDTO;
import com.example.mindtrack.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<ApiResponse<CheckInResponseDTO>> create(@RequestBody @Valid CreateCheckInDTO dto) {
        CheckInResponseDTO created = checkInService.createTodayCheckIn(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Check-in criado com sucesso", created));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<ApiResponse<List<CheckInResponseDTO>>> myCheckins() {
        List<CheckInResponseDTO> list = checkInService.listMyCheckIns();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
}
