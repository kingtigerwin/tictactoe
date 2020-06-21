package com.fincrime.tictactoe.dtos;

import com.fincrime.tictactoe.constants.Player;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class MovePostDto {

    @ApiModelProperty(value = "The vertical axis of the move player performs. The value should not be larger than 3")
    private int verticalAxis;

    @ApiModelProperty(value = "The horizontal axis of the move player performs. The value should not be larger than 3")
    private int horizontalAxis;
    
    @ApiModelProperty(value = "Who performs the move. There are two types: NOUGHT & CROSS")
    private Player player;
}
