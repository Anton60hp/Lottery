package ru.vgerasimov.Lottery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DrawResultDTO {
    private List<Integer> winningNumbers;
    private List<TicketDTO> allTickets;
}
