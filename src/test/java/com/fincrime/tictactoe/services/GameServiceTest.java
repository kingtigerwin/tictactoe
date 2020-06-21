package com.fincrime.tictactoe.services;

import com.fincrime.tictactoe.dtos.GameGetDto;
import com.fincrime.tictactoe.dtos.GamePostDto;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.constants.Status;
import com.fincrime.tictactoe.mappers.GameMapper;
import com.fincrime.tictactoe.repositories.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @Resource
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

    }

}
