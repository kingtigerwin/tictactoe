package com.fincrime.tictactoe.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class GameGetDto {

    private UUID id;
    private String name;
    private String status;
    private String lastPlayer;
}
