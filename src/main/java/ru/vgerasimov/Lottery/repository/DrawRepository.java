package ru.vgerasimov.Lottery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.vgerasimov.Lottery.entity.Draw;

import java.util.Optional;

public interface DrawRepository extends JpaRepository<Draw, Long> {
    Optional<Draw> findByActiveTrue();
}
