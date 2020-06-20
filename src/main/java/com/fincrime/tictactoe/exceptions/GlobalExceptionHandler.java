package com.fincrime.tictactoe.exceptions;

import com.fincrime.tictactoe.dtos.TicTacToeErrorDto;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { Throwable.class })
    public ResponseEntity<TicTacToeErrorDto> handleAnyException(Throwable ex) {
        log.error("Intercepted an Throwable :", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new TicTacToeErrorDto(500, "Server error"));
    }

    @ExceptionHandler(value = {GameNotFoundException.class})
    public ResponseEntity<TicTacToeErrorDto> handleGameNotFoundException(GameNotFoundException ex) {
        log.warn("Game not found:", ex);
        return ResponseEntity.ok(new TicTacToeErrorDto(404, ex.getMessage()));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<TicTacToeErrorDto> handleBacRequestException(BadRequestException ex) {
        log.error("Invalid fields in request payload:", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TicTacToeErrorDto(400, ex.getMessage()));
    }
}
