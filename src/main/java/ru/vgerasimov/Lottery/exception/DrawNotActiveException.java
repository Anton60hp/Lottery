package ru.vgerasimov.Lottery.exception;

public class DrawNotActiveException extends RuntimeException {
    public DrawNotActiveException(Long drawId) {
        super("Draw is not active with id: " + drawId);
    }
}