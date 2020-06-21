package com.fincrime.tictactoe.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicTacToeErrorDto {

    private final int errorCode;

    private final String message;
}
