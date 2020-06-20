package com.fincrime.tictactoe.controllers;

import com.fincrime.tictactoe.dtos.GameGetDto;
import com.fincrime.tictactoe.dtos.GamePostDto;
import com.fincrime.tictactoe.dtos.MoveGetDto;
import com.fincrime.tictactoe.dtos.MovePostDto;
import com.fincrime.tictactoe.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameGetDto> createGame(@RequestBody GamePostDto gamePostDto) {
        GameGetDto gameGetDto = gameService.createGame(gamePostDto);
        return ResponseEntity.ok(gameGetDto);
    }

    @GetMapping
    public ResponseEntity<List<GameGetDto>> finAllGames() {
        return ResponseEntity.ok(gameService.listAll());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameGetDto> findGameById(@PathVariable UUID gameId) {
        return ResponseEntity.ok(gameService.findGameById(gameId));
    }

    @GetMapping("query")
    public ResponseEntity<GameGetDto> findGameByName(@RequestParam String gameName) {
        return ResponseEntity.ok(gameService.findGameByName(gameName));
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<GameGetDto> performMove(@PathVariable UUID gameId, @RequestBody MovePostDto movePostDto) {
        return ResponseEntity.ok(gameService.performMove(gameId, movePostDto));
    }

}