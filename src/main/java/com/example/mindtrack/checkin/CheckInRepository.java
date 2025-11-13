package com.example.mindtrack.checkin;

import com.example.mindtrack.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    boolean existsByUserAndDate(User user, LocalDate date);

    List<CheckIn> findByUserOrderByDateDesc(User user);

    @Query("SELECT c FROM CheckIn c WHERE c.user.team.id = :teamId AND c.date BETWEEN :start AND :end")
    List<CheckIn> findByTeamAndPeriod(
            @Param("teamId") Long teamId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
