package ru.vgerasimov.Lottery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vgerasimov.Lottery.dto.TicketDTO;
import ru.vgerasimov.Lottery.dto.TicketRequest;
import ru.vgerasimov.Lottery.entity.Draw;
import ru.vgerasimov.Lottery.entity.Ticket;
import ru.vgerasimov.Lottery.exception.DrawNotActiveException;
import ru.vgerasimov.Lottery.exception.DrawNotFoundException;
import ru.vgerasimov.Lottery.exception.InvalidTicketException;
import ru.vgerasimov.Lottery.repository.DrawRepository;
import ru.vgerasimov.Lottery.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final DrawRepository drawRepository;
    private final TicketRepository ticketRepository;

    public TicketService(DrawRepository drawRepository, TicketRepository ticketRepository) {
        this.drawRepository = drawRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketDTO createTicket(TicketRequest request) {
        // Validate ticket numbers
        validateTicketNumbers(request.getNumbers());

        Draw draw = drawRepository.findById(request.getDrawId())
                .orElseThrow(() -> new DrawNotFoundException(request.getDrawId()));

        if (!draw.isActive()) {
            throw new DrawNotActiveException(request.getDrawId());
        }

        Ticket ticket = new Ticket();
        ticket.setDraw(draw);
        ticket.setNumbers(request.getNumbers());
        ticket.setWinner(false); // Will be set when draw is closed

        ticket = ticketRepository.save(ticket);

        return convertToDTO(ticket);
    }

    public List<TicketDTO> getTicketsForDraw(Long drawId) {
        return ticketRepository.findByDrawId(drawId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validateTicketNumbers(List<Integer> numbers) {
        if (numbers == null || numbers.size() != 5) {
            throw new InvalidTicketException("Ticket must contain exactly 5 numbers");
        }

        if (numbers.stream().distinct().count() != 5) {
            throw new InvalidTicketException("Ticket numbers must be unique");
        }

        if (numbers.stream().anyMatch(n -> n < 1 || n > 36)) {
            throw new InvalidTicketException("Ticket numbers must be between 1 and 36");
        }
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setDrawId(ticket.getDraw().getId());
        dto.setNumbers(ticket.getNumbers());
        dto.setWinner(ticket.isWinner());
        return dto;
    }
}
