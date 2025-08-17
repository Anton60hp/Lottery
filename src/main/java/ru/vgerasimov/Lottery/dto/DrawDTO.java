package ru.vgerasimov.Lottery.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DrawDTO {
    private Long id;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private List<Integer> winningNumbers;
}
