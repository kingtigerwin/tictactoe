package com.fincrime.tictactoe.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameGetDto {

    @ApiModelProperty(value = "The primary key of the game")
    private UUID id;

    @ApiModelProperty(value = "The name of the Game")
    private String name;

    @ApiModelProperty(value = "The name of the status, There are 3 types:READY, STARTED, FINISHED")
    private String status;

    @ApiModelProperty(value = "There are 3 types: NOUGHT, DRAW, CROSS")
    private String lastPlayer;
}
