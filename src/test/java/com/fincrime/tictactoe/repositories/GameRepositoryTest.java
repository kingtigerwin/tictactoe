package com.fincrime.tictactoe.repositories;

import com.fincrime.tictactoe.TictactoeApplication;
import com.fincrime.tictactoe.entities.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TictactoeApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameRepositoryTest {
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void givenGameRepository_whenSaveAndRetrieveGame_thenOK() {
        Game game = buildGameObject("aaa","ready");
        Game returnedGame = gameRepository.save(game);
        Assertions.assertNotNull(returnedGame);
        Assertions.assertNotNull(returnedGame.getId());
        Assertions.assertEquals(returnedGame.getName(), "aaa");
        Assertions.assertEquals(returnedGame.getStatus(), "ready");
    }

    @Test
    public void givenGameRepository_whenSaveAndUpdateGame_thenOK() {
        Game game = buildGameObject("aaa", "ready");
        gameRepository.save(game);
        Game returnedGame = gameRepository.findByName("aaa");
        Assertions.assertNotNull(returnedGame);
        Assertions.assertEquals(returnedGame.getName(), "aaa");
        Assertions.assertEquals(returnedGame.getStatus(), "ready");
    }

    private Game buildGameObject(String name, String status) {
        Game game = new Game();
        game.setName(name);
        game.setStatus(status);
        return game;
    }
}
