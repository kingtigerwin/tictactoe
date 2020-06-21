package com.fincrime.tictactoe.services;

import com.fincrime.tictactoe.dtos.GameGetDto;
import com.fincrime.tictactoe.dtos.GamePostDto;
import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.entities.Move;
import com.fincrime.tictactoe.constants.Status;
import com.fincrime.tictactoe.exceptions.GameNotFoundException;
import com.fincrime.tictactoe.exceptions.BadRequestException;
import com.fincrime.tictactoe.mappers.GameMapper;
import com.fincrime.tictactoe.mappers.MoveMapper;
import com.fincrime.tictactoe.repositories.GameRepository;
import com.fincrime.tictactoe.repositories.MoveRepository;
import com.fincrime.tictactoe.utils.GameUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final GameMapper gameMapper;
    private final MoveMapper moveMapper;
    private final GameUtils gameUtils;

    public GameGetDto createGame(GamePostDto gamePostDto) {
        Game game = gameRepository.save(gameMapper.toEntity(gamePostDto));
        return gameMapper.fromEntity(game);
    }

    /**
     * List all the Games which each game contains details such as game name, status etc.
     *
     * @return a list of game objects
     */
    public List<GameGetDto> listAll() {
        List<Game> gameList = gameRepository.findAll();
        return gameList.stream().map(game -> gameMapper.fromEntity(game)).collect(Collectors.toList());
    }

    /**
     * Find a game by given game name. If there is no name, exception will be thrown which would be captured
     * globally and return back as the a proper response json format.
     *
     * @param name  game name
     * @return the GameGetDto containing game details.
     */
    public GameGetDto findGameByName(String name) {
        Game game = gameRepository.findByName(name);
        if (game == null) {
            log.warn("Game with name:" + name + " not found");
            throw new GameNotFoundException("Game not found");
        }
        return gameMapper.fromEntity(gameRepository.findByName(name));
    }

    public GameGetDto findGameById(UUID id) {
        return gameMapper.fromEntity(findGameEntity(id));
    }

    @Transactional
    public GameGetDto performMove(UUID gameId, MovePostDto movePostDto) {
        if (!gameUtils.isMovePayloadValid(movePostDto)) {
            log.error("Invalid move payload while perform a new move, axis should be less than 3");
            throw new BadRequestException("Invalid move payload, axis should be within 3");
        }

        Game game = findGameEntity(gameId);
        if (!gameUtils.isPlayerValid(movePostDto, game)) {
            log.error("It should be opponent's turn to play");
            throw new BadRequestException("opponent's turn to play");
        }
        game.setLastPlayer(movePostDto.getPlayer());
        if (gameUtils.isWinner(movePostDto, game)) {
            game.setStatus(Status.FINISH);
        } else {
            game.setStatus(Status.STARTED);
        }

        Move move = moveMapper.toEntity(movePostDto);
        move.setGame(game);
        moveRepository.save(move);
        return gameMapper.fromEntity(gameRepository.save(game));
    }

    private Game findGameEntity(UUID id) {
        return gameRepository.findById(id).<GameNotFoundException>orElseThrow(() -> {
            log.warn("Game with id:" + id + " not found");
            throw new GameNotFoundException("Game not found");
        });
    }

}
