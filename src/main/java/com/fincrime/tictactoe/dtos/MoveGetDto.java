package com.fincrime.tictactoe.dtos;

import com.fincrime.tictactoe.enums.Player;
import lombok.Data;

import java.util.UUID;

@Data
public class MoveGetDto {
    private UUID id;
    private int verticalAxis;
    private int horizontalAxis;
    private Player player;
}
