package ru.vgerasimov.Lottery.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vgerasimov.Lottery.dto.DrawDTO;
import ru.vgerasimov.Lottery.entity.Draw;
import ru.vgerasimov.Lottery.exception.DrawNotActiveException;
import ru.vgerasimov.Lottery.exception.DrawNotFoundException;
import ru.vgerasimov.Lottery.repository.DrawRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DrawService {
    private final DrawRepository drawRepository;

    public DrawService(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }

    @Transactional
    public DrawDTO createDraw() {
        // Ensure only one active draw exists
        drawRepository.findByActiveTrue().ifPresent(draw -> {
            draw.setActive(false);
            drawRepository.save(draw);
        });

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

        return convertToDTO(drawRepository.save(draw));
    }

    public DrawDTO getDrawResults(Long drawId) {
        Draw draw = drawRepository.findById(drawId)
                .orElseThrow(() -> new DrawNotFoundException(drawId));
        return convertToDTO(draw);
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
}