package ru.vgerasimov.Lottery.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Draw draw;

    @ElementCollection
    private List<Integer> numbers;

    private boolean winner;
}
