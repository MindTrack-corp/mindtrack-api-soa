package com.example.mindtrack.team;

import com.example.mindtrack.common.exception.BusinessException;
import com.example.mindtrack.user.User;
import com.example.mindtrack.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada"));
    }

    public TeamDTO createTeam(CreateTeamDTO dto) {
        boolean exists = teamRepository.findAll().stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(dto.name()));

        if (exists) {
            throw new BusinessException("Já existe uma equipe com esse nome");
        }

        Team team = new Team();
        team.setName(dto.name());
        Team saved = teamRepository.save(team);

        return toDTO(saved);
    }

    public List<TeamDTO> listAll() {
        return teamRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TeamDTO assignUserToTeam(Long teamId, Long userId) {
        Team team = findById(teamId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setTeam(team);
        userRepository.save(user);

        return toDTO(team);
    }

    private TeamDTO toDTO(Team team) {
        return new TeamDTO(team.getId(), team.getName());
    }
}
