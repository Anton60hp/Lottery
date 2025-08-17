package ru.vgerasimov.Lottery.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketRequest {
    private List<Integer> numbers;
    private Long drawId;
}
