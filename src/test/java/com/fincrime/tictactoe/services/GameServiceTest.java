package com.fincrime.tictactoe.services;

import com.fincrime.tictactoe.dtos.GameGetDto;
import com.fincrime.tictactoe.dtos.GamePostDto;
import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.constants.Status;
import com.fincrime.tictactoe.entities.Move;
import com.fincrime.tictactoe.exceptions.BadRequestException;
import com.fincrime.tictactoe.exceptions.GameNotFoundException;
import com.fincrime.tictactoe.mappers.GameMapper;
import com.fincrime.tictactoe.mappers.MoveMapper;
import com.fincrime.tictactoe.repositories.GameRepository;
import com.fincrime.tictactoe.repositories.MoveRepository;
import com.fincrime.tictactoe.utils.GameUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @Mock
    private MoveMapper moveMapper;

    @Mock
    private MoveRepository moveRepository;

    @Mock
    private GameUtils gameUtils;

    @InjectMocks
    private GameService gameService;

    @Test
    public void givenGameService_whenCreateNewGame_thenOK() {
        GamePostDto gamePostDto = new GamePostDto();
        gamePostDto.setName("game_name");
        gamePostDto.setStatus(Status.READY.name());

        when(gameRepository.save(any())).thenReturn(new Game());
        when(gameMapper.toEntity(any())).thenReturn(new Game());

        GameGetDto expectedResult = new GameGetDto();
        expectedResult.setName("mock_game");
        expectedResult.setStatus(Status.READY.name());
        when(gameMapper.fromEntity(any())).thenReturn(expectedResult);

        GameGetDto returnedResult = gameService.createGame(gamePostDto);
        verify(gameMapper, times(1)).toEntity(any(GamePostDto.class));
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        Assertions.assertEquals(returnedResult.getName(), expectedResult.getName());
        Assertions.assertEquals(returnedResult.getStatus(), expectedResult.getStatus());
    }

    @Test
    public void givenGameService_whenFindAllGames_thenOK() {
        List<Game> gameList = new ArrayList<>();
        gameList.add(new Game());
        when(gameRepository.findAll()).thenReturn(gameList);

        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setName("game_name");
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        List<GameGetDto> returnedGameList = gameService.listAll();
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        verify(gameRepository, times(1)).findAll();
        GameGetDto returnedGameGetDto = returnedGameList.get(0);
        Assertions.assertEquals(returnedGameGetDto.getStatus(), mockGameGetDto.getStatus());
        Assertions.assertEquals(returnedGameGetDto.getName(), mockGameGetDto.getName());
    }

    @Test
    public void givenGameService_whenFindOneGameByName_thenOK() {
        when(gameRepository.findByName(any())).thenReturn(new Game());

        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setName("game_name");
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        GameGetDto returnedGameGetDto = gameService.findGameByName("mock_name");
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        Assertions.assertEquals(returnedGameGetDto.getStatus(), mockGameGetDto.getStatus());
        Assertions.assertEquals(returnedGameGetDto.getName(), mockGameGetDto.getName());
    }

    @Test
    public void givenGameService_whenNoGameFoundByName_thenThrowException() {
        when(gameRepository.findByName(any())).thenReturn(null);
        Assertions.assertThrows(GameNotFoundException.class, ()-> {
            gameService.findGameByName("mock_name");
        });
    }

    @Test
    public void givenGameService_whenFindOneById_thenOK() {
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));
        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setName("game_name");
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);
        GameGetDto returnedGameGetDto = gameService.findGameById(UUID.randomUUID());
        Assertions.assertEquals(returnedGameGetDto.getStatus(), mockGameGetDto.getStatus());
        Assertions.assertEquals(returnedGameGetDto.getName(), mockGameGetDto.getName());
    }

    @Test
    public void givenGameService_whenNoGameFoundById_thenThrowException() {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(GameNotFoundException.class, ()-> {
            gameService.findGameById(UUID.randomUUID());
        });
    }

    @Test
    public void givenGameService_whenMovePayloadIsInvalid_thenThrowException() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(false);
        Assertions.assertThrows(BadRequestException.class, ()-> {
            gameService.performMove(UUID.randomUUID(), new MovePostDto());
        });
    }

    @Test
    public void givenGameService_whenMovePayloadIsValidAndPlayerInvalid_thenThrowException() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(false);
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));

        Assertions.assertThrows(BadRequestException.class, ()-> {
            gameService.performMove(UUID.randomUUID(), new MovePostDto());
        });
    }

    @Test
    public void givenGameService_whenMovePayloadAndPlayerAreBothValidAndDuplicatedAxis_thenThrowException() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(true);
        when(gameUtils.isDuplicatedAxis(any(),any())).thenReturn(true);
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));

        Assertions.assertThrows(BadRequestException.class, ()-> {
            gameService.performMove(UUID.randomUUID(), new MovePostDto());
        });
    }

    @Test void givenGameService_whenMovePayloadAndPlayerValidAndNoDuplicatedAxisAndGameStarted_thenOK() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(true);
        when(gameUtils.isDuplicatedAxis(any(),any())).thenReturn(false);
        when(gameUtils.isWinner(any(),any())).thenReturn(false);
        Game game = new Game();
        Set<Move> moves = new HashSet<>();
        game.setMoves(moves);
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(new Game());
        when(moveMapper.toEntity(any())).thenReturn(new Move());
        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setName("game_name");
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        gameService.performMove(UUID.randomUUID(), new MovePostDto());
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test void givenGameService_whenMovePayloadAndPlayerValidAndNoDuplicatedAxisAndGameFinished_thenOK() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(true);
        when(gameUtils.isDuplicatedAxis(any(),any())).thenReturn(false);
        when(gameUtils.isWinner(any(),any())).thenReturn(true);
        Game game = new Game();
        Set<Move> moves = new HashSet<>();
        game.setMoves(moves);
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(new Game());
        when(moveMapper.toEntity(any())).thenReturn(new Move());
        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setName("game_name");
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        gameService.performMove(UUID.randomUUID(), new MovePostDto());
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        verify(gameRepository, times(1)).save(any(Game.class));



    }




}
