package com.example.mindtrack.checkin;

import com.example.mindtrack.checkin.dto.CheckInResponseDTO;
import com.example.mindtrack.checkin.dto.CreateCheckInDTO;
import com.example.mindtrack.common.exception.BusinessException;
import com.example.mindtrack.user.User;
import com.example.mindtrack.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final UserService userService;

    public CheckInResponseDTO createTodayCheckIn(CreateCheckInDTO dto) {
        User user = userService.getCurrentUser();
        LocalDate today = LocalDate.now();

        boolean exists = checkInRepository.existsByUserAndDate(user, today);
        if (exists) {
            throw new BusinessException("Você já fez check-in hoje");
        }

        CheckIn c = new CheckIn();
        c.setUser(user);
        c.setDate(today);
        c.setMood(dto.mood());
        c.setNote(dto.note());

        checkInRepository.save(c);
        return toDTO(c);
    }

    public List<CheckInResponseDTO> listMyCheckIns() {
        User user = userService.getCurrentUser();
        return checkInRepository.findByUserOrderByDateDesc(user)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private CheckInResponseDTO toDTO(CheckIn c) {
        return new CheckInResponseDTO(c.getId(), c.getDate(), c.getMood(), c.getNote());
    }
}
