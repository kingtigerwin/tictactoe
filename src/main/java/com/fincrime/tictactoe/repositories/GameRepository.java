package com.fincrime.tictactoe.repositories;

import com.fincrime.tictactoe.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    Game findByName(String name);
}
