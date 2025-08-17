package ru.vgerasimov.Lottery.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
public class Draw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    @ElementCollection
    private List<Integer> winningNumbers;
}
