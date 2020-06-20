package com.fincrime.tictactoe.repositories;

import com.fincrime.tictactoe.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface MoveRespository extends JpaRepository<Move, UUID> {

    Set<Move> findAllByGameId(UUID id);
}
