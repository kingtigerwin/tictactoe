package com.fincrime.tictactoe.utils;

import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.entities.Move;
import com.fincrime.tictactoe.enums.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        List<Move> currentPlayerMoves = moveSet.stream().filter((move -> move.getPlayer() == movePostDto.getPlayer())).collect(Collectors.toList());
        return false;

    }

    private boolean isHorizontalWinner(Set<Move> moveSet, List<Move> currentPlayerMoves) {
        Map<String, Integer> map = countDuplicatedNumber(currentPlayerMoves.stream().map(move -> move.getHorizontalAxis()).collect(Collectors.toList()));
        // TODO
        return false;
    }

    private <T> Map<T, Integer> countDuplicatedNumber(List<T> inputList) {
        Map<T, Integer> result = new HashMap<>();
        inputList.forEach(e -> result.put(e, result.getOrDefault(e, 0) + 1));
        return result;
    }
}
