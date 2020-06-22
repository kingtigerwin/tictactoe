package com.fincrime.tictactoe.services;

import com.fincrime.tictactoe.constants.Player;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
     * @param name The name of the game
     * @return the GameGetDto containing game details.
     */
    public GameGetDto findGameByName(String name) {
        Game game = gameRepository.findByName(name);
        if (game == null) {
            throw new GameNotFoundException("Game not found");
        }
        return gameMapper.fromEntity(game);
    }

    /**
     * Find a game by game Id.
     *
     * @param id the primary key of the game
     * @return GameGetDto object containing game details
     */
    public GameGetDto findGameById(UUID id) {
        return gameMapper.fromEntity(findGameEntity(id));
    }

    /**
     * Perform a move in a game and do the following validation:
     *  1. Horizontal axis and vertical axis should be not be large than 3.
     *  2. Game Id should be valid.
     *  3. Players should play game in tern.
     *  4. move can't be duplicated.
     *
     *  The initial status of the game is READY. then it will be STARTED if there is no winner. Otherwise
     *  it will be FINISH.
     *
     *  The attribute lastPlayer of the game records current player which is performing this move. It can
     *  be either NOUGHT or CROSS.
     *  If game is finished but still there is no winner, the game ended in a draw. the LastPlayer would be
     *  DRAW.
     *
     * @param gameId the primary key of the game
     * @param movePostDto the MovePostDto passed from client.
     * @return the updated GameGetDto object contains details such as status last player etc.
     */
    @Transactional
    public GameGetDto performMove(UUID gameId, MovePostDto movePostDto) {
        if (!gameUtils.isMovePayloadValid(movePostDto)) {
            throw new BadRequestException("Invalid move payload, axis should be within 3");
        }

        Game game = findGameEntity(gameId);
        if (!gameUtils.isPlayerValid(movePostDto, game)) {
            throw new BadRequestException("opponent's turn to play");
        }

        if (gameUtils.isDuplicatedAxis(movePostDto, game)) {
            throw new BadRequestException("This axis already exists");
        }
        updateStatusAndLastPlayerInGame(movePostDto, game);

        Move move = moveMapper.toEntity(movePostDto);
        move.setGame(game);
        moveRepository.save(move);
        return gameMapper.fromEntity(gameRepository.save(game));
    }

    private void updateStatusAndLastPlayerInGame(MovePostDto movePostDto, Game game) {
        game.setLastPlayer(movePostDto.getPlayer());
        if (gameUtils.isWinner(movePostDto, game)) {
            game.setStatus(Status.FINISH);
        } else {
            // The game ended in a draw when the size of moves is 9 and no winner.
            if (game.getMoves().size() == 9) {
                game.setLastPlayer(Player.DRAW);
            }
            game.setStatus(Status.STARTED);
        }
    }

    private Game findGameEntity(UUID id) {
        return gameRepository.findById(id).<GameNotFoundException>orElseThrow(() -> {
            throw new GameNotFoundException("Game not found");
        });
    }

}
