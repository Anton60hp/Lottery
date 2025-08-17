package ru.vgerasimov.Lottery.exception;

public class DrawNotClosedException extends RuntimeException {
    public DrawNotClosedException(Long drawId) {
        super("Draw is not closed with id: " + drawId);
    }
}
