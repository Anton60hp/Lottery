package ru.vgerasimov.Lottery.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vgerasimov.Lottery.dto.DrawDTO;
import ru.vgerasimov.Lottery.dto.DrawResultDTO;
import ru.vgerasimov.Lottery.dto.TicketDTO;
import ru.vgerasimov.Lottery.entity.Draw;
import ru.vgerasimov.Lottery.entity.Ticket;
import ru.vgerasimov.Lottery.exception.DrawNotActiveException;
import ru.vgerasimov.Lottery.exception.DrawNotClosedException;
import ru.vgerasimov.Lottery.exception.DrawNotFoundException;
import ru.vgerasimov.Lottery.repository.DrawRepository;
import ru.vgerasimov.Lottery.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrawService {
    private final DrawRepository drawRepository;
    private final TicketRepository ticketRepository;

    public DrawService(DrawRepository drawRepository, TicketRepository ticketRepository) {
        this.drawRepository = drawRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public DrawDTO createDraw() {
        // Ensure only one active draw exists
        drawRepository.findByActiveTrue().ifPresent(draw -> closeDraw(draw.getId()));

        Draw draw = new Draw();
        draw.setActive(true);
        draw.setCreatedAt(LocalDateTime.now());
        draw = drawRepository.save(draw);

        return convertToDTO(draw);
    }

    @Transactional
    public DrawDTO closeDraw(Long drawId) {
        Draw draw = drawRepository.findById(drawId)
                .orElseThrow(() -> new DrawNotFoundException(drawId));

        if (!draw.isActive()) {
            throw new DrawNotActiveException(drawId);
        }

        // Generate winning numbers (5 unique numbers between 1-36)
        List<Integer> winningNumbers = generateRandomNumbers();
        draw.setWinningNumbers(winningNumbers);
        draw.setActive(false);
        draw.setClosedAt(LocalDateTime.now());

        ticketRepository.findByDrawId(drawId).forEach(ticket -> {
            boolean isWinner = new HashSet<>(ticket.getNumbers())
                    .equals(new HashSet<>(winningNumbers));
            ticket.setWinner(isWinner);
            ticketRepository.save(ticket);
        });

        return convertToDTO(drawRepository.save(draw));
    }

    public DrawResultDTO getDrawResults(Long drawId) {

        Draw draw = drawRepository.findById(drawId)
                .orElseThrow(() -> new DrawNotFoundException(drawId));

        if (draw.isActive()) {
            throw new DrawNotClosedException(drawId);
        }

        List<TicketDTO> allTickets = ticketRepository.findByDrawId(drawId)
                .stream()
                .map(this::convertTicketToDTO)
                .toList();
        return new DrawResultDTO(
                draw.getWinningNumbers(),
                allTickets
        );
    }

    private List<Integer> generateRandomNumbers() {
        return new Random().ints(1, 37)
                .distinct()
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
    }

    private DrawDTO convertToDTO(Draw draw) {
        DrawDTO dto = new DrawDTO();
        dto.setId(draw.getId());
        dto.setActive(draw.isActive());
        dto.setCreatedAt(draw.getCreatedAt());
        dto.setClosedAt(draw.getClosedAt());
        dto.setWinningNumbers(draw.getWinningNumbers());
        return dto;
    }
    private TicketDTO convertTicketToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setDrawId(ticket.getDraw().getId());
        dto.setNumbers(ticket.getNumbers());
        dto.setWinner(ticket.isWinner());
        return dto;
    }
}