package com.fincrime.tictactoe.utils;

import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.entities.Move;
import com.fincrime.tictactoe.enums.Player;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GameUtils {
    public boolean isMovePayloadValid(MovePostDto movePostDto) {
        return movePostDto.getHorizontalAxis() <= 3 && movePostDto.getVerticalAxis() <= 3;
    }

    public boolean isPlayerValid(MovePostDto movePostDto, Game game) {
        Player lastPlayer = game.getLastPlayer();
        return movePostDto.getPlayer() != lastPlayer;
    }

    public boolean isWinner(MovePostDto movePostDto, Game game) {
        Set<Move> moveSet = game.getMoves();
        List<String> currentPlayerMoves = moveSet.stream()
                .filter((move -> move.getPlayer() == movePostDto.getPlayer()))
                .map(move -> move.getHorizontalAxis() + "" + move.getVerticalAxis())
                .collect(Collectors.toList());
        currentPlayerMoves.add(movePostDto.getHorizontalAxis() + "" + movePostDto.getVerticalAxis());
        List<String[]> criteriaList = new ArrayList<>();
        criteriaList.add(new String[]{"11", "21", "31"});
        criteriaList.add(new String[]{"12", "22", "32"});
        criteriaList.add(new String[]{"13", "23", "33"});
        criteriaList.add(new String[]{"11", "12", "13"});
        criteriaList.add(new String[]{"21", "22", "23"});
        criteriaList.add(new String[]{"31", "32", "33"});
        criteriaList.add(new String[]{"11", "22", "33"});
        criteriaList.add(new String[]{"31", "22", "13"});

        Optional<String[]> criteriaOption = criteriaList.stream().filter(criteria -> {
            List<String> subCriteriaList = Arrays.asList(criteria);
            return currentPlayerMoves.containsAll(subCriteriaList) ? true : false;
        }).findFirst();

        return criteriaOption.isPresent();
    }
}
