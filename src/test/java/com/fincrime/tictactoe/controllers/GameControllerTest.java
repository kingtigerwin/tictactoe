package com.fincrime.tictactoe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincrime.tictactoe.constants.Player;
import com.fincrime.tictactoe.constants.Status;
import com.fincrime.tictactoe.dtos.GameGetDto;
import com.fincrime.tictactoe.dtos.GamePostDto;
import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testCreateGame() throws Exception {
        GamePostDto gamePostDto = new GamePostDto();
        gamePostDto.setName("game_name");
        gamePostDto.setStatus(Status.READY.name());
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setStatus(Status.READY.name());
        gameGetDto.setName("game_name");
        BDDMockito.given(gameService.createGame(gamePostDto)).willReturn(gameGetDto);
        mockMvc.perform(post("/games")
                .content(objectMapper.writeValueAsString(gamePostDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("game_name"))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(Status.READY.name()));
    }

    @Test
    public void testFindAllGames() throws Exception {
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setStatus(Status.READY.name());
        gameGetDto.setName("game_name");
        List<GameGetDto> gameList = new ArrayList<>();
        gameList.add(gameGetDto);
        BDDMockito.given(gameService.listAll()).willReturn(gameList);
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0].name").value("game_name"))
                .andExpect(jsonPath("$.[0].status").value(Status.READY.name()));
    }

    @Test
    public void testFindNameById() throws Exception {
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setName("game_name");
        gameGetDto.setStatus(Status.READY.name());
        UUID id = UUID.randomUUID();
        BDDMockito.given(gameService.findGameById(id)).willReturn(gameGetDto);
        mockMvc.perform(get("/games/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.name").value("game_name"))
                .andExpect(jsonPath("$.status").value(Status.READY.name()));
    }


    @Test
    public void testFindNameByName() throws Exception {
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setName("game_name");
        gameGetDto.setStatus(Status.READY.name());
        BDDMockito.given(gameService.findGameByName("game_name")).willReturn(gameGetDto);
        mockMvc.perform(get("/games/query?gameName=game_name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.name").value("game_name"))
                .andExpect(jsonPath("$.status").value(Status.READY.name()));
    }

    @Test
    public void testPerformMove() throws Exception {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setVerticalAxis(1);
        movePostDto.setHorizontalAxis(2);
        movePostDto.setPlayer(Player.CROSS);
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setStatus(Status.READY.name());
        gameGetDto.setName("game_name");
        UUID gameId = UUID.randomUUID();
        BDDMockito.given(gameService.performMove(gameId, movePostDto)).willReturn(gameGetDto);
        mockMvc.perform(post("/games/" + gameId + "/moves")
                .content(objectMapper.writeValueAsString(movePostDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("game_name"))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(Status.READY.name()));
    }
}
