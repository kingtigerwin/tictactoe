package com.fincrime.tictactoe.dtos;

import com.fincrime.tictactoe.enums.Player;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class MovePostDto {

    @ApiModelProperty(value = "The vertical axis of the move player performs")

    private int verticalAxis;
    @ApiModelProperty(value = "The horizontal axis of the move player performs")
    private int horizontalAxis;

    
    @ApiModelProperty(value = "Who performs the move. There are two types: NOUGHT & CROSS")
    private Player player;
}
