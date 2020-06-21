package com.fincrime.tictactoe.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GamePostDto {

    @ApiModelProperty(value = "The name of the Game")
    private String name;

    @ApiModelProperty(value = "The name of the status, There are 3 types:READY, STARTED, FINISHED")
    private String status;
}
