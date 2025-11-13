package com.example.mindtrack.checkin;

import com.example.mindtrack.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "checkins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Column(length = 500)
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
