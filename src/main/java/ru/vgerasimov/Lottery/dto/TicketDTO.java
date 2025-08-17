package ru.vgerasimov.Lottery.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketDTO {
    private Long id;
    private Long drawId;
    private List<Integer> numbers;
    private boolean winner;
}
