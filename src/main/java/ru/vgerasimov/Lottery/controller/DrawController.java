package ru.vgerasimov.Lottery.controller;

import org.springframework.web.bind.annotation.*;
import ru.vgerasimov.Lottery.dto.DrawDTO;
import ru.vgerasimov.Lottery.service.DrawService;

@RestController
@RequestMapping("/draws")
public class DrawController {
    private final DrawService drawService;

    public DrawController(DrawService drawService) {
        this.drawService = drawService;
    }

    @PostMapping
    public DrawDTO createDraw() {
        return drawService.createDraw();
    }

    @PostMapping("/{drawId}/close")
    public DrawDTO closeDraw(@PathVariable Long drawId) {
        return drawService.closeDraw(drawId);
    }

    @GetMapping("/{drawId}/results")
    public DrawDTO getDrawResults(@PathVariable Long drawId) {
        return drawService.getDrawResults(drawId);
    }
}
