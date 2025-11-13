package com.example.mindtrack.team;

import com.example.mindtrack.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // Criar equipe (RH ou ADMIN)
    @PostMapping
    @PreAuthorize("hasAnyRole('RH','ADMIN')")
    public ResponseEntity<ApiResponse<TeamDTO>> create(@RequestBody @Valid CreateTeamDTO dto) {
        TeamDTO created = teamService.createTeam(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Equipe criada com sucesso", created));
    }

    // Listar equipes (RH ou ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('RH','ADMIN')")
    public ResponseEntity<ApiResponse<List<TeamDTO>>> listAll() {
        List<TeamDTO> teams = teamService.listAll();
        return ResponseEntity.ok(ApiResponse.success(teams));
    }

    @PostMapping("/{teamId}/members/{userId}")
    @PreAuthorize("hasAnyRole('RH','ADMIN')")
    public ResponseEntity<ApiResponse<TeamDTO>> assignUser(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long userId
    ) {
        TeamDTO dto = teamService.assignUserToTeam(teamId, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Usuário vinculado à equipe com sucesso", dto)
        );
    }

}
