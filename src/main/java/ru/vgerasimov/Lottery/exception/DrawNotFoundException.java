package ru.vgerasimov.Lottery.exception;

public class DrawNotFoundException extends RuntimeException {
    public DrawNotFoundException(Long drawId) {
        super("Draw not found with id: " + drawId);
    }
}