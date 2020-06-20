package com.fincrime.tictactoe.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameNotFoundException extends RuntimeException {

    private final String message;
}
