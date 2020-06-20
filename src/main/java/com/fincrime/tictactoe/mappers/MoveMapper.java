package com.fincrime.tictactoe.mappers;

import com.fincrime.tictactoe.dtos.MoveGetDto;
import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.entities.Move;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MoveMapper {

    Move toEntity(MovePostDto movePostDto);

    MoveGetDto fromEntity(Move move);
}
