package ru.vgerasimov.Lottery.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vgerasimov.Lottery.dto.TicketDTO;
import ru.vgerasimov.Lottery.dto.TicketRequest;
import ru.vgerasimov.Lottery.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TicketDTO createTicket(@RequestBody TicketRequest request) {
        return ticketService.createTicket(request);
    }

    @GetMapping("/draw/{drawId}")
    public List<TicketDTO> getTicketsForDraw(@PathVariable Long drawId) {
        return ticketService.getTicketsForDraw(drawId);
    }
}