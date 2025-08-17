package ru.vgerasimov.Lottery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vgerasimov.Lottery.entity.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByDrawId(Long drawId);
}
